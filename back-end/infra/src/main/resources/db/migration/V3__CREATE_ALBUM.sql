CREATE SEQUENCE seq_album START WITH 1 INCREMENT BY 1;

CREATE TABLE album (
    id INTEGER PRIMARY KEY DEFAULT nextval('seq_album'),
    secure_id UUID DEFAULT uuid_generate_v4() UNIQUE,
    titulo VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_album_secure_id ON album (secure_id);