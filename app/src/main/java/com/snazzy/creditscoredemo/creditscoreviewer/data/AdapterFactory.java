package com.snazzy.creditscoredemo.creditscoreviewer.data;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
abstract class AdapterFactory implements TypeAdapterFactory {
    static TypeAdapterFactory create() {
        return new AutoValueGson_AdapterFactory();
    }
}
