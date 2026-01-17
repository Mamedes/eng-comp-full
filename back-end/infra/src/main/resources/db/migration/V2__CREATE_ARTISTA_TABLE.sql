CREATE SEQUENCE seq_artista START WITH 1 INCREMENT BY 1;

CREATE TABLE artista (
    id INTEGER PRIMARY KEY DEFAULT nextval('seq_artista'),
    secure_id UUID DEFAULT uuid_generate_v4() UNIQUE,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX uk_artista_secure_id ON artista (secure_id);
