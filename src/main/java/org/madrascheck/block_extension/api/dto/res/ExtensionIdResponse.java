package org.madrascheck.block_extension.api.dto.res;

import lombok.Getter;
import org.madrascheck.block_extension.domain.entity.BlockedExtension;

@Getter
public class ExtensionIdResponse {

    private final Long id;
    private final String name;

    private ExtensionIdResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ExtensionIdResponse of(BlockedExtension blockedExtension) {
        return new ExtensionIdResponse(
            blockedExtension.getId(),
            blockedExtension.getName()
        );
    }

}
