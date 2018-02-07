package com.snazzy.creditscoredemo.core.presentation.creditscoreprogressbar;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ArcSize {

    public abstract float left();

    public abstract float top();

    public abstract float right();

    public abstract float bottom();

    public static Builder builder() {
        return new AutoValue_ArcSize.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder left(float left);

        public abstract Builder top(float top);

        public abstract Builder right(float right);

        public abstract Builder bottom(float bottom);

        public abstract ArcSize build();
    }
}
