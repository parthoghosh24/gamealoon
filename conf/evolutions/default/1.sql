# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table game (
  id                        bigint,
  title                     varchar(255),
  description               varchar(255),
  release_date              varchar(255),
  price                     varchar(255),
  rating                    varchar(255),
  publisher                 varchar(255),
  developer                 varchar(255),
  genere                    varchar(255),
  score                     float)
;

create table platform (
  id                        bigint not null,
  title                     varchar(255),
  description               varchar(255),
  manufacturer              varchar(255),
  constraint pk_platform primary key (id))
;

create sequence platform_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists game;

drop table if exists platform;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists platform_seq;

