CREATE TABLE admins (

                        id_admin BIGINT PRIMARY KEY AUTO_INCREMENT,

                        id_usuario BIGINT NOT NULL,

                        credencial VARCHAR(100) NOT NULL,

                        nivel_acceso VARCHAR(100) NOT NULL,

                        fecha_asignacion DATE NOT NULL
);