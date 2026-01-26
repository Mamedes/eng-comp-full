CREATE SEQUENCE seq_regional
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE regional (
    id BIGINT PRIMARY KEY DEFAULT nextval('seq_regional'),
    secure_id UUID DEFAULT uuid_generate_v4() UNIQUE,
    external_id INTEGER NOT NULL,
    nome VARCHAR(200) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_regional_external_ativo ON regional (external_id) WHERE ativo = true;

CREATE INDEX idx_regional_secure_id ON regional (secure_id);