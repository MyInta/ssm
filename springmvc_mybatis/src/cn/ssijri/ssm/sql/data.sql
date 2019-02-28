/*Data for the table `items` */
insert  into `items`(`id`,`name`,`price`,`detail`,`pic`,`createtime`) 
values (1,'̨ʽ��',3000.0,'�õ��������ǳ��ã�������',NULL,'2015-02-03 13:22:53'),
(2,'�ʼǱ�',6000.0,'�ʼǱ����ܺã������ã���������',NULL,'2015-02-09 13:22:57'),
(3,'����',200.0,'���Ʊ����������������ã�������',NULL,'2015-02-06 13:23:02');

/*Data for the table `user` */
insert  into `user`(`id`,`username`,`birthday`,`sex`,`address`) 
values (1,'����',NULL,'2',NULL),(10,'����','2014-07-10','1','������'),
(16,'��С��',NULL,'1','����֣��'),(22,'��С��',NULL,'1','����֣��'),
(24,'������',NULL,'1','����֣��'),(25,'��С��',NULL,'1','����֣��'),(26,'����',NULL,NULL,NULL);

/*Data for the table `orders` */
insert  into `orders`(`id`,`user_id`,`number`,`createtime`,`note`) 
values (3,1,'1000010','2015-02-04 13:22:35',NULL),
(4,1,'1000011','2015-02-03 13:22:41',NULL),(5,10,'1000012','2015-02-12 16:13:23',NULL);

/*Data for the table `orderdetail` */
insert  into `orderdetail`(`id`,`orders_id`,`items_id`,`items_num`) 
values (1,3,1,1),(2,3,2,3),(3,4,3,4),(4,4,2,3);