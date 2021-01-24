## 项目介绍

Polygon是一个前后端分离的日常事务管理系统，目前功能包括文档功能(文档上传，查询，修改，下载，删除)、请假审批功能(创建申请，查询，审批，删除)和系统管理功能(用户管理，角色管理)

项目地址：https://github.com/Yuji19/Polygon

### 项目技术栈

#### 后端技术栈

1.SpringBoot

2.SpringSecurity

3.SpringAOP

4.SpringCache

5.MyBatis

6.MySQL

7.Redis

8.RabbitMQ

#### 前端技术栈

基于vue-admin-template修改

1.vue

2.element-ui

3.vue-router

4.vuex

5.axios

---------------

## 快速部署

1.使用git clone项目到本地

2.本地准备好MySQL，创建一个名为polygon的数据库，运行sql文件(由于Security中没有对密码进行加密，所以在添加密码的使用需要加上前缀{noop})，并修改polygon-web中application.yml中有关数据库的配置

3.本地准备好Redis，并修改mailservice和polygon-web的application.yml中相关配置

4.本地准备好RabbitMQ，并修改mailservice和polygon-web的application.yml中相关配置

5.在IntelliJ IDEA中打开polygon并运行polygonservice和mailservice

6.进入vue-polygon目录，在命令行输入以下命令
```
#安装该项目的依赖
npm install

#运行vue-polygon项目
npm run serve
```
