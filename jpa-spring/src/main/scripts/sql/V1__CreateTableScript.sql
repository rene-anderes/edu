
--------------------------------- Table Recipe --------------------------------
CREATE TABLE RECIPE (
		-- ID BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY  (START WITH 1 ,INCREMENT BY 1), --
		UUID CHAR(36) NOT NULL,
		ADDINGDATE TIMESTAMP NOT NULL,
		LASTUPDATE TIMESTAMP NOT NULL,
		NOOFPERSON VARCHAR(10) NOT NULL,
		PREAMPLE VARCHAR(8000),
		PREPARATION VARCHAR(8000) NOT NULL,
		RATING INTEGER NOT NULL,
		TITLE VARCHAR(80) NOT NULL,
		VERSION INTEGER,
		IMAGE_DESCRIPTION VARCHAR(50),
		IMAGE_URL VARCHAR(255)
	);

CREATE UNIQUE INDEX SQL_RECIPE_IDX_01 ON RECIPE (UUID ASC);

ALTER TABLE RECIPE ADD CONSTRAINT SQL_RECIPE_PK PRIMARY KEY (UUID);

--------------------------------- Table Ingredient ----------------------------
CREATE TABLE INGREDIENT (
		ID BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY  (START WITH 1 ,INCREMENT BY 1),
		ANNOTATION VARCHAR(80),
		DESCRIPTION VARCHAR(80) NOT NULL,
		QUANTITY VARCHAR(20),
		RECIPE_ID CHAR(36)
	);

CREATE UNIQUE INDEX SQL_INGREDIENT_IDX_01 ON INGREDIENT (ID ASC);

CREATE INDEX SQL_INGREDIENT_IDX_02 ON INGREDIENT (RECIPE_ID ASC);

ALTER TABLE INGREDIENT ADD CONSTRAINT SQL_INGREDIENT_PK PRIMARY KEY (ID);

ALTER TABLE INGREDIENT ADD CONSTRAINT INGREDIENTRECIPEID FOREIGN KEY (RECIPE_ID)
	REFERENCES RECIPE (UUID);


--------------------------------- Table Tags ----------------------------------
CREATE TABLE TAGS (
		RECIPE_ID CHAR(36),
		TAGS VARCHAR(255)
	);

CREATE INDEX SQL_TAGS_IDX ON TAGS (RECIPE_ID ASC);

ALTER TABLE TAGS ADD CONSTRAINT FK_TAGS_RECIPE_ID FOREIGN KEY (RECIPE_ID)
	REFERENCES RECIPE (UUID);