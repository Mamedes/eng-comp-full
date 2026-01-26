package com.seletivo.application.regional.sync;

import java.util.List;

public record SyncRegionaisCommand(
        List<RegionalInput> regionais
) {
    public static SyncRegionaisCommand with(final List<RegionalInput> regionais) {
        return new SyncRegionaisCommand(regionais);
    }
}
