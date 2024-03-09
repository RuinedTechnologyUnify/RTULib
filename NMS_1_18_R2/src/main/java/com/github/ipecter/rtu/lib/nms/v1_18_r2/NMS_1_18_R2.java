package com.github.ipecter.rtu.lib.nms.v1_18_r2;

import com.github.ipecter.rtu.lib.nms.NMSBiome;

public class NMS_1_18_R2 implements com.github.ipecter.rtu.lib.nms.NMS {

    private final NMSBiome biome = new Biome();

    @Override
    public NMSBiome biome() {
        return biome;
    }
}
