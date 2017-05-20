package com.example.androidapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;


public class MyView extends View {

    private final int screenWidth;
    private final int indentSize = 20;
    private int cellSize;
    private int spaceSize;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        screenWidth = display.widthPixels;

//        setLayoutParams(new ViewGroup.LayoutParams(200, 200));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(60, 234, 100));
        paint.setStrokeWidth(10);
        canvas.drawRect(indentSize, 0, screenWidth - indentSize, screenWidth - indentSize, paint);

        Paint cellPaint = new Paint();
        cellPaint.setStyle(Paint.Style.FILL);
        Drawable d = getResources().getDrawable(R.drawable.kitten, getContext().getTheme());
        d.setBounds(40, 100, 1000, 600);
        d.draw(canvas);
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), 10, 10, cellPaint);

    }

}
