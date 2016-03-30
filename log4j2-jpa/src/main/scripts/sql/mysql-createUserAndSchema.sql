CREATE DATABASE IF NOT EXISTS `LoggingDatabase`;
CREATE USER 'logging'@'localhost' IDENTIFIED BY 'logging';
GRANT ALL PRIVILEGES ON `recipes`.* TO 'logging'@'localhost';
