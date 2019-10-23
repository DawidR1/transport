insert into employee (id, pesel, first_name, last_name, image_name, email, birth) values ('1','12345678932','Dawid', 'xyz', 'picture1.jpg', 'example@cos.com', '1990-10-11');
insert into employee (id, pesel, first_name, last_name, email, birth) values ('2','12345678912','John', 'xyz', 'example@cos.com', '2000-10-11');
insert into driver (id, driving_license) values ('1', 'A');
insert into driver (id, driving_license) values ('2', 'B');
insert into car (plate, brand, model) values ('XX9999','Ford', 'Transit');
insert into car (plate, brand, model) values ('XXX9999','Renault', 'Clio');

INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`) VALUES ('1', 'Katowice', '40-748', 'Moniuszki', 'Poland');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`) VALUES ('2', 'Żywiec', '40-700', 'Szkolna', 'Poland');

INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (1, 4, 50, 'Google');

INSERT INTO loading_place (id, nr, date, location_id) VALUES (1, 1, '2010-10-11', 1);


INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance) VALUES (1,'500',1,2, 2,'2010-10-11','2010-11-11',1, 1,'IN_PROGRESS',200,50,20);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (1,1);

INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (2, 4, 50, 'Google');
INSERT INTO loading_place (id, nr, date, location_id) VALUES (2, 1, '2010-10-11', 1);
insert into loading_place_cargo (loading_place_id,cargo_id) values (2,2);
--
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance) VALUES (2,'300',1,2, 2,'2010-10-11','2010-11-11',2, 2,'IN_PROGRESS',200,50,20);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (2,2);



