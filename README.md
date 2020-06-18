# user-service

### 介绍

思必驰标注与训练一体化平台（EZML）-- 用户鉴权微服务。主要提供权限管理与鉴权API

### 软件架构

* SpringCloud
* SpringBoot
* H2(dev) + MySQL + MyBatis + Druid
* JWT

### 开发约定

#### 项目结构

* `annotation` 中存放所有自定义注解
* `bean` 中存放所有自定义spring组件
* `config` 中存放所有Spring配置类
* `constant` 中存放所有静态变量类包括枚举等
* `controller`
* `dao`
* `menum` 中存放所有数据库实体类属性变量对应的枚举类
* `model`
* `pojo`
* `service`
* `support` 中存放一些结构或者设计模式辅助类，不应包含POJO
* `tool` 中存放所有工具类，代码中需要使用的第三方库尽量使用工具类封装
* `validation` 中存放自定义参数校验

#### 资源文件

* `db` 中包括一些初始化与数据库迁移的sql文件
* `i18n` 中包含国际化消息配置文件

### 安装教程



