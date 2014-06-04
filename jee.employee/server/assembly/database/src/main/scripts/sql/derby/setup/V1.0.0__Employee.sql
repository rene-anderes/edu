--------------------------------------------------------
--  DDL for Table ADDRESS
--------------------------------------------------------
CREATE TABLE ADDRESS (
	ADDRESS_ID BIGINT NOT NULL,
	CITY VARCHAR(255),
	COUNTRY VARCHAR(255),
	P_CODE VARCHAR(255),
	PROVINCE VARCHAR(255),
	STREET VARCHAR(255),
	CONSTRAINT TBL_ADDRESS_PK PRIMARY KEY (ADDRESS_ID)
);

--------------------------------------------------------
--  DDL for Table DEGREE
--------------------------------------------------------
CREATE TABLE DEGREE (
	DEGREE_ID BIGINT NOT NULL,
	NAME VARCHAR(255),
	EMP_ID BIGINT,
	CONSTRAINT TBL_DEGREE_PK PRIMARY KEY (DEGREE_ID)
);

--------------------------------------------------------
--  DDL for Table EMAIL
--------------------------------------------------------
CREATE TABLE EMAIL (
	EMAIL_ID BIGINT NOT NULL,
	EMAIL_ADDRESS VARCHAR(255),
	EMP_ID BIGINT,
	EMAIL_TYPE VARCHAR(255),
	CONSTRAINT TBL_EMAIL_PK PRIMARY KEY (EMAIL_ID)
);

--------------------------------------------------------
--  DDL for Table EMP_JOB
--------------------------------------------------------
CREATE TABLE EMP_JOB (
	EMP_ID BIGINT NOT NULL,
	TITLE_ID BIGINT NOT NULL,
	CONSTRAINT TBL_EMP_JOB_PK PRIMARY KEY (EMP_ID,TITLE_ID)
);

--------------------------------------------------------
--  DDL for Table EMPLOYEE
--------------------------------------------------------
CREATE TABLE EMPLOYEE (
	EMP_ID BIGINT NOT NULL,
	--   EMP_ID BIGINT NOT NULL DEFAULT GENERATED_BY_DEFAULT AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	F_NAME VARCHAR(255),
	GENDER VARCHAR(255),
	L_NAME VARCHAR(255),
	VERSION BIGINT,
	END_DATE TIMESTAMP,
	START_DATE TIMESTAMP,
	MANAGER_ID BIGINT,
	ADDR_ID BIGINT,
	CONSTRAINT TBL_EMPLOYEE_PK PRIMARY KEY (EMP_ID)
);

--------------------------------------------------------
--  DDL for Table JOBTITLE
--------------------------------------------------------
CREATE TABLE JOBTITLE (
	JOB_ID BIGINT NOT NULL,
	TITLE VARCHAR(255),
	CONSTRAINT TBL_JOBTITLE_PK PRIMARY KEY (JOB_ID)
);

--------------------------------------------------------
--  DDL for Table LPROJECT
--------------------------------------------------------
CREATE TABLE LPROJECT (
	PROJ_ID BIGINT NOT NULL,
	BUDGET DOUBLE,
	MILESTONE TIMESTAMP,
	CONSTRAINT TBL_LPROJECT_PK PRIMARY KEY (PROJ_ID)
);

--------------------------------------------------------
--  DDL for Table PHONE
--------------------------------------------------------
CREATE TABLE PHONE (
	TYPE VARCHAR(255) NOT NULL,
	AREA_CODE VARCHAR(255),
	P_NUMBER VARCHAR(255),
	EMP_ID BIGINT NOT NULL,
	CONSTRAINT TBL_PHONE_PK PRIMARY KEY (TYPE,EMP_ID)
);

--------------------------------------------------------
--  DDL for Table PROJ_EMP
--------------------------------------------------------
CREATE TABLE PROJ_EMP (
	EMP_ID BIGINT NOT NULL,
	PROJ_ID BIGINT NOT NULL,
	CONSTRAINT TBL_PROJ_EMP_PK PRIMARY KEY (EMP_ID,PROJ_ID)
);

--------------------------------------------------------
--  DDL for Table PROJECT
--------------------------------------------------------
CREATE TABLE PROJECT (
	PROJ_ID BIGINT NOT NULL,
	PROJ_TYPE VARCHAR(31),
	DESCRIP VARCHAR(255),
	PROJ_NAME VARCHAR(255),
	VERSION BIGINT,
	LEADER_ID BIGINT,
	CONSTRAINT TBL_PROJECT_PK PRIMARY KEY (PROJ_ID)
);

--------------------------------------------------------
--  DDL for Table RESPONS
--------------------------------------------------------
CREATE TABLE RESPONSE (
	EMP_ID BIGINT,
	RESPONSIBILITY VARCHAR(255),
	PRIORITY INTEGER
);

--------------------------------------------------------
--  DDL for Table SALARY
--------------------------------------------------------
CREATE TABLE SALARY (
	EMP_ID BIGINT NOT NULL,
	SALARY DOUBLE,
	CONSTRAINT TBL_SALARY_PK PRIMARY KEY (EMP_ID)
);

--------------------------------------------------------
--  Ref Constraints for Table DEGREE
--------------------------------------------------------
ALTER TABLE DEGREE ADD CONSTRAINT FK_DEGREE_EMP_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE(EMP_ID);

--------------------------------------------------------
--  Ref Constraints for Table EMAIL
--------------------------------------------------------
ALTER TABLE EMAIL ADD CONSTRAINT FK_EMAIL_EMP_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE(EMP_ID);

--------------------------------------------------------
--  Ref Constraints for Table EMP_JOB
--------------------------------------------------------
ALTER TABLE EMP_JOB	ADD CONSTRAINT FK_EMP_JOB_EMP_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE(EMP_ID);
ALTER TABLE EMP_JOB ADD	CONSTRAINT EMP_JOB_TITLE_ID FOREIGN KEY (TITLE_ID) REFERENCES JOBTITLE(JOB_ID);

--------------------------------------------------------
--  Ref Constraints for Table EMPLOYEE
--------------------------------------------------------
ALTER TABLE EMPLOYEE ADD CONSTRAINT EMPLOYEEMANAGER_ID FOREIGN KEY (MANAGER_ID) REFERENCES APP.EMPLOYEE(EMP_ID);
ALTER TABLE EMPLOYEE ADD CONSTRAINT EMPLOYEE_ADDR_ID FOREIGN KEY (ADDR_ID) REFERENCES ADDRESS(ADDRESS_ID);

--------------------------------------------------------
--  Ref Constraints for Table LPROJECT
--------------------------------------------------------
ALTER TABLE LPROJECT ADD CONSTRAINT LPROJECT_PROJ_ID FOREIGN KEY (PROJ_ID) REFERENCES PROJECT(PROJ_ID);

--------------------------------------------------------
--  Ref Constraints for Table PHONE
--------------------------------------------------------
ALTER TABLE PHONE ADD CONSTRAINT FK_PHONE_EMP_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE(EMP_ID);

--------------------------------------------------------
--  Ref Constraints for Table PROJ_EMP
--------------------------------------------------------
ALTER TABLE PROJ_EMP ADD CONSTRAINT FK_PROJ_EMP_EMP_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE(EMP_ID);
ALTER TABLE PROJ_EMP ADD CONSTRAINT PROJ_EMP_PROJ_ID FOREIGN KEY (PROJ_ID) REFERENCES PROJECT(PROJ_ID);

--------------------------------------------------------
--  Ref Constraints for Table PROJECT
--------------------------------------------------------
ALTER TABLE PROJECT ADD CONSTRAINT PROJECT_LEADER_ID FOREIGN KEY (LEADER_ID) REFERENCES EMPLOYEE(EMP_ID);

--------------------------------------------------------
--  Ref Constraints for Table RESPONSE
--------------------------------------------------------
ALTER TABLE RESPONSE ADD CONSTRAINT FK_RESPONSE_EMP_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE(EMP_ID);

--------------------------------------------------------
--  Ref Constraints for Table SALARY
--------------------------------------------------------
ALTER TABLE SALARY ADD CONSTRAINT FK_SALARY_EMP_ID FOREIGN KEY (EMP_ID) REFERENCES EMPLOYEE(EMP_ID);

--------------------------------------------------------
--  DDL for Sequence SEQ_GEN_SEQUENCE
--------------------------------------------------------
CREATE SEQUENCE SEQ_GEN_SEQUENCE AS BIGINT START WITH 1000 INCREMENT BY 1 NO MAXVALUE NO CYCLE;
