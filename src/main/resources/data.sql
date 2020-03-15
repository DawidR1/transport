
-- ########## DRIVERS #############
insert into employee (id, pesel, first_name, last_name, image_name, email, birth, phone)
values ('1','12345678932','Anna', 'Nowak', 'p01.jpg', 'anna.nowak@sth.com', '1990-10-12', '123596873');

insert into employee (id, pesel, first_name, last_name, image_name, email, birth, phone)
values ('2','12345678912','John', 'Smith', 'p02.jpg', 'john.smith@cos.com', '1995-10-21', '925596223');

insert into driver (id, driving_license) values ('1', 'A');
insert into driver (id, driving_license) values ('2', 'B');

insert into employee (id, pesel, first_name, last_name, image_name, email, birth, phone) values ('3','12325678932','Joanna', 'Kowalska', 'p03.jpg', 'joanna.kowalska@cos.com', '1994-10-11', '965526236');
insert into employee (id, pesel, first_name, last_name, image_name, email, birth, phone) values ('4','12345678955','Dawid', 'Nowak', 'p04.jpg', 'dawid.nowak@cos.com', '1980-12-11','223546692');




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
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`,`lat_lng`) VALUES ('1', 'Katowice', '40-097', '3 Maja 30', 'Poland', '50.2593822,19.017685400000005');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`,`lat_lng`) VALUES ('2', 'Żywiec', '34-300', 'Wesoła 64', 'Poland', '49.6895941,19.18290860000002');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`,`lat_lng`) VALUES ('3', 'Brno-střed', '639 00', 'Bakalovo nábř. 8', 'Czechia','49.1846906,16.598918599999934');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`,`lat_lng`) VALUES ('4', 'Berlin', '12101', 'Wüsthoffstraße 15', 'Germany','52.4781617,13.373127100000033');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`,`lat_lng`) VALUES ('5', 'Lyon', '69008', '6 Cours Albert Thomas', 'France','45.7489557,4.86127620000002');
INSERT INTO `location` (`id`, `city`, `postal_code`, `street_address`, `country`,`lat_lng`) VALUES ('6', 'Antwerpen', '2060', 'Handelstraat 101', 'Belgium','51.2241328,4.430914700000017');


-- ########## LOCATION #############


-- ########## TRIP #############
INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (1, 4, 1000, 'Audi');
INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (6, 3, 350, 'BMW');
INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (7, 1, 500, 'Mercedes');

INSERT INTO loading_place (id, nr, date, location_id, finished) VALUES (1, 1, '2019-09-16', 2, true);
INSERT INTO loading_place (id, nr, date, location_id, finished) VALUES (5, 2, '2019-09-24', 1, true);
INSERT INTO loading_place (id, nr, date, location_id, finished) VALUES (6, 3, '2019-09-28', 5, true);
-- INSERT INTO loading_place (id, nr, date, location_id, finished) VALUES (7, 4, '2019-10-05', 2, true);

insert into loading_place_cargo (loading_place_id,cargo_id) values (1,1);
insert into loading_place_cargo (loading_place_id,cargo_id) values (5,6);
insert into loading_place_cargo (loading_place_id,cargo_id) values (6,7);

INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (1,'5000',2,2, 5,'2019-09-11','2019-10-11',1, 1,'FINISHED',800,500,2000,500);

INSERT INTO trip_loading_places (trip_id,loading_places_id) values (1,1);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (1,5);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (1,6);
-- INSERT INTO trip_loading_places (trip_id,loading_places_id) values (1,7);


INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (2, 6, 250, 'BMW');
INSERT INTO loading_place (id, nr, date, location_id, finished) VALUES (2, 1, '2019-10-13', 1, true);
insert into loading_place_cargo (loading_place_id,cargo_id) values (2,2);
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (2,'3000',1,2, 1,'2019-10-12','2019-10-15',2, 1,'FINISHED',200,50,20,150);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (2,2);

INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (3, 6, 250, 'VW');
INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (4, 7, 300, 'BMW');
INSERT INTO loading_place (id, nr, date, location_id, finished) VALUES (3, 1, '2019-12-14', 1, false );
insert into loading_place_cargo (loading_place_id,cargo_id) values (3,3);
insert into loading_place_cargo (loading_place_id,cargo_id) values (3,4);
INSERT INTO trip (id,income,place_start_id,destination_id,start_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (3,'1000',1, 4,'2019-12-10',1, 1,'IN_PROGRESS',2200,70,20,400);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (3,3);
--COPY
INSERT INTO cargo (id, number_of_pallets, weight, company_name) VALUES (5, 1, 250, 'BMW');
INSERT INTO loading_place (id, nr, date, location_id, finished) VALUES (4, 1, '2019-11-11', 1, true );
insert into loading_place_cargo (loading_place_id,cargo_id) values (4,5);
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (4,'3000',2,5, 5,'2019-11-10','2019-11-14',2, 1,'FINISHED',2000,100,200,700);
INSERT INTO trip_loading_places (trip_id,loading_places_id) values (4,4);
INSERT INTO user_role VALUES (1,'description','ADMIN');
INSERT INTO user VALUES (1,'$2a$10$U8MM/DXBkf9SNcuznKCUAu3iEDLJR87OfiWfbnoVsfIp/uqOTXOi6','example@example.pl');
INSERT INTO user_roles VALUES (1,1);


INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (5,'3000',1,2, 1,'2019-08-12','2019-08-15',2, 2,'FINISHED',200,50,20,600);

INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (6,'3000',1,2, 5,'2019-09-14','2019-09-15',2, 3,'FINISHED',200,50,20,800);

INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (7,'3000',1,2, 5,'2019-10-11','2019-10-15',2, 4,'FINISHED',200,50,20,750);




