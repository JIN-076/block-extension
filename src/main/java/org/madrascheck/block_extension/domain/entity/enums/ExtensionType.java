package org.madrascheck.block_extension.domain.entity.enums;

import lombok.Getter;

@Getter
public enum ExtensionType {

    FIXED("고정"),
    CUSTOM("커스텀")
    ;

    private final String description;

    ExtensionType(String description) {
        this.description = description;
    }
}
