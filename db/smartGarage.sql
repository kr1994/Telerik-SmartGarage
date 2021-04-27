-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for smartgarage
CREATE DATABASE IF NOT EXISTS `smartgarage` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `smartgarage`;

-- Dumping structure for table smartgarage.cars
CREATE TABLE IF NOT EXISTS `cars` (
  `car_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL,
  `plate` varchar(8) NOT NULL,
  `identification` varchar(17) NOT NULL,
  `year` int(4) NOT NULL,
  `colour_id` int(11) NOT NULL,
  `engine_id` int(11) DEFAULT NULL,
  `owner_id` int(11) NOT NULL,
  PRIMARY KEY (`car_id`),
  UNIQUE KEY `cars_plate_id_uindex` (`plate`),
  UNIQUE KEY `cars_identifications_fk` (`identification`),
  KEY `cars_colours_colour_id_fk` (`colour_id`),
  KEY `cars_engines_engine_id_fk` (`engine_id`),
  KEY `cars_years_years_id_fk` (`year`),
  KEY `cars_models_model_id_fk` (`model_id`),
  KEY `cars_customers_id_fk` (`owner_id`),
  CONSTRAINT `cars_colours_colour_id_fk` FOREIGN KEY (`colour_id`) REFERENCES `colours` (`colour_id`),
  CONSTRAINT `cars_customers_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `cars_engines_engine_id_fk` FOREIGN KEY (`engine_id`) REFERENCES `engines` (`engine_id`),
  CONSTRAINT `cars_models_model_id_fk` FOREIGN KEY (`model_id`) REFERENCES `models` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.cars: ~15 rows (approximately)
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` (`car_id`, `model_id`, `plate`, `identification`, `year`, `colour_id`, `engine_id`, `owner_id`) VALUES
	(2, 1, 'CA1234HM', 'Id111213141517162', 2018, 1, 1, 2),
	(23, 2, 'X2314KP', 'Id321233141517162', 2015, 1, 3, 2),
	(24, 1, 'C1234HM', 'Id321215441517162', 2018, 1, 4, 2),
	(25, 1, 'CO1264HM', 'Id761215441517162', 2018, 1, 2, 2),
	(26, 1, 'CO1124HM', 'Id761215441516532', 2018, 1, 3, 2),
	(27, 1, 'CO1004HM', 'Id700035441516532', 2018, 1, 3, 2),
	(28, 1, 'CO1304HM', 'Id700035400016532', 2018, 1, 1, 2),
	(29, 1, 'CO1545HM', 'Id700035760016532', 2018, 1, 2, 2),
	(30, 1, 'A1545HM', 'Id700WEAD0016532', 2018, 1, 4, 2),
	(31, 2, 'B3721HM', 'id123456789098765', 2003, 1, 3, 2),
	(32, 1, 'A3731AX', 'id123411189098765', 2020, 1, 4, 2),
	(33, 2, 'A3421HM', 'id121116789098765', 2017, 1, 3, 3),
	(34, 2, 'A3731CA', 'id123411183098765', 2020, 1, 3, 3),
	(35, 23, 'A3121CA', 'id123411189098762', 2019, 8, 3, 6),
	(36, 15, 'B3221HM', 'id123411189098761', 2021, 3, 2, 3),
	(37, 18, 'B3121HM', 'id12345678909876', 2020, 7, 3, 2);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;

-- Dumping structure for table smartgarage.car_services
CREATE TABLE IF NOT EXISTS `car_services` (
  `car_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `car_services_services_service_id_fk` (`service_id`),
  KEY `car_services_invoice_fk` (`invoice_id`),
  KEY `car_services_cars_car_id_fk` (`car_id`),
  CONSTRAINT `car_services_cars_car_id_fk` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
  CONSTRAINT `car_services_invoice_fk` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`),
  CONSTRAINT `car_services_services_service_id_fk` FOREIGN KEY (`service_id`) REFERENCES `services` (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.car_services: ~14 rows (approximately)
/*!40000 ALTER TABLE `car_services` DISABLE KEYS */;
INSERT INTO `car_services` (`car_id`, `service_id`, `id`, `invoice_id`) VALUES
	(2, 4, 2, 1),
	(2, 5, 3, 1),
	(2, 6, 4, 1),
	(2, 3, 7, 2),
	(33, 3, 8, 3),
	(33, 6, 9, 3),
	(33, 5, 10, 3),
	(31, 3, 11, 4),
	(24, 6, 13, 6),
	(25, 7, 14, 7),
	(30, 6, 15, 8),
	(24, 6, 16, 9),
	(23, 7, 17, 10),
	(23, 7, 18, 11),
	(33, 5, 19, 3),
	(37, 6, 20, 12);
/*!40000 ALTER TABLE `car_services` ENABLE KEYS */;

-- Dumping structure for table smartgarage.city
CREATE TABLE IF NOT EXISTS `city` (
  `index` varchar(2) NOT NULL,
  PRIMARY KEY (`index`),
  UNIQUE KEY `city_index_uindex` (`index`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.city: ~30 rows (approximately)
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` (`index`) VALUES
	('A'),
	('B'),
	('BH'),
	('BP'),
	('BT'),
	('C'),
	('CA'),
	('CB'),
	('CC'),
	('CH'),
	('CM'),
	('CO'),
	('CT'),
	('E'),
	('EB'),
	('EN'),
	('H'),
	('K'),
	('KH'),
	('M'),
	('OB'),
	('P'),
	('PA'),
	('PB'),
	('PK'),
	('PP'),
	('T'),
	('TX'),
	('X'),
	('Y');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;

-- Dumping structure for table smartgarage.colours
CREATE TABLE IF NOT EXISTS `colours` (
  `colour_id` int(11) NOT NULL AUTO_INCREMENT,
  `colour` varchar(100) NOT NULL,
  PRIMARY KEY (`colour_id`),
  UNIQUE KEY `colours_colour_uindex` (`colour`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.colours: ~9 rows (approximately)
/*!40000 ALTER TABLE `colours` DISABLE KEYS */;
INSERT INTO `colours` (`colour_id`, `colour`) VALUES
	(7, 'Black'),
	(5, 'Blue'),
	(6, 'Cyan'),
	(9, 'Gray'),
	(1, 'Green'),
	(3, 'Orange'),
	(2, 'Red'),
	(8, 'White'),
	(4, 'Yellow');
/*!40000 ALTER TABLE `colours` ENABLE KEYS */;

-- Dumping structure for table smartgarage.credentials
CREATE TABLE IF NOT EXISTS `credentials` (
  `credential_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(250) NOT NULL,
  PRIMARY KEY (`credential_id`),
  UNIQUE KEY `users_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.credentials: ~5 rows (approximately)
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` (`credential_id`, `username`, `password`) VALUES
	(2, 'TestOne', '98b482b141acd2f46965631f00af4caf'),
	(3, 'PlamenChipev', 'e00cf25ad42683b3df678c61f42c6bda'),
	(4, 'GeorgiChikov', 'User1'),
	(5, 'admin', 'Admin1'),
	(6, 'User', 'User1'),
	(7, 'plamenchipev16%??', '755b25162bbe34d4fbedca90f3261bee');
/*!40000 ALTER TABLE `credentials` ENABLE KEYS */;

-- Dumping structure for table smartgarage.currency
CREATE TABLE IF NOT EXISTS `currency` (
  `currency_name` varchar(3) NOT NULL,
  PRIMARY KEY (`currency_name`),
  UNIQUE KEY `currency_currency_name_uindex` (`currency_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.currency: ~5 rows (approximately)
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` (`currency_name`) VALUES
	('BGN'),
	('RON'),
	('RUB'),
	('TRY'),
	('USD');
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;

-- Dumping structure for table smartgarage.engines
CREATE TABLE IF NOT EXISTS `engines` (
  `engine_id` int(11) NOT NULL AUTO_INCREMENT,
  `hpw` int(4) NOT NULL,
  `fuel_id` int(11) NOT NULL,
  `cubic_capacity` int(4) NOT NULL,
  PRIMARY KEY (`engine_id`),
  KEY `engines_cubic_capacities_cc_id_fk` (`cubic_capacity`),
  KEY `engines_fuels_fuel_id_fk` (`fuel_id`),
  KEY `engines_horse_powers_power_id_fk` (`hpw`),
  CONSTRAINT `engines_fuels_fuel_id_fk` FOREIGN KEY (`fuel_id`) REFERENCES `fuels` (`fuel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.engines: ~3 rows (approximately)
/*!40000 ALTER TABLE `engines` DISABLE KEYS */;
INSERT INTO `engines` (`engine_id`, `hpw`, `fuel_id`, `cubic_capacity`) VALUES
	(1, 115, 1, 2000),
	(2, 450, 2, 4500),
	(3, 450, 2, 4500),
	(4, 450, 1, 4500);
/*!40000 ALTER TABLE `engines` ENABLE KEYS */;

-- Dumping structure for table smartgarage.fuels
CREATE TABLE IF NOT EXISTS `fuels` (
  `fuel_id` int(11) NOT NULL AUTO_INCREMENT,
  `fuel` varchar(20) NOT NULL,
  PRIMARY KEY (`fuel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.fuels: ~2 rows (approximately)
/*!40000 ALTER TABLE `fuels` DISABLE KEYS */;
INSERT INTO `fuels` (`fuel_id`, `fuel`) VALUES
	(1, 'Gas'),
	(2, 'Diesel');
/*!40000 ALTER TABLE `fuels` ENABLE KEYS */;

-- Dumping structure for table smartgarage.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  PRIMARY KEY (`invoice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.invoice: ~11 rows (approximately)
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` (`invoice_id`, `date`) VALUES
	(1, '2021-04-06'),
	(2, '2021-04-02'),
	(3, '2021-08-02'),
	(4, '2021-04-23'),
	(5, '2021-06-16'),
	(6, '2021-04-29'),
	(7, '2021-04-22'),
	(8, '2021-04-29'),
	(9, '2021-04-29'),
	(10, '2021-04-29'),
	(11, '2021-04-28'),
	(12, '2021-04-26');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;

-- Dumping structure for table smartgarage.manufacturers
CREATE TABLE IF NOT EXISTS `manufacturers` (
  `manufacturers_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`manufacturers_id`),
  UNIQUE KEY `manufacturers_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.manufacturers: ~4 rows (approximately)
/*!40000 ALTER TABLE `manufacturers` DISABLE KEYS */;
INSERT INTO `manufacturers` (`manufacturers_id`, `name`) VALUES
	(3, 'Audi'),
	(1, 'BMW'),
	(4, 'Lada'),
	(2, 'Mercedes'),
	(5, 'Reno');
/*!40000 ALTER TABLE `manufacturers` ENABLE KEYS */;

-- Dumping structure for table smartgarage.models
CREATE TABLE IF NOT EXISTS `models` (
  `model_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `manufacturer_id` int(11) NOT NULL,
  PRIMARY KEY (`model_id`),
  UNIQUE KEY `models_name_uindex` (`name`),
  KEY `models_manufacturers_manufacturers_id_fk` (`manufacturer_id`),
  CONSTRAINT `models_manufacturers_manufacturers_id_fk` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`manufacturers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.models: ~31 rows (approximately)
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` (`model_id`, `name`, `manufacturer_id`) VALUES
	(1, 'X5', 1),
	(2, 'GLE', 2),
	(15, 'A1', 3),
	(16, 'A3', 3),
	(17, 'A4', 3),
	(18, 'A5', 3),
	(19, 'A8', 3),
	(20, 'Q5', 3),
	(21, 'Q6', 3),
	(22, 'Q7', 3),
	(23, 'M1', 1),
	(24, 'M3', 1),
	(25, 'M5', 1),
	(26, 'X7', 1),
	(27, '5 Series ', 1),
	(28, 'i3', 1),
	(29, 'i8', 1),
	(30, 'A-Class', 2),
	(31, 'AMG GT', 2),
	(32, 'C-Class', 2),
	(33, 'CLA', 2),
	(34, 'CLS', 2),
	(35, 'ML', 2),
	(36, 'X1', 1),
	(37, 'X3', 1),
	(38, 'Laguna', 5),
	(39, 'S8', 3),
	(40, 'S6', 3),
	(41, 'S4', 3),
	(42, 'S1', 3),
	(43, 'M6', 1),
	(44, 'Q4', 3);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;

-- Dumping structure for table smartgarage.personal_info
CREATE TABLE IF NOT EXISTS `personal_info` (
  `info_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  PRIMARY KEY (`info_id`),
  UNIQUE KEY `personal_info_email_uindex` (`email`),
  UNIQUE KEY `personal_info_phone_number_uindex` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.personal_info: ~5 rows (approximately)
/*!40000 ALTER TABLE `personal_info` DISABLE KEYS */;
INSERT INTO `personal_info` (`info_id`, `first_name`, `last_name`, `email`, `phone_number`) VALUES
	(1, 'Plamen', 'Chipev', 'PlamenChipev@gmail.com', '884779256'),
	(2, 'Georgi', 'Chikov', 'GeorgiChikovv@gmail.com', '0888726354'),
	(3, 'Admin', 'Admin', 'ADmin@gmail.com', '0884779256'),
	(4, 'User', 'User', 'User@gmail.com', '0899999999'),
	(6, 'Plamen', 'Chipev', 'plamenchipev16@gmail.com', '0887995645');
/*!40000 ALTER TABLE `personal_info` ENABLE KEYS */;

-- Dumping structure for table smartgarage.services
CREATE TABLE IF NOT EXISTS `services` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(100) NOT NULL,
  `service_price` double NOT NULL,
  PRIMARY KEY (`service_id`),
  UNIQUE KEY `services_service_name_uindex` (`service_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.services: ~7 rows (approximately)
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` (`service_id`, `service_name`, `service_price`) VALUES
	(3, 'Oil change', 123.5),
	(4, 'Brake disk change', 220.7),
	(5, 'Oil filter', 50),
	(6, 'Fuel filter', 40.2),
	(7, 'Air filter', 25.6),
	(8, 'Paint parts', 151),
	(9, 'Tire change', 50);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;

-- Dumping structure for table smartgarage.types
CREATE TABLE IF NOT EXISTS `types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `types_type_uindex` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.types: ~2 rows (approximately)
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` (`type_id`, `type`) VALUES
	(2, 'Customer'),
	(1, 'Employee');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;

-- Dumping structure for table smartgarage.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `credential_id` int(11) NOT NULL,
  `personal_info_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `users_personal_info_info_id_fk` (`personal_info_id`),
  KEY `users_users_user_id_fk` (`credential_id`),
  KEY `users_types_type_id_fk` (`type_id`),
  CONSTRAINT `users_personal_info_info_id_fk` FOREIGN KEY (`personal_info_id`) REFERENCES `personal_info` (`info_id`),
  CONSTRAINT `users_types_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`),
  CONSTRAINT `users_users_user_id_fk` FOREIGN KEY (`credential_id`) REFERENCES `credentials` (`credential_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumping data for table smartgarage.users: ~3 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `credential_id`, `personal_info_id`, `type_id`) VALUES
	(2, 3, 1, 1),
	(3, 4, 2, 2),
	(6, 7, 6, 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
