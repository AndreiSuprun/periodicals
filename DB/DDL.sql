create table frequencies
(
    id          int auto_increment,
    name        varchar(255) not null,
    value       int          not null,
    description varchar(255) not null,
    constraint frequencies_id_UNIQUE
        unique (id),
    constraint frequencies_name_UNIQUE
        unique (name)
);

alter table frequencies
    add primary key (frequency_id);

create table payments
(
    id      int unsigned auto_increment,
    user_id int unsigned  not null,
    amount  decimal(7, 2) not null,
    date    datetime      not null,
    primary key (id, user_id)
);

create index payment_user_user_fk
    on payments (user_id);

create table periodical_categories
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    description varchar(510) not null,
    constraint periodicals_categories_name_UNIQUE
        unique (name)
);

create table publishers
(
    id   int auto_increment,
    name varchar(255) not null,
    constraint publisher_id_UNIQUE
        unique (id),
    constraint publisher_name_UNIQUE
        unique (name)
);

alter table publishers
    add primary key (id);

create table periodicals
(
    id                     int unsigned auto_increment,
    name                   varchar(255)         not null,
    publisher_id           int                  not null,
    month_price            decimal(5, 2)        not null,
    availability           tinyint(1) default 1 not null,
    periodical_category_id int                  not null,
    frequency_id           int                  not null,
    description            varchar(510)         not null,
    picture                varchar(255)         null,
    primary key (id, publisher_id, periodical_category_id, frequency_id),
    constraint periodicals_name_uk
        unique (name),
    constraint periodicals_publisher_publisher_id_fk
        foreign key (publisher_id) references publishers (id)
);

create fulltext index periodical_name
    on periodicals (periodical_name, periodical_description);

create index periodicals_frequencies_frequencies_id_fk_idx
    on periodicals (frequency_id);

create index periodicals_periodicals_categories_periodicals_categories_i_idx
    on periodicals (periodical_category_id);

create index periodicals_publisher_publisher_id_fk_idx
    on periodicals (publisher_id);

create table roles
(
    id   int auto_increment
        primary key,
    name varchar(45) not null,
    constraint role_UNIQUE
        unique (name)
);

create table subscription_periods
(
    id            int auto_increment
        primary key,
    name          varchar(255)  not null,
    rate          decimal(3, 2) not null,
    months_amount int           not null,
    constraint period_name_UNIQUE
        unique (name)
);

create table users
(
    id         int unsigned auto_increment
        primary key,
    role_id    int          not null,
    last_name  varchar(255) not null,
    first_name varchar(255) not null,
    email      varchar(255) not null,
    password   varchar(510) not null,
    constraint user_email_uk
        unique (email)
);

create table subscriptions
(
    id                     int unsigned auto_increment,
    user_id                int unsigned not null,
    periodical_id          int unsigned not null,
    subscription_period_id int          not null,
    start_date             datetime     not null,
    end_date               datetime     not null,
    payment_id             int unsigned not null,
    primary key (id, subscription_period_id),
    constraint subscription_reader_reader_fk
        foreign key (user_id) references users (user_id),
    constraint subscriptions_payment_fk
        foreign key (payment_id) references payments (payment_id)
);

create index subscription_periodicals_periodicals_fk
    on subscriptions (periodical_id);

create index subscription_subscription_period_subscription_period_fk
    on subscriptions (subscription_period_id);

create index role_id_idx
    on users (role_id);


