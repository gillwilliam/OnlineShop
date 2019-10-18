CREATE DATABASE IF NOT EXISTS `shopdb`;

USE `shopdb`;

CREATE TABLE IF NOT EXISTS `shopdb`.`Product` (
  `idProduct` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `category` VARCHAR(45) NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `description` TEXT NULL,
  `quantity` INT NULL DEFAULT 0,
  `image` VARCHAR(45) NULL,
  PRIMARY KEY (`idProduct`),
  UNIQUE INDEX `idProduct_UNIQUE` (`idProduct` ASC))
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `shopdb`.`User` (
  `email` NVARCHAR(255) NOT NULL,
  `password` VARCHAR(45) NULL,
  `first_name` NVARCHAR(30) NULL,
  `last_name` NVARCHAR(30) NULL,
  `address` VARCHAR(255) NULL,
  `phone` VARCHAR(15) NULL,
  `type` ENUM('BUYER', 'SELLER', 'ADMIN') NULL DEFAULT 'BUYER',
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `shopdb`.`Buyer` (
  `email` NVARCHAR(255) NOT NULL,
  `shoppingCart` MEDIUMTEXT NULL,
  `wishList` MEDIUMTEXT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_email`
    FOREIGN KEY (`email`)
    REFERENCES `shopdb`.`User` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;