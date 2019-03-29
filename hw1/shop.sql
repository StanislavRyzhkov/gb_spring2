
/*
    Примерный скрипт для создания базы данных интернет-магазина
    тканей для СУБД PostgresQL
*/

/*
    Таблица пользователя стандартная
 */
create table if not exists app_user
(
	id bigserial not null
		constraint app_user_pkey
			primary key,
	email varchar(255) not null
		constraint app_user_email_unique
			unique,
	login varchar(255) not null
		constraint app_user_login_unique
			unique,
	password_hash varchar(255) not null,
	first_name varchar(255),
	patronimic_name varchar(255),
	second_name varchar(255)
);

create table if not exists app_role
(
	id bigserial not null
		constraint app_role_pkey
			primary key,
	value_of_role varchar(255)
);


/*
    Таблицы app_user и app_role связаны отношением "Многие ко многим"
    Создается промежуточная таблица app_user_role
*/
create table if not exists app_user_role
(
	role_id bigint not null
		constraint role_fk
			references app_role,
	user_id bigint not null
		constraint user_fk
			references app_user,
	constraint app_user_role_pkey
		primary key (role_id, user_id)
);

/*
    У товара должен быть артикул (vendor_code) - уникальное
    цифро-буквенное обозначение в системе учета продавца
    Из собственных полей - краткое и полное название, описание
    Все остальное можно вынести в отдельные таблицы, который
    будут ссылаться на id товара как на внешний ключ
*/

create table if not exists app_product
(
  id bigserial not null
    constraint app_product_pkey
      primary key,
  vendor_code varchar(32) not null
    constraint app_product_vendor_code_unique
      unique,
  short_name varchar(32),
  full_name varchar(128),
  description varchar(1000)
);

create table if not exists app_price
(
  id bigserial not null
    constraint app_price_pkey
      primary key,
  old_price integer,
  present_price integer,
  new_price integer,
  product_id bigint
    constraint price_product_fk
      references app_product,
);

/*
    Категория товара выносится в отдельную таблицу
    Под категорией понимается тип текстильного изделия (шелк, бархат и т.д.)
    У категории может быть скидка
*/

create table if not exists app_category
(
  id bigserial not null
    constraint app_category_pkey
      primary key,
  name_of_category varchar(32) not null,
  discount real,
  product_id bigint
    constraint category_product_fk
      references app_product,
);

/*
    Минимальным элементом корзины будет position (позиция)
    У позиции два внешних ключа: пользователь и продукт.
    Соответственно, позиция определенного пользователя с определенным продуктом
    должна быть уникальной. Фильтруя все позиции по пользователю,
    можно получить корзину пользователя.
    У позиции есть поле "количество товара" (count_of_product)
    Которое может быть дробным числом, т.к. товар измеряется в метрах
*/

create table if not exists app_position
(
  user_id bigint not null
		constraint position_user_fk
			references app_user,
	product_id bigint not null
		constraint position_product_fk
			references app_product,
	constraint app_user_product_pkey
		primary key (user_id, product_id),
  count_of_product real
);
