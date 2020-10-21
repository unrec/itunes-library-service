DROP SCHEMA IF EXISTS library CASCADE;
CREATE SCHEMA library;
SET search_path TO library;

CREATE TABLE artists (
	"name" varchar(255) NOT NULL,
	CONSTRAINT artists_pkey PRIMARY KEY (name)
);

CREATE TABLE albums (
	album_name varchar(255) NOT NULL,
	album_year int4 NOT NULL,
	compilation bool NULL,
	artist_name varchar(255) NULL,
	CONSTRAINT albums_pkey PRIMARY KEY (album_name, album_year),
	CONSTRAINT albums_artist_name_fkey FOREIGN KEY (artist_name) REFERENCES library.artists(name)
);

CREATE TABLE tracks (
	id varchar(255) NOT NULL,
	bit_rate int4 NULL,
	bpm int4 NULL,
	"comments" varchar(255) NULL,
	composer varchar(255) NULL,
	date_added timestamp NULL,
	date_modified timestamp NULL,
	disc_number int4 NULL,
	explicit bool NULL,
	genre varchar(255) NULL,
	kind varchar(255) NULL,
	"location" oid NULL,
	movie bool NULL,
	"name" varchar(255) NULL,
	play_count int4 NULL,
	play_dateutc timestamp NULL,
	playlist_id int4 NULL,
	podcast bool NULL,
	purchased bool NULL,
	rating int4 NULL,
	release_date timestamp NULL,
	sample_rate int4 NULL,
	"size" int8 NULL,
	skip_count int4 NULL,
	skip_date timestamp NULL,
	track_number int4 NULL,
	track_type varchar(255) NULL,
	album_name varchar(255) NULL,
	album_year int4 NULL,
	artist_name varchar(255) NULL,
	CONSTRAINT tracks_pkey PRIMARY KEY (id),
	CONSTRAINT tracks_album_id_fkey FOREIGN KEY (album_name, album_year) REFERENCES library.albums(album_name, album_year),
	CONSTRAINT tracks_artist_name_fkey FOREIGN KEY (artist_name) REFERENCES library.artists(name)
);

CREATE TABLE album_track_disc_info (
	album_album_name varchar(255) NOT NULL,
	album_album_year int4 NOT NULL,
	track_disc_info int4 NULL,
	track_disc_info_key int4 NOT NULL,
	CONSTRAINT album_track_disc_info_pkey PRIMARY KEY (album_album_name, album_album_year, track_disc_info_key),
	CONSTRAINT fk940khf942uj7ncn05otgn7rj9 FOREIGN KEY (album_album_name, album_album_year) REFERENCES library.albums(album_name, album_year)
);

CREATE TABLE playlists (
	id varchar(255) NOT NULL,
	has_all_items bool NULL,
	is_master bool NULL,
	is_smart bool NULL,
	"name" varchar(255) NULL,
	CONSTRAINT playlists_pkey PRIMARY KEY (id)
);

CREATE TABLE tracks_in_playlist (
	playlist_id varchar(255) NOT NULL,
	track_id varchar(255) NOT NULL,
	CONSTRAINT fk3w0n2ny9wcru2fe5xibn9qprs FOREIGN KEY (track_id) REFERENCES library.tracks(id),
	CONSTRAINT fkqxkmrrx0nojbpi863sep8siq8 FOREIGN KEY (playlist_id) REFERENCES library.playlists(id)
);