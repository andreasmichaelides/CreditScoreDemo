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
import com.snazzy.creditscoredemo.core.presentation.creditscoreprogressbar.ArcSize;
import com.snazzy.creditscoredemo.core.presentation.creditscoreprogressbar.CreditScoreProgressBarUtils;
import com.snazzy.creditscoredemo.core.presentation.creditscoreprogressbar.GradientColourValues;

public class CreditScoreProgressBar extends View {

    private CreditScoreProgressBarUtils utils;

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
    private RectF outerArcOval;
    private RectF innerArcOval;

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
        float outerArcSize = getResources().getDimension(R.dimen.donut_outer_size);
        float innerOutlineArcSize = getResources().getDimension(R.dimen.donut_inner_arc_size);
        float innerArcPadding = getResources().getDimension(R.dimen.donut_inner_padding);
        utils = new CreditScoreProgressBarUtils(innerOutlineArcSize, innerArcPadding, outerArcSize);

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
        utils.setSize(width, height);
        initInnerArcGradient();
        initOuterArc();
        initInnerArc();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOuterArc(canvas);
        drawInnerArcOutline(canvas);
    }

    private void initInnerArcGradient() {
        GradientColourValues gradientValues = utils.calculateGradientColourValues();
        Shader gradient = new SweepGradient(gradientValues.xCentre(), gradientValues.yCentre(), colors, gradientValues.colourPositions());
        Matrix gradientMatrix = new Matrix();
        gradientMatrix.preRotate(gradientRotationAngle, gradientValues.xCentre(), gradientValues.yCentre());
        gradient.setLocalMatrix(gradientMatrix);

        innerArcPaint.setShader(gradient);
    }

    private void initOuterArc() {
        ArcSize arcSize = utils.calculateOuterArcSize();
        outerArcOval = new RectF(arcSize.left(), arcSize.top(), arcSize.right(), arcSize.bottom());
    }

    private void initInnerArc() {
        ArcSize arcSize = utils.calculateInnerArcSize();
        innerArcOval = new RectF(arcSize.left(), arcSize.top(), arcSize.right(), arcSize.bottom());
    }

    private void drawOuterArc(Canvas canvas) {
        canvas.drawArc(outerArcOval, startAngle, 360, false, outerArcPaint);
    }

    private void drawInnerArcOutline(Canvas canvas) {
        // Draw the outline of the inner arc first, so the inner arc paint can draw on top of it
        canvas.drawArc(innerArcOval, startAngle, sweepAngle, false, outlineArcPaint);
        canvas.drawArc(innerArcOval, startAngle, sweepAngle, false, innerArcPaint);
    }

    private float calcSweepAngleFromProgress(int progress) {
        return (maxSweepAngle / maxProgress) * progress;
    }

    public void setProgress(@IntRange(from = 0, to = 100) int progress) {
        utils.setProgress(progress);
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
