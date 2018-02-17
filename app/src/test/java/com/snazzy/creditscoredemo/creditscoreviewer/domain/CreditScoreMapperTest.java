package com.snazzy.creditscoredemo.creditscoreviewer.domain;

import com.snazzy.creditscoredemo.creditscoreviewer.data.model.CreditReportInfo;
import com.snazzy.creditscoredemo.creditscoreviewer.data.model.CreditScoreResponse;
import com.snazzy.creditscoredemo.creditscoreviewer.domain.model.CreditScore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

public class CreditScoreMapperTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private CreditScoreResponse creditScoreResponseMock;
    @Mock
    private CreditReportInfo creditReportInfoMock;

    private CreditScoreMapper creditScoreMapper;

    @Before
    public void setUp() {
        when(creditScoreResponseMock.creditReportInfo()).thenReturn(creditReportInfoMock);
        creditScoreMapper = new CreditScoreMapper();
    }

    @Test
    public void mapToCreditScore() throws Exception {
        int currentScore = 125;
        int maxScore = 952;
        when(creditReportInfoMock.score()).thenReturn(currentScore);
        when(creditReportInfoMock.maxScoreValue()).thenReturn(maxScore);
        CreditScore expectedCreditScore = createCreditScore(maxScore, currentScore);

        TestObserver<CreditScore> test = creditScoreMapper.mapToCreditScore(creditScoreResponseMock).test();

        test.assertNoErrors()
                .assertValue(expectedCreditScore);
    }

    private CreditScore createCreditScore(int maxScore, int currentScore) {
        return CreditScore.builder()
                .maxScore(maxScore)
                .currentScore(currentScore)
                .progress(calculateProgress(maxScore, currentScore))
                .build();
    }

    private float calculateProgress(int maxScore, int currentScore) {
        return (float) currentScore / (float) maxScore * 100f;
    }
}