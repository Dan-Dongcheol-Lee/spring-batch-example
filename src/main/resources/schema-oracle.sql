DROP TABLE PERSON;

CREATE TABLE PERSON (
	ID NUMBER NOT NULL PRIMARY KEY,
	NAME VARCHAR2(100 char),
	SUR_NAME VARCHAR2(100 char),
	AGE NUMBER(3),
    DATE_OF_BIRTH DATE,
    ADDRESS VARCHAR2(100 char)
);

DROP SEQUENCE PERSON_SEQ;

CREATE SEQUENCE PERSON_SEQ;


DROP SEQUENCE BATCH_RUN_ID_SEQ;

CREATE SEQUENCE BATCH_RUN_ID_SEQ;
