package com.mega.megacards;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class EditTabDialog  extends EditDialog{
    Context mContext;
    ThumbTable thumbTable;
    List<EditText> list;

    public EditTabDialog(Context context, ThumbTable thumbTable)  {
        super(context);
        mContext = context;
        this.thumbTable = thumbTable;
    }

    public void showNew() {
        showRename("New tab");
    }

    public void showRename(String tab) {
        TableLayout tableLayout = new TableLayout(mContext);
        list = new ArrayList<EditText>();
        float[] weights = new float[]{0.25f};
        int[] textTypes = new int[] {InputType.TYPE_CLASS_TEXT};
        list.add(addtext(tableLayout, "Tab name", tab, weights[0], textTypes[0]));

        this.setView(tableLayout);
        this.show();
    }

    public String getValue() {
        return list.get(0).getText().toString();
    }
}
