package org.madrascheck.block_extension.domain.repository;

import org.madrascheck.block_extension.domain.entity.BlockedExtension;
import org.madrascheck.block_extension.domain.entity.enums.ExtensionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedExtensionJpaRepository extends JpaRepository<BlockedExtension, Long> {

    @Query("select count(*) from BlockedExtension be where be.type = :type")
    Integer findCount(ExtensionType type);
}
