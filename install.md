# 部署说明

## 1. 前提条件

| 序号 | 软件                |
| ---- | ------------------- |
| 1    | FISCO-BCOS 2.2.0    |
| 2    | WeBASE-Front v1.2.3 |
| 3    | Java8或以上版本     |
| 4    | RabbitMQ新版本      |


## 2. 注意事项
*  Java推荐使用[OpenJDK](https://openjdk.java.net/ )，建议从OpenJDK网站自行下载（CentOS的yum仓库的OpenJDK缺少JCE(Java Cryptography Extension)，导致Web3SDK无法正常连接区块链节点）[下载地址](https://jdk.java.net/java-se-ri/11) [安装指南](https://openjdk.java.net/install/index.html)
*  安装说明可以参考 [安装示例](./appendix.md#1-安装示例)
*  在服务搭建的过程中，如碰到问题，请查看 [常见问题解答](./appendix.md#2-常见问题)
*  安全温馨提示： 强烈建议设置复杂的数据库登录密码，且严格控制数据操作的权限和网络策略


## 3. 拉取代码
执行命令：
```shell
git clone https://github.com/WeBankFinTech/WeBASE-Event-Client.git
```
进入目录：

```shell
cd WeBASE-Event-Client
```

## 4. 编译代码

方式一：如果服务器已安装Gradle，且版本为Gradle-4.10或以上

```shell
gradle build -x test
```

方式二：如果服务器未安装Gradle，或者版本不是Gradle-4.10或以上，使用gradlew编译

```shell
chmod +x ./gradlew && ./gradlew build -x test
```

构建完成后，会在根目录WeBASE-Event-Client下生成已编译的代码目录dist。

## 5. 服务配置及启停
### 5.1 服务配置修改
（1）回到dist目录，dist目录提供了一份配置模板conf_template：

```
根据配置模板生成一份实际配置conf。初次部署可直接拷贝。
例如：cp conf_template conf -r
```

（2）修改服务配置，完整配置项说明请查看 [配置说明](./appendix.md#3-applicationyml配置项说明)
```shell
vi conf/application.yml
```

### 5.2 服务启停
在dist目录下执行：
```shell
启动：bash start.sh
停止：bash stop.sh
检查：bash status.sh
```
**备注**：服务进程起来后，需通过日志确认是否正常启动，出现以下内容表示正常；如果服务出现异常，确认修改配置后，重启。如果提示服务进程在运行，则先执行stop.sh，再执行start.sh。

```
...
	Application() - main run success...
```

## 6 访问

可以通过swagger查看调用接口：

```
http://{deployIP}:{deployPort}/WeBASE-Event-Client/swagger-ui.html
示例：http://localhost:5006/WeBASE-Event-Client/swagger-ui.html
```

**备注：** 

- 部署服务器IP和服务端口需对应修改，网络策略需开通

## 7 查看日志

在dist目录查看：
```shell
全量日志：tail -f log/WeBASE-Event-Client.log
错误日志：tail -f log/WeBASE-Event-Client-error.log
```