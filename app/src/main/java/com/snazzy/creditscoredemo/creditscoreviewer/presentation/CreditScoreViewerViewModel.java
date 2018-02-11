package com.snazzy.creditscoredemo.creditscoreviewer.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.snazzy.creditscoredemo.core.Logger;
import com.snazzy.creditscoredemo.core.presentation.SingleLiveEvent;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.GetCreditScore;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.model.CreditScore;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

class CreditScoreViewerViewModel extends ViewModel {

    // This subject acts as a trigger, that informs the stream to load the credit score.
    private final Subject<Object> loadCreditScore = PublishSubject.create();
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final MutableLiveData<CreditScore> creditScore = new MutableLiveData<>();
    // The single live event acts as a PublishSubject in RxJava, which emits a string once and does not keep it after its emission.
    // This avoids showing the error multiple times, when the view (Activity) subscribes again to it, for example a screen rotation.
    private final SingleLiveEvent<Boolean> showLoading = new SingleLiveEvent<>();
    // Having a separate SingleLiveEvent for showing the credit score views, can help if we don't want to hide the current credit score
    // if already displayed, while loading new values. Having this logic separated here, we can test these scenarios using unit tests
    // avoiding if statements in the Activity on whether to show the views or not.
    private final SingleLiveEvent<Boolean> showCreditScore = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> loadingErrorMessage = new SingleLiveEvent<>();

    CreditScoreViewerViewModel(GetCreditScore getCreditScore, Logger logger) {
        subscriptions.addAll(
                loadCreditScore.flatMapSingle(ignored -> getCreditScore.execute()
                        .doOnSubscribe(disposable -> onCreditScoreLoadingStarted())
                        .doOnError(this::onCreditScoreLoadError)
                        // Resuming with a Single.never() to avoid stopping the stream when an error has been emitted
                        .onErrorResumeNext(Single.never()))
                        .subscribe(this::onCreditScoreLoadSuccess, throwable -> logger.e(this, throwable))
        );
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
        this.creditScore.setValue(creditScore);
        showLoading.setValue(false);
        showCreditScore.setValue(true);
    }

    private void onCreditScoreLoadError(Throwable throwable) {
        loadingErrorMessage.setValue(throwable.getMessage());
        showLoading.setValue(false);
    }

    void loadCreditScore() {
        loadCreditScore.onNext(new Object());
    }

    LiveData<CreditScore> creditScore() {
        return creditScore;
    }

    LiveData<String> showLoadingError() {
        return loadingErrorMessage;
    }

    LiveData<Boolean> showLoading() {
        return showLoading;
    }

    LiveData<Boolean> showCreditScore() {
        return showCreditScore;
    }

}
