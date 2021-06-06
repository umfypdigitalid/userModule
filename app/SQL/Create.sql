CREATE TABLE `digitalid`.`userdata` (
    `userId` INT(100) NOT NULL AUTO_INCREMENT ,
    `fullName` TEXT NOT NULL ,
    `username` VARCHAR(100) NOT NULL ,
    `password` TEXT NOT NULL ,
    `ic` VARCHAR(20) NOT NULL ,
    `birthDate` VARCHAR(20) NOT NULL ,
    `email` TEXT NOT NULL ,
    `address` TEXT NOT NULL ,
    `icimage` LONGTEXT NOT NULL,
    `createdOn` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,

    PRIMARY KEY (`userId`)
) ENGINE = InnoDB;

CREATE TABLE `digitalid`.`qrhistory` (
    `historyid` INT(200) NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(50) NOT NULL ,
    `detail` TEXT NOT NULL ,
    `userid` INT NOT NULL ,
    `scannedon` INT NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    PRIMARY KEY (`historyid`),
    FOREIGN KEY (`userid`) REFERENCES users(`userid`)
) ENGINE = InnoDB;

