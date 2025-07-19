package org.madrascheck.block_extension.api.dto.res;

import java.util.List;
import lombok.Getter;

@Getter
public class FixedExtensionInfoResponse {

    private final List<FixedExtensionInfo> extensions;

    private FixedExtensionInfoResponse(List<FixedExtensionInfo> extensions) {
        this.extensions = extensions;
    }

    public static FixedExtensionInfoResponse of(List<FixedExtensionInfo> extensions) {
        return new FixedExtensionInfoResponse(extensions);
    }

}
