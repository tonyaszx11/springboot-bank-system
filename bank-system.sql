create database bank;
use bank;
create table user(
	user_id int primary key auto_increment,
	user_login_name  varchar(10) unique not null, -- 登入名
	user_password   char(64) not null,
	user_name varchar(10), -- 名字
    	user_gender char(1) check(user_gender = '男' or user_gender = '女'),
	user_phone char(10),
	user_address varchar(50),
	user_email varchar(50),
    	user_authority int -- 用戶權限 0為客戶，1為管理員 
);

create table account(
	account_id int primary key auto_increment,
	account_name char(10) unique not null,
	account_balance bigint unsigned default 0,
    	user_id int not null,
    	account_status int default 0, -- 帳戶審核是否通過，0尚未審核， 1審核通過(可使用)， -1審核拒絕通過
    	foreign key(user_id) references user(user_id) on update cascade on delete cascade
); 

-- 交易種類(轉帳、存款、提款)
create table type( 
	type_id int primary key,
    	type_name varchar(10)
);

insert into type values(1, '轉帳');
insert into type values(2, '存款');
insert into type values(3, '提款');

create table transactions(
	tran_id  int primary key auto_increment,          
	account_id int,
	tran_reciprocal_id int, -- 對方account id
	tran_amount bigint unsigned, -- 金額
	tran_datetime datetime,
	tran_description varchar(100),
	tran_balance bigint unsigned, -- account 餘額 
    	tran_reciprocal_balance bigint unsigned, -- 對方account 餘額
	type_id int,
    	foreign key(account_id) references account(account_id) on update cascade on delete cascade,
    	foreign key(tran_reciprocal_id) references account(account_id) on update cascade on delete cascade,
    	foreign key(type_id) references type(type_id) on update cascade on delete cascade
);

-- 管理員測試資料
insert into user values(null, 'root', '4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2', 
	'管理員', '男', null, null, null,1); -- 密碼為root(SHA256)

-- 客戶測試資料
insert into user values(null, 'ming666', '9a2e2d42c89705533f0687c3b27b336a5fc81fab7d51f8f2ccf707e29d43d322', 
	'王小明', '男', '0901222555', '新北市', 'ming666@gmail.com',0); -- 密碼為ming666(SHA256)

-- 客戶測試資料    
insert into user values(null, 'hua666', 'c6946ec45a2b3dca7c479cdf2a4d37596f1e7c8a64f429603c46e505a720c1e2', 
	'林小華', '女', '0912333666', '台北市', 'hua666@gmail.com',0); -- 密碼為hua666(SHA256)
    
insert into account values(null, '8851236214', 10000, 2, 1); -- 王小明的account
insert into account values(null, '8863589974', 10000, 3, 1); -- 林小華的account
