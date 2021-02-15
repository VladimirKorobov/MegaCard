package com.mega.megacards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EditDialog  extends AlertDialog.Builder {
    Context mContext;
    public EditDialog(Context context) {
        super(context);
        mContext = context;
    }

    TableRow initRow(TableLayout layout, String name, float weight, float height)   {
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

    EditText addtext(TableLayout layout, String name, String value, float weight, int textType) {
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

}
