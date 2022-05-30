create table tbl_role
(
    RoleId    bigint auto_increment
        primary key,
    CreatedAt datetime(6)  not null,
    RoleName  varchar(255) not null,
    UpdatedAt datetime(6)  null,
    constraint UK_kbfitspp3hw2noucb55ivhq9p
        unique (RoleName)
);

INSERT INTO blogv2.tbl_role (RoleId, CreatedAt, RoleName, UpdatedAt) VALUES (1, '2022-05-30 13:41:34', 'ADMIN', '2022-05-30 13:41:35');
INSERT INTO blogv2.tbl_role (RoleId, CreatedAt, RoleName, UpdatedAt) VALUES (2, '2022-05-30 13:41:37', 'USER', '2022-05-30 13:41:36');

create table if exists tbl_user
(
    UserId              bigint auto_increment
        primary key,
    Avatar              longtext     null,
    CreatedAt           datetime(6)  not null,
    Email               varchar(40)  not null,
    IsActive            bit          not null,
    Password            varchar(100) not null,
    Reset_password_code varchar(255) null,
    UpdatedAt           datetime(6)  null,
    Username            varchar(30)  not null,
    Verification_code   varchar(255) null,
    constraint UK_50y6vb6ahkc2wap3mqplpofy4
        unique (Email),
    constraint UK_fmqr00479xd8fcsafq0oo107k
        unique (Username)
);

INSERT INTO blogv2.tbl_user (UserId, Avatar, CreatedAt, Email, IsActive, Password, Reset_password_code, UpdatedAt, Username, Verification_code) VALUES (1, 'assets/images/portrait/small/avatar-s-7.jpg', '2022-05-30 13:41:51.797000', 'tuanpa.tpa@gmail.com', true, '$2a$10$rSZbl61Rrh/Q8sX1ZesLxeyO5J5FJUrIfHzO/GHP3S8idCd22XbRm', null, '2022-05-30 13:41:51.797000', 'tuanpa.tpa', 'yiz1VM6yMd1pj4HA7BykmirAgJ5XrtPLPDAjbviML9UBEQfj26F9XjuG73wISBM8');



create table if exists tbl_user_role
(
    UserId bigint not null,
    RoleId bigint not null,
    primary key (UserId, RoleId),
    constraint FK52to6f3y19prgr8h70expcce2
        foreign key (RoleId) references tbl_role (RoleId),
    constraint FKji1t9lrc1oqsqoevwepmvmrka
        foreign key (UserId) references tbl_user (UserId)
);

INSERT INTO blogv2.tbl_user_role (UserId, RoleId) VALUES (1, 1);
INSERT INTO blogv2.tbl_user_role (UserId, RoleId) VALUES (1, 2);




create table if exists tbl_category
(
    CategoryId   bigint auto_increment
    primary key,
    CategoryName text charset utf8mb3 null,
    CreatedAt    datetime(6)          not null,
    Icon         varchar(50)          null,
    UpdatedAt    datetime(6)          null
    );

INSERT INTO blogv2.tbl_category (CategoryId, CategoryName, CreatedAt, Icon, UpdatedAt) VALUES (1, 'Món Ăn', '2022-05-30 13:42:49', 'shopping-cart', '2022-05-30 13:42:50');
INSERT INTO blogv2.tbl_category (CategoryId, CategoryName, CreatedAt, Icon, UpdatedAt) VALUES (2, 'Âm Nhạc', '2022-05-30 14:02:01', 'command', '2022-05-30 14:02:02');
INSERT INTO blogv2.tbl_category (CategoryId, CategoryName, CreatedAt, Icon, UpdatedAt) VALUES (3, 'Video', '2022-05-30 14:03:04', 'video', '2022-05-30 14:03:06');
INSERT INTO blogv2.tbl_category (CategoryId, CategoryName, CreatedAt, Icon, UpdatedAt) VALUES (4, 'Trích Dẫn', '2022-05-30 14:03:54', 'hash', '2022-05-30 14:03:55');
