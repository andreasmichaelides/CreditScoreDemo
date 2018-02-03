package com.snazzy.creditscoredemo.creditscoreviewer.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

@AutoValue
public abstract class CreditScoreResponse {

    @SerializedName("dashboardStatus")
    public abstract String dashboardStatus();

    @SerializedName("personaType")
    public abstract String personaType();

    @SerializedName("coachingSummary")
    public abstract CoachingSummary coachingSummary();

    @SerializedName("creditReportInfo")
    public abstract CreditReportInfo creditReportInfo();

    @SerializedName("accountIDVStatus")
    public abstract String accountIDVStatus();

    public static TypeAdapter<CreditScoreResponse> typeAdapter(Gson gson) {
        return new AutoValue_CreditScoreResponse.GsonTypeAdapter(gson);
    }
}