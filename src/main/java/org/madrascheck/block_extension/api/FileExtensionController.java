package org.madrascheck.block_extension.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.madrascheck.block_extension.api.dto.req.ActiveExtensionRequest;
import org.madrascheck.block_extension.api.dto.req.RegisterExtensionRequest;
import org.madrascheck.block_extension.api.dto.res.CustomExtensionInfoResponse;
import org.madrascheck.block_extension.api.dto.res.ExtensionIdResponse;
import org.madrascheck.block_extension.api.dto.res.FixedExtensionInfoResponse;
import org.madrascheck.block_extension.api.dto.res.FixedExtensionResponse;
import org.madrascheck.block_extension.application.FileExtensionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 고정 확장자의 차단을 활성화/비활성화하는 API
     * @param extensionName (활성/비활성하려는 확장자)
     * @param request (isEnabled: 활성화할지, 비활성화할지 여부) ex) isEnabled=true -> 활성화하겠다!
     * @return FixedExtensionResponse (200 OK)
     */

    @PatchMapping("/fixed/{extensionName}")
    public ResponseEntity<FixedExtensionResponse> checkToFixedExtension(
            @PathVariable String extensionName,
            @RequestBody ActiveExtensionRequest request
    ) {
        Long id = fileExtensionManager.updateActivation(extensionName, request);
        return ResponseEntity.ok(FixedExtensionResponse.from(id, extensionName, request.getIsEnabled()));
    }

    /**
     * 고정 확장자 목록과 차단 활성화 여부를 가져오는 API
     * @return FixedExtensionInfoResponse (200 OK)
     */

    @GetMapping("/fixed")
    public ResponseEntity<FixedExtensionInfoResponse> fetchFixedExtensionInfo() {
        return ResponseEntity.ok(
                FixedExtensionInfoResponse.of(fileExtensionManager.getFixedExtensions())
        );
    }

    /**
     * 커스텀 확장자 목록을 가져오는 API
     * @return CustomExtensionInfoResponse
     */

    @GetMapping("/custom")
    public ResponseEntity<CustomExtensionInfoResponse> fetchCustomExtensionInfo() {
        return ResponseEntity.ok(
                CustomExtensionInfoResponse.of(fileExtensionManager.getCustomExtensions())
        );
    }

    /**
     * 커스텀 확장자를 삭제하는 API
     * @param extensionName (삭제할 확장자 이름)
     * @return Void (204 No Content)
     */

    @DeleteMapping("/custom/{extensionName}")
    public ResponseEntity<Void> deleteCustomExtension(@PathVariable String extensionName) {
        fileExtensionManager.deleteCustomExtension(extensionName);
        return ResponseEntity.noContent().build();
    }

}
