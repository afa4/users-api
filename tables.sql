create table USER
(
    ID BIGINT identity
        constraint USER_PK
            primary key,
    NAME VARCHAR(100),
    EMAIL VARCHAR(100)
        unique,
    PASSWORD VARCHAR(100)
);

create table PHONE
(
    ID BIGINT identity
        constraint PHONE_PK
            primary key,
    DDD VARCHAR(3),
    NUMBER VARCHAR(10),
    USER_ID BIGINT
        constraint PHONE_USER_ID_FK
            references USER
);

