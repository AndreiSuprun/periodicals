create table if not exists frequencies
(
	frequency_id int auto_increment,
	frequency_name varchar(255) not null,
	frequency_value int not null,
	frequency_description varchar(255) not null,
	constraint frequencies_id_UNIQUE
		unique (frequency_id),
	constraint frequencies_name_UNIQUE
		unique (frequency_name)
);

alter table frequencies
	add primary key (frequency_id);

create table if not exists payments
(
	payment_id int unsigned auto_increment,
	user_id int unsigned not null,
	payment_amount decimal(7,2) not null,
	payment_date datetime not null,
	payment_paid bit default b'0' not null,
	primary key (payment_id, user_id)
);

create index payment_user_user_fk
	on payments (user_id);

create table if not exists periodical_categories
(
	periodical_category_id int auto_increment
		primary key,
	periodical_category_name varchar(255) not null,
	periodical_category_description varchar(510) not null,
	constraint periodicals_categories_name_UNIQUE
		unique (periodical_category_name)
);

create table if not exists publishers
(
	publisher_id int auto_increment,
	publisher_name varchar(255) not null,
	constraint publisher_id_UNIQUE
		unique (publisher_id),
	constraint publisher_name_UNIQUE
		unique (publisher_name)
);

alter table publishers
	add primary key (publisher_id);

create table if not exists periodicals
(
	periodical_id int unsigned auto_increment,
	periodical_name varchar(255) not null,
	publisher_id int not null,
	periodical_month_price decimal(5,2) not null,
	periodical_availability tinyint(1) default 1 not null,
	frequency_id int not null,
	periodical_category_id int not null,
	periodical_description varchar(510) null,
	periodical_picture varchar(255) null,
	primary key (periodical_id, publisher_id, periodical_category_id, frequency_id),
	constraint periodicals_name_uk
		unique (periodical_name),
	constraint periodicals_publisher_publisher_id_fk
		foreign key (publisher_id) references publishers (publisher_id)
);

create index periodicals_frequencies_frequencies_id_fk_idx
	on periodicals (frequency_id);

create index periodicals_periodicals_categories_periodicals_categories_i_idx
	on periodicals (periodical_category_id);

create index periodicals_publisher_publisher_id_fk_idx
	on periodicals (publisher_id);

create table if not exists roles
(
	role_id int auto_increment
		primary key,
	role_name varchar(45) not null,
	constraint role_UNIQUE
		unique (role_name)
);

create table if not exists subscription_periods
(
	subscription_period_id int auto_increment
		primary key,
	subscription_period_name varchar(255) not null,
	subscription_period_rate decimal(3,2) not null,
	subscription_period_months_amount int not null,
	constraint period_name_UNIQUE
		unique (subscription_period_name)
);

create table if not exists users
(
	user_id int unsigned auto_increment
		primary key,
	role_id int not null,
	last_name varchar(255) not null,
	first_name varchar(255) not null,
	email varchar(255) not null,
	password varchar(510) not null,
	constraint user_email_uk
		unique (email)
);

create table if not exists subscriptions
(
	subscription_id int unsigned auto_increment,
	user_id int unsigned not null,
	periodical_id int unsigned not null,
	subscription_period_id int not null,
	subscription_start_date datetime not null,
	subscription_end_date datetime not null,
	payment_id int unsigned not null,
	primary key (subscription_id, subscription_period_id),
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

