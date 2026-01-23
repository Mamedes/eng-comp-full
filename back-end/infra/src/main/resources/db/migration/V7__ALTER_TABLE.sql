ALTER TABLE album DROP COLUMN IF EXISTS artista_id;

CREATE TABLE album_artista (
    album_id INTEGER NOT NULL REFERENCES album(id) ON DELETE CASCADE,
    artista_id INTEGER NOT NULL REFERENCES artista(id) ON DELETE CASCADE,
    PRIMARY KEY (album_id, artista_id)
);

CREATE INDEX idx_album_artista_album ON album_artista(album_id);
CREATE INDEX idx_album_artista_artista ON album_artista(artista_id);
