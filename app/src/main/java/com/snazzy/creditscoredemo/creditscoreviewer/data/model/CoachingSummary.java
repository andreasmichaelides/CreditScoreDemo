package com.snazzy.creditscoredemo.creditscoreviewer.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

@AutoValue
public abstract class CoachingSummary {

    @SerializedName("activeChat")
    public abstract boolean activeChat();

    @SerializedName("numberOfTodoItems")
    public abstract int numberOfTodoItems();

    @SerializedName("activeTodo")
    public abstract boolean activeTodo();

    @SerializedName("numberOfCompletedTodoItems")
    public abstract int numberOfCompletedTodoItems();

    @SerializedName("selected")
    public abstract boolean selected();

    public static TypeAdapter<CoachingSummary> typeAdapter(Gson gson) {
        return new AutoValue_CoachingSummary.GsonTypeAdapter(gson);
    }
}