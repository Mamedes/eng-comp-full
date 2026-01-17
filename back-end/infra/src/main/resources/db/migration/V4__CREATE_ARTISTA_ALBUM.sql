CREATE TABLE artista_album (
    artista_id INTEGER REFERENCES artista(id) ON DELETE CASCADE,
    album_id INTEGER REFERENCES album(id) ON DELETE CASCADE,
    PRIMARY KEY (artista_id, album_id)
);
CREATE UNIQUE INDEX uq_artista_album ON artista_album (artista_id, album_id);