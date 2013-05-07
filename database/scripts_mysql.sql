DROP TABLE ARTIFACT_ITEM;
DROP TABLE COLLOCATION;
DROP TABLE COLLOCATION_COMMENTS;
DROP TABLE PERSON;
DROP TABLE PERSON_RELATIONS;
DROP TABLE PERSON_MESSAGES;

CREATE TABLE ARTIFACT_ITEM(ID VARCHAR(32),PIC_ID VARCHAR(32), OWNER_ID VARCHAR(32),LATITUDE1 VARCHAR(100),LATITUDE2 VARCHAR(100),DESCRIPTION VARCHAR(500),TYPE VARCHAR(100),CREATE_DATE DATE,PRIMARY KEY(ID));
CREATE TABLE COLLOCATION(ID VARCHAR(32),TEMMPLATE_ID VARCHAR(32), OWNER_ID VARCHAR(32),PIC_ID VARCHAR(100),DESCRIPTION VARCHAR(500),CREATE_DATE DATE,PRIMARY KEY(ID));
CREATE TABLE COLLOCATION_COMMENTS(ID VARCHAR(32),COMMENTS VARCHAR(500), COLLOCATION_ID VARCHAR(32),OWNER_ID VARCHAR(100),CREATE_DATE DATE,PRIMARY KEY(ID));
CREATE TABLE PERSON(ID VARCHAR(32),PORTRAIT_ID VARCHAR(32), NICK VARCHAR(100),SIGNATURE VARCHAR(100),GENDER VARCHAR(20), MAIL VARCHAR(100),MOBILE VARCHAR(100), SINA VARCHAR(100),TENCENT VARCHAR(100),RENREN VARCHAR(100),DOUBAN VARCHAR(100),CREATE_DATE DATE,LOGIN_DATE DATE, PRIMARY KEY(ID));

ALTER TABLE PERSON ADD OBSERVERS_COUNTER DECIMAL(18,0);
ALTER TABLE PERSON ADD FANS_COUNTER DECIMAL(18,0);
ALTER TABLE PERSON ADD FRIENDS_COUNTER DECIMAL(18,0);
ALTER TABLE COLLOCATION ADD COMMENTS_COUNTER DECIMAL(18,0);

CREATE TABLE PERSON_RELATIONS(ID VARCHAR(32),PERSON_MASTER VARCHAR(32), PERSON_LINK VARCHAR(100),CREATE_DATE DATE,TYPE DECIMAL(1,0), PRIMARY KEY(ID));
CREATE TABLE PERSON_MESSAGES(ID VARCHAR(32),SEND_FROM VARCHAR(32), SEND_TO VARCHAR(32),MSG VARCHAR(500),CREATE_DATE DATE,TYPE DECIMAL(1,0), PRIMARY KEY(ID));
ALTER TABLE PERSON_MESSAGES ADD READ_ALREADY DECIMAL(18);
ALTER TABLE PERSON ADD PWD VARCHAR(32);

ALTER TABLE COLLOCATION ADD ILLEGAL DECIMAL(1);
ALTER TABLE COLLOCATION ADD SHOWN DECIMAL(1);
ALTER TABLE COLLOCATION ADD REPORT_MSG VARCHAR(500);
ALTER TABLE COLLOCATION ADD REPORT_BY VARCHAR(32);
ALTER TABLE COLLOCATION ADD ADORE_COUNTER VARCHAR(32);
ALTER TABLE COLLOCATION ADD DESCRIPTION VARCHAR(500);
ALTER TABLE COLLOCATION ADD ARTIFACT_ITEM_IDS VARCHAR(500);
ALTER TABLE COLLOCATION ADD TEMPLATE_ID VARCHAR(500);

ALTER TABLE PERSON ADD OFFENCE_REPORT VARCHAR(500);
ALTER TABLE PERSON ADD OFFENCE_REPORT_BY VARCHAR(32);
ALTER TABLE PERSON ADD OFFENCE_REPORT_DATE DATE;
ALTER TABLE PERSON ADD BIRTHDAY VARCHAR(20);
