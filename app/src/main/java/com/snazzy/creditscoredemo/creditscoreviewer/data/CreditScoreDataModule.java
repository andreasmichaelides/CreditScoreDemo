package com.snazzy.creditscoredemo.creditscoreviewer.data;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.snazzy.creditscoredemo.R;
import com.snazzy.creditscoredemo.core.SchedulersProvider;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class CreditScoreDataModule {

    @Provides
    static Gson provideGson() {
        return new GsonBuilder().
                registerTypeAdapterFactory(AdapterFactory.create())
                .create();
    }

    @Provides
    static CreditScoreService provideCreditScoreService(Resources resources, Gson gson) {
        return new RetrofitCreditScoreService(resources.getString(R.string.credit_score_api_url), gson);
    }

    @Provides
    static MockCreditApi provideMockCreditApi(CreditScoreService creditScoreService) {
        return creditScoreService.createService(MockCreditApi.class);
    }

    @Provides
    static CreditScoreRepository provideCreditScoreRepository(MockCreditApi mockCreditApi, SchedulersProvider schedulersProvider) {
        return new MockCreditScoreRepository(mockCreditApi, schedulersProvider);
    }

}
