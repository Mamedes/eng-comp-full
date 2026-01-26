package com.seletivo.application.regional.sync;

public record SyncRegionaisOutput(
        boolean success,
        int processados
) {
    public static SyncRegionaisOutput from(final boolean success, final int count) {
        return new SyncRegionaisOutput(success, count);
    }
}