# 接口说明

[TOC]

## 1 队列管理模块

### 1.1 获取消息队列信息

#### 1.1.1 传输协议规范

- 网络传输协议：使用HTTP协议
- 请求地址： **/mqManage/getAllMessageQueueDetail**
- 请求方式：GET
- 请求头：Content-type: application/json
- 返回格式：JSON

#### 1.1.2 请求参数

***1）入参表***

无

***2）入参示例***

```
http://127.0.0.1:5006/WeBASE-Event-Client/mqManage/getAllMessageQueueDetail
```

#### 1.1.3 返回参数

***1）出参表***

| 序号 | 输出参数            | 类型    |      | 备注           |
| ---- | ------------------- | ------- | ---- | -------------- |
| 1    |                     | List    |      | 队列信息对象   |
| 1.1  | queueName           | string  | 否   | 链编号         |
| 1.2  | containerIdentity   | string  | 否   | 监听容器标识   |
| 1.3  | activeContainer     | boolean | 否   | 监听是否有效   |
| 1.4  | running             | boolean | 是   | 是否正在监听   |
| 1.5  | activeConsumerCount | int     | 否   | 活动消费者数量 |

***2）出参示例***

- 成功：

```
[
  {
    "queueName": "alice",
    "containerIdentity": "Container@2ce1ee55",
    "activeContainer": true,
    "running": true,
    "activeConsumerCount": 1
  }
]
```

- 失败：

```
{
    "code": 102000,
    "message": "system exception",
    "data": {}
}
```

### 1.2 重启对消息队列的监听

#### 1.2.1 传输协议规范

- 网络传输协议：使用HTTP协议
- 请求地址：**/mqManage/restartMessageListener**
- 请求方式：GET
- 返回格式：JSON

#### 1.2.2 请求参数

***1）入参表***

无

***2）入参示例***

```
http://127.0.0.1:5006/WeBASE-Event-Client/mqManage/restartMessageListener
```

#### 1.2.3 返回参数 

***1）出参表***

| 序号 | 输出参数 | 类型    |      | 备注 |
| ---- | -------- | ------- | ---- | ---- |
| 1    |          | boolean | 否   | 结果 |

***2）出参示例***

- 成功：

```
true
```

- 失败：

```
{
   "code": 102000,
   "message": "system exception",
   "data": {}
}
```

### 1.3 停止对消息队列的监听

#### 1.3.1 传输协议规范

- 网络传输协议：使用HTTP协议
- 请求地址：**/mqManage/stopMessageListener**
- 请求方式：DELETE
- 请求头：Content-type: application/json
- 返回格式：JSON

#### 1.3.2 请求参数

***1）入参表***

***2）入参示例***

```
http://127.0.0.1:5006/WeBASE-Event-Client/mqManage/stopMessageListener
```

#### 1.3.3 返回参数 

***1）出参表***

| 序号 | 输出参数 | 类型    |      | 备注 |
| ---- | -------- | ------- | ---- | ---- |
| 1    |          | boolean | 否   | 结果 |

***2）出参示例***

- 成功：

```
true
```

- 失败：

```
{
    "code": 102000,
    "message": "system exception",
    "data": {}
}
```

## 2 前置模块

### 2.1 注册出块事件监听配置

注册出块通知监听配置，返回改用户的出块通知监听配置列表。

#### 2.1.1 传输协议规范

- 网络传输协议：使用HTTP协议
- 请求地址： **/front/newBlockEvent**
- 请求方式：POST
- 请求头：Content-type: application/json
- 返回格式：JSON

#### 2.1.2 请求参数

***1）入参表***

| 序号 | 输入参数     | 类型   | 可为空 | 备注                         |
| ---- | ------------ | ------ | ------ | ---------------------------- |
| 1    | appId        | string | 否     | 注册事件通知的应用的唯一编号 |
| 2    | groupId      | int    | 否     | 群组编号                     |
| 3    | exchangeName | string | 否     | 队列交换机名称               |
| 4    | queueName    | string | 否     | 队列名称，以用户名作队列名   |

***2）入参示例***

```
http://127.0.0.1:5006/WeBASE-Event-Client/front/newBlockEvent
```

```
{
  "appId": "appId7",
  "exchangeName": "exchange_group1",
  "groupId": 1,
  "queueName": "alice"
}
```

#### 2.1.3 返回参数

***1）出参表***

| 序号 | 输出参数     | 类型   |      | 备注                              |
| ---- | ------------ | ------ | ---- | --------------------------------- |
| 1    | code         | Int    | 否   | 返回码，0：成功 其它：失败        |
| 2    | message      | String | 否   | 描述                              |
| 3    |              | Object |      | 节点信息对象                      |
| 3.1  | id           | int    | 否   | 事件编号                          |
| 3.2  | eventType    | int    | 否   | 事件类型（1: 出块, 2: 合约event） |
| 3.3  | appId        | string | 否   | 注册事件通知的应用的唯一编号      |
| 3.4  | groupId      | string | 否   | 群组编号                          |
| 3.5  | exchangeName | int    | 否   | 队列所属交换机名字                |
| 3.6  | queueName    | string | 否   | 队列名称，以用户名作队列名        |
| 3.7  | routingKey   | string | 是   | 路由键                            |

***2）出参示例***

- 成功：

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": "8aa4a195706058d60170606041e00001",
      "eventType": 1,
      "appId": "appId7",
      "groupId": 1,
      "exchangeName": "exchange_group1",
      "queueName": "alice",
      "routingKey": "alice_block_appId7"
    }
  ]
}
```

- 失败：

```
{
    "code": 102000,
    "message": "system exception",
    "data": {}
}
```

### 2.2 注册合约事件监听配置

注册合约事件监听配置，返回改用户的合约事件监听配置列表。

#### 2.2.1 传输协议规范

- 网络传输协议：使用HTTP协议
- 请求地址： **/front/contractEvent**
- 请求方式：POST
- 请求头：Content-type: application/json
- 返回格式：JSON

#### 2.2.2 请求参数

***1）入参表***

| 序号 | 输入参数        | 类型         | 可为空 | 备注                          |
| ---- | --------------- | ------------ | ------ | ----------------------------- |
| 1    | appId           | string       | 否     | 注册事件通知的应用的唯一编号  |
| 2    | groupId         | int          | 否     | 群组编号                      |
| 3    | exchangeName    | string       | 否     | 队列交换机名称                |
| 4    | queueName       | string       | 否     | 队列名称，以用户名作队列名    |
| 5    | contractAddress | string       | 否     | 已部署合约地址                |
| 6    | contractAbi     | string       | 否     | 已部署合约abi                 |
| 7    | fromBlock       | string       | 否     | 监听的开始块高                |
| 8    | toBlock         | string       | 否     | 监听的结束块高                |
| 9    | topicList       | List<String> | 是     | 事件名（如：SetName(string)） |

***2）入参示例***

```
http://127.0.0.1:5006/WeBASE-Event-Client/front/contractEvent
```

```
{
  "appId": "appId8",
  "contractAbi": "[{\"constant\":false,\"inputs\":[{\"name\":\"n\",\"type\":\"string\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"n\",\"type\":\"string\"}],\"name\":\"set2\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"}],\"name\":\"SetName\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"}],\"name\":\"SetName2\",\"type\":\"event\"}]",
  "contractAddress": "0x8ec4f530256ad3ee3957b2bdccc6d58252ecf29d",
  "exchangeName": "exchange_group1",
  "fromBlock": "latest",
  "groupId": 1,
  "queueName": "alice",
  "toBlock": "latest",
  "topicList": [
    "SetName(string)"
  ]
}
```

#### 2.2.3 返回参数

***1）出参表***

| 序号 | 输出参数        | 类型         |      | 备注                              |
| ---- | --------------- | ------------ | ---- | --------------------------------- |
| 1    | code            | Int          | 否   | 返回码，0：成功 其它：失败        |
| 2    | message         | String       | 否   | 描述                              |
| 3    |                 | Object       |      | 节点信息对象                      |
| 3.1  | id              | int          | 否   | 事件编号                          |
| 3.2  | eventType       | int          | 否   | 事件类型（1: 出块, 2: 合约event） |
| 3.3  | appId           | string       | 否   | 注册事件通知的应用的唯一编号      |
| 3.4  | groupId         | string       | 否   | 群组编号                          |
| 3.5  | exchangeName    | int          | 否   | 队列所属交换机名字                |
| 3.6  | queueName       | string       | 否   | 队列名称，以用户名作队列名        |
| 3.7  | routingKey      | string       | 是   | 路由键                            |
| 3.8  | contractAddress | string       | 否   | 已部署合约地址                    |
| 3.9  | contractAbi     | string       | 否   | 已部署合约abi                     |
| 3.10 | fromBlock       | string       | 否   | 监听的开始块高                    |
| 3.11 | toBlock         | string       | 否   | 监听的结束块高                    |
| 3.12 | topicList       | List<String> | 是   | 事件名（如：SetName(string)）     |

***2）出参示例***

- 成功：

```
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": "8aa4a195706058d60170606b69260002",
      "eventType": 2,
      "appId": "appId8",
      "groupId": 1,
      "exchangeName": "exchange_group1",
      "queueName": "alice",
      "routingKey": "alice_event_appId8",
      "contractAbi": "[{\"constant\":false,\"inputs\":[{\"name\":\"n\",\"type\":\"string\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"n\",\"type\":\"string\"}],\"name\":\"set2\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"}],\"name\":\"SetName\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"}],\"name\":\"SetName2\",\"type\":\"event\"}]",
      "fromBlock": "latest",
      "toBlock": "latest",
      "contractAddress": "0x8ec4f530256ad3ee3957b2bdccc6d58252ecf29d",
      "topicList": "SetName(string)"
    }
  ]
}
```

- 失败：

```
{
    "code": 102000,
    "message": "system exception",
    "data": {}
}
```

