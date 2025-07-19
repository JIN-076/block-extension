package org.madrascheck.block_extension.api.dto.res;

import java.util.List;
import lombok.Getter;

@Getter
public class CustomExtensionInfoResponse {

    private final List<CustomExtensionInfo> extensions;

    private CustomExtensionInfoResponse(List<CustomExtensionInfo> extensions) {
        this.extensions = extensions;
    }

    public static CustomExtensionInfoResponse of(List<CustomExtensionInfo> extensions) {
        return new CustomExtensionInfoResponse(extensions);
    }

}
