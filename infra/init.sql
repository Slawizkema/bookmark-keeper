create table if not exists category
(
    id   SERIAL PRIMARY KEY NOT NULL,
    name TEXT
);
CREATE UNIQUE INDEX if not exists category_id_uindex ON category (id);
CREATE SEQUENCE if not exists category_id_seq START WITH 3 INCREMENT BY 1;

CREATE TABLE if not exists bookmark
(
    id          SERIAL PRIMARY KEY NOT NULL,
    message_id  text               NOT NULL,
    type        TEXT,
    category_id int,
    CONSTRAINT bookmark_category_id_fk FOREIGN KEY (category_id) REFERENCES category (id),
    url         TEXT,
    body        TEXT
);
CREATE SEQUENCE if not exists bookmark_id_seq START WITH 3 INCREMENT BY 1;
CREATE UNIQUE INDEX if not exists bookmark_id_uindex ON bookmark (id);

CREATE TABLE if not exists tag
(
    id   SERIAL NOT NULL,
    name TEXT
);
CREATE UNIQUE INDEX if not exists tag_id_uindex ON tag (id);
CREATE SEQUENCE if not exists tag_id_seq START WITH 3 INCREMENT BY 1;

create table if not exists tag_bookmark
(
    bookmark_id int REFERENCES bookmark (id) ON UPDATE CASCADE ON DELETE CASCADE,
    tag_id      int REFERENCES tag (id) ON UPDATE CASCADE,
    CONSTRAINT bookmark_tag_pkey PRIMARY KEY (bookmark_id, tag_id)
);

insert into category (name)
values ('prog');
insert into category (name)
values ('life');
