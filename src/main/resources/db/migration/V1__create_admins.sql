CREATE TABLE admins (
                        id_admin         BIGINT AUTO_INCREMENT PRIMARY KEY,
                        id_usuario       BIGINT       NOT NULL,
                        credencial       VARCHAR(100) NOT NULL,
                        nivel_acceso     VARCHAR(20)  NOT NULL,
                        fecha_asignacion DATE         NOT NULL,
                        activo           BOOLEAN      NOT NULL DEFAULT TRUE
);