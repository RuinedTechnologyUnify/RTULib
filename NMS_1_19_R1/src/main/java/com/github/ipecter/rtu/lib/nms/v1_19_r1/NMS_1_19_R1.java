package com.github.ipecter.rtu.lib.nms.v1_19_r1;

import com.github.ipecter.rtu.lib.nms.NMSBiome;

public class NMS_1_19_R1 implements com.github.ipecter.rtu.lib.nms.NMS {

    private final NMSBiome biome = new Biome();

    @Override
    public NMSBiome biome() {
        return biome;
    }
}
