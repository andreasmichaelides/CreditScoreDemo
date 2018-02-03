package com.snazzy.creditscoredemo.creditscoreviewer.data;

import com.snazzy.creditscoredemo.creditscoreviewer.data.model.CreditScoreResponse;

import io.reactivex.Single;

public interface CreditScoreRepository {

    Single<CreditScoreResponse> getCreditScore();

}
