# add userId to tasks

# --- !Ups

alter table task
add column userId varchar(64);

update task
set userId = '885910';

alter table task
alter userId set not null;

# --- !Downs
alter table task drop column userId;
