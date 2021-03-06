CREATE TABLE RECIPE (ID BIGINT NOT NULL, LASTUPDATE TIMESTAMP, NOOFPERSON INTEGER NOT NULL, PREAMBLE VARCHAR(255), PREPARATION VARCHAR(500) NOT NULL, TITLE VARCHAR(200) NOT NULL, IMAGE_DESCRIPTION VARCHAR(50), IMAGE_URL VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE INGREDIENT (ID BIGINT NOT NULL, ANNOTATION VARCHAR(150), DESCRIPTION VARCHAR(150), QUANTITY VARCHAR(25), RECIPE_ID BIGINT, PRIMARY KEY (ID));
CREATE TABLE TAGS (RECIPE_ID BIGINT, TAGS VARCHAR(25));
ALTER TABLE INGREDIENT ADD CONSTRAINT INGREDIENTRECIPEID FOREIGN KEY (RECIPE_ID) REFERENCES RECIPE (ID);
ALTER TABLE TAGS ADD CONSTRAINT FK_TAGS_RECIPE_ID FOREIGN KEY (RECIPE_ID) REFERENCES RECIPE (ID);
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(15), PRIMARY KEY (SEQ_NAME));
CREATE VIEW TAG_COUNT AS SELECT TAGS, COUNT(*) AS NUMBER_OF_APPEARANCE FROM RECIPE, TAGS WHERE RECIPE.ID = TAGS.RECIPE_ID GROUP BY TAGS;
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0);
