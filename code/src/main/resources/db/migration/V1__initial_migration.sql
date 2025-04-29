create table item_type (
    id int auto_increment primary key,
    type_name varchar(255) not null unique
);

create table inventory_items (
    id int auto_increment primary key,
    name varchar(255) not null,
    price decimal(18,2) not null,
    description varchar(1000),
    sold boolean default false,
    item_type_id int,
    foreign key (item_type_id) references item_type(id)
);

create table item_pictures (
    id int auto_increment primary key,
    image_url varchar(255) not null,
    inventory_item_id int,
    foreign key (inventory_item_id) references inventory_items(id)
);

create table users (
    id int auto_increment primary key,
    username varchar(255) not null unique,
    email varchar(255) not null,
    password varchar(255) not null,
    is_admin boolean default false
);

create table shopping_carts (
    id int auto_increment primary key,
    user_id int unique,
    foreign key (user_id) references users(id)
);

create table cart_items (
    id int auto_increment primary key,
    cart_id int,
    item_id int,
    foreign key (cart_id) references shopping_carts(id),
    foreign key (item_id) references inventory_items(id)
);
create table payment_information (
    id int auto_increment primary key,
    credit_card_number varchar(255),
    shipping_address varchar(255),
    phone_number varchar(255),
    shipping_speed varchar(255),
    shipping_cost decimal(18,2)
);

create table orders (
    id int auto_increment primary key,
    user_id int not null,
    subtotal decimal(18,2),
    tax decimal(18,2),
    grand_total decimal(18,2),
    last4cc varchar(4),
    order_date DATETIME,
    payment_information_id int,
    foreign key (user_id) references users(id),
    foreign key (payment_information_id) references payment_information(id)

);

create table order_items (
    id int auto_increment primary key,
    name varchar(255),
    price decimal(18,2),
    order_id int,
    item_id int,
    foreign key (order_id) references orders(id),
    foreign key (item_id) references inventory_items(id)
);



create table shipping_information (
    id int auto_increment primary key,
    address_line varchar(255),
    city varchar(255),
    state varchar(255),
    zip_code varchar(10),
    user_id int,
    foreign key (user_id) references users(id)
);

-- ====================
-- Pre-populated Data
-- ====================

-- Insert default item types
insert into item_type (type_name) values ('Sword');
insert into item_type (type_name) values ('Armor');
insert into item_type (type_name) values ('Shield');
insert into item_type (type_name) values ('Gun');
insert into item_type (type_name) values ('Axe');
insert into item_type (type_name) values ('Bow');

-- Insert some users (passwords are plain for now - you'll hash them later)
insert into users (username, email, password, is_admin) values ('admin', 'admin@gearedup.com', '$2a$12$spxy2hyYk.M33HBySxp8oewE8If1/gGJ3pf8bTPgxC98MCRZB6kIG', true);
insert into users (username, email, password, is_admin) values ('user1', 'user1@gearedup.com', '$2a$12$spxy2hyYk.M33HBySxp8oewE8If1/gGJ3pf8bTPgxC98MCRZB6kIG', false);

-- Insert some inventory
insert into inventory_items (name, price, description, sold, item_type_id)
values ('Master Sword', 50000.00, 'The Master Sword is not just a weapon, but a sacred blade imbued with divine power.', false, 1);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Buster Sword', 30000.00, 'The Buster Sword is more than just a weapon; its a symbol of a warriors spirit, carrying with it the hopes and dreams of those who have wielded it before.', false, 1);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Energy Sword', 45000.00, 'Closely aligned with the Sangheili rigid sense of honor, the energy sword is the signature weapon of the Sangheili, and its usage has historically been regarded as both an expression of a Sangheili warrior clerical honor and his personal combat skills.', false, 1);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('CryNet Nanosuit', 75000.00, 'It features: Muscular CryFibril system that grants the wearer super human strength and speed. Protects the wearer from other harmful factors including radiation, energy blasts, blunt trauma, kinetic force and renders any gas weapon useless.', false, 2);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Daedric Armor', 27500.00, 'Daedric Armor in Skyrim is a powerful, gothic-looking heavy armor set. It features a black-metallic color scheme with silken chains, intricate designs, and large fasteners. The helmet has large eye-like sockets and six horn-like spikes, creating a monstrous appearance. The gauntlets and cuirass glow red in the dark.', false, 2);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('MJOLNIR [GEN3]', 37500.00, 'The MJOLNIR Powered Assault Armor [GEN3] is the third system-wide generation of the MJOLNIR Powered Assault Armor. As of 2560, Generation 3 MJOLNIR has been adopted by nearly all active Spartans serving in the United Nations Space Command.', false, 2);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Hylian Shield',62500.00,'The shield features a blue plate, a metal border, the Triforce symbol, and the Hylian Crest.', false, 3);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Guardian Shield',20000.00,'The shield is described as being made by Faye for Kratos and is implied to be quite durable, capable of enduring attacks from gods and absorbing impacts.', false, 3);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Deku Shield',6250.00,'The Deku Shield is a simple wooden shield crafted from the bark of the Great Deku Tree. The Kokiri Symbol painted on its surface resembles the Kokiris Spiritual Stone, the Kokiris Emerald.', false, 3);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Golden Gun',85000.00,'Golden gun used by Scaramanga a 4.2 calibre gold-plated pistol assembled from a cigarette case (the handle), lighter (the bullet chamber), fountain pen (the barrel) and cuff link (the trigger). The weapon fires one gold bullet which Scaramanga conceals in his belt buckle.', false, 4);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('BFG9000',90000.00,'It appears as a large, silver metallic gun with a dark gray aperture, and fires large spheres of green plasma. It is capable of destroying nearly any player or monster with a single shot, and can disperse damage over a wide area to multiple targets simultaneously.', false, 4);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Portal Gun',15000.00,'The portal gun can create two distinct portal ends, orange and blue. The portals create a visual and physical connection between two different locations in three-dimensional space.', false, 4);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Leviathan Axe',37500.00,'It was forged by the Huldra Brothers, Sindri and Brok, who also forged Thors hammer, Mjölnir, after feeling responsible for the destruction brought about by Thor. As a weapon made to oppose the power of Mjölnir and inspired by the epic size of Jörmungandr, Brok suggests the axe be named Leviathan.', false, 5);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Golden Axe',50000.00,'A legendary artifact capable of imbuing the wielder with a number of different powers. It is considered a symbol of peace and prosperity, but in the wrong hands it can bring chaos and destruction.', false, 5);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Ancient Battle Axe',20000.00,'A weapon used by Guardian Scouts. Its unique blade was forged using ancient technology. Although powerful, its unusual shape causes it to break easily.', false, 5);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Sacred Bow',77700.00,'The Sacred Bow is the final upgrade of the Bow. It lives up to its name by being enhanced by the power of the Goddess.', false, 6);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('SotC: Bow & Arrow',10000.00,'The bow seems to be made from some sort of pink wood and has around four strings in front of the handle, which the arrow goes through, to enhance accuracy. It has a long, silvery string that shines very brightly and is very durable.', false, 6);

insert into inventory_items (name, price, description, sold, item_type_id)
values ('Predator Bow',67800.00,'The Predator Bow is a powerful, silent, and accurate compound bow designed for Nanosuit users', false, 6);

-- Insert pictures for those items
insert into item_pictures (image_url, inventory_item_id) values ('/images/Master_Sword.jpg', 1);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Buster_Sword.jpg', 2);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Energy_Sword.jpg', 3);
insert into item_pictures (image_url, inventory_item_id) values ('/images/CryNet_Nanosuit.jpg', 4);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Daedric_Armor.jpg', 5);
insert into item_pictures (image_url, inventory_item_id) values ('/images/MJOLNIR_GEN3.jpg', 6);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Hylian_Shield.jpg', 7);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Guardian_Shield.jpg', 8);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Deku_Shield.jpg', 9);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Golden_Gun.jpg', 10);
insert into item_pictures (image_url, inventory_item_id) values ('/images/BFG9000.jpg', 11);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Portal_Gun.jpg', 12);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Leviathan_Axe.jpg', 13);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Golden_Axe.jpg', 14);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Ancient_Battle_Axe.jpg', 15);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Sacred_Bow.jpg', 16);
insert into item_pictures (image_url, inventory_item_id) values ('/images/SotC_Bow_and_Arrow.jpg', 17);
insert into item_pictures (image_url, inventory_item_id) values ('/images/Predator_Bow.jpg', 18);