CREATE TABLE EMPLOYEE (ID BIGINT NOT NULL, F_NAME VARCHAR(200) NOT NULL, L_NAME VARCHAR(200) NOT NULL, PRIMARY KEY (ID));
CREATE TABLE PROJECT (ID BIGINT NOT NULL, PROJECT_NAME VARCHAR(255) NOT NULL, PRIMARY KEY (ID));
CREATE TABLE PERSONBASIC (ID BIGINT NOT NULL, BIRTHDAY DATE, F_NAME VARCHAR(200) NOT NULL, GENDER VARCHAR(255), L_NAME VARCHAR(200) NOT NULL, SALARY DECIMAL(8,2), PRIMARY KEY (ID));
CREATE TABLE CASH_PAYMENT (ID BIGINT NOT NULL, AMOUNT DECIMAL(8,2), CURRENCY VARCHAR(255), EXCHANGERATE DECIMAL(8,4), PRIMARY KEY (ID));
CREATE TABLE CREDITCARD_PAYMENT (ID BIGINT NOT NULL, AMOUNT DECIMAL(8,2), TYPE VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE PERSON (ID BIGINT NOT NULL, PERSON_TYPE VARCHAR(31), PERSON_NR INTEGER, PRIMARY KEY (ID));
CREATE TABLE JP (ID BIGINT NOT NULL, LEGALFORM VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE NP (ID BIGINT NOT NULL, LASTNAME VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE ORDERING (ID BIGINT NOT NULL, ORDERNUMBER BIGINT NOT NULL, PRIMARY KEY (ID));
CREATE TABLE ORDERITEM (ID BIGINT NOT NULL, DESCRIPTION VARCHAR(255) NOT NULL, ORDER_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE CLASSROOM (ID BIGINT NOT NULL, DESCRIPTION VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE STUDENT (ID BIGINT NOT NULL, STUDENTNAME VARCHAR(255), CLASSROOM_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE STORY (ID BIGINT NOT NULL, DESCRIPTION VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE TASK (ID BIGINT NOT NULL, DESCRIPTION VARCHAR(255), TASKNO INTEGER, PRIMARY KEY (ID));
CREATE TABLE CAR (ID VARCHAR(255) NOT NULL, COLOR VARCHAR(255), SERIALNUMBER INTEGER, PRIMARY KEY (ID));
CREATE TABLE EMP_PROJ (EMP_ID BIGINT NOT NULL, PROJ_ID BIGINT NOT NULL, PRIMARY KEY (EMP_ID, PROJ_ID));
CREATE TABLE STORY_TASK (STORY_ID BIGINT, SEQUENCENO INTEGER, TASK_ID BIGINT);
ALTER TABLE JP ADD CONSTRAINT FK_JP_ID FOREIGN KEY (ID) REFERENCES PERSON (ID);
ALTER TABLE NP ADD CONSTRAINT FK_NP_ID FOREIGN KEY (ID) REFERENCES PERSON (ID);
ALTER TABLE ORDERITEM ADD CONSTRAINT ORDERITEM_ORDER_ID FOREIGN KEY (ORDER_ID) REFERENCES ORDERING (ID);
ALTER TABLE STUDENT ADD CONSTRAINT STUDENTCLASSROOMID FOREIGN KEY (CLASSROOM_ID) REFERENCES CLASSROOM (ID);
ALTER TABLE EMP_PROJ ADD CONSTRAINT EMP_PROJ_PROJ_ID FOREIGN KEY (PROJ_ID) REFERENCES PROJECT (ID);
ALTER TABLE EMP_PROJ ADD CONSTRAINT FK_EMP_PROJ_EMP_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE (ID);
ALTER TABLE STORY_TASK ADD CONSTRAINT STORY_TASKSTORY_ID FOREIGN KEY (STORY_ID) REFERENCES STORY (ID);
ALTER TABLE STORY_TASK ADD CONSTRAINT STORY_TASK_TASK_ID FOREIGN KEY (TASK_ID) REFERENCES TASK (ID);
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(15), PRIMARY KEY (SEQ_NAME));
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0);
