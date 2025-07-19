CREATE TABLE IF NOT EXISTS blocked_extension_tb (
    id          BIGINT                  NOT NULL    AUTO_INCREMENT,
    name        VARCHAR(20)             NOT NULL,
    type        ENUM('FIXED', 'CUSTOM') NOT NULL,
    is_enabled  TINYINT(1)              NOT NULL    DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT uq_blocked_extension_tb_extension UNIQUE (name)
) ENGINE=InnoDB DEFAULT CHAR SET utf8mb4;
