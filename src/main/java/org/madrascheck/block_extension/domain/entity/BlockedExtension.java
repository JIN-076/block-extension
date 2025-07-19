package org.madrascheck.block_extension.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.madrascheck.block_extension.api.dto.req.RegisterExtensionRequest;
import org.madrascheck.block_extension.domain.entity.enums.ExtensionType;

@Getter
@Entity
@Table(name = "blocked_extension_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockedExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, updatable = false)
    private String name;

    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ExtensionType type;

    private Boolean isEnabled;

    private BlockedExtension(String name, ExtensionType type, Boolean isEnabled) {
        this.name = name;
        this.type = type;
        applyEnabledFlagWithType(type, isEnabled);
    }

    public void toggle(boolean isEnabled) {
        applyEnabledFlagWithType(ExtensionType.FIXED, isEnabled);
    }

    private void applyEnabledFlagWithType(ExtensionType extensionType, Boolean isEnabled) {
        if (extensionType == ExtensionType.CUSTOM) {
            this.isEnabled = Boolean.FALSE;
        } else {
            this.isEnabled = isEnabled;
        }
    }

    public static BlockedExtension of(final RegisterExtensionRequest request) {
        return new BlockedExtension(
            request.getExtensionName(),
            ExtensionType.CUSTOM,
            null
        );
    }
}
