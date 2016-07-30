insert into library.user (first_name, last_name, phone, address, email, join_date)
values ('Cristiano', 'Ronaldo', '555-555321', 'Concha Espina 1', 'cris@cr7.com', '2014-09-03 00:00:00+00');

insert into library.user (first_name, last_name, phone, address, email, join_date)
values ('Karim', 'Benzema', '555-555322', 'Concha Espina 1', 'karim@benzema.com', '2014-09-04 00:00:00+00');

insert into library.user (first_name, last_name, phone, address, email, join_date)
values ('Gareth', 'Bale', '555-555323', 'Concha Espina 1', 'gareth@bale.com', '2014-09-05 00:00:00+00');

insert into library.user (first_name, last_name, phone, address, email, join_date)
values ('Ramos', 'Sergio', '555-555324', 'Concha Espina 1', 'sergio@ramos.com', '2014-09-06 00:00:00+00');

insert into library.user (first_name, last_name, phone, address, email, join_date)
values ('Iker', 'Casillas', '555-555325', 'Concha Espina 1', 'iker@casillas.com', '2014-09-07 00:00:00+00');


insert into library.book (title, isbn, bought_date)
values ('Guerra y paz', '1234-5678', '2014-09-01 00:00:00');

insert into library.book (title, isbn, bought_date)
values ('Crimen y castigo', '1234-5679', '2014-09-01 00:00:00');

insert into library.book (title, isbn, bought_date)
values ('La Metamorfosis', '1234-5680', '2014-09-02 00:00:00');

insert into library.book (title, isbn, bought_date)
values ('Once and future king', '1234-5681', '2014-09-03 00:00:00');

insert into library.book (title, isbn, bought_date)
values ('Tale of two cities', '1234-5682', '2014-09-04 00:00:00');

insert into library.book (title, isbn, bought_date)
values ('El guardian en el centeno', '1234-5683', '2014-09-05 00:00:00');


insert into library.borrow (user_id, book_id, borrow_date, expected_return_date, actual_return_date)
values (1, 1, '2014-09-03 00:00:00', '2014-09-17 10:00:00', '2014-09-16 18:00:00');

insert into library.borrow (user_id, book_id, borrow_date, expected_return_date, actual_return_date)
values (2, 1, '2014-10-03 10:00:00', '2014-10-17 10:00:00', '2014-10-18 18:00:00');

insert into library.borrow (user_id, book_id, borrow_date, expected_return_date, actual_return_date)
values (2, 2, '2014-11-03 10:00:00', '2014-11-17 10:00:00', '2014-11-18 18:00:00');

insert into library.borrow (user_id, book_id, borrow_date, expected_return_date)
values (3, 1, '2014-11-03 10:00:00', '2014-11-17 10:00:00');

insert into library.borrow (user_id, book_id, borrow_date, expected_return_date, actual_return_date)
values (4, 3, '2014-11-03 10:00:00', '2014-11-17 10:00:00', '2014-11-18 18:00:00');

insert into library.borrow (user_id, book_id, borrow_date, expected_return_date)
values (5, 4, '2114-11-03 10:00:00', '2114-11-17 10:00:00');

insert into library.borrow (user_id, book_id, borrow_date, expected_return_date)
values (5, 5, '2114-11-03 10:01:00', '2114-11-17 10:00:01');


insert into library.fine (user_id, fine_start_date, fine_end_date)
values (2, '2014-10-18 18:00:00', '2014-10-19 18:00:00');

insert into library.fine (user_id, fine_start_date, fine_end_date)
values (2, '2014-11-18 18:00:00', '2014-11-19 18:00:00');

insert into library.fine (user_id, fine_start_date, fine_end_date)
values (4, '2014-11-18 18:00:00', '2114-11-19 18:00:00');