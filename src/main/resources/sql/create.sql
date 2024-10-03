CREATE TABLE `dept` (
                        `id` int(10) NOT NULL AUTO_INCREMENT,
                        `name` varchar(30) DEFAULT NULL,
                        PRIMARY KEY (`id`)
);
INSERT INTO `dept` (`id`, `name`) VALUES (1, '元动力学习一组'), (2, '元动力学习二组');

CREATE TABLE `account` (
                           `id` int(10) NOT NULL AUTO_INCREMENT,
                           `username` varchar(30) DEFAULT NULL,
                           `money` int(10) DEFAULT 0,
                           PRIMARY KEY (`id`)
);
INSERT INTO `account` values
                          (1, '张三', 1000),
                          (2, '李四', 1000),
                          (3, '王五', 1000),
                          (4, '赵六', 1000),
                          (5, '孙七', 1000),
                          (6, '周八', 1000),
                          (7, '吴九', 1000),
                          (8, '郑十', 1000);