create schema saraza;
use saraza;

CREATE TABLE `career` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

CREATE TABLE `period` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `period_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `time` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `since` time NOT NULL,
  `until` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

CREATE TABLE course (
	id bigint(20) not null auto_increment,
	code varchar(150) not null,
    name varchar(200) not null,
    shift varchar(20) not null,
    year int(4) not null,
    period_id bigint(20) not null,
    time_start_id bigint(20) not null,
    time_end_id bigint(20) not null,
    career_id bigint(20) not null,
    primary key (id),
    foreign key (period_id) references period(id),
    foreign key (time_start_id) references `time`(id),
    foreign key (time_end_id) references `time`(id),
    foreign key (career_id) references `career`(id)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

CREATE TABLE `course_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_number` int(11) NOT NULL,
  `class_state` varchar(255) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `date` date NOT NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`course_id`) REFERENCES course(id)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

CREATE TABLE `contact_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emails` varchar(255) DEFAULT NULL,
  `phones` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


CREATE TABLE `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `identification_number` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `legacy_number` varchar(255) DEFAULT NULL,
  `names` varchar(255) NOT NULL,
  `perfil_pic` tinyblob,
  `contact_data_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`identification_number`),
  UNIQUE KEY (`legacy_number`),
  FOREIGN KEY (`contact_data_id`) REFERENCES `contact_data`(`id`),
  CONSTRAINT `FK2sjonrvhei884ttnrtoh9gu5s` FOREIGN KEY (`contact_data_id`) REFERENCES `contact_data` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lastname` varchar(255) NOT NULL,
  `legacy_number` varchar(255) DEFAULT NULL,
  `names` varchar(255) NOT NULL,
  `contact_data_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`legacy_number`),
  FOREIGN KEY (`contact_data_id`) REFERENCES `contact_data`(`id`),
  CONSTRAINT `FK11mhnyvg9lrbsm2xgftw6vnnf` FOREIGN KEY (`contact_data_id`) REFERENCES `contact_data` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `note` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `calification` varchar(255) NOT NULL,
  `note_type` varchar(255) NOT NULL,
  `observations` varchar(500) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `teacher_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `student_link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `link` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `class_student_present` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `present` varchar(255) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `course_class_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  FOREIGN KEY (`course_class_id`) REFERENCES `course_class` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

CREATE TABLE `class_file_document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longblob NOT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `file_document_type` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `course_class_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`course_class_id`) REFERENCES `course_class` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `last_time_logged` datetime DEFAULT NULL,
  `last_time_modified` datetime DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `user_status` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

CREATE TABLE `user_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`user_id`,`student_id`),
  UNIQUE KEY (`student_id`),
  UNIQUE KEY (`user_id`),
  CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE `user_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`user_id`,`teacher_id`),
  UNIQUE KEY (`teacher_id`),
  UNIQUE KEY (`user_id`),
  CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE `course_student` (
  `course_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  PRIMARY KEY (`course_id`,`student_id`),
  FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `course_teacher` (
  `course_id` bigint(20) NOT NULL,
  `teacher_id` bigint(20) NOT NULL,
  PRIMARY KEY (`course_id`,`teacher_id`),
  FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
