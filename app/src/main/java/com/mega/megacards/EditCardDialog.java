package com.mega.megacards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        String[] keysS = new String[keys.length];
        int selectedIndex = -1;
        for(int i = 0; i < keys.length; i ++) {
            keysS[i] = (String)keys[i];
            if(keysS[i] == thumbHolder.tab) {
                selectedIndex = i;
            }
        }
        addListView(tableLayout, "Tab", keysS, selectedIndex, weights[0]);

        this.setView(tableLayout);
        this.show();
    }

    List<EditText> getTextEdits() {
        return list;
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
        //editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30f);
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
    private ListView addListView(TableLayout layout, String name, String[] stringArray, final int selectedItem, float weight) {
        final float height = ((Activity)mContext).getWindow().getDecorView().getHeight();
        TableRow tableRow = initRow(layout, name, weight, height);
        // Create List view
        ListView listView = new ListView(mContext);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.BLUE);
                if(position == selectedItem) {
                    tv.setBackgroundColor(Color.LTGRAY);
                    //tv.setSelected(true);
                }
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, height / 30.0f);
                tv.setPadding(50, 0, 5, 2);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                // Generate ListView Item using TextView
                return view;
            }
        };

        listView.setAdapter(modeAdapter);

        listView.setBackgroundColor(Color.WHITE);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                (int)(height / 30.0f * 3),
                1.f - weight);

        listView.setLayoutParams(params);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                for (int j = 0; j < parent.getChildCount(); j++)
                    parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                // change the background color of the selected element
                v.setBackgroundColor(Color.LTGRAY);
                // по позиции получаем выбранный элемент
            }
        });

        tableRow.addView(listView);

        return listView;
    }
}
