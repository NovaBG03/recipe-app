## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run -dp 3306:3306 --name ra_mysql -v C:/Programming/.dockerdata/ra_mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root mysql:5.7

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE ra_dev;
CREATE DATABASE ra_prod;

#Create database service accounts
CREATE USER 'ra_dev_user'@'localhost' IDENTIFIED BY 'DevPass1234';
CREATE USER 'ra_prod_user'@'localhost' IDENTIFIED BY 'ProdPass1234';
CREATE USER 'ra_dev_user'@'%' IDENTIFIED BY 'DevPass1234';
CREATE USER 'ra_prod_user'@'%' IDENTIFIED BY 'ProdPass1234';

#Database grants
GRANT SELECT ON ra_dev.* to 'ra_dev_user'@'localhost';
GRANT INSERT ON ra_dev.* to 'ra_dev_user'@'localhost';
GRANT DELETE ON ra_dev.* to 'ra_dev_user'@'localhost';
GRANT UPDATE ON ra_dev.* to 'ra_dev_user'@'localhost';
GRANT SELECT ON ra_prod.* to 'ra_prod_user'@'localhost';
GRANT INSERT ON ra_prod.* to 'ra_prod_user'@'localhost';
GRANT DELETE ON ra_prod.* to 'ra_prod_user'@'localhost';
GRANT UPDATE ON ra_prod.* to 'ra_prod_user'@'localhost';
GRANT SELECT ON ra_dev.* to 'ra_dev_user'@'%';
GRANT INSERT ON ra_dev.* to 'ra_dev_user'@'%';
GRANT DELETE ON ra_dev.* to 'ra_dev_user'@'%';
GRANT UPDATE ON ra_dev.* to 'ra_dev_user'@'%';
GRANT SELECT ON ra_prod.* to 'ra_prod_user'@'%';
GRANT INSERT ON ra_prod.* to 'ra_prod_user'@'%';
GRANT DELETE ON ra_prod.* to 'ra_prod_user'@'%';
GRANT UPDATE ON ra_prod.* to 'ra_prod_user'@'%';