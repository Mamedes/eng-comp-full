CREATE SEQUENCE seq_album START WITH 1 INCREMENT BY 1;

CREATE TABLE album (
    id INTEGER PRIMARY KEY DEFAULT nextval('seq_album'),
    secure_id UUID DEFAULT uuid_generate_v4() UNIQUE,
    titulo VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    artista_id INTEGER REFERENCES artista(id) ON DELETE CASCADE


);

CREATE UNIQUE INDEX uk_album_secure_id ON album (secure_id);
CREATE INDEX idx_album_artista_id ON album (artista_id);