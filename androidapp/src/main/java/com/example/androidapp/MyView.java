package com.example.androidapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;


public class MyView extends View {

    private int x;
    private int y;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        x = display.widthPixels;
        y = display.heightPixels;

        setLayoutParams(new ViewGroup.LayoutParams(200, 200));
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(60, 234, 100));
        paint.setStrokeWidth(10);
        canvas.drawRect(30, 30, x - 30, y - 30, paint);
    }

}
