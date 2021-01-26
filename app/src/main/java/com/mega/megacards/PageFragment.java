package com.mega.megacards;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private Context mContext;
    GridView gridView;
    ThumbMap thumbMap;
    @Override
    public void onCreate(Bundle savedInstanceSrate) {
        super.onCreate(savedInstanceSrate);
        if(getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    public static PageFragment newInstance(Context context, int page, ThumbMap thumbMap) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.thumbMap = thumbMap;
        fragment.mContext = context;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceSrate) {
        View view = inflater.inflate(R.layout.grid_view, container, false);
        gridView = (GridView)view;
        UpdateView();
        return view;
    }

    public void UpdateView() {
        Object[] keys = thumbMap.keySet().toArray();
        String key = (String)keys[mPage];
        gridView.setAdapter(new imageAdapter(mContext, thumbMap.get(key)));

        /*
        String[] processes = getActiveApps(mContext, mPage == 0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_list_item_1, processes);

        listView.setAdapter(adapter);

         */
    }
}
