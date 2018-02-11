package com.snazzy.creditscoredemo.creditscoreviewer.presentation.creditscoreprogressbar;

/**
 * Created this class to handle most of the calculations needed for the custom progress bar.
 * This way, the code can be easily tested with unit tests and spot any bugs early
 */
class CreditScoreProgressBarUtils {

    private int viewWidth;
    private int viewHeight;
    private int xCentre = 0;
    private int yCentre = 0;
    private float progress = 0;
    private final float innerOutlineArcSize;
    private final float innerArcPadding;
    private final float outerArcSize;
    private final float startProgress;
    private final float maxProgress;
    private final float maxSweepAngle;

    CreditScoreProgressBarUtils(float innerOutlineArcSize,
                                float innerArcPadding,
                                float outerArcSize,
                                float startProgress,
                                float maxProgress,
                                float maxSweepAngle) {
        this.innerOutlineArcSize = innerOutlineArcSize;
        this.innerArcPadding = innerArcPadding;
        this.outerArcSize = outerArcSize;
        this.startProgress = startProgress;
        this.maxProgress = maxProgress;
        this.maxSweepAngle = maxSweepAngle;
    }

    void setSize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
        xCentre = viewWidth / 2;
        yCentre = viewHeight / 2;
    }

    void setProgress(float progress) {
        this.progress = progress;
    }

    /**
     * Used AutoValue models to pass the required parameters to the Custom View, to keep things consistent.
     * Also for their immutability and throwing an exception if a value is not set
     */
    GradientColourValues calculateGradientColourValues() {
        return GradientColourValues.builder()
                .colourPositions(new float[]{startProgress, progress / maxProgress})
                .xCentre(xCentre)
                .yCentre(yCentre)
                .build();
    }

    ArcSize calculateOuterArcSize() {
        float outerArcDiameter = calculateHalfDiameter() - outerArcSize;
        return calculateArcSize(outerArcDiameter);
    }

    ArcSize calculateInnerArcSize() {
        float innerArcDiameter = calculateHalfDiameter() - innerOutlineArcSize - innerArcPadding;
        return calculateArcSize(innerArcDiameter);
    }

    float calculateSweepAngle(float progress) {
        return (maxSweepAngle / maxProgress) * progress;
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
