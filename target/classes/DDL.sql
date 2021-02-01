

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

create table if not exists periodicals_categories
(
	periodicals_category_id int auto_increment
		primary key,
	periodicals_category_name varchar(255) not null,
	periodicals_category_description varchar(255) not null,
	constraint periodicals_categories_name_UNIQUE
		unique (periodicals_category_name)
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
	id int unsigned auto_increment,
	name varchar(255) not null,
	publisher_id int not null,
	month_price decimal(5,2) not null,
	periodical_availablity bit not null,
	frequencies_id int not null,
	periodicals_categories_id int not null,
	periodical_description varchar(255) null,
	primary key (id, publisher_id, periodicals_categories_id, frequencies_id),
	constraint periodicals_name_uk
		unique (name),
	constraint periodicals_frequencies_frequencies_id_fk
		foreign key (frequencies_id) references frequencies (frequency_id),
	constraint periodicals_periodicals_categories_periodicals_categories_id_fk
		foreign key (periodicals_categories_id) references periodical_categories (periodical_category_id),
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
	roles_id int auto_increment
		primary key,
	role varchar(45) not null,
	constraint role_UNIQUE
		unique (role)
);

create table if not exists subscription_periods
(
	subscription_period_id int auto_increment
		primary key,
	period_name varchar(255) not null,
	rate decimal(3,2) not null,
	months_amount int not null,
	constraint period_name_UNIQUE
		unique (period_name)
);

create table if not exists users
(
	id int unsigned auto_increment
		primary key,
	role_id int not null,
	last_name varchar(255) not null,
	first_name varchar(255) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	constraint user_email_uk
		unique (email),
	constraint role_id
		foreign key (role_id) references roles (role_id)
);

create table if not exists subscriptions
(
	id int unsigned auto_increment,
	user_id int unsigned not null,
	periodicals_id int unsigned not null,
	subscription_period_id int not null,
	start_date datetime not null,
	primary key (id, subscription_period_id),
	constraint subscription_periodicals_periodicals_fk
		foreign key (periodicals_id) references periodicals (periodical_id),
	constraint subscription_reader_reader_fk
		foreign key (user_id) references users (user_id),
	constraint subscription_subscription_period_fk
		foreign key (subscription_period_id) references subscription_periods (subscription_period_id)
);

create table if not exists payments
(
	id int unsigned auto_increment,
	user_id int unsigned not null,,
	price decimal(7,2) not null,
	creation_date datetime not null,
	paid bit default b'0' not null,
	payment_date datetime not null,
	primary key (id, subscription_id),
	constraint payment_user_user_fk
		foreign key (user_id) references users (user_id)
);

create index subscription_subscription_period_subscription_period_fk
	on subscriptions (subscription_period_id);

create index role_id_idx
	on users (role_id);

