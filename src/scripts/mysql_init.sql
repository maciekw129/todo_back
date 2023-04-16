DROP DATABASE IF EXISTS tododb;
DROP USER IF EXISTS 'todoadmin'@'localhost';
CREATE DATABASE IF NOT EXISTS tododb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'todoadmin'@'localhost' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'todoadmin'@'localhost';
FLUSH PRIVILEGES;