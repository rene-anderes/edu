CREATE TABLE APPLICATION_LOG (
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, 
	CONTEXTMAP CLOB(64000), 
	CONTEXTSTACK VARCHAR(1000), 
	LEVEL VARCHAR(8), 
	LOGGERFQCN VARCHAR(255), 
	LOGGERNAME VARCHAR(1000), 
	MARKER VARCHAR(255), 
	MESSAGE VARCHAR(1000), 
	NANOTIME BIGINT, 
	SOURCE VARCHAR(1000), 
	THREADNAME VARCHAR(255), 
	THROWN CLOB(64000), 
	TIMEMILLIS BIGINT, 
	PRIMARY KEY (ID));
