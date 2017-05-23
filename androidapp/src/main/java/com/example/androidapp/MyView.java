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

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.Owner;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.returningClasses.Cell;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.returningClasses.Field;


public class MyView extends View {

    private final int screenWidth;
    private Quoridor game;
    private Field field;

    private int indentSize;
    private int pixelFieldSize;
    private int cellSize;
    private int spaceSize;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics display = this.getResources().getDisplayMetrics();
        screenWidth = display.widthPixels;
        setLayoutParams(new ViewGroup.LayoutParams(200, 200));
    }

    public void setGame(Quoridor game) {
        this.game = game;
        field = game.getField();

        indentSize = 20; // расстояние от границы поля до краев экрана
        pixelFieldSize = screenWidth - 2 * indentSize;

        int cellAndSpace = pixelFieldSize / field.getSize();

        spaceSize = cellAndSpace / 5;

        pixelFieldSize += spaceSize;
        cellAndSpace = pixelFieldSize / field.getSize();
        indentSize += pixelFieldSize % field.getSize() / 2;

        spaceSize = cellAndSpace / 5;
        cellSize = cellAndSpace - spaceSize;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(screenWidth - indentSize, pixelFieldSize - spaceSize);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Drawable d = getResources().getDrawable(R.drawable.kitten, getContext().getTheme());
//        d.setBounds(40, 100, 1000, 600);
//        d.draw(canvas);
        drawCells(canvas);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), 10, 10, cellPaint);

    }

    private void drawCells(Canvas canvas) {

        Paint paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(0, 220, 160));
        paint.setStrokeWidth(5);
        canvas.drawRect(indentSize, 2, screenWidth - indentSize, pixelFieldSize - spaceSize, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(0, 220, 160));
        paint.setStrokeWidth(1);

        for (int i = 0; i < field.getSize(); i++ ) {
            for (int j = 0; j < field.getSize(); j++) {

                canvas.drawRect(i * (cellSize + spaceSize) + indentSize,
                        j * (cellSize + spaceSize),
                        i * (cellSize + spaceSize) + indentSize + cellSize,
                        j * (cellSize + spaceSize) + cellSize, paint);
            }
        }

        for (int i = 0; i < field.getRealSize(); i++) {
            for (int j = 0; j < field.getRealSize(); j++) {
                Cell cell = field.getCell(i, j);
                switch (cell.getType()) {
                    case MARKER: {
                        if (cell.getOwner() == Owner.TOP) {
                            paint.setColor(Color.rgb(20, 50, 255));
                            canvas.drawOval(j / 2 * (cellSize + spaceSize) + spaceSize + cellSize / 8 ,
                                    i / 2 * (cellSize + spaceSize) + cellSize / 8,
                                    j / 2 * (cellSize + spaceSize) + cellSize + spaceSize - cellSize / 8,
                                    i / 2 * (cellSize + spaceSize) + cellSize - cellSize / 8, paint);
                        } else if (cell.getOwner() == Owner.BOTTOM) {
                            paint.setColor(Color.rgb(255, 50, 20));
                            canvas.drawOval(j / 2 * (cellSize + spaceSize) + spaceSize + cellSize / 8 ,
                                    i / 2 * (cellSize + spaceSize) + cellSize / 8,
                                    j / 2 * (cellSize + spaceSize) + cellSize + spaceSize - cellSize / 8,
                                    i / 2 * (cellSize + spaceSize) + cellSize - cellSize / 8, paint);
                        } else if (cell.getOwner() == Owner.FOX) {
                            paint.setColor(Color.rgb(255, 77, 0));
                            canvas.drawOval(j / 2 * (cellSize + spaceSize) + spaceSize + cellSize / 8 ,
                                    i / 2 * (cellSize + spaceSize) + cellSize / 8,
                                    j / 2 * (cellSize + spaceSize) + cellSize + spaceSize - cellSize / 8,
                                    i / 2 * (cellSize + spaceSize) + cellSize - cellSize / 8, paint);
                        }
                        break;
                    }
                    case BARRIER: {
                        paint.setColor(Color.rgb(140, 90, 90));
                        if (i % 2 == 1 && j % 2 == 1)
                            canvas.drawRect((j + 1) * (cellSize + spaceSize) / 2 - spaceSize + indentSize,
                                    (i + 1) * (cellSize + spaceSize) / 2 - spaceSize,
                                    (j + 1) * (cellSize + spaceSize) / 2 + indentSize,
                                    (i + 1) * (cellSize + spaceSize) / 2, paint);
                        if (i % 2 == 1 && j % 2 == 0)
                            canvas.drawRect(j * (cellSize + spaceSize) / 2 + indentSize,
                                    (i + 1) * (cellSize + spaceSize) / 2 - spaceSize,
                                    j * (cellSize + spaceSize) / 2 + cellSize + indentSize,
                                    (i + 1) * (cellSize + spaceSize) / 2, paint);
                        if (i % 2 == 0 && j % 2 == 1)
                            canvas.drawRect((j + 1) * (cellSize + spaceSize) / 2 - spaceSize + indentSize,
                                    i * (cellSize + spaceSize) / 2,
                                    (j + 1) * (cellSize + spaceSize) / 2 + indentSize,
                                    i * (cellSize + spaceSize) / 2 + cellSize, paint);
                        break;
                    }
                }
            }
        }
    }

}
