package com.snazzy.creditscoredemo.core;

import android.util.Log;

import javax.annotation.Nonnull;

public class AndroidLogger implements Logger {

    @Override
    public void e(@Nonnull Object caller, @Nonnull String message) {
        Log.e(getNameOfCaller(caller), message);
    }

    @Override
    public void e(@Nonnull Object caller, Throwable throwable) {
        Log.e(getNameOfCaller(caller), getMessage(throwable));
    }

    private String getNameOfCaller(@Nonnull Object caller) {
        return caller.getClass().getSimpleName();
    }

    private String getMessage(Throwable throwable) {
        String message;
        if (throwable != null) {
            message = throwable.getMessage() != null ? throwable.getMessage() : "No message in throwable";
            throwable.printStackTrace();
        } else {
            message = "Throwable null";
        }
        return message;
    }
}
