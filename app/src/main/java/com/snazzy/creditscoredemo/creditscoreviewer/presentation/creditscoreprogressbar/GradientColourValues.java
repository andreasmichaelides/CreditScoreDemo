package com.snazzy.creditscoredemo.creditscoreviewer.presentation.creditscoreprogressbar;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GradientColourValues {

    public abstract float[] colourPositions();

    public abstract int xCentre();

    public abstract int yCentre();

    public static Builder builder() {
        return new AutoValue_GradientColourValues.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder colourPositions(float[] colourPositions);

        public abstract Builder xCentre(int xCentre);

        public abstract Builder yCentre(int yCentre);

        public abstract GradientColourValues build();
    }
}
