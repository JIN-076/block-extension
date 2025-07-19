package org.madrascheck.block_extension.api.dto.res;

import lombok.Getter;

@Getter
public class FixedExtensionResponse {

    private final Long id;
    private final String name;
    private final Boolean isEnabled;

    private FixedExtensionResponse(Long id, String name, Boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.isEnabled = isEnabled;
    }

    public static FixedExtensionResponse from(
        final Long id,
        final String name,
        final Boolean isEnabled
    ) {
        return new FixedExtensionResponse(id, name, isEnabled);
    }

}
