package com.snazzy.creditscoredemo.creditscoreviewer.presentation.creditscoreprogressbar;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CreditScoreProgressBarUtilsTest {

    private final float innerOutlineArcSize = 4;
    private final float innerArcPadding = 2;
    private final float outerArcSize = 30;
    private final float startProgress = 0;
    private final float maxProgress = 100;
    private final float maxSweepAngle = 360;

    private CreditScoreProgressBarUtils creditScoreProgressBarUtils;

    @Before
    public void setUp() {
        creditScoreProgressBarUtils = new CreditScoreProgressBarUtils(innerOutlineArcSize,
                innerArcPadding,
                outerArcSize,
                startProgress,
                maxProgress,
                maxSweepAngle);
    }

    @Test
    public void calculateOuterArcSize() throws Exception {
        // given
        int width = 100, height = 100;
        float halfDiameter = 100 / 2 - outerArcSize;
        ArcSize expectedArcSize = calculateArcSize(halfDiameter, width, height);
        creditScoreProgressBarUtils.setSize(width, height);

        // when
        ArcSize actualArcSize = creditScoreProgressBarUtils.calculateOuterArcSize();

        // then
        assertEquals(expectedArcSize, actualArcSize);
    }

    @Test
    public void calculateInnerArcSize() throws Exception {
        // given
        int width = 100, height = 100;
        float halfDiameter = 100 / 2  - innerOutlineArcSize - innerArcPadding;
        ArcSize expectedArcSize = calculateArcSize(halfDiameter, width, height);
        creditScoreProgressBarUtils.setSize(width, height);

        // when
        ArcSize actualArcSize = creditScoreProgressBarUtils.calculateInnerArcSize();

        // then
        assertEquals(expectedArcSize, actualArcSize);
    }

    @Test
    public void calculateInnerArcSizeWithSmallerHeight() throws Exception {
        // given
        int width = 100, height = 50;
        float halfDiameter = 50 / 2  - innerOutlineArcSize - innerArcPadding;
        ArcSize expectedArcSize = calculateArcSize(halfDiameter, width, height);
        creditScoreProgressBarUtils.setSize(width, height);

        // when
        ArcSize actualArcSize = creditScoreProgressBarUtils.calculateInnerArcSize();

        // then
        assertEquals(expectedArcSize, actualArcSize);
    }

    @Test
    public void calculateInnerArcSizeWithSmallerWidth() throws Exception {
        // given
        int width = 78, height = 120;
        float halfDiameter = 78 / 2  - innerOutlineArcSize - innerArcPadding;
        ArcSize expectedArcSize = calculateArcSize(halfDiameter, width, height);
        creditScoreProgressBarUtils.setSize(width, height);

        // when
        ArcSize actualArcSize = creditScoreProgressBarUtils.calculateInnerArcSize();

        // then
        assertEquals(expectedArcSize, actualArcSize);
    }

    @Test
    public void calculateGradientColourValues() throws Exception {
        // given
        int width = 78, height = 120;
        float progress = 43;
        GradientColourValues expectedGradientColourValues = createGradientColourValues(width / 2, height / 2, progress);
        creditScoreProgressBarUtils.setSize(width, height);
        creditScoreProgressBarUtils.setProgress(progress);

        // when
        GradientColourValues actualGradientColourValues = creditScoreProgressBarUtils.calculateGradientColourValues();

        // then
        assertEquals(expectedGradientColourValues, actualGradientColourValues);
    }

    private ArcSize calculateArcSize(float halfDiameter, float width, float height) {
        float xCentre = width / 2;
        float yCentre = height / 2;
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

    private GradientColourValues createGradientColourValues(int xCentre, int yCentre, float progress) {
        return GradientColourValues.builder()
                .colourPositions(new float[]{startProgress, progress / maxProgress})
                .xCentre(xCentre)
                .yCentre(yCentre)
                .build();
    }
}