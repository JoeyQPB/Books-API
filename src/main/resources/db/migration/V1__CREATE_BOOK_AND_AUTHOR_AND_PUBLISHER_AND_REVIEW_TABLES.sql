-- V1 to create entities on book-store data base

-- create table publisher_tb
CREATE TABLE IF NOT EXISTS publisher_tb (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE
);

-- create table book_tb
CREATE TABLE IF NOT EXISTS book_tb (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    publisher_id UUID,
    FOREIGN KEY (publisher_id) REFERENCES publisher_tb(id)
);

-- create table author_tb
CREATE TABLE IF NOT EXISTS author_tb (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE
);

-- create table review_tb
CREATE TABLE IF NOT EXISTS review_tb (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comment TEXT NOT NULL,
    book_id UUID UNIQUE,
    FOREIGN KEY (book_id) REFERENCES book_tb(id)
);

-- associative table tb_book_author
CREATE TABLE IF NOT EXISTS book_author_tb (
    book_id UUID NOT NULL,
    author_id UUID NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book_tb(id),
    FOREIGN KEY (author_id) REFERENCES author_tb(id)
);

-- add a column to the relation One-to-Many between publisher_tb and book_tb
ALTER TABLE book_tb ADD CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id) REFERENCES publisher_tb (id);

-- add a column to the relation One-to-One between review_tb and book_tb
ALTER TABLE review_tb ADD CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES book_tb (id) ON DELETE CASCADE;

-- Rollback for this migration
--DROP TABLE IF EXISTS tb_book_author;
--DROP TABLE IF EXISTS review_tb;
--DROP TABLE IF EXISTS book_tb;
--DROP TABLE IF EXISTS author_tb;
--DROP TABLE IF EXISTS publisher_tb;
