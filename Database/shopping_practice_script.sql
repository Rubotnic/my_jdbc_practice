-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema shopping_practice
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema shopping_practice
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shopping_practice` DEFAULT CHARACTER SET utf8 ;
USE `shopping_practice` ;

-- -----------------------------------------------------
-- Table `shopping_practice`.`shopping_cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopping_practice`.`shopping_cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `last_update` DATETIME NOT NULL,
  `order_status` VARCHAR(45) NOT NULL,
  `delivery_address` VARCHAR(255) NOT NULL,
  `customer_reference` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopping_practice`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopping_practice`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `price` DECIMAL NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `shopping_practice`.`shopping_cart_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `shopping_practice`.`shopping_cart_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `amount` INT NOT NULL,
  `total_price` DECIMAL NOT NULL,
  `product_id` INT NULL,
  `shopping_cart_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_shopping_cart_item_product_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_shopping_cart_item_shopping_cart1_idx` (`shopping_cart_id` ASC) VISIBLE,
  CONSTRAINT `fk_shopping_cart_item_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `shopping_practice`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shopping_cart_item_shopping_cart1`
    FOREIGN KEY (`shopping_cart_id`)
    REFERENCES `shopping_practice`.`shopping_cart` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
