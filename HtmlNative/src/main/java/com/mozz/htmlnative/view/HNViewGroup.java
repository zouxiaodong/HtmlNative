package com.mozz.htmlnative.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Yang Tao, 17/3/8.
 */

public final class HNViewGroup extends FrameLayout {

    private final Map<String, View> mViewWithId = new ArrayMap<>();

    private static final String TAG = HNViewGroup.class.getSimpleName();

    @NonNull
    private List<WebView> mWebViewList = new LinkedList<>();

    public HNViewGroup(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (child instanceof WebView) {
            mWebViewList.add((WebView) child);
        }
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if (child instanceof WebView) {
            mWebViewList.remove(child);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        Log.d(TAG, "onDetachedFromWindow" + mWebViewList.toString());

        List<WebView> tempWebViewList = mWebViewList;
        mWebViewList.clear();

        for (WebView webView : tempWebViewList) {
            if (webView != null) {
                webView.destroy();
            }
        }
    }

    public View findViewById(@NonNull String id) {
        return mViewWithId.get(id);
    }


    public View putViewWithId(String id, View view) {
        View before = mViewWithId.put(id, view);
        if (before != null) {
            Log.w(TAG, "Duplicated id " + id + ", before is " + before + ", current is " + view);
        }
        return before;
    }

    public String allIdTag() {
        return mViewWithId.toString();
    }

    public boolean containsView(String id) {
        return mViewWithId.containsKey(id);
    }


}
