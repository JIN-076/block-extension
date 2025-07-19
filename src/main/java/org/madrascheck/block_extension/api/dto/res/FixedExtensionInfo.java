package org.madrascheck.block_extension.api.dto.res;

import lombok.Getter;
import org.madrascheck.block_extension.domain.entity.BlockedExtension;

@Getter
public class FixedExtensionInfo {

    private final Long id;
    private final String name;
    private final boolean isEnabled;

    private FixedExtensionInfo(Long id, String name, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.isEnabled = isEnabled;
    }

    public static FixedExtensionInfo of(BlockedExtension blockedExtension) {
        return new FixedExtensionInfo(
            blockedExtension.getId(),
            blockedExtension.getName(),
            blockedExtension.getIsEnabled()
        );
    }

}
