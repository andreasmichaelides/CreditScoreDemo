package com.snazzy.creditscoredemo.creditscoreviewer.data;

import com.snazzy.creditscoredemo.core.SchedulersProvider;
import com.snazzy.creditscoredemo.creditscoreviewer.data.model.CreditScoreResponse;

import io.reactivex.Single;

class MockCreditScoreRepository implements CreditScoreRepository {

    private final MockCreditApi mockCreditApi;
    private final SchedulersProvider schedulersProvider;

    MockCreditScoreRepository(MockCreditApi mockCreditApi, SchedulersProvider schedulersProvider) {
        this.mockCreditApi = mockCreditApi;
        this.schedulersProvider = schedulersProvider;
    }

    @Override
    public Single<CreditScoreResponse> getCreditScore() {
        return mockCreditApi.getMockCredit()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread());
    }
}
