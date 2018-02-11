package com.snazzy.creditscoredemo.creditscoreviewer.presentation.creditscoreprogressbar;

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

    private static final int MAX_SWEEP_ANGLE = 360;
    private static final int START_PROGRESS = 0;
    private static final int MAX_PROGRESS = 100;
    private static final int ANIMATION_DURATION = 400;
    // The gradient rotation angle starts from -91 instead of 90 degrees, to overcome a colouring error which happens when the arc is drawn
    // with a rounded paint. This is not ideal it is recommended to find a way to calculate the angle offset that avoids the colouring error.
    // Or another way of solving it!
    private static final float GRADIENT_ROTATION_ANGLE = -91;
    private static final float ARC_START_ANGLE = -90;

    private float sweepAngle = 0;
    private CreditScoreProgressBarUtils utils;
    private Paint innerArcPaint;
    private Paint outerArcPaint;
    private Paint outlineArcPaint;

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
        utils = new CreditScoreProgressBarUtils(innerOutlineArcSize,
                innerArcPadding,
                outerArcSize,
                START_PROGRESS,
                MAX_PROGRESS,
                MAX_SWEEP_ANGLE);

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

    /**
     * The initialisation of the arc components should be done only when when size has changed, to avoid instantiating objects during
     * rendering, to avoid any frame drops
     */
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        utils.setSize(width, height);
        initInnerArcGradient();
        // Used a black arc, to draw an outline around the gradient arc.
        initOuterArc();
        initInnerArc();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOuterArc(canvas);
        drawInnerArc(canvas);
    }

    private void initInnerArcGradient() {
        GradientColourValues gradientValues = utils.calculateGradientColourValues();
        Shader gradient = new SweepGradient(gradientValues.xCentre(), gradientValues.yCentre(), colors, gradientValues.colourPositions());
        Matrix gradientMatrix = new Matrix();
        gradientMatrix.preRotate(GRADIENT_ROTATION_ANGLE, gradientValues.xCentre(), gradientValues.yCentre());
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
        canvas.drawArc(outerArcOval, ARC_START_ANGLE, MAX_SWEEP_ANGLE, false, outerArcPaint);
    }

    private void drawInnerArc(Canvas canvas) {
        // Draw the outline of the inner arc first, so the inner arc paint can draw on top of it
        canvas.drawArc(innerArcOval, ARC_START_ANGLE, sweepAngle, false, outlineArcPaint);
        canvas.drawArc(innerArcOval, ARC_START_ANGLE, sweepAngle, false, innerArcPaint);
    }

    /**
     * The initialisation of the inner arc gradient depends on the progress that has been set on the custom view, so it is initialised
     * whenever the progress is set
     */
    public void setProgress(@IntRange(from = START_PROGRESS, to = MAX_PROGRESS) int progress) {
        utils.setProgress(progress);
        initInnerArcGradient();

        ValueAnimator animator = ValueAnimator.ofFloat(sweepAngle, utils.calculateSweepAngle(progress));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(valueAnimator -> {
            sweepAngle = (float) valueAnimator.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }
}
