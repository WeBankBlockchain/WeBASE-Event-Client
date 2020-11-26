# WeBASE-Event-Client
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE/CONTRIBUTING.html)
[![CodeFactor](https://www.codefactor.io/repository/github/webankfintech/WeBASE-Event-Client/badge)](https://www.codefactor.io/repository/github/webankfintech/WeBASE-Event-Client)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f5be085401f54e7080a654693ac260d4)](https://www.codacy.com/gh/WeBankFinTech/WeBASE-Event-Client?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=WeBankFinTech/WeBASE-Event-Client&amp;utm_campaign=Badge_Grade)
[![Code Lines](https://tokei.rs/b1/github/WeBankFinTech/WeBASE-Event-Client?category=code)](https://github.com/WeBankFinTech/WeBASE-Event-Client)
[![license](http://img.shields.io/badge/license-Apache%20v2-blue.svg)](http://www.apache.org/licenses/)
[![GitHub (pre-)release](https://img.shields.io/github/release/WeBankFinTech/WeBASE-Event-Client/all.svg)](https://github.com/WeBankFinTech/WeBASE-Event-Client/releases)


WeBASE-Event-Client为客户端事件订阅服务。通过调用[WeBASE-Front](https://github.com/WeBankFinTech/WeBASE-Front)前置服务注册事件监听，获取消息进行处理。

- 客户端开发流程：

1. 客户端用户向mq-server运维管理员申请账号（用户名和密码、virtual host），运维管理员创建账号，并创建**以appId为名字的队列**，然后赋予该账户read其专属队列的权限（permission-read:queueName）。

   运维管理员提供用户名（队列名）和密码、virtual host、消息交换机名（exchangeName）。

2. 客户端调用[WeBASE-Front](https://github.com/WeBankFinTech/WeBASE-Front)前置服务接口注册事件监听。

3. 用户在客户端以用户名密码连接到对应的virtual host，监听自己队列的消息（在MQClientListener.java中`@RabbitListener`注解中配置队列名），接收到消息后解析处理。

- [部署说明](./install.md)

- [接口说明](./interface.md)

## 贡献说明
请阅读我们的[贡献文档](<https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE/CONTRIBUTING.html>)，了解如何贡献代码，并提交你的贡献。

希望在您的参与下，[WeBASE](<https://webasedoc.readthedocs.io/zh_CN/latest/index.html>)会越来越好！

## 社区
联系我们：webase@webank.com

