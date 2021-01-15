package com.mega.megacards;

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
    @Override
    public void onCreate(Bundle savedInstanceSrate) {
        super.onCreate(savedInstanceSrate);
        if(getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceSrate) {
        GridView view = (GridView)(inflater.inflate(R.layout.activity_main, container, false));

        View view = inflater.inflate(R.layout.tab_running, container, false);
        listView = (ListView) view;
        UpdateView();
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String)listView.getItemAtPosition(position);


            }
        });
        return view;
    }

}
