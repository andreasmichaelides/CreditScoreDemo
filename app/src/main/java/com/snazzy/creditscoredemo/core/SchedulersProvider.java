package com.snazzy.creditscoredemo.core;

import io.reactivex.Scheduler;

public interface SchedulersProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler mainThread();

}
