CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `score_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attempt_id` bigint(20) DEFAULT NULL,
  `score` int(11) NOT NULL,
  `score_timestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `badge_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `badge` VARCHAR(30) NOT NULL,
  `badge_timestamp` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `multiplication` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `factor_a` int(11) NOT NULL,
  `factor_b` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

CREATE TABLE `multiplication_result_attempt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correct` bit(1) NOT NULL,
  `result_attempt` int(11) DEFAULT NULL,
  `multiplication_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MULTIPLICATION_RESULT_ATTEMPT_MULTIPLICATION` (`multiplication_id`),
  KEY `FK_MULTIPLICATION_RESULT_ATTEMPT_USER` (`user_id`),
  CONSTRAINT `FK_MULTIPLICATION_RESULT_ATTEMPT_MULTIPLICATION` FOREIGN KEY (`multiplication_id`) REFERENCES `multiplication` (`id`),
  CONSTRAINT `FK_MULTIPLICATION_RESULT_ATTEMPT_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
