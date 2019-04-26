create database if not exists sell;

use sell;

-- 类目
create table `product_category` (
    `category_id` int not null auto_increment,
    `category_name` varchar(64) not null comment '类目名字',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`category_id`)
);

-- 商品
create table `product_info` (
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '单价',
    `product_stock` int not null comment '库存',
    `product_description` varchar(64) comment '描述',
    `product_icon` varchar(512) comment '小图',
    `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`product_id`)
);

-- 订单
create table `order_master` (
    `order_id` varchar(32) not null,
    `buyer_name` varchar(32) not null comment '买家名字',
    `buyer_phone` varchar(32) not null comment '买家电话',
    `buyer_address` varchar(128) not null comment '买家地址',
    `buyer_openid` varchar(64) not null comment '买家微信openid',
    `order_amount` decimal(8,2) not null comment '订单总金额',
    `order_status` tinyint(3) not null default '0' comment '订单状态, 默认为新下单',
    `pay_status` tinyint(3) not null default '0' comment '支付状态, 默认未支付',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`order_id`),
    key `idx_buyer_openid` (`buyer_openid`)
);

-- 订单商品
create table `order_detail` (
    `detail_id` varchar(32) not null,
    `order_id` varchar(32) not null,
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '当前价格,单位分',
    `product_quantity` int not null comment '数量',
    `product_icon` varchar(512) comment '小图',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`detail_id`),
    key `idx_order_id` (`order_id`)
);

-- 卖家(登录后台使用, 卖家登录之后可能直接采用微信扫码登录，不使用账号密码)
create table `seller_info` (
    `id` varchar(32) not null,
    `username` varchar(32) not null,
    `password` varchar(32) not null,
    `openid` varchar(64) not null comment '微信openid',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
) comment '卖家信息表';

INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539319025769379468', '1539319025718424064', '1234568', '燕麦牛奶', 2.30, 1, 'http://yyyyyyyy.jpg', '2018-10-12 12:37:05', '2018-10-12 12:37:05');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539319025802366278', '1539319025718424064', '123457', '黑米粥', 2.30, 2, 'http://xxxxx.jpg', '2018-10-12 12:37:05', '2018-10-12 12:37:05');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539320742913793292', '1539320742867104766', '123456', '皮蛋粥', 3.20, 20, 'http://xxxxx.jpg', '2018-10-12 13:05:42', '2018-10-12 13:05:42');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539320742937814718', '1539320742867104766', '123457', '黑米粥', 2.30, 50, 'http://xxxxx.jpg', '2018-10-12 13:05:42', '2018-10-12 13:05:42');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539322137779451111', '1539322137736930112', '123456', '皮蛋粥', 3.20, 3, 'http://xxxxx.jpg', '2018-10-12 13:28:57', '2018-10-12 13:28:57');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539322137807748213', '1539322137736930112', '123457', '黑米粥', 2.30, 2, 'http://xxxxx.jpg', '2018-10-12 13:28:57', '2018-10-12 13:28:57');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539322162083244358', '1539322162034855797', '123456', '皮蛋粥', 3.20, 3, 'http://xxxxx.jpg', '2018-10-12 13:29:22', '2018-10-12 13:29:22');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539322162108878573', '1539322162034855797', '123457', '黑米粥', 2.30, 2, 'http://xxxxx.jpg', '2018-10-12 13:29:22', '2018-10-12 13:29:22');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539526916693620809', '1539526916662309443', '123456', '皮蛋粥', 3.20, 2, 'http://xxxxx.jpg', '2018-10-14 22:21:56', '2018-10-14 22:21:56');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539527254975102286', '1539527254779679943', '123456', '皮蛋粥', 3.20, 2, 'http://xxxxx.jpg', '2018-10-14 22:27:35', '2018-10-14 22:27:35');
INSERT INTO sell.order_detail (detail_id, order_id, product_id, product_name, product_price, product_quantity, product_icon, create_time, update_time) VALUES ('1539527713850997156', '1539527713822964843', '123456', '皮蛋粥', 3.20, 2, 'http://xxxxx.jpg', '2018-10-14 22:35:13', '2018-10-14 22:35:13');

INSERT INTO sell.order_master (order_id, buyer_name, buyer_phone, buyer_address, buyer_openid, order_amount, order_status, pay_status, create_time, update_time) VALUES ('1539319025718424064', 'VaderWang', '123456789012', 'zhh', '1', 6.90, 0, 0, '2018-10-12 00:00:00', '2019-04-23 22:17:35');
INSERT INTO sell.order_master (order_id, buyer_name, buyer_phone, buyer_address, buyer_openid, order_amount, order_status, pay_status, create_time, update_time) VALUES ('1539320742867104766', 'VaderWang', '123456789012', 'Iceberg', '1', 179.00, 0, 1, '2018-10-12 00:00:00', '2019-04-23 22:18:05');
INSERT INTO sell.order_master (order_id, buyer_name, buyer_phone, buyer_address, buyer_openid, order_amount, order_status, pay_status, create_time, update_time) VALUES ('1539322137736930112', 'VaderWang', '123456789012', 'Iceberg', '1', 14.20, 0, 0, '2018-10-12 00:00:00', '2019-04-23 22:18:05');
INSERT INTO sell.order_master (order_id, buyer_name, buyer_phone, buyer_address, buyer_openid, order_amount, order_status, pay_status, create_time, update_time) VALUES ('1539322162034855797', 'VaderWang', '123456789012', 'Iceberg', '1', 14.20, 0, 0, '2018-10-12 00:00:00', '2019-04-23 22:18:05');
INSERT INTO sell.order_master (order_id, buyer_name, buyer_phone, buyer_address, buyer_openid, order_amount, order_status, pay_status, create_time, update_time) VALUES ('1539526916662309443', ' vader', '188888888', ' iceberg', '2', 6.40, 0, 0, '2018-10-14 00:00:00', '2019-04-23 22:52:44');
INSERT INTO sell.order_master (order_id, buyer_name, buyer_phone, buyer_address, buyer_openid, order_amount, order_status, pay_status, create_time, update_time) VALUES ('1539527254779679943', ' vader', '188888888', ' iceberg', '2', 6.40, 0, 0, '2018-10-14 00:00:00', '2019-04-23 22:52:44');
INSERT INTO sell.order_master (order_id, buyer_name, buyer_phone, buyer_address, buyer_openid, order_amount, order_status, pay_status, create_time, update_time) VALUES ('1539527713822964843', ' vader', '188888888', ' iceberg', '2', 6.40, 0, 0, '2018-10-14 00:00:00', '2019-04-23 22:52:44');


INSERT INTO sell.product_category (category_id, category_name, category_type, create_time, update_time) VALUES (1, '热榜', 1, '2018-10-10 01:18:35', '2019-04-23 23:21:43');
INSERT INTO sell.product_category (category_id, category_name, category_type, create_time, update_time) VALUES (2, '折扣', 2, '2018-10-10 00:00:00', '2019-04-23 23:21:43');
INSERT INTO sell.product_category (category_id, category_name, category_type, create_time, update_time) VALUES (4, '满减', 3, '2018-10-10 00:00:00', '2019-04-23 23:21:43');
INSERT INTO sell.product_category (category_id, category_name, category_type, create_time, update_time) VALUES (5, '啦啦啦', 4, '2018-10-10 00:00:00', '2019-04-23 23:21:43');
INSERT INTO sell.product_category (category_id, category_name, category_type, create_time, update_time) VALUES (9, '来来来', 4, '2018-10-11 00:00:00', '2019-04-23 23:21:43');

INSERT INTO sell.product_info (product_id, product_name, product_price, product_stock, product_description, product_icon, product_status, category_type, create_time, update_time) VALUES ('123456', '皮蛋粥', 3.20, 68, '很好喝的粥', 'http://xxxxx.jpg', 0, 2, '2018-10-10 12:37:28', '2019-04-24 00:40:51');
INSERT INTO sell.product_info (product_id, product_name, product_price, product_stock, product_description, product_icon, product_status, category_type, create_time, update_time) VALUES ('1234568', '燕麦牛奶', 2.30, 99, '很好喝的燕麦牛奶', 'http://yyyyyyyy.jpg', 0, 1, '2018-10-12 00:00:00', '2019-04-23 23:06:15');
INSERT INTO sell.product_info (product_id, product_name, product_price, product_stock, product_description, product_icon, product_status, category_type, create_time, update_time) VALUES ('123457', '黑米粥', 2.30, 44, '很好喝的粥', 'http://xxxxx.jpg', 0, 2, '2018-10-12 00:00:00', '2019-04-23 23:06:15');

INSERT INTO sell.seller_info (id, username, password, openid, create_time, update_time) VALUES ('1', 'wangql', '123456', '', '2019-04-24 08:43:52', '2019-04-24 08:44:00');
