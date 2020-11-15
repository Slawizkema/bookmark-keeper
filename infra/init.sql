create table category
(
    id   SERIAL PRIMARY KEY NOT NULL,
    name TEXT
);
CREATE UNIQUE INDEX category_id_uindex ON category (id);
CREATE SEQUENCE category_id_seq START WITH 3 INCREMENT BY 1;

CREATE TABLE bookmark
(
    id          SERIAL PRIMARY KEY NOT NULL,
    message_id  INT                NOT NULL,
    type        TEXT,
    category_id int,
    CONSTRAINT bookmark_category_id_fk FOREIGN KEY (category_id) REFERENCES category (id),
    url         TEXT,
    body        TEXT
);
CREATE SEQUENCE bookmark_id_seq START WITH 3 INCREMENT BY 1;
CREATE UNIQUE INDEX bookmark_id_uindex ON bookmark (id);

CREATE TABLE tag
(
    id   SERIAL NOT NULL,
    name TEXT
);
CREATE UNIQUE INDEX tag_id_uindex ON tag (id);
CREATE SEQUENCE tag_id_seq START WITH 3 INCREMENT BY 1;
drop table tag_bookmark;
create table tag_bookmark
(
    bookmark_id int REFERENCES bookmark (id) ON UPDATE CASCADE ON DELETE CASCADE,
    tag_id      int REFERENCES tag (id) ON UPDATE CASCADE,
    CONSTRAINT bookmark_tag_pkey PRIMARY KEY (bookmark_id, tag_id)
);