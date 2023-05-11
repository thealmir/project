-- primary key == unique not null
create table fibers(

    id bigserial,
    constraint pk_fibers primary key (id),

    section text not null,

    creation_date timestamp not null,

    comment_to bigint,
    constraint fk_fibers_id foreign key (comment_to) references fibers(id)

);

insert into fibers (section, creation_date, comment_to)
values ('first section ever', current_timestamp, null);

insert into fibers (section, creation_date, comment_to)
values ('wow', current_timestamp, 1);

insert into fibers (section, creation_date, comment_to)
values ('second section ever', current_timestamp, null),
       ('third section ever', current_timestamp, null),
       ('forth section ever', current_timestamp, null),
       ('fifth section ever', current_timestamp, null),
       ('sixth section ever', current_timestamp, null),
       ('seventh section ever', current_timestamp, null),
       ('eighth section ever', current_timestamp, null),
       ('ninth section ever', current_timestamp, null);

insert into fibers (section, creation_date, comment_to)
values ('time check section', current_timestamp, null);

create table admins (

        id bigserial,
        constraint pk_admins primary key (id),

        login text not null,

        password timestamp not null

);

alter table admins
alter column password type varchar;

alter table fibers
drop constraint fk_fibers_id,

add constraint fk_fibers_id foreign key (comment_to) references fibers(id)
on delete cascade;

create table files (

    id bigserial,
    constraint pk_files primary key (id),

    fiber_id bigint,
    constraint fk_fibers_id foreign key (fiber_id) references fibers(id)
    on delete cascade,

    name varchar not null

);