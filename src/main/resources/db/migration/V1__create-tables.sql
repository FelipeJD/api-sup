CREATE TABLE sup_tb_usuarios (
  id_usuario BIGINT NOT NULL AUTO_INCREMENT,
  nm_usuario VARCHAR(80) NOT NULL,
  ds_email VARCHAR(150) NOT NULL,
  ds_senha VARCHAR(255) NOT NULL,
  PRIMARY KEY (id_usuario)
);

CREATE TABLE sup_tb_empresa (
  id_empresa BIGINT NOT NULL AUTO_INCREMENT,
  nm_empresa VARCHAR(255) NOT NULL,
  ds_email VARCHAR(255) NOT NULL,
  ds_cargo VARCHAR(80),
  id_usuario BIGINT NOT NULL,
  PRIMARY KEY (id_empresa),
  FOREIGN KEY (id_usuario) REFERENCES sup_tb_usuarios(id_usuario)
);

