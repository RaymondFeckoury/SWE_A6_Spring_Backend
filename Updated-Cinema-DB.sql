-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 26, 2018 at 07:52 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

use cinemabooking;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cinemabooking`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(100) NOT NULL UNIQUE,
  `uname` varchar(100) NOT NULL,
  `uusername` varchar(100) NOT NULL,
  `upassword` varchar(100) NOT NULL,
  `uhash` varchar(100) NOT NULL,
  `uemail` varchar(100) NOT NULL UNIQUE,
  `umobile` int(10) NOT NULL UNIQUE,
  `uaddress` text NOT NULL,
  `uactive` int(255) NOT NULL DEFAULT '0',
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1; 

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `uname`, `uusername`, `upassword`, `uhash`, `uemail`, `umobile`, `uaddress`, `uactive`) VALUES
(1, 'Naveen Kurra', 'freedom', '$2y$10$22ezmzHRa9c5ycHmVm5RpOnlT4LwFaDZar1XhmLRJQKGrcVRhPgti', '61b4a64be663682e8cb037d9719ad8cd', 'nk121@gmail.com', 7089, 'abcde', 0),
(2, 'Shanwaz K', 'shan', '$2y$10$22ezmzHRa9c5ycHmVm5RpOnlT4LwFaDZar1XhmLRJQKGrcVRhPgti', '61b4a64be663682e8cb037d9719ad8cd', 'sk198@gmail.com', 7090, 'abcde', 0),
(3, 'Raymond Jackie F', 'RJF', '$2y$10$22ezmzHRa9c5ycHmVm5RpOnlT4LwFaDZar1XhmLRJQKGrcVRhPgti', '61b4a64be663682e8cb037d9719ad8cd', 'rjf121@gmail.com', 9090, 'abcde', 0),
(4, 'Punith K', 'venkata', '$2y$10$22ezmzHRa9c5ycHmVm5RpOnlT4LwFaDZar1XhmLRJQKGrcVRhPgti', '61b4a64be663682e8cb037d9719ad8cd', 'vk134@gmail.com',1234, 'abcde', 0);

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(255) NOT NULL UNIQUE,
  `aname` varchar(255) NOT NULL,
  `ausername` varchar(255) NOT NULL,
  `apassword` varchar(255) NOT NULL,
  `ahash` varchar(255) NOT NULL,
  `aemail` varchar(255) NOT NULL UNIQUE,
  `amobile` int(10) NOT NULL UNIQUE,
  `aaddress` text NOT NULL,
  `aactive` int(255) NOT NULL DEFAULT '0',
   PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `aname`, `ausername`, `apassword`, `ahash`, `aemail`, `amobile`, `aaddress`, `aactive`) VALUES
(1, 'Naveen Kurra', 'freedom', '$2y$10$22ezmzHRa9c5ycHmVm5RpOnlT4LwFaDZar1XhmLRJQKGrcVRhPgti', '61b4a64be663682e8cb037d9719ad8cd', 'nk121@gmail.com', 1, 'abcde', 0),
(2, 'Raymond Jakie F', 'Befree', '$2y$10$22ezmzHRa9c5ycHmVm5RpOnlT4LwFaDZar1XhmLRJQKGrcVRhPgti', '61b4a64be663682e8cb037d9719ad8cd', 'rjf198@gmail.com', 2, 'abcde', 0),
(3, 'Punith', 'freesoul', '$2y$10$22ezmzHRa9c5ycHmVm5RpOnlT4LwFaDZar1XhmLRJQKGrcVRhPgti', '61b4a64be663682e8cb037d9719ad8cd', 'vk122@gmail.com', 3, 'abcde', 0),
(4, 'Shanwaz', 'spreadsmiles', '$2y$10$22ezmzHRa9c5ycHmVm5RpOnlT4LwFaDZar1XhmLRJQKGrcVRhPgti', '61b4a64be663682e8cb037d9719ad8cd', 'sk198@gmail.com', 4, 'abcde', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Movie`
--

CREATE TABLE `movie` (
  `mid` int(255) NOT NULL UNIQUE,
  `aid` int(255) NOT NULL,
  `moviename` varchar(255) NOT NULL UNIQUE,
  `movielanguage` varchar(255) NOT NULL,
  `minfo` varchar(255) NOT NULL,
  `moviebookingstartdate` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `mimage` varchar(255) NOT NULL DEFAULT 'blank.png',
  `picStatus` int(10) NOT NULL DEFAULT '0',
  `trailerlink` varchar(255) NOT NULL,
   PRIMARY KEY (mid),
   FOREIGN KEY (aid) REFERENCES admin(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Dumping data for table `Movie`
--

INSERT INTO `movie` (`aid`, `mid`, `moviename`, `movielanguage`,`minfo`,`moviebookingstartdate`, `price`, `mimage`, `picStatus`,`trailerlink`) VALUES
(1, 1, 'Avatar','English','best collected movie\r\n','09-24-2022', 23, 'Avatar.jpeg', 1,'https://www.youtube.com/watch?v=3Soht3ISW-E'),
(1, 2, 'EndGame','English','sad to hear that ironman was dead\r\n','09-24-2022', 23, 'endgame.jpeg', 1,'https://www.youtube.com/watch?v=3Soht3ISW-E'),
(1, 3, 'HarryPorter','English','The revolutionary movie\r\n','09-24-2022', 23, 'Harryporter.jpeg', 1,'https://www.youtube.com/watch?v=3Soht3ISW-E'),
(1, 4, 'FantasticBeasts','English','The beasts are fantastic here\r\n','09-24-2022', 23, 'Fb.jpeg', 1,'https://www.youtube.com/watch?v=3Soht3ISW-E');

-- --------------------------------------------------------

--
-- Table structure for table `theater`
--

CREATE TABLE `theater` (
  `tid` int(255) NOT NULL UNIQUE,
  `Name` varchar(255) NOT NULL UNIQUE,
  `NoOfScreens` int(255) NOT NULL,
   PRIMARY KEY (tid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `theater`
--

INSERT INTO `theater` (`tid`, `Name`,`NoOfScreens`) VALUES
(1,'B&B', 5),
(2,'TJMax',10),
(3, 'GeorgiaTheater' ,11),
(4,'GSM', 7);

-- --------------------------------------------------------

--
-- Table structure for table `Showroom`
--

CREATE TABLE `showroom` (
  `srid` int(255) NOT NULL UNIQUE,
  `theaterID` int(255) NOT NULL,
  `mid` int(255) NOT NULL,
  `NoOfSeats` int(255) NOT NULL,
   PRIMARY KEY (srid),
   FOREIGN KEY (theaterID) REFERENCES theater(tid),
   FOREIGN KEY (mID) REFERENCES movie(mid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `showroom`
--

INSERT INTO `showroom` (`srid`,`theaterID`,`mid`, `NoOfSeats`) VALUES
(1,1,2,255),
(2,2,3,255),
(3, 2,1 ,220),
(4,4,4, 240);

-- --------------------------------------------------------

--
-- Table structure for table `showtime`
--

CREATE TABLE `showtime` (
  `stid` int(255) NOT NULL UNIQUE,
  `theaterID` int(255) NOT NULL,
  `showroomID` int(255) NOT NULL,
  `mid` int(255) NOT NULL,
  `NoOfSeatsAvailiable` int(255) NOT NULL,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
   PRIMARY KEY (stid),
   FOREIGN KEY (mid) REFERENCES movie(mid),
   FOREIGN KEY (showroomID) REFERENCES showroom(srid),
   FOREIGN KEY (theaterID) REFERENCES theater(tid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `showtime`
--

INSERT INTO `showtime` (`stid`,`theaterID`,`showroomID`,`mid`,`NoOfSeatsAvailiable`, `date`,`time`) VALUES
(1,1,2,1,125,'2022-09-24', '10:00:00'),
(2,2,3,1,123,'2022-09-25', '18:00:00'),
(3,2,1,2,150,'2022-09-26', '13:00:00'),
(4,3,1,1,200,'2022-09-27', '22:30:00');

-- --------------------------------------------------------
--
-- Table structure for table `PaymentCard`
--

CREATE TABLE `paymentcard` (
  `pid` int(255) NOT NULL UNIQUE,
  `uid` int(255) NOT NULL,
  `cardnumber` int(255) NOT NULL UNIQUE,
  `expirymonth` int(255) NOT NULL,
  `expiryyear` int(255) NOT NULL,
   PRIMARY KEY (pid),
   FOREIGN KEY (uid) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paymentcard`
--

INSERT INTO `paymentcard` (`pid`, `uid`,`cardnumber`,`expirymonth`,`expiryyear`) VALUES
(1,1,123,09, 2028),
(2,2,456,09, 2028),
(3,1,742,09, 2028),
(4,3,328,09, 2028);


-- --------------------------------------------------------

--
-- Table structure for table `Promotion`
--

CREATE TABLE `promotion` (
  `pid` int(255) NOT NULL UNIQUE,
  `pcode` varchar(255) NOT NULL UNIQUE,
  `start` DATE NOT NULL,
  `end` DATE NOT NULL,
  `percentage` int(255) NOT NULL,
   PRIMARY KEY (pid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Promotion`
--

INSERT INTO `promotion` (`pid`, `pcode`,`start`,`end`,`percentage`) VALUES
(1,'SE123','2022-10-15','2022-12-15', 15),
(2,'SE666','2022-10-16','2022-12-15',10),
(3, 'SE456','2022-10-17','2022-12-15' ,25),
(4,'SE159','2022-10-18','2022-12-15', 30);


-- --------------------------------------------------------

--
-- Table structure for table `Booking`
--

CREATE TABLE `booking` (
  `Bid` int(255) NOT NULL UNIQUE,
  `uid` int(255) NOT NULL,
  `mid` int(255) NOT NULL,
  `showID` int(255) NOT NULL,
  `numberoftickets` int NOT NULL,
  `paymentID` int NOT NULL,
  `promoID` int NOT NULL,
  `ticketcost` float NOT NULL,
  `taxes` float NOT NULL,
  `totalamount` float NOT NULL,
   PRIMARY KEY (Bid),
   FOREIGN KEY (uid) REFERENCES users(id),
   FOREIGN KEY (mid) REFERENCES movie(mid),
   FOREIGN KEY (showID) REFERENCES showtime(stid),
   FOREIGN KEY (paymentID) REFERENCES paymentcard(pid),
   FOREIGN KEY (promoID) REFERENCES promotion(pid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Booking`
--

INSERT INTO `booking` (`Bid`,`uid`, `mid`,`showID`,`numberoftickets`,`paymentID`,`promoID`, `ticketcost`,`taxes`,`totalamount`) VALUES
(1,1, 1,1,4,1,1,22.2,1.23,23.43),
(2,2, 1,2,3,2,1,22.2,1.23,23.43),
(3,1, 1,1,5,2,2,22.2,1.23,23.43),
(4, 3,1,2,3,3,4,22.2,1.23,23.43);

-- --------------------------------------------------------

--
-- Table structure for table `SeatInShow`
--

CREATE TABLE `seatinshow` (
  `seatID` int(255) NOT NULL UNIQUE,
  `showID` int(255) NOT NULL,
   PRIMARY KEY (seatID),
   FOREIGN KEY (showID) REFERENCES showtime(stid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SeatInShow`
--

INSERT INTO `seatinshow` (`seatID`, `showID`) VALUES
(1,1),
(2,2),
(3,1),
(4,3);

-- --------------------------------------------------------

--
-- Table structure for table `ratings`
--

CREATE TABLE `rating` (
  `rID` int(255) NOT NULL UNIQUE,
  `movieID` int(255) NOT NULL,
  `rating`  float,
   PRIMARY KEY (rID),
   FOREIGN KEY (movieID) REFERENCES movie(mid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`rID`, `movieID`,`rating`) VALUES
(1,1,4.5),
(2,2,5),
(3,1,9.5),
(4,3,8.9);

-- --------------------------------------------------------

--
-- Table structure for table `userstatus`
--

CREATE TABLE `userstatus` (
  `uID` int(255) NOT NULL,
  `Status` int(2) NOT NULL DEFAULT '0',
   PRIMARY KEY (uID),
   FOREIGN KEY (uID) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userstatus`
--

INSERT INTO `userstatus` (`uID`, `Status`) VALUES
(1,0),
(2,0),
(3,0),
(4,1);

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `tid` int NOT NULL UNIQUE,
  `ttp` int NOT NULL,
  `bID` int NOT NULL,
  `SID` int NOT NULL,
   PRIMARY KEY (tID),
   FOREIGN KEY (bID) REFERENCES booking(Bid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`tID`,`ttp`, `bID`,`SID`) VALUES
(1,1,1,11),
(2,2,1,22),
(3,3,2,33),
(4,1,3,44);

-- --------------------------------------------------------

--
-- Table structure for table `tickettype`
--

CREATE TABLE `tickettype` (
  `tid` int NOT NULL UNIQUE,
  `ttp` varchar(255) NOT NULL,
   PRIMARY KEY (tid),
   FOREIGN KEY (tid) REFERENCES ticket(tid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tickettype`
--

INSERT INTO `tickettype` (`tid`,`ttp`) VALUES
(1,1),
(2,2),
(3,3),
(4,1);

-- --------------------------------------------------------








/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;