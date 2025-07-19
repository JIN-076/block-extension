package org.madrascheck.block_extension.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.madrascheck.block_extension.api.dto.req.RegisterExtensionRequest;
import org.madrascheck.block_extension.domain.entity.BlockedExtension;
import org.madrascheck.block_extension.domain.entity.enums.ExtensionType;
import org.madrascheck.block_extension.domain.repository.BlockedExtensionJpaRepository;
import org.madrascheck.block_extension.exception.base.DuplicateKeyException;
import org.madrascheck.block_extension.exception.base.IllegalStateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.madrascheck.block_extension.exception.code.ErrorCode400.DUPLICATE_EXTENSION;
import static org.madrascheck.block_extension.exception.code.ErrorCode400.MAX_CAPACITY_REACHED;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileExtensionManager {

    private static final int MAX_SIZE = 200;

    private final BlockedExtensionJpaRepository blockedExtensionJpaRepository;

    @Transactional
    public BlockedExtension register(final RegisterExtensionRequest request) {
        canRegisterMore();
        try {
            BlockedExtension extension = BlockedExtension.of(request);
            return blockedExtensionJpaRepository.save(extension);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException(DUPLICATE_EXTENSION);
        }
    }

    private void canRegisterMore() {
        if (blockedExtensionJpaRepository.findCount(ExtensionType.CUSTOM) == MAX_SIZE) {
            throw new IllegalStateException(MAX_CAPACITY_REACHED);
        }
    }

}
