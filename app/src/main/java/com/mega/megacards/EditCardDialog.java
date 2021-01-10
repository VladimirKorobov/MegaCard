package com.mega.megacards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

    public void show(MainActivity.thumbHolder thumbHolder) {
        TableLayout tableLayout = new TableLayout(mContext);

        tableLayout = new TableLayout(mContext);
        list = new ArrayList<EditText>();
        float[] weights = new float[]{0.25f};
        int[] textTypes = new int[] {InputType.TYPE_CLASS_TEXT};
        list.add(addtext(tableLayout, "Title", thumbHolder.title, weights[0], textTypes[0]));

        this.setView(tableLayout);
        this.show();
    }

    List<EditText> getTextEdits() {
        return list;
    }

     private EditText addtext(TableLayout layout, String name, String value, float weight, int textType) {
        TableRow tableRow = new TableRow(mContext);
        tableRow.setBackgroundColor(Color.BLACK);

        // Create row with black paddings
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1f);
        tableRow.setPadding(0, 0, 0, 2);
        tableRow.setLayoutParams(params);

        float height = ((Activity)mContext).getWindow().getDecorView().getHeight();

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

        // Create text edit for field value
        EditText editText = new EditText(mContext);
        editText.setText(value);
        editText.setPadding(5, (int)(editText.getTextSize() / 2), 5, 2);
        //editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30f);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, height / 30.0f);
        editText.setBackgroundColor(Color.WHITE);
        editText.setTextColor(Color.BLACK);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setInputType(textType);

        params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.f - weight);

        editText.setLayoutParams(params);

        tableRow.addView(editText);


        layout.addView(tableRow);

        return editText;
    }
}
