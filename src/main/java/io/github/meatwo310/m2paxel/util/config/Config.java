package io.github.meatwo310.m2paxel.util.config;

import io.github.meatwo310.m2paxel.M2Paxel;

public class Config<T> {
    private final T defaultValue;

    private T value;
    private boolean initialized = false;

    public Config(T defaultValue) {
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public T get() {
        if (!initialized) {
            M2Paxel.LOG.warn("Config value accessed before initialization. Returning default value: {}", defaultValue);
        }

        return value;
    }

    public T getDefault() {
        return defaultValue;
    }

    public void set(T value) {
        this.value = value;
        this.initialized = true;
    }
}
