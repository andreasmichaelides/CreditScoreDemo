package com.snazzy.creditscoredemo.creditscoreviewer.domain;

import com.snazzy.creditscoredemo.creditscoreviewer.data.model.CreditReportInfo;
import com.snazzy.creditscoredemo.creditscoreviewer.data.model.CreditScoreResponse;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.model.CreditScore;

import io.reactivex.Single;

public class CreditScoreMapper {

    Single<CreditScore> mapToCreditScore(CreditScoreResponse creditScoreResponse) {
        return Single.just(creditScoreResponse)
                .map(CreditScoreResponse::creditReportInfo)
                .map(this::mapToCreditScore);
    }

    private CreditScore mapToCreditScore(CreditReportInfo creditReportInfo) {
        return CreditScore.builder()
                .currentScore(creditReportInfo.score())
                .maxScore(creditReportInfo.maxScoreValue())
                .progress(calculateProgress(creditReportInfo))
                .build();
    }

    private float calculateProgress(CreditReportInfo creditReportInfo) {
        return (float) creditReportInfo.score() / (float) creditReportInfo.maxScoreValue() * 100f;
    }

}
