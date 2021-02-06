package com.mega.megacards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditCardDialog extends AlertDialog.Builder{
    Context mContext;
    List<EditText> list;
    ArrayList<CardEditAdapter.EditorItem> items = new ArrayList<CardEditAdapter.EditorItem>();
    public EditCardDialog(Context context) {
        super(context);
        mContext = context;
    }

    public void show(MainActivity.thumbHolder thumbHolder, ThumbMap map) {
        TableLayout tableLayout = new TableLayout(mContext);

        tableLayout = new TableLayout(mContext);
        list = new ArrayList<EditText>();
        float[] weights = new float[]{0.25f};
        int[] textTypes = new int[] {InputType.TYPE_CLASS_TEXT};
        list.add(addtext(tableLayout, "Title", thumbHolder.title, weights[0], textTypes[0]));
        Object[] keys = map.keySet().toArray();
        int selectedIndex = -1;
        for(int i = 0; i < keys.length; i ++) {
            CardEditAdapter.EditorItem item = new CardEditAdapter.EditorItem();
            item.tab = (String)keys[i];
            if(item.tab.equals(thumbHolder.tab)) {
                item.color = ((MainActivity)mContext).getCardSelection();
            }
            else {
                item.color = Color.TRANSPARENT;
            }
            items.add(item);
        }

        addListView(tableLayout, "Tab", items, selectedIndex, weights[0]);

        this.setView(tableLayout);
        this.show();
    }

    private TableRow initRow(TableLayout layout, String name, float weight, float height)   {
        TableRow tableRow = new TableRow(mContext);
        tableRow.setBackgroundColor(Color.BLACK);

        // Create row with black paddings
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1f);
        tableRow.setPadding(0, 0, 0, 2);
        tableRow.setLayoutParams(params);

        // Create text view for field name
        TextView textView = new TextView(mContext);
        textView.setText(name);
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30f);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, height / 30.0f);
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(Color.GRAY);
        textView.setPadding(5, 0, 5, 2);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                weight);
        params.setMargins(0,0, 2, 0);
        textView.setLayoutParams(params);
        tableRow.addView(textView);
        layout.addView(tableRow);
        return tableRow;
    }

    private EditText addtext(TableLayout layout, String name, String value, float weight, int textType) {
        float height = ((Activity)mContext).getWindow().getDecorView().getHeight();
        TableRow tableRow = initRow(layout, name, weight, height);
        // Create text edit for field value
        EditText editText = new EditText(mContext);
        editText.setText(value);
        editText.setPadding(50, (int)(editText.getTextSize() / 2), 5, 2);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, height / 30.0f);
        editText.setBackgroundColor(Color.WHITE);
        editText.setTextColor(Color.BLACK);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, height / 30.0f);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setInputType(textType);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.f - weight);

        editText.setLayoutParams(params);

        tableRow.addView(editText);
        return editText;
    }
    private ListView addListView(TableLayout layout, String name, ArrayList<CardEditAdapter.EditorItem> items, final int selectedItem, float weight) {
        final float height = ((Activity)mContext).getWindow().getDecorView().getHeight();
        TableRow tableRow = initRow(layout, name, weight, height);
        // Create List view
        final ListView listView = new ListView(mContext);
        final CardEditAdapter adapter = new CardEditAdapter(mContext, items);

        listView.setAdapter(adapter);

        listView.setBackgroundColor(Color.WHITE);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                (int)(height / 30.0f * 6),
                1.f - weight);

        listView.setLayoutParams(params);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                for (int j = 0; j < adapter.getCount(); j++)
                    ((CardEditAdapter.EditorItem)adapter.getItem(j)).color = Color.TRANSPARENT;

                // change the background color of the selected element
                ((CardEditAdapter.EditorItem)adapter.getItem(position)).color =
                        ((MainActivity)mContext).getCardSelection();
                adapter.notifyDataSetChanged();
            }
        });

        tableRow.addView(listView);

        return listView;
    }

    public String getTab() {
        for(CardEditAdapter.EditorItem item : items) {
            if(item.color == ((MainActivity)mContext).getCardSelection()) {
                return item.tab;
            }
        }
        return "";
    }

    public String getTitle() {
        return list.get(0).getText().toString();
    }
}
