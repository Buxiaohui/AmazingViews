package com.example.bxh.viewsapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by bxh on 5/17/17.
 */

public class DefineView extends View {
    private static final String TAG = "DefineView";
    private static int MODE = 0;
    private PathMeasure pathMeasure;
    private Path path;
    private float value;
    private Paint paint;
    private Paint paint1;
    private Bitmap bitmap;
    private ValueAnimator valueAnimator = null;
    private HandlerThread handlerThread;
    private Handler handler;

    public DefineView(Context context) {
        super(context);
    }


    public DefineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initBgThread() {
        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()) {
            float num = 0f;
            float maxNum = 1000f;
            long delayMs = 5;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                num += 5.0f;
                if (num > maxNum) {
                    num = 0f;
                }
                value = num / maxNum;
                postInvalidate();
                sendEmptyMessageDelayed(0, delayMs);
            }
        };
        handler.sendEmptyMessage(0);
    }

    private void initRestartMode() {
        valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(500);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.d(TAG, "val=" + value);
                setVal(value);
            }
        });
        valueAnimator.start();
    }



    private void stopRestartMode() {
        if (valueAnimator != null && valueAnimator.isStarted() && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (MODE == ValueAnimator.RESTART) {
            initRestartMode();
        } else {
            initBgThread();
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (MODE == ValueAnimator.RESTART) {
            stopRestartMode();
        } else {
            handler.removeCallbacksAndMessages(null);
            handlerThread.quit();
            handler = null;
            handlerThread = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        int centerX = 500;
        int centerY = 800;
        int radius = 200;
        path.addCircle(centerX, centerY, radius, Path.Direction.CW);//顺时针，逆时针
        canvas.drawPath(path, paint);
        pathMeasure = new PathMeasure(path, false);
        float[] pos = new float[2];
        float[] tan = new float[2];
        pathMeasure.getPosTan(pathMeasure.getLength() * value, pos, tan);
        float degress = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix1 = new Matrix();
        pathMeasure.getMatrix(pathMeasure.getLength() * value, matrix1, PathMeasure.POSITION_MATRIX_FLAG);
        matrix1.postTranslate(-w / 2, -h / 2);
        matrix1.postRotate(degress, pos[0], pos[1]);
        canvas.drawBitmap(bitmap, matrix1, null);
    }

    public void setVal(float val) {
        this.value = val;
        invalidate();
    }

    private void init() {
        if (paint == null) {
            paint = new Paint();
        }
        if (paint1 == null) {
            paint1 = new Paint();
        }
        path = new Path();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint1.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint1.setStrokeWidth(5);
    }
}
