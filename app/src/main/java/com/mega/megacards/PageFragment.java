package com.mega.megacards;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private Context mContext;
    GridView gridView;
    ThumbMap thumbMap;
    private int selectedPosition = -1;
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if(position == selectedPosition) {
                    setItemColor(position, ((MainActivity)mContext).getBackground());
                    selectedPosition = -1;
                    ((imageAdapter)gridView.getAdapter()).notifyDataSetChanged();
                }
                else {
                    Intent intent = new Intent((MainActivity)mContext, ViewActivity.class);
                    MainActivity.thumbHolder holder = (MainActivity.thumbHolder) gridView.getAdapter().getItem(position);

                    intent.putExtra("fileName", holder.fileName);
                    intent.putExtra("Title", holder.title);
                    startActivity(intent);
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView parent, View view,
                                           int position, long id) {
                setItemColor(selectedPosition, ((MainActivity)mContext).getBackground());
                setItemColor(position, ((MainActivity)mContext).getSelection());
                selectedPosition = position;
                ((imageAdapter)gridView.getAdapter()).notifyDataSetChanged();
                return true;
            }
        });
        UpdateView();
        return view;
    }

    public void setItemColor(int position, int color) {
        Object[] keys = thumbMap.keySet().toArray();
        String key = (String)keys[mPage];
        ArrayList<MainActivity.thumbHolder> list = thumbMap.get(key);

        if(position >= 0 && position <= list.size()) {
            list.get(position).bkColor = color;
            /*
            int firstVisible = gridView.getFirstVisiblePosition();
            View view = gridView.getChildAt(position - firstVisible);
            ImageView imageView;
            imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setBackgroundColor(color);

             */
        }
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
