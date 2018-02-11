package com.snazzy.creditscoredemo.creditscoreviewer.presentation;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.snazzy.creditscoredemo.R;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.model.CreditScore;
import com.snazzy.creditscoredemo.creditscoreviewer.presentation.creditscoreprogressbar.CreditScoreProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CreditScoreViewerActivity extends DaggerAppCompatActivity {

    @Inject
    CreditScoreViewerViewModelFactory viewModelFactory;

    @BindView(R.id.creditScoreToolbar)
    Toolbar creditScoreToolbar;
    @BindView(R.id.creditScoreLoadingProgressBar)
    ProgressBar creditScoreLoadingProgressBar;
    @BindView(R.id.creditScoreProgressBar)
    CreditScoreProgressBar creditScoreProgressBar;
    @BindView(R.id.creditScoreNumbersLayout)
    View creditScoreNumbersLayout;
    @BindView(R.id.creditScoreValueTextView)
    TextView creditScoreValueTextView;
    @BindView(R.id.creditScoreMaxValueTextView)
    TextView creditScoreMaxValueTextView;

    private CreditScoreViewerViewModel viewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_score_viewer);
        ButterKnife.bind(this);

        setSupportActionBar(creditScoreToolbar);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CreditScoreViewerViewModel.class);

        viewModel.creditScore().observe(this, this::setCreditScoreValues);
        viewModel.showLoadingError().observe(this, ignored -> showCreditScoreLoadingError());
        viewModel.showLoading().observe(this, this::onLoadingStateChanged);
        viewModel.showCreditScore().observe(this, this::showCreditScoreViews);

        viewModel.loadCreditScore();
    }

    private void onLoadingStateChanged(boolean isLoading) {
        creditScoreLoadingProgressBar.setVisibility(isLoading ? VISIBLE : GONE);
    }

    private void showCreditScoreLoadingError() {
        Snackbar.make(creditScoreToolbar, getString(R.string.credit_score_load_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, view -> viewModel.loadCreditScore())
                .show();
    }

    private void setCreditScoreValues(CreditScore creditScore) {
        float progress = ((float) creditScore.currentScore() / (float) creditScore.maxScore() * 100f);
        creditScoreProgressBar.setProgress(progress);

        creditScoreValueTextView.setText(String.valueOf(creditScore.currentScore()));
        creditScoreMaxValueTextView.setText(getString(R.string.credit_score_max_score, creditScore.maxScore()));
    }

    private void showCreditScoreViews(boolean show) {
        creditScoreNumbersLayout.setVisibility(show ? VISIBLE : GONE);
        creditScoreProgressBar.setVisibility(show ? VISIBLE : GONE);
    }
}
