-- MySQL Script generated by MySQL Workbench
-- Mon 18 Nov 2019 08:57:34 PM CET
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema shop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema shop
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS `shop` DEFAULT CHARACTER SET utf8 ;
USE `shop` ;

-- -----------------------------------------------------
-- Table `shop`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shop`.`users` ;

CREATE TABLE IF NOT EXISTS `shop`.`users` (
  `email` NVARCHAR(255) NOT NULL,
  `password` VARCHAR(45) NULL,
  `firstName` NVARCHAR(30) NULL,
  `lastName` NVARCHAR(30) NULL,
  `address` VARCHAR(255) NULL,
  `phone` VARCHAR(15) NULL,
  `type` ENUM('BUYER', 'SELLER', 'ADMIN') NULL DEFAULT 'BUYER',
  `id` INT NOT NULL AUTO_INCREMENT,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shop`.`category` ;

CREATE TABLE IF NOT EXISTS `shop`.`category` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `parent` INT NULL,
  PRIMARY KEY (`category_id`),
  INDEX `parent_idx` (`parent` ASC),
  CONSTRAINT `parent`
    FOREIGN KEY (`parent`)
    REFERENCES `shop`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `shop`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shop`.`products` ;

CREATE TABLE IF NOT EXISTS `shop`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `category` INT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `description` TEXT NULL,
  `quantity` INT NULL DEFAULT 0,
  `image` LONGBLOB NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idProduct_UNIQUE` (`id` ASC),
  INDEX `category_idx` (`category` ASC),
  CONSTRAINT `category`
    FOREIGN KEY (`category`)
    REFERENCES `shop`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop`.`product_lists`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shop`.`product_lists` ;

CREATE TABLE IF NOT EXISTS `shop`.`product_lists` (
  `user` INT NULL,
  `listId` INT NOT NULL AUTO_INCREMENT,
  `isShoppingCart` TINYINT(1) NULL,
  PRIMARY KEY (`listId`),
  UNIQUE INDEX `cartId_UNIQUE` (`listId` ASC),
  INDEX `fk_user_idx` (`user` ASC),
  CONSTRAINT `fk_user`
    FOREIGN KEY (`user`)
    REFERENCES `shop`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop`.`lists_to_products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shop`.`lists_to_products` ;

CREATE TABLE IF NOT EXISTS `shop`.`lists_to_products` (
  `listId` INT NOT NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`listId`, `productId`),
  INDEX `fk_list_to_product_1_idx` (`productId` ASC),
  CONSTRAINT `product`
    FOREIGN KEY (`productId`)
    REFERENCES `shop`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `list`
    FOREIGN KEY (`listId`)
    REFERENCES `shop`.`product_lists` (`listId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shop`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shop`.`orders` ;

CREATE TABLE IF NOT EXISTS `shop`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `owner` INT NULL,
  `items` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_idx` (`owner` ASC),
  INDEX `fk_list_idx` (`items` ASC),
  CONSTRAINT `fk_owner`
    FOREIGN KEY (`owner`)
    REFERENCES `shop`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_items`
    FOREIGN KEY (`items`)
    REFERENCES `shop`.`product_lists` (`listId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
