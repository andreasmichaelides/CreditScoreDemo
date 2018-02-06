package com.snazzy.creditscoredemo.creditscoreviewer.presentation;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.snazzy.creditscoredemo.R;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.model.CreditScore;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class CreditScoreViewerActivity extends DaggerAppCompatActivity {

    @Inject
    CreditScoreViewerViewModelFactory viewModelFactory;

    @BindView(R.id.creditScoreToolbar)
    Toolbar creditScoreToolbar;
    @BindView(R.id.progressBar)
    CreditScoreProgressBar progressBar;

    private CreditScoreViewerViewModel viewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_score_viewer);
        ButterKnife.bind(this);

        setSupportActionBar(creditScoreToolbar);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CreditScoreViewerViewModel.class);

        viewModel.creditScore().observe(this, this::onCreditScoreLoaded);
        viewModel.loadError().observe(this, this::onCreditScoreLoadError);

        viewModel.loadCreditScore();
    }

    private void onCreditScoreLoadError(String errorMessage) {
        Snackbar.make(creditScoreToolbar, errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, view -> viewModel.loadCreditScore())
                .show();
    }

    private void onCreditScoreLoaded(CreditScore creditScore) {
        Toast.makeText(this, creditScore.toString(), Toast.LENGTH_SHORT).show();

        float progress = ((float)creditScore.currentScore() / (float)creditScore.maxScore() * 100f) ;
        progressBar.setProgress((int)progress);
    }
}
