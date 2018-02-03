package com.snazzy.creditscoredemo.creditscoreviewer.domain;

import com.snazzy.creditscoredemo.creditscoreviewer.data.CreditScoreRepository;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.model.CreditScore;

import io.reactivex.Single;

public class GetCreditScore {

    private final CreditScoreMapper creditScoreMapper;
    private final CreditScoreRepository creditScoreRepository;

    public GetCreditScore(CreditScoreMapper creditScoreMapper, CreditScoreRepository creditScoreRepository) {
        this.creditScoreMapper = creditScoreMapper;
        this.creditScoreRepository = creditScoreRepository;
    }

    public Single<CreditScore> execute() {
        return creditScoreRepository.getCreditScore()
                .flatMap(creditScoreMapper::mapToCreditScore);
    }

}
