CREATE TABLE `digitalid`.`userdata` (
    `userId` INT(255) NOT NULL AUTO_INCREMENT ,
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
    FOREIGN KEY (`userid`) REFERENCES userdata(`userid`)
) ENGINE = InnoDB;

CREATE TABLE `digitalid`.`userdata` (
  `userId` int(255) NOT NULL AUTO_INCREMENT,
  `fullName` text NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` text NOT NULL,
  `address` varchar(100) NOT NULL DEFAULT '38, Jalan Indah 17/10, Taman Bukit Indah, 81200, Johor Bahru, Johor.',
  `birthDate` varchar(20) NOT NULL DEFAULT '15/09/1999',
  `ic` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `userstatus` varchar(20) NOT NULL DEFAULT 'UNVERIFIED',
  `createdOn` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`userId`)
 ) ENGINE = InnoDB;

 CREATE TABLE `digitalid`.`loginqrhistory` (
   `historyid` int(200) NOT NULL AUTO_INCREMENT,
   `name` varchar(50) NOT NULL,
   `detail` text NOT NULL,
   `userid` int(255) NOT NULL,
   `username` varchar(100) NOT NULL,
   `scannedon` datetime NOT NULL DEFAULT current_timestamp(),
   PRIMARY KEY (`historyid`),
   FOREIGN KEY (`userid`) REFERENCES userdata(`userid`)
 ) ENGINE = InnoDB;