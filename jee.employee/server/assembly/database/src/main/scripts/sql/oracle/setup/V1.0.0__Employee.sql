--------------------------------------------------------
--  DDL for Table ADDRESS
--------------------------------------------------------

  CREATE TABLE "ADDRESS" 
   (	
    "ADDRESS_ID" NUMBER(19,0), 
	"CITY" VARCHAR2(255 CHAR), 
	"COUNTRY" VARCHAR2(255 CHAR), 
	"P_CODE" VARCHAR2(255 CHAR), 
	"PROVINCE" VARCHAR2(255 CHAR), 
	"STREET" VARCHAR2(255 CHAR)
   );
--------------------------------------------------------
--  DDL for Table DEGREE
--------------------------------------------------------

  CREATE TABLE "DEGREE" 
   (	
    "DEGREE_ID" NUMBER(19,0), 
	"NAME" VARCHAR2(255 CHAR), 
	"EMP_ID" NUMBER(19,0)
   );
--------------------------------------------------------
--  DDL for Table EMAIL
--------------------------------------------------------

  CREATE TABLE "EMAIL" 
   (	
   	"EMAIL_ID" NUMBER(19,0),
    "EMAIL_TYPE" VARCHAR2(255 CHAR), 
	"EMAIL_ADDRESS" VARCHAR2(255 CHAR), 
	"EMP_ID" NUMBER(19,0)
   );
--------------------------------------------------------
--  DDL for Table EMP_JOB
--------------------------------------------------------

  CREATE TABLE "EMP_JOB" 
   (	
    "EMP_ID" NUMBER(19,0), 
	"TITLE_ID" NUMBER(19,0)
   );
--------------------------------------------------------
--  DDL for Table PARKINGSPACE
--------------------------------------------------------

  CREATE TABLE "PARKINGSPACE" 
   (	
    "PS_ID" NUMBER(19,0), 
	"PARKNO" NUMBER(19,0),
	"GPSDATA" VARCHAR2(255 CHAR), 
	"DESCRIPTION" VARCHAR2(255 CHAR)
   );
--------------------------------------------------------
--  DDL for Table EMPLOYEE
--------------------------------------------------------

  CREATE TABLE "EMPLOYEE" 
   (	
    "EMP_ID" NUMBER(19,0), 
	"F_NAME" VARCHAR2(255 CHAR), 
	"GENDER" VARCHAR2(255 CHAR), 
	"L_NAME" VARCHAR2(255 CHAR), 
	"VERSION" NUMBER(19,0), 
	"END_DATE" TIMESTAMP (6), 
	"START_DATE" TIMESTAMP (6), 
	"MANAGER_ID" NUMBER(19,0), 
	"ADDR_ID" NUMBER(19,0),
	"PARKINGKSPACE_ID" NUMBER(19,0)
   );
--------------------------------------------------------
--  DDL for Table JOBTITLE
--------------------------------------------------------

  CREATE TABLE "JOBTITLE" 
   (	
    "JOB_ID" NUMBER(19,0), 
	"TITLE" VARCHAR2(255 CHAR)
   );
--------------------------------------------------------
--  DDL for Table LPROJECT
--------------------------------------------------------

  CREATE TABLE "LPROJECT" 
   (	
    "PROJ_ID" NUMBER(19,0), 
	"BUDGET" NUMBER(19,4), 
	"MILESTONE" TIMESTAMP (6)
   );
--------------------------------------------------------
--  DDL for Table PHONE
--------------------------------------------------------

  CREATE TABLE "PHONE" 
   (	
    "TYPE" VARCHAR2(255 CHAR), 
	"AREA_CODE" VARCHAR2(255 CHAR), 
	"P_NUMBER" VARCHAR2(255 CHAR), 
	"EMP_ID" NUMBER(19,0)
   );
--------------------------------------------------------
--  DDL for Table PROJ_EMP
--------------------------------------------------------

  CREATE TABLE "PROJ_EMP" 
   (	
    "EMP_ID" NUMBER(19,0), 
	"PROJ_ID" NUMBER(19,0)
   );
--------------------------------------------------------
--  DDL for Table PROJECT
--------------------------------------------------------

  CREATE TABLE "PROJECT" 
   (	
    "PROJ_ID" NUMBER(19,0), 
	"PROJ_TYPE" VARCHAR2(31 CHAR), 
	"DESCRIP" VARCHAR2(255 CHAR), 
	"PROJ_NAME" VARCHAR2(255 CHAR), 
	"VERSION" NUMBER(19,0), 
	"LEADER_ID" NUMBER(19,0),
	"STATUS" VARCHAR2(8)
   );
--------------------------------------------------------
--  DDL for Table RESPONS
--------------------------------------------------------

  CREATE TABLE "RESPONSE" 
   (	
    "EMP_ID" NUMBER(19,0), 
	"RESPONSIBILITY" VARCHAR2(255 CHAR), 
	"PRIORITY" NUMBER(10,0)
   );
--------------------------------------------------------
--  DDL for Table SALARY
--------------------------------------------------------

  CREATE TABLE "SALARY" 
   (	
    "EMP_ID" NUMBER(19,0), 
	"SALARY" NUMBER(19,4)
   );
--------------------------------------------------------
--  Constraints for Table ADDRESS
--------------------------------------------------------

  ALTER TABLE "ADDRESS" ADD PRIMARY KEY ("ADDRESS_ID");
  ALTER TABLE "ADDRESS" MODIFY ("ADDRESS_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DEGREE
--------------------------------------------------------

  ALTER TABLE "DEGREE" ADD PRIMARY KEY ("DEGREE_ID");
  ALTER TABLE "DEGREE" MODIFY ("DEGREE_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EMP_JOB
--------------------------------------------------------

  ALTER TABLE "EMP_JOB" ADD PRIMARY KEY ("EMP_ID", "TITLE_ID");
  ALTER TABLE "EMP_JOB" MODIFY ("TITLE_ID" NOT NULL ENABLE);
  ALTER TABLE "EMP_JOB" MODIFY ("EMP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PARKINGSPACE
--------------------------------------------------------

  ALTER TABLE "PARKINGSPACE" ADD PRIMARY KEY ("PS_ID");
  ALTER TABLE "PARKINGSPACE" MODIFY ("PS_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EMPLOYEE
--------------------------------------------------------

  ALTER TABLE "EMPLOYEE" ADD PRIMARY KEY ("EMP_ID");
  ALTER TABLE "EMPLOYEE" MODIFY ("EMP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table JOBTITLE
--------------------------------------------------------

  ALTER TABLE "JOBTITLE" ADD PRIMARY KEY ("JOB_ID");
  ALTER TABLE "JOBTITLE" MODIFY ("JOB_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LPROJECT
--------------------------------------------------------

  ALTER TABLE "LPROJECT" ADD PRIMARY KEY ("PROJ_ID");
  ALTER TABLE "LPROJECT" MODIFY ("PROJ_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PHONE
--------------------------------------------------------

  ALTER TABLE "PHONE" ADD PRIMARY KEY ("TYPE", "EMP_ID");
  ALTER TABLE "PHONE" MODIFY ("EMP_ID" NOT NULL ENABLE);
  ALTER TABLE "PHONE" MODIFY ("TYPE" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PROJ_EMP
--------------------------------------------------------

  ALTER TABLE "PROJ_EMP" ADD PRIMARY KEY ("EMP_ID", "PROJ_ID");
  ALTER TABLE "PROJ_EMP" MODIFY ("PROJ_ID" NOT NULL ENABLE);
  ALTER TABLE "PROJ_EMP" MODIFY ("EMP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PROJECT
--------------------------------------------------------

  ALTER TABLE "PROJECT" ADD PRIMARY KEY ("PROJ_ID");
  ALTER TABLE "PROJECT" MODIFY ("PROJ_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SALARY
--------------------------------------------------------

  ALTER TABLE "SALARY" ADD PRIMARY KEY ("EMP_ID");
  ALTER TABLE "SALARY" MODIFY ("EMP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table DEGREE
--------------------------------------------------------

  ALTER TABLE "DEGREE" ADD CONSTRAINT "FK_DEGREE_EMP_ID" FOREIGN KEY ("EMP_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EMAIL
--------------------------------------------------------

  ALTER TABLE "EMAIL" ADD CONSTRAINT "FK_EMAIL_EMP_ID" FOREIGN KEY ("EMP_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EMP_JOB
--------------------------------------------------------

  ALTER TABLE "EMP_JOB" ADD CONSTRAINT "FK_EMP_JOB_EMP_ID" FOREIGN KEY ("EMP_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
  ALTER TABLE "EMP_JOB" ADD CONSTRAINT "FK_EMP_JOB_TITLE_ID" FOREIGN KEY ("TITLE_ID")
	  REFERENCES "JOBTITLE" ("JOB_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EMPLOYEE
--------------------------------------------------------

  ALTER TABLE "EMPLOYEE" ADD CONSTRAINT "FK_EMPLOYEE_ADDR_ID" FOREIGN KEY ("ADDR_ID")
	  REFERENCES "ADDRESS" ("ADDRESS_ID") ENABLE;
  ALTER TABLE "EMPLOYEE" ADD CONSTRAINT "FK_EMPLOYEE_MANAGER_ID" FOREIGN KEY ("MANAGER_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
  ALTER TABLE "EMPLOYEE" ADD CONSTRAINT "FK_EMPLOYEE_PARKINGSPACE_ID" FOREIGN KEY ("PARKINGKSPACE_ID")
	  REFERENCES "PARKINGSPACE" ("PS_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LPROJECT
--------------------------------------------------------

  ALTER TABLE "LPROJECT" ADD CONSTRAINT "FK_LPROJECT_PROJ_ID" FOREIGN KEY ("PROJ_ID")
	  REFERENCES "PROJECT" ("PROJ_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PHONE
--------------------------------------------------------

  ALTER TABLE "PHONE" ADD CONSTRAINT "FK_PHONE_EMP_ID" FOREIGN KEY ("EMP_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PROJ_EMP
--------------------------------------------------------

  ALTER TABLE "PROJ_EMP" ADD CONSTRAINT "FK_PROJ_EMP_EMP_ID" FOREIGN KEY ("EMP_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
  ALTER TABLE "PROJ_EMP" ADD CONSTRAINT "FK_PROJ_EMP_PROJ_ID" FOREIGN KEY ("PROJ_ID")
	  REFERENCES "PROJECT" ("PROJ_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PROJECT
--------------------------------------------------------

  ALTER TABLE "PROJECT" ADD CONSTRAINT "FK_PROJECT_LEADER_ID" FOREIGN KEY ("LEADER_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESPONSE
--------------------------------------------------------

  ALTER TABLE "RESPONSE" ADD CONSTRAINT "FK_RESPONSE_EMP_ID" FOREIGN KEY ("EMP_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SALARY
--------------------------------------------------------

  ALTER TABLE "SALARY" ADD CONSTRAINT "FK_SALARY_EMP_ID" FOREIGN KEY ("EMP_ID")
	  REFERENCES "EMPLOYEE" ("EMP_ID") ENABLE;
	  
--------------------------------------------------------
--  DDL for Sequence SEQ_GEN_SEQUENCE
--------------------------------------------------------

  CREATE SEQUENCE  "SEQ_GEN_SEQUENCE"  INCREMENT BY 1 START WITH 1000 NOMAXVALUE NOMINVALUE NOCACHE NOORDER  NOCYCLE ;
--  CREATE SEQUENCE  "SEQ_EMPLOYEE"  INCREMENT BY 1 START WITH 1000 NOMAXVALUE NOMINVALUE NOCACHE NOORDER  NOCYCLE ;
	  
  
  