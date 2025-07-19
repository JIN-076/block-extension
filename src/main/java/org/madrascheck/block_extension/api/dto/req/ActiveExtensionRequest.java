package org.madrascheck.block_extension.api.dto.req;

import lombok.Getter;

@Getter
public class ActiveExtensionRequest {

    private final Boolean isEnabled;

    private ActiveExtensionRequest(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
