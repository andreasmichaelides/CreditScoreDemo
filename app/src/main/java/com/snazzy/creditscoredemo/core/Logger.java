package com.snazzy.creditscoredemo.core;

import javax.annotation.Nonnull;

/**
 * The logger class is created to centralise the logging of errors or any other messages in the app.
 * This helps to avoid any logs in Logcat on a release build or flavor of the app, by injecting a different implementation of the logger.
 * Crashlytics or any other type of logging can be added in the implementation of the Logger, for easy logging of any errors
 */
public interface Logger {

    void e(@Nonnull Object caller, @Nonnull String message);

    void e(@Nonnull Object caller, Throwable throwable);

}
