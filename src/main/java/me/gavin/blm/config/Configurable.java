package me.gavin.blm.config;

import com.google.gson.JsonObject;

public interface Configurable {

    String getName();

    String getConfigGroup();

    void saveProperties(JsonObject jsonObject);

    void writeProperties(JsonObject jsonObject);
}
