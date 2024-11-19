insert into users(id, name, join_date, ssn)
values (1, 'user1', now(), '111111-1111111');
insert into users(id, name, join_date, ssn)
values (2, 'user2', now(), '222222-2222222');
insert into users(id, name, join_date, ssn)
values (3, 'user3', now(), '333333-3333333');

insert into post(description, user_id)
values ('user1 post', 1);
insert into post(description, user_id)
values ('user2 post', 2);