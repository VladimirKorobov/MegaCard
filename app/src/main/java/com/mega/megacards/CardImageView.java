package com.mega.megacards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class CardImageView extends ImageView {

    float prevRadius = -1;
    private static final int INVALID_POINTER_ID = -1;
    // Active pointerID
    int mPointer0ID = INVALID_POINTER_ID;
    // Second pointerID
    int mPointer1ID = INVALID_POINTER_ID;
    float moveDeltaX;
    float moveDeltaY;
    float prevX;
    float prevY;
    boolean pointer0Down = false;
    boolean pointer1Down = false;

    public CardImageView(Context context) {
        super(context);
    }

    private void TouchDownProcessing(float x, float y) {
        float scaleX = this.getScaleX();
        if(pointer0Down == true) {
            float xPos = this.getTranslationX();
            float yPos = this.getTranslationY();
            float deltaX = x + moveDeltaX - prevX;
            float deltaY = y + moveDeltaY - prevY;

            if(this.getRotation() != 0) {
                yPos += deltaX * scaleX;
                xPos -= deltaY * scaleX;
            }
            else {
                xPos += deltaX * scaleX;
                yPos += deltaY * scaleX;
            }

            moveDeltaX = deltaX;
            moveDeltaY = deltaY;

            this.setTranslationX(xPos);
            this.setTranslationY(yPos);
        }
        else {
            moveDeltaX = 0;
            moveDeltaY = 0;
        }
        prevX = x;
        prevY = y;
    }

    private void TouchScaleProcessing(float x0, float y0, float x1, float y1) {
        float r1 = (float)Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));

        float scaleX = this.getScaleX();
        r1 *= scaleX;

        if(pointer1Down == true) {

            float scale = scaleX * (1 + (r1 - prevRadius) / 1000f);

            if(scale >= 0.2 && scale <= 10) {
                this.setScaleX(scale);
                this.setScaleY(scale);
            }
        }

        prevRadius = r1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent m) {
        float x0 = m.getX();
        float y0 = m.getY();

        switch (m.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mPointer0ID = m.getPointerId(0);
                pointer0Down = false;
                this.TouchDownProcessing(x0, y0);
                pointer0Down = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if(mPointer1ID == INVALID_POINTER_ID)
                {
                    mPointer1ID = m.getPointerId(m.getActionIndex());
                    pointer0Down = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mPointer0ID != INVALID_POINTER_ID)
                {
                    if(mPointer1ID != INVALID_POINTER_ID)
                    {
                        int index1 = m.findPointerIndex(mPointer1ID);
                        if(index1 >= 0)
                        {
                            float x1 = m.getX(index1);
                            float y1 = m.getY(index1);
                            this.TouchScaleProcessing(x0, y0, x1, y1);
                            pointer1Down = true;
                        }
                    }
                    else {
                        this.TouchDownProcessing(x0, y0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mPointer0ID = INVALID_POINTER_ID;
                pointer0Down = false;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mPointer1ID = INVALID_POINTER_ID;
                pointer1Down = false;
                break;
        }
        return true;
    }
}
