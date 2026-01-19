CREATE SEQUENCE seq_album_imagem START WITH 1 INCREMENT BY 1;

CREATE TABLE album_imagem (
    id INTEGER PRIMARY KEY DEFAULT nextval('seq_album_imagem'),
    secure_id UUID DEFAULT uuid_generate_v4() UNIQUE,
    album_id INTEGER REFERENCES album(id) ON DELETE CASCADE,
    file_name VARCHAR(255) NOT NULL,
    file_key VARCHAR(512) NOT NULL,
    content_type VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);