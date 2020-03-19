# erp-framework

erp-framework是一个基于在Springboot框架上的开箱即用的管理平台，目的为了简化构建平台的繁杂过程，让大家直接能够基于在此结构上直接开发或者扩展。

**QQ交流群：1023992012**
**登录用户/密码: admin/1**

1、代码更新日志

**2019年1月15日**
主要完成了代码生成工具，目的是完成基本的增删改查功能，无需重复复制代码

**2019年2月3日**
调整对应的代码功能，使用Mybatis-Plus实现代码的生成功能，同时实现权限功能，优化Swagger代码独立于一个module中。

**2019年3月6日**
重新调整框架的目录结构，实现权限管理功能界面的开发

**2019年4月4日**
集成shiro，实现用户登录权限管理

**2019年4月26日~2019年4月28日**
使用fork/join来实现数据的导入功能

**2019年5月5日~2019年5月13日**
实现pdf预览，基于Webupload和FastDFS的文件上传

2、技术选型

后端技术：

技术 |	名称 |	官网
------------ | ------------- | -------------
SpringBoot | 容器框架	|
freemarker | 模板引擎	|
velocity | 模板引擎	|
tk.mybatis | ORM框架	|
Mybatis-plus | ORM框架和代码生成	|
Lombok | setter和getter	|
mysql-connector-java | 连接驱动	|
druid | 数据库连接池	|
pagehelper | 分页插件	|
swagger2 | 接口测试框架 | https://swagger.io/
Jackson-databind | 对象与json相互转换	|
Apache Shiro | 权限控制	|
hutool | 工具类	|
Shiro-redis | 缓存	|
Easypoi | Excel导入导出工具	|
Itextpdf | PDF预览	|
FastDFS | 分布式文件系统	|

前端技术：

技术 |	名称 |	官网
------------ | ------------- | -------------
Layui | |
Webuploader | 文件上传 |

3、目录结构

![Image](https://github.com/chyanwu/erp-framework/blob/master/src/main/resources/image/catalogue.png)

4、简单功能界面
![Image text](https://github.com/chyanwu/erp-framework/blob/master/0.png)
![Image text](https://github.com/chyanwu/erp-framework/blob/master/1.png)
![Image text](https://github.com/chyanwu/erp-framework/blob/master/2.png)
![Image text](https://github.com/chyanwu/erp-framework/blob/master/3.png)
![Image text](https://github.com/chyanwu/erp-framework/blob/master/4.png)
