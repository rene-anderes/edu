ALTER TABLE JP DROP CONSTRAINT FK_JP_ID;
ALTER TABLE NP DROP CONSTRAINT FK_NP_ID;
ALTER TABLE ORDERITEM DROP CONSTRAINT ORDERITEM_ORDER_ID;
ALTER TABLE STUDENT DROP CONSTRAINT STUDENTCLASSROOMID;
ALTER TABLE EMP_PROJ DROP CONSTRAINT EMP_PROJ_PROJ_ID;
ALTER TABLE EMP_PROJ DROP CONSTRAINT FK_EMP_PROJ_EMP_ID;
ALTER TABLE STORY_TASK DROP CONSTRAINT STORY_TASKSTORY_ID;
ALTER TABLE STORY_TASK DROP CONSTRAINT STORY_TASK_TASK_ID;
DROP TABLE EMPLOYEE;
DROP TABLE PROJECT;
DROP TABLE PERSONBASIC;
DROP TABLE CASH_PAYMENT;
DROP TABLE CREDITCARD_PAYMENT;
DROP TABLE PERSON;
DROP TABLE JP;
DROP TABLE NP;
DROP TABLE ORDERING;
DROP TABLE ORDERITEM;
DROP TABLE CLASSROOM;
DROP TABLE STUDENT;
DROP TABLE STORY;
DROP TABLE TASK;
DROP TABLE CAR;
DROP TABLE EMP_PROJ;
DROP TABLE STORY_TASK;
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN';
