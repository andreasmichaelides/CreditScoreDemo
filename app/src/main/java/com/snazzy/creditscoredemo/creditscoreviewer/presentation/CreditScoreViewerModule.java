package com.snazzy.creditscoredemo.creditscoreviewer.presentation;

import com.snazzy.creditscoredemo.core.Logger;
import com.snazzy.creditscoredemo.core.presentation.ActivityScope;
import com.snazzy.creditscoredemo.creditscoreviewer.data.CreditScoreDataModule;
import com.snazzy.creditscoredemo.creditscoreviewer.data.CreditScoreRepository;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.CreditScoreMapper;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.GetCreditScore;

import dagger.Module;
import dagger.Provides;

@Module(includes = CreditScoreDataModule.class)
public abstract class CreditScoreViewerModule {

    @Provides
    @ActivityScope
    static CreditScoreMapper provideCreditScoreMapper() {
        return new CreditScoreMapper();
    }

    @Provides
    @ActivityScope
    static GetCreditScore provideGetCreditScore(CreditScoreMapper creditScoreMapper, CreditScoreRepository creditScoreRepository) {
        return new GetCreditScore(creditScoreMapper, creditScoreRepository);
    }

    @Provides
    @ActivityScope
    static CreditScoreViewerViewModelFactory provideCreditScoreViewer(GetCreditScore getCreditScore, Logger logger) {
        return new CreditScoreViewerViewModelFactory(getCreditScore, logger);
    }

}
