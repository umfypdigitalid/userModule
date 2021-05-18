CREATE TABLE `digitalid`.`userdata` (
    `userId` INT(100) NOT NULL AUTO_INCREMENT ,
    `fullName` TEXT NOT NULL ,
    `username` VARCHAR(100) NOT NULL ,
    `password` TEXT NOT NULL ,
    `ic` VARCHAR(20) NOT NULL ,
    `birthDate` DATE NOT NULL ,
    `email` TEXT NOT NULL ,
    `address` TEXT NOT NULL ,
    `createdOn` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    PRIMARY KEY (`userId`)
) ENGINE = InnoDB;