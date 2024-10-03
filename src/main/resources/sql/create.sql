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

CREATE TABLE `employee` (
                            `id` int(10) NOT NULL AUTO_INCREMENT,
                            `name` varchar(30) DEFAULT NULL,
                            `dept_id` int(10) NOT NULL,
                            PRIMARY KEY (`id`)
);
INSERT INTO `employee` VALUES
                           (1, '小张', 1),
                           (2, '小李', 1),
                           (3, '小吴', 1),
                           (4, '小蒋', 1),
                           (5, '小王', 2),
                           (6, '小刘', 2),
                           (7, '小崔', 2),
                           (8, '小林', 2);

CREATE TABLE `user` (
                        `user_id` int(10) NOT NULL AUTO_INCREMENT,
                        `user_name` varchar(30) NOT NULL,
                        `nick_name` varchar(30),
                        `email` varchar(255) NOT NULL,
                        `sex` char(1) DEFAULT '男',
                        `avatar` varchar(255),
                        `password` varchar(30) NOT NULL,
                        `login_ip` varchar(15) NOT NULL,
                        `login_date` date not null,
                        `text` varchar(255),
                        PRIMARY KEY (`user_id`)
);
INSERT INTO `user` (`user_name`, `nick_name`, `email`, `sex`, `avatar`, `password`, `login_ip`, `login_date`, `text`)
VALUES
    ('张三', '张三丰', 'zhangsan@example.com', '男', 'avatar_zhangsan.png', 'password1', '192.168.1.1', '2023-04-01', '用户1描述'),
    ('李四', '李四娘', 'lisi@example.com', '女', 'avatar_lisi.png', 'password2', '192.168.1.2', '2023-04-02', '用户2描述'),
    ('王五', '王五爷', 'wangwu@example.com', '男', 'avatar_wangwu.png', 'password3', '192.168.1.3', '2023-04-03', '用户3描述'),
    ('赵六', '赵六妹', 'zhaoliu@example.com', '女', 'avatar_zhaoliu.png', 'password4', '192.168.1.4', '2023-04-04', '用户4描述'),
    ('孙七', '孙七弟', 'sunqi@example.com', '男', 'avatar_sunqi.png', 'password5', '192.168.1.5', '2023-04-05', '用户5描述'),
    ('周八', '周八姐', 'zhouba@example.com', '女', 'avatar_zhouba.png', 'password6', '192.168.1.6', '2023-04-06', '用户6描述'),
    ('吴九', '吴九妹', 'wujiu@example.com', '女', 'avatar_wujiu.png', 'password7', '192.168.1.7', '2023-04-07', '用户7描述'),
    ('郑十', '郑十弟', 'zhengshi@example.com', '男', 'avatar_zhengshi.png', 'password8', '192.168.1.8', '2023-04-08', '用户8描述'),
    ('钱十一', '钱十一妹', 'qianshiyi@example.com', '女', 'avatar_qianshiyi.png', 'password9', '192.168.1.9', '2023-04-09', '用户9描述'),
    ('陈十二', '陈十二弟', 'chenshier@example.com', '男', 'avatar_chenshier.png', 'password10', '192.168.1.10', '2023-04-10', '用户10描述'),
    ('卫十三', '卫十三姐', 'weishisan@example.com', '女', 'avatar_weishisan.png', 'password11', '192.168.1.11', '2023-04-11', '用户11描述'),
    ('蒋十四', '蒋十四弟', 'jiangshisi@example.com', '男', 'avatar_jiangshisi.png', 'password12', '192.168.1.12', '2023-04-12', '用户12描述'),
    ('沈十五', '沈十五妹', 'shenshiwu@example.com', '女', 'avatar_shenshiwu.png', 'password13', '192.168.1.13', '2023-04-13', '用户13描述'),
    ('韩十六', '韩十六弟', 'hanshiliu@example.com', '男', 'avatar_hanshiliu.png', 'password14', '192.168.1.14', '2023-04-14', '用户14描述');
