-- makefile for mysql database for klipapi
-- by Jakub Wawak
-- kubawawak@gmail.com
-- all rights reserved
CREATE DATABASE IF NOT EXISTS klipapi_database;
USE klipapi_database;
SET SQL_MODE = 'ALLOW_INVALID_DATES';
-- loading tables

CREATE TABLE HEALTH
(
    health_database_version VARCHAR(10),
    health_database_status INT,

)
-- table for storing bucket data
CREATE TABLE BUCKET
(
    bucket_id INT AUTO_INCREMENT PRIMARY KEY,
    bucket_sid VARCHAR(20),
    bucket_archived INT,
    bucket_time TIMESTAMP,
    bucket_note TEXT,
    bucket_hash_password VARCHAR(100)
) AUTO_INCREMENT = 10000;
-- table for storing klip object data
CREATE TABLE KLIP
(
    klip_id INT AUTO_INCREMENT PRIMARY KEY,
    bucket_id INT,
    klip_time TIMESTAMP,
    klip_active INT,
    klip_session_id INT,
    klip_source TEXT,
    klip_blob BLOB,

    CONSTRAINT fk_klip FOREIGN KEY (bucket_id) REFERENCES BUCKET(bucket_id)
);
-- table for storing session data
CREATE TABLE SESSION
(
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    bucket_id INT,
    session_token VARCHAR(10),
    session_start TIMESTAMP,
    session_stop TIMESTAMP,
    session_mac VARCHAR(30),
    session_active INT,

    CONSTRAINT fk_session FOREIGN KEY (bucket_id) REFERENCES BUCKET(bucket_id)
);
-- table for storing klip object data
CREATE TABLE KLIP_LOG
(
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    bucket_id INT,
    session_id INT,
    klip_id INT,
    log_code VARCHAR(50),
    log_desc VARCHAR(400),
    log_timestamp TIMESTAMP,

    CONSTRAINT fk_kliplog1 FOREIGN KEY  (bucket_id) REFERENCES BUCKET(bucket_id),
    CONSTRAINT fk_kliplog2 FOREIGN KEY (session_id) REFERENCES SESSION(session_id)
);
