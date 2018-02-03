package com.snazzy.creditscoredemo.creditscoreviewer.data;

import com.snazzy.creditscoredemo.creditscoreviewer.data.model.CreditScoreResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MockCreditApi {

    @GET("mockcredit/values")
    Single<CreditScoreResponse> getMockCredit();

}
