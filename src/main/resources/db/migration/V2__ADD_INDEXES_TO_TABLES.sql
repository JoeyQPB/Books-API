-- V2__add_indexes_to_tables.sql

-- add index in table publisher_tb by name
CREATE INDEX idx_publisher_name ON publisher_tb USING BTREE (name);

-- add index in table book_tb by name
CREATE INDEX idx_book_name ON book_tb USING BTREE (name);

-- add index in table book_tb author_tb by name
CREATE INDEX idx_author_name ON author_tb USING BTREE (name);

-- add index in table book_tb review_tb by book_id
CREATE INDEX idx_review_book_id ON review_tb USING BTREE (book_id);

-- add index in table book_tb tb_book_author by ids
CREATE INDEX idx_book_author_tb_book_id ON book_author_tb USING BTREE (book_id);
CREATE INDEX idx_book_author_tb_author_id ON book_author_tb USING BTREE (author_id);
