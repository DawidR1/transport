
-- ########## DRIVERS #############
insert into employee (id, pesel, first_name, last_name, image_name, email, birth) values ('1','12345678932','Dawid', 'Nowak', 'picture1.jpg', 'dawid.nowak@cos.com', '1990-10-12');
insert into employee (id, pesel, first_name, last_name, email, birth) values ('2','12345678912','John', 'Smith', 'john.smith@cos.com', '1995-10-21');

insert into employee (id, pesel, first_name, last_name, image_name, email, birth) values ('3','12325678932','Janusz', 'Kowalski', 'picture1.jpg', 'janusz.kowalski@cos.com', '1994-10-11');
insert into employee (id, pesel, first_name, last_name, email, birth) values ('4','12345678955','Test', 'Test', 'example@cos.com', '1980-12-11');


insert into driver (id, driving_license) values ('1', 'A');
insert into driver (id, driving_license) values ('2', 'B');

insert into driver (id, driving_license) values ('3', 'C');
insert into driver (id, driving_license) values ('4', 'A');
-- ########## DRIVERS #############


-- ########## CARS #############
insert into car (plate, brand, model) values ('XX9999','Ford', 'Transit');
insert into car (plate, brand, model) values ('KR9999','Renault', 'Clio');
insert into car (plate, brand, model) values ('SZY9521','Renault', 'Master');
insert into car (plate, brand, model) values ('SB6321','VW', 'Transporter');

--RANDOM--
insert into car (plate, brand, model) values ('XX9199','Ford', 'Transit');
insert into car (plate, brand, model) values ('XX9299','Ford', 'Transit');
insert into car (plate, brand, model) values ('XX9399','Ford', 'Transit');
insert into car (plate, brand, model) values ('XX9499','Ford', 'Transit');
insert into car (plate, brand, model) values ('XX9599','Ford', 'Transit');
insert into car (plate, brand, model) values ('XX9699','Ford', 'Transit');
insert into car (plate, brand, model) values ('XX9799','Ford', 'Transit');

-- ########## CARS #############


-- ########## LOCATION #############
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`) VALUES ('1', 'Katowice', '40-748', 'Moniuszki', 'Poland');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`) VALUES ('2', 'Żywiec', '40-700', 'Szkolna', 'Poland');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`) VALUES ('3', 'Berlin', '00-005', 'Strasse', 'Germany');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`) VALUES ('4', 'Berlin', '10178', 'Keibelstrase', 'Germany');

-- ########## LOCATION #############


-- ########## TRIP #############
INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (1, 4, 50, 'Audi');
INSERT INTO loading_place (id, nr, date, location_id) VALUES (1, 1, '2019-10-11', 1);
insert into loading_place_cargo (loading_place_id,cargo_id) values (1,1);
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (1,'5000',1,2, 2,'2019-09-11','2019-11-11',1, 1,'FINISHED',800,500,2000,500);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (1,1);


INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (2, 6, 250, 'BMW');
INSERT INTO loading_place (id, nr, date, location_id) VALUES (2, 1, '2010-10-11', 1);
insert into loading_place_cargo (loading_place_id,cargo_id) values (2,2);
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (2,'3000',1,2, 2,'2019-05-11','2010-06-11',2, 2,'FINISHED',200,50,20,150);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (2,2);

INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (3, 6, 250, 'VW');
INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (4, 7, 300, 'BMW');
INSERT INTO loading_place (id, nr, date, location_id) VALUES (3, 1, '2010-10-11', 1);
insert into loading_place_cargo (loading_place_id,cargo_id) values (3,3);
insert into loading_place_cargo (loading_place_id,cargo_id) values (3,4);
INSERT INTO trip (id,income,place_start_id,destination_id,start_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (3,'1000',1, 2,'2019-05-11',1, 2,'IN_PROGRESS',2200,70,20,400);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (3,3);
--COPY
INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (5, 1, 250, 'BMW');
INSERT INTO loading_place (id, nr, date, location_id) VALUES (4, 1, '2010-10-11', 1);
insert into loading_place_cargo (loading_place_id,cargo_id) values (4,5);
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (4,'3000',3,2, 2,'2019-05-11','2010-06-11',2, 1,'FINISHED',200,50,20,700);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (4,4);
INSERT INTO user_role VALUES (1,'description','ADMIN');
INSERT INTO user VALUES (1,'$2a$10$U8MM/DXBkf9SNcuznKCUAu3iEDLJR87OfiWfbnoVsfIp/uqOTXOi6','cos@cos');
INSERT INTO user_roles VALUES (1,1)





