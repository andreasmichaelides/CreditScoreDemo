package com.snazzy.creditscoredemo.creditscoreviewer.presentation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.snazzy.creditscoredemo.R;

public class CreditScoreProgressBar extends View {

    private float outerArcSize;
    private float innerOutlineArcSize;
    private float innerArcPadding;
    private int viewWidth;
    private int viewHeight;

    private final float startAngle = -90;
    private float sweepAngle = 0;
    private float maxSweepAngle = 360;
    private int animationDuration = 400;
    private int maxProgress = 100;

    private Paint innerArcPaint;
    private Paint outerArcPaint;
    private Paint outlineArcPaint;

    final float gradientRotationAngle = -91;
    int[] colors;
    private int progress = 0;
    private int xCentre;
    private int yCentre;
    Shader gradient;
    int outerArcDiameter;
    RectF outerArcOval;
    RectF innerArcOval;

    public CreditScoreProgressBar(Context context) {
        this(context, null);
        init();
    }

    public CreditScoreProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public CreditScoreProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        float innerArcOutlineSize = getResources().getDimension(R.dimen.donut_inner_arc_outline_size);
        outerArcSize = getResources().getDimension(R.dimen.donut_outer_size);
        innerOutlineArcSize = getResources().getDimension(R.dimen.donut_inner_arc_size);
        innerArcPadding = getResources().getDimension(R.dimen.donut_inner_padding);

        int startColor = ContextCompat.getColor(getContext(), R.color.progress_dark);
        int endColor = ContextCompat.getColor(getContext(), R.color.progress_light);
        colors = new int[]{startColor, endColor};

        outerArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerArcPaint.setColor(Color.BLACK);
        outerArcPaint.setStrokeWidth(outerArcSize);
        outerArcPaint.setAntiAlias(true);
        outerArcPaint.setStrokeCap(Paint.Cap.ROUND);
        outerArcPaint.setStyle(Paint.Style.STROKE);

        outlineArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlineArcPaint.setAntiAlias(true);
        outlineArcPaint.setStyle(Paint.Style.STROKE);
        outlineArcPaint.setColor(Color.BLACK);
        outlineArcPaint.setStrokeCap(Paint.Cap.ROUND);
        outlineArcPaint.setStrokeWidth(innerOutlineArcSize);

        innerArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerArcPaint.setAntiAlias(true);
        innerArcPaint.setStyle(Paint.Style.STROKE);
        innerArcPaint.setStrokeCap(Paint.Cap.ROUND);
        innerArcPaint.setStrokeWidth(innerOutlineArcSize - innerArcOutlineSize);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        viewWidth = width;
        viewHeight = height;
        xCentre = viewWidth / 2;
        yCentre = viewHeight / 2;
        initInnerArcGradient();
        initOuterArcVariables();
        initInnerArcVariables();
    }

    private void initInnerArcGradient() {
        float[] positions = {0.0f, ((float) progress) / 100f};
        gradient = new SweepGradient(xCentre, yCentre, colors , positions);
        Matrix gradientMatrix = new Matrix();
        gradientMatrix.preRotate(gradientRotationAngle, xCentre, yCentre);
        gradient.setLocalMatrix(gradientMatrix);

        innerArcPaint.setShader(gradient);
    }

    private void initOuterArcVariables() {
        outerArcDiameter = Math.min(viewWidth, viewHeight);
        int halfDiameter = outerArcDiameter / 2;
        float left = xCentre - halfDiameter + outerArcSize;
        float top = yCentre - halfDiameter + outerArcSize;
        float right = xCentre + halfDiameter - outerArcSize;
        float bottom = yCentre + halfDiameter - outerArcSize;

        outerArcOval = new RectF(left, top, right, bottom);
    }

    private void initInnerArcVariables() {
        int innerArcDiameter = Math.min(viewWidth, viewHeight);
        int halfDiameter = innerArcDiameter / 2;
        float left = xCentre - halfDiameter + innerOutlineArcSize + (int) innerArcPadding;
        float top = yCentre - halfDiameter + innerOutlineArcSize + (int) innerArcPadding;
        float right = xCentre + halfDiameter - innerOutlineArcSize - (int) innerArcPadding;
        float bottom = yCentre + halfDiameter - innerOutlineArcSize - (int) innerArcPadding;

        innerArcOval = new RectF(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOuterArc(canvas);
        drawInnerArcOutline(canvas);
    }

    private void drawOuterArc(Canvas canvas) {
        canvas.drawArc(outerArcOval, startAngle, 360, false, outerArcPaint);
    }

    private void drawInnerArcOutline(Canvas canvas) {
        canvas.drawArc(innerArcOval, startAngle, sweepAngle, false, outlineArcPaint);
        canvas.drawArc(innerArcOval, startAngle, sweepAngle, false, innerArcPaint);
    }

    private float calcSweepAngleFromProgress(int progress) {
        return (maxSweepAngle / maxProgress) * progress;
    }

    public void setProgress(@IntRange(from = 0, to = 100) int progress) {
        this.progress = progress;
        initInnerArcGradient();

        ValueAnimator animator = ValueAnimator.ofFloat(sweepAngle, calcSweepAngleFromProgress(progress));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(animationDuration);
        animator.addUpdateListener(valueAnimator -> {
            sweepAngle = (float) valueAnimator.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }
}
