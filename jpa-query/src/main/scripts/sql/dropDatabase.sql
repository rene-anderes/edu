ALTER TABLE BOOK DROP CONSTRAINT BOOK_PUBLISHER_ID;
ALTER TABLE AUTHOR_BOOK DROP CONSTRAINT AUTHOR_BOOKBOOK_ID;
ALTER TABLE AUTHOR_BOOK DROP CONSTRAINT AUTHORBOOKAUTHORID;
DROP TABLE AUTHOR;
DROP TABLE BOOK;
DROP TABLE PUBLISHER;
DROP TABLE PHONEBOOK;
DROP TABLE AUTHOR_BOOK;
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN';