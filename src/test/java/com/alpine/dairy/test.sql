create table customer(
    customer_id bigint primary key,
    name text not null,
    phone_number text not null,
    address text not null
);
insert into customer(customer_id, name, phone_number, address)
values
(1, 'Alice Johnson', '555-1234', '123 Maple St'),
(2, 'Bob Smith', '555-5678', '456 Oak St');



create table order_request(
    request_id bigint primary key,
    mozzarella int not null,
    paneer int not null,
    kanchan int not null,
    request_status text,
    customer_id bigint not null,
    updated_at timestamptz
);
insert into order_request(request_id, mozzarella, paneer, kanchan, request_status, customer_id, updated_at)
values
(1, 2, 5, 1, 'pending', 1, now()),
(2, 0, 2, 14, 'pending', 1, now()),
(3, 10, 0, 5, 'unable', 2, now());



create table inventory_item(
    product_id text primary key,
    product_name text not null,
    total_quantity int not null,
    quantity_temporary_hold int not null,
    quantity_promised int not null,
    updated_at timestamptz
);
insert into inventory_item(product_id, product_name, total_quantity, quantity_temporary_hold, quantity_promised, updated_at)
values
('mozzarella', 'Mozzarella Cheese', 100, 2, 0, now()),
('paneer', 'Paneer', 150, 7, 0, now()),
('kanchan', 'Kanchan Cheese', 190, 15, 0, now());
