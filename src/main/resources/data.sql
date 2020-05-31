
-- ########## DRIVERS #############
INSERT INTO employee (id, pesel, first_name, last_name, image_name, email, birth, phone)
VALUES ('1','123456789321','Anna', 'Nowak', 'p01.jpg', 'anna.nowak@sth.com', '1990-10-12', '123596873');
INSERT INTO employee (id, pesel, first_name, last_name, image_name, email, birth, phone)
VALUES ('2','123456789123','John', 'Smith', 'p02.jpg', 'john.smith@cos.com', '1995-10-21', '925596223');
INSERT INTO employee (id, pesel, first_name, last_name, image_name, email, birth, phone) 
VALUES ('3','123256789321','Joanna', 'Kowalska', 'p03.jpg', 'joanna.kowalska@cos.com', '1994-10-11', '965526236');
INSERT INTO employee (id, pesel, first_name, last_name, image_name, email, birth, phone) 
VALUES ('4','123456789552','Dawid', 'Nowak', 'p04.jpg', 'dawid.nowak@cos.com', '1980-12-11','223546692');

INSERT INTO driver (id, driving_license) VALUES ('1', 'A');
INSERT INTO driver (id, driving_license) VALUES ('2', 'B');
INSERT INTO driver (id, driving_license) VALUES ('3', 'C');
INSERT INTO driver (id, driving_license) VALUES ('4', 'A');
-- ########## DRIVERS #############


-- ########## CARS #############
INSERT INTO car (plate, brand, model) VALUES ('XX9999','Ford', 'Transit');
INSERT INTO car (plate, brand, model) VALUES ('KR9999','Renault', 'Clio');
INSERT INTO car (plate, brand, model) VALUES ('SZY9521','Renault', 'Master');
INSERT INTO car (plate, brand, model) VALUES ('SB6321','VW', 'Transporter');
--RANDOM--
INSERT INTO car (plate, brand, model) VALUES ('XX9199','Ford', 'Transit');
INSERT INTO car (plate, brand, model) VALUES ('XX9299','Ford', 'Transit');
INSERT INTO car (plate, brand, model) VALUES ('XX9399','Ford', 'Transit');
INSERT INTO car (plate, brand, model) VALUES ('XX9499','Ford', 'Transit');
INSERT INTO car (plate, brand, model) VALUES ('XX9599','Ford', 'Transit');
INSERT INTO car (plate, brand, model) VALUES ('XX9699','Ford', 'Transit');
INSERT INTO car (plate, brand, model) VALUES ('XX9799','Ford', 'Transit');
-- ########## CARS #############


-- ########## LOCATION #############
INSERT INTO location (id, city, postal_code, street_address, country,lat_lng) VALUES ('1', 'Katowice', '40-097', '3 Maja 30', 'Poland', '50.2593822,19.017685400000005');
INSERT INTO location (id, city, postal_code, street_address, country,lat_lng) VALUES ('2', 'Żywiec', '34-300', 'Wesoła 64', 'Poland', '49.6895941,19.18290860000002');
INSERT INTO location (id, city, postal_code, street_address, country,lat_lng) VALUES ('3', 'Brno-střed', '639 00', 'Bakalovo nábř. 8', 'Czechia','49.1846906,16.598918599999934');
INSERT INTO location (id, city, postal_code, street_address, country,lat_lng) VALUES ('4', 'Berlin', '12101', 'Wüsthoffstraße 15', 'Germany','52.4781617,13.373127100000033');
INSERT INTO location (id, city, postal_code, street_address, country,lat_lng) VALUES ('5', 'Lyon', '69008', '6 Cours Albert Thomas', 'France','45.7489557,4.86127620000002');
INSERT INTO location (id, city, postal_code, street_address, country,lat_lng) VALUES ('6', 'Antwerpen', '2060', 'Handelstraat 101', 'Belgium','51.2241328,4.430914700000017');
-- ########## LOCATION #############


-- ########## TRIP #############
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (1,'5000',2,6, 6,'2020-01-11','2020-01-13',1, 1,'IN_PROGRESS',800,500,2000,500);
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (2,'3000',1,5, 5,'2019-10-12','2019-10-15',2, 1,'FINISHED',200,50,20,150);
INSERT INTO trip (id,income,place_start_id,destination_id,start_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (3,'1000',1, 4,'2019-12-10',1, 1,'FINISHED',2200,70,20,400);
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (4,'3500',2,5, 5,'2019-11-10','2019-11-14',2, 1,'FINISHED',2000,100,200,700);
INSERT INTO trip (id,income,place_start_id,place_finish_id,destination_id,start_date,finish_date,car_id,driver_id,
status,cost,fuel,distance,driver_salary) VALUES (5,'1000',4,6, 6,'2019-11-09','2019-11-15',2, 1,'FINISHED',2000,100,200,700);
-- ########## TRIP #############

-- ########## LOADING PLACE #############
INSERT INTO loading_place (id, nr, date, location_id, finished, trip_id) VALUES (1, 1, '2019-09-16', 5, true,1);
INSERT INTO loading_place (id, nr, date, location_id, finished,trip_id) VALUES (2, 1, '2019-10-13', 3, true,2);
INSERT INTO loading_place (id, nr, date, location_id, finished,trip_id) VALUES (3, 1, '2019-12-14', 6, false,3);
INSERT INTO loading_place (id, nr, date, location_id, finished,trip_id) VALUES (5, 2, '2019-09-24', 4, true,1);
INSERT INTO loading_place (id, nr, date, location_id, finished,trip_id) VALUES (6, 3, '2019-09-28', 6, true,4);
INSERT INTO loading_place (id, nr, date, location_id, finished,trip_id) VALUES (7, 1, '2019-10-05', 2, true,15;
-- ########## LOADING PLACE #############

-- ########## CARGO #############
INSERT INTO cargo (id, number_of_pallets, weight, company_name,loading_place_id) VALUES (1, 4, 1000, 'Audi',1);
INSERT INTO cargo (id, number_of_pallets, weight, company_name,loading_place_id) VALUES (6, 3, 350, 'BMW',5);
INSERT INTO cargo (id, number_of_pallets, weight, company_name,loading_place_id) VALUES (7, 1, 500, 'Mercedes',6);
INSERT INTO cargo (id, number_of_pallets, weight, company_name,loading_place_id) VALUES (2, 6, 250, 'BMW',2);
INSERT INTO cargo (id, number_of_pallets, weight, company_name,loading_place_id) VALUES (3, 6, 250, 'VW',3);
INSERT INTO cargo (id, number_of_pallets, weight, company_name,loading_place_id) VALUES (4, 7, 300, 'BMW',3);
-- ########## CARGO #############
