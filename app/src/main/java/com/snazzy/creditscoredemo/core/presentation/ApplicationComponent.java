package com.snazzy.creditscoredemo.core.presentation;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@dagger.Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<BaseApplication>, Component<BaseApplication> {
    @dagger.Component.Builder
    abstract class Builder {
        @BindsInstance
        public abstract Builder application(Application application);

        public abstract ApplicationComponent build();
    }
}
