create table Customer(
    customerId bigint primary key,
    name text not null,
    phoneNumber text not null,
    address text not null
);
insert into Customer(customerId, name, phoneNumber, address)
values
(1, 'Alice Johnson', '555-1234', '123 Maple St'),
(2, 'Bob Smith', '555-5678', '456 Oak St');



create table OrderRequest(
    requestId bigint primary key,
    mozzarella int not null,
    paneer int not null,
    kanchan int not null,
    requestStatus text,
    customerId bigint not null,
    updatedAt timestamptz
);
insert into OrderRequest(requestId, mozzarella, paneer, kanchan, requestStatus, customerId, updatedAt)
values
(1, 2, 5, 1, 'pending', 1, now()),
(2, 0, 2, 14, 'pending', 1, now()),
(3, 10, 0, 5, 'unable', 2, now());



create table InventoryItem(
    productId text primary key,
    productName text not null,
    totalQuantity int not null,
    quantityTemporaryHold int not null,
    quantityPromised int not null,
    updatedAt timestamptz
);
insert into InventoryItem(productId, productName, totalQuantity, quantityTemporaryHold, quantityPromised, updatedAt)
values
('mozzarella', 'Mozzarella Cheese', 100, 2, 0, now()),
('paneer', 'Paneer', 150, 7, 0, now()),
('kanchan', 'Kanchan Cheese', 190, 15, 0, now());
