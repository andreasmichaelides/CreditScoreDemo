package com.snazzy.creditscoredemo.creditscoreviewer.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class CreditReportInfo {

    @SerializedName("numPositiveScoreFactors")
    public abstract int numPositiveScoreFactors();

    @SerializedName("changeInShortTermDebt")
    public abstract int changeInShortTermDebt();

    @SerializedName("clientRef")
    public abstract String clientRef();

    @SerializedName("currentShortTermDebt")
    public abstract int currentShortTermDebt();

    @SerializedName("percentageCreditUsedDirectionFlag")
    public abstract int percentageCreditUsedDirectionFlag();

    @SerializedName("score")
    public abstract int score();

    @SerializedName("currentShortTermNonPromotionalDebt")
    public abstract int currentShortTermNonPromotionalDebt();

    @SerializedName("currentLongTermDebt")
    public abstract int currentLongTermDebt();

    @SerializedName("changedScore")
    public abstract int changedScore();

    @SerializedName("currentShortTermCreditLimit")
    public abstract int currentShortTermCreditLimit();

    @SerializedName("percentageCreditUsed")
    public abstract int percentageCreditUsed();

    @SerializedName("daysUntilNextReport")
    public abstract int daysUntilNextReport();

    @SerializedName("equifaxScoreBand")
    public abstract int equifaxScoreBand();

    @SerializedName("minScoreValue")
    public abstract int minScoreValue();

    @SerializedName("currentShortTermCreditUtilisation")
    public abstract int currentShortTermCreditUtilisation();

    @SerializedName("changeInLongTermDebt")
    public abstract int changeInLongTermDebt();

    @SerializedName("equifaxScoreBandDescription")
    public abstract String equifaxScoreBandDescription();

    @SerializedName("monthsSinceLastDelinquent")
    public abstract int monthsSinceLastDelinquent();

    @SerializedName("numNegativeScoreFactors")
    public abstract int numNegativeScoreFactors();

    @SerializedName("hasEverBeenDelinquent")
    public abstract boolean hasEverBeenDelinquent();

    @SerializedName("currentLongTermNonPromotionalDebt")
    public abstract int currentLongTermNonPromotionalDebt();

    @SerializedName("scoreBand")
    public abstract int scoreBand();

    @SerializedName("hasEverDefaulted")
    public abstract boolean hasEverDefaulted();

    @SerializedName("maxScoreValue")
    public abstract int maxScoreValue();

    @SerializedName("status")
    public abstract String status();

    @SerializedName("monthsSinceLastDefaulted")
    public abstract int monthsSinceLastDefaulted();

    public static TypeAdapter<CreditReportInfo> typeAdapter(Gson gson) {
        return new AutoValue_CreditReportInfo.GsonTypeAdapter(gson);
    }
}