package com.snazzy.creditscoredemo.core.presentation;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.snazzy.creditscoredemo.core.AndroidLogger;
import com.snazzy.creditscoredemo.core.Logger;
import com.snazzy.creditscoredemo.core.SchedulersProvider;
import com.snazzy.creditscoredemo.core.SchedulersProviderImpl;
import com.snazzy.creditscoredemo.creditscoreviewer.presentation.CreditScoreViewerActivity;
import com.snazzy.creditscoredemo.creditscoreviewer.presentation.CreditScoreViewerModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = AndroidInjectionModule.class)
abstract class ApplicationModule {

    @Provides
    @Singleton
    static Logger provideLogger() {
        return new AndroidLogger();
    }

    @Provides
    @Singleton
    static Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    static Resources provideResources(Context context) {
        return context.getResources();
    }

    @Provides
    static SchedulersProvider provideSchedulersProvider() {
        return new SchedulersProviderImpl();
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = CreditScoreViewerModule.class)
    public abstract CreditScoreViewerActivity creditScoreViewerActivityInjector();
}
