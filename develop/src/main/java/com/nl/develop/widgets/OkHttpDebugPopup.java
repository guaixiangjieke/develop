package com.nl.develop.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.nl.develop.net.OkHttpInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by NiuLei on 2018/4/3.
 * 网络请求调试信息
 */

public class OkHttpDebugPopup extends PopupWindow {
    final ExpandableListView expandableListView;
    final List<Map<String, String>> groupData = new ArrayList<>();
    final List<List<Map<String, String>>> childData = new ArrayList<>();
    private final SimpleExpandableListAdapter simpleExpandableListAdapter;

    public OkHttpDebugPopup(Context context) {
        super(context);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        expandableListView = new ExpandableListView(context);
        simpleExpandableListAdapter = new SimpleExpandableListAdapter(context
                , groupData
                , android.R.layout.simple_expandable_list_item_1
                , new String[]{"url"}
                , new int[]{android.R.id.text1}
                , childData
                , android.R.layout.simple_list_item_1
                , new String[]{"text"}
                , new int[]{android.R.id.text1}) {
            @Override
            public View newChildView(boolean isLastChild, ViewGroup parent) {
                View view = super.newChildView(isLastChild, parent);
                if (view instanceof TextView) {
                    ((TextView) view).setMovementMethod(ScrollingMovementMethod
                            .getInstance());
                    ((TextView) view).setHorizontallyScrolling(true); // 不让超出屏幕的文本自动换行，使用滚动条
                    view.setFocusable(true);
                }
                return view;
            }
        };
        expandableListView.setAdapter(simpleExpandableListAdapter);
        expandableListView.setBackgroundColor(Color.WHITE);
        setContentView(expandableListView);
        setOutsideTouchable(true);
        setFocusable(true);

    }


    @Override
    public void showAsDropDown(View anchor) {
        LinkedBlockingQueue<ArrayMap<String, String>> requestQueue = OkHttpInterceptor.getRequestQueue();
        groupData.clear();
        childData.clear();
        Iterator<ArrayMap<String, String>> iterator = requestQueue.iterator();
        if (iterator != null && iterator.hasNext()) {
            while (iterator.hasNext()) {
                ArrayMap<String, String> peek = iterator.next();
                if (peek.isEmpty()) {
                    continue;
                }
                String url = peek.keyAt(0);
                String text = peek.valueAt(0);
                HashMap<String, String> groupItem = new HashMap<>();
                groupItem.put("url", url);
                groupData.add(groupItem);
                List<Map<String, String>> childListItem = new ArrayList<>();
                HashMap<String, String> childItem = new HashMap<>();
                childItem.put("text", text);
                childListItem.add(childItem);
                childData.add(childListItem);
            }
        }
        simpleExpandableListAdapter.notifyDataSetChanged();
        super.showAsDropDown(anchor);
    }
}
