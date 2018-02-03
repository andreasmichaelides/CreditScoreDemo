package com.snazzy.creditscoredemo.core.presentation;

public interface Component<T> {
    void inject(T objectToBeInjectedWithModules);
}
