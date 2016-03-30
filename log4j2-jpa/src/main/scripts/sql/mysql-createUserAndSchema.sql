CREATE DATABASE IF NOT EXISTS `loggingdatabase`;
CREATE USER 'logging'@'localhost' IDENTIFIED BY 'logging';
GRANT ALL PRIVILEGES ON `loggingdatabase`.* TO 'logging'@'localhost';
