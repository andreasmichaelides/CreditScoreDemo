package com.snazzy.creditscoredemo.creditscoreviewer.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.snazzy.creditscoredemo.core.Logger;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.GetCreditScore;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.model.CreditScore;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

class CreditScoreViewerViewModel extends ViewModel {

    private final Logger logger;
    // This subject acts as a trigger, that informs the stream to load the credit score.
    private final Subject<Object> loadCreditScore = PublishSubject.create();
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final MutableLiveData<CreditScore> creditScore = new MutableLiveData<>();
    // Having a separate value for showing the credit score views, can help if we don't want to hide the current credit score,
    // if already displayed, while loading new values. Having this logic separated here, we can test these scenarios using unit tests
    // avoiding if statements in the Activity on whether to show the views or not.
    private final MutableLiveData<Boolean> showCreditScore = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    // Multiple LiveData could be switched to a single LiveData parameter which emits an ActivityModel, specifying all the values required
    // by the Activity, like the loading , error state, etc. So one LiveData parameter could be enough to define the state of the CreditScore

    CreditScoreViewerViewModel(GetCreditScore getCreditScore, Logger logger) {
        this.logger = logger;
        subscriptions.addAll(
                loadCreditScore.flatMapSingle(ignored -> getCreditScore.execute()
                        .doOnSubscribe(disposable -> onCreditScoreLoadingStarted())
                        .doOnError(this::onCreditScoreLoadError)
                        // Resuming with a Single.never() to avoid stopping the stream when an error has been emitted
                        .onErrorResumeNext(Single.never()))
                        .subscribe(this::onCreditScoreLoadSuccess, throwable -> logger.e(this, throwable))
        );

        // Loading the credit score as soon as the ViewModel is created. During any Activity rotations, the existing credit score will be
        // re used in the Activity, as soon as the Activity starts observing the LiveData again. This avoids API calls when only the Activity
        // is recreated
        // We also do not have to worry about the lifecycle of the Activity, because LiveData emit data during the right Activity states,
        // so loading data before an Activity is initialised is possible and the Activity can display the loaded data when it starts observing
        // the current LiveData
        loadCreditScore();
    }

    private void onCreditScoreLoadingStarted() {
        showLoading.setValue(true);
        showCreditScore.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Clear the subscriptions when the ViewModel is cleared, to avoid any memory leaks
        subscriptions.clear();
    }

    private void onCreditScoreLoadSuccess(CreditScore creditScore) {
        loadingErrorMessage.setValue(false);
        this.creditScore.setValue(creditScore);
        showLoading.setValue(false);
        showCreditScore.setValue(true);
    }

    private void onCreditScoreLoadError(Throwable throwable) {
        logger.e(this, throwable);
        loadingErrorMessage.setValue(true);
        showLoading.setValue(false);
    }

    void loadCreditScore() {
        loadCreditScore.onNext(new Object());
    }

    LiveData<CreditScore> creditScore() {
        return creditScore;
    }

    LiveData<Boolean> showLoadingError() {
        return loadingErrorMessage;
    }

    LiveData<Boolean> showLoading() {
        return showLoading;
    }

    LiveData<Boolean> showCreditScore() {
        return showCreditScore;
    }

}
