create table am_account (
  id                        number(19) not null,
  ref_id                    varchar2(255),
  create_time               number(19),
  change_time               number(19),
  login_name                varchar2(255),
  password                  varchar2(255),
  salt                      varchar2(255),
  first_login               number(1),
  lock_time                 number(19),
  lock_reason               varchar2(255),
  login_fail_number         number(10),
  person_id                 number(19),
  constraint pk_am_account primary key (id))
;

create table am_company (
  id                        number(19) not null,
  ref_id                    varchar2(255),
  create_time               number(19),
  change_time               number(19),
  type                      varchar2(255),
  name                      varchar2(255),
  former_name               varchar2(255),
  address                   varchar2(255),
  constraint pk_am_company primary key (id))
;

create table am_department (
  id                        number(19) not null,
  ref_id                    varchar2(255),
  create_time               number(19),
  change_time               number(19),
  name                      varchar2(255),
  constraint pk_am_department primary key (id))
;

create table am_person (
  id                        number(19) not null,
  ref_id                    varchar2(255),
  create_time               number(19),
  change_time               number(19),
  name                      varchar2(255),
  email                     varchar2(255),
  phone_number              varchar2(255),
  company_id                number(19),
  department_id             number(19),
  constraint pk_am_person primary key (id))
;

create table am_role (
  id                        number(19) not null,
  ref_id                    varchar2(255),
  create_time               number(19),
  change_time               number(19),
  role_name                 varchar2(255),
  role_type                 varchar2(255),
  constraint pk_am_role primary key (id))
;


create table am_account_role (
  account_id                     number(19) not null,
  role_id                        number(19) not null,
  constraint pk_am_account_role primary key (account_id, role_id))
;
create sequence am_account_seq;

create sequence am_company_seq;

create sequence am_department_seq;

create sequence am_person_seq;

create sequence am_role_seq;

alter table am_account add constraint fk_am_account_person_1 foreign key (person_id) references am_person (id);
create index ix_am_account_person_1 on am_account (person_id);
alter table am_person add constraint fk_am_person_company_2 foreign key (company_id) references am_company (id);
create index ix_am_person_company_2 on am_person (company_id);
alter table am_person add constraint fk_am_person_department_3 foreign key (department_id) references am_department (id);
create index ix_am_person_department_3 on am_person (department_id);



alter table am_account_role add constraint fk_am_account_role_am_accou_01 foreign key (account_id) references am_account (id);

alter table am_account_role add constraint fk_am_account_role_am_role_02 foreign key (role_id) references am_role (id);
