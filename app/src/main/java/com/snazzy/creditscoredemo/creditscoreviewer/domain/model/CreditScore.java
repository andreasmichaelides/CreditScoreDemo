package com.snazzy.creditscoredemo.creditscoreviewer.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CreditScore {

    public abstract int currentScore();

    public abstract int maxScore();

    public abstract float progress();

    public static Builder builder() {
        return new AutoValue_CreditScore.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder currentScore(int currentScore);

        public abstract Builder maxScore(int maxScore);

        public abstract Builder progress(float progress);

        public abstract CreditScore build();
    }
}
