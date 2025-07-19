package org.madrascheck.block_extension.api.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterExtensionRequest {

    @Size(min = 1, max = 20, message = "확장자는 20자를 넘을 수 없습니다.")
    @NotBlank(message = "확장자는 공백일 수 없습니다.")
    private final String extensionName;

    private RegisterExtensionRequest(String extensionName) {
        this.extensionName = extensionName;
    }

}
