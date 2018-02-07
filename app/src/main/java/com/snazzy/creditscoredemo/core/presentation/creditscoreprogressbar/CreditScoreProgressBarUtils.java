package com.snazzy.creditscoredemo.core.presentation.creditscoreprogressbar;

import android.support.annotation.IntRange;

public class CreditScoreProgressBarUtils {

    private int viewWidth;
    private int viewHeight;
    private int xCentre = 0;
    private int yCentre = 0;
    private int progress = 0;
    private final float innerOutlineArcSize;
    private final float innerArcPadding;
    private final float outerArcSize;

    public CreditScoreProgressBarUtils(float innerOutlineArcSize, float innerArcPadding, float outerArcSize) {
        this.innerOutlineArcSize = innerOutlineArcSize;
        this.innerArcPadding = innerArcPadding;
        this.outerArcSize = outerArcSize;
    }

    public void setSize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
        xCentre = viewWidth / 2;
        yCentre = viewHeight / 2;
    }

    public void setProgress(@IntRange(from = 0, to = 100) int progress) {
        this.progress = progress;
    }

    public GradientColourValues calculateGradientColourValues() {
        return GradientColourValues.builder()
                .colourPositions(new float[]{0.0f, ((float) progress) / 100f})
                .xCentre(xCentre)
                .yCentre(yCentre)
                .build();
    }

    public ArcSize calculateOuterArcSize() {
        float outerArcDiameter = calculateHalfDiameter() - outerArcSize;
        return calculateArcSize(outerArcDiameter);
    }

    public ArcSize calculateInnerArcSize() {
        float innerArcDiameter = calculateHalfDiameter() - innerOutlineArcSize - innerArcPadding;
        return calculateArcSize(innerArcDiameter);
    }

    private int calculateHalfDiameter() {
        return Math.min(viewWidth, viewHeight) / 2;
    }

    private ArcSize calculateArcSize(float halfDiameter) {
        float left = xCentre - halfDiameter;
        float top = yCentre - halfDiameter;
        float right = xCentre + halfDiameter;
        float bottom = yCentre + halfDiameter;
        return ArcSize.builder()
                .left(left)
                .top(top)
                .right(right)
                .bottom(bottom)
                .build();
    }
}
