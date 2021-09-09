package me.gavin.blm.config;

import com.google.gson.JsonObject;

public interface Configurable {

    String getName();

    String getConfigCategory();

    void saveProperties(JsonObject jsonObject);

    void writeProperties(JsonObject jsonObject);
}
