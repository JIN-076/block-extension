package org.madrascheck.block_extension.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.madrascheck.block_extension.api.dto.req.RegisterExtensionRequest;
import org.madrascheck.block_extension.api.dto.res.ExtensionIdResponse;
import org.madrascheck.block_extension.application.FileExtensionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blocked-extension")
@RequiredArgsConstructor
public class FileExtensionController {

    private final FileExtensionManager fileExtensionManager;

    /**
     * 차단할 커스텀 확장자를 추가하는 API
     * @param request (extensionName: 추가하려는 확장자)
     * @return ExtensionIdResponse (201 CREATED)
     */

    @PostMapping("/custom")
    public ResponseEntity<ExtensionIdResponse> registerToBlockExtension(
        @Valid @RequestBody RegisterExtensionRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ExtensionIdResponse.of(fileExtensionManager.register(request)));
    }
}
