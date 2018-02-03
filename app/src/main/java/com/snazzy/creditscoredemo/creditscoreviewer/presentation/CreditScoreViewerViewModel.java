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

    private final Subject<Object> loadCreditScore = PublishSubject.create();
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final MutableLiveData<CreditScore> creditScore = new MutableLiveData<>();
    private final SingleLiveEvent<String> creditScoreLoadFailed = new SingleLiveEvent<>();

    CreditScoreViewerViewModel(GetCreditScore getCreditScore, Logger logger) {
        subscriptions.addAll(
                loadCreditScore.flatMapSingle(ignored -> getCreditScore.execute()
                        .doOnError(throwable -> creditScoreLoadFailed.setValue(throwable.getMessage()))
                        .onErrorResumeNext(Single.never()))
                        .subscribe(creditScore::setValue, throwable -> logger.e(this, throwable))
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        subscriptions.clear();
    }

    void loadCreditScore() {
        loadCreditScore.onNext(new Object());
    }

    LiveData<CreditScore> creditScore() {
        return creditScore;
    }

    LiveData<String> loadError() {
        return creditScoreLoadFailed;
    }

}
