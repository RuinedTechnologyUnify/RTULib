package com.github.ipecter.rtu.lib.managers.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalFile extends Data {

    @Getter
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    void set(String key, String value) {

    }
}
