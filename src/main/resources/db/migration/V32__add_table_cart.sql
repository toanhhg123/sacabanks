create table cart (
    id binary(16) primary key,
    user_id binary(16),
    product_id binary(16),
    quantity int,
    created_at datetime(6),
    updated_at datetime(6),
    foreign key (user_id) references user(id) on delete cascade,
    foreign key (product_id) references product(id) on delete cascade
);