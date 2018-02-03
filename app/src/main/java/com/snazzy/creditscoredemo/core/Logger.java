package com.snazzy.creditscoredemo.core;

import javax.annotation.Nonnull;

public interface Logger {

    void e(@Nonnull Object caller, @Nonnull String message);

    void e(@Nonnull Object caller, Throwable throwable);

}
