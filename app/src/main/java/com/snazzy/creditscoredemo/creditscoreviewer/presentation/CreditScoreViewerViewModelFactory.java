package com.snazzy.creditscoredemo.creditscoreviewer.presentation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.snazzy.creditscoredemo.core.Logger;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.GetCreditScore;

import java.lang.Class;
import java.lang.Override;

class CreditScoreViewerViewModelFactory implements ViewModelProvider.Factory {

    private final GetCreditScore getCreditScore;
    private final Logger logger;

    CreditScoreViewerViewModelFactory(GetCreditScore getCreditScore, Logger logger) {
        this.getCreditScore = getCreditScore;
        this.logger = logger;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreditScoreViewerViewModel.class)) {
            return (T) new CreditScoreViewerViewModel(getCreditScore, logger);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
