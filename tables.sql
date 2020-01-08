create table USER
(
    ID BIGINT identity
        constraint USER_PK
            primary key,
    CREATED    TIMESTAMP NOT NULL,
    MODIFIED   TIMESTAMP,
    LAST_LOGIN TIMESTAMP,
    NAME       VARCHAR(255) NOT NULL,
    EMAIL      VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD   VARCHAR(255) NOT NULL,
    TOKEN      VARCHAR(255) UNIQUE NOT NULL
);

create table PHONE
(
    ID BIGINT identity
        constraint PHONE_PK
            primary key,
    DDD VARCHAR(3) NOT NULL,
    NUMBER VARCHAR(10) NOT NULL,
    USER_ID BIGINT
        constraint PHONE_USER_ID_FK
            references USER
);