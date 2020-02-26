/**
 * Copyright 2014-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package webase.event.client.mq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.fisco.bcos.web3j.tx.txdecode.LogResult;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import webase.event.client.base.tools.CommonTools;
import webase.event.client.front.FrontRestTools;
import webase.event.client.mq.entity.BlockPushMessage;
import webase.event.client.mq.entity.EventLogPushMessage;
import webase.event.client.mq.entity.EventTypes;

/**
 * 队列监听，消费消息
 * 
 * @author marsli
 */
@Log4j2
@Component
public class MQClientListener {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private FrontRestTools frontRestTools;

    /**
     * Listener注解注解在方法上，按队列来监听
     * 
     * @param message
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RabbitListener(queues = "${spring.rabbitmq.username}")
    public void receive(Channel channel, Message message) throws IOException {
        log.info("++++++++ mq message body: {}, queue:{}", new String(message.getBody()),
                message.getMessageProperties());
        try {
            String bodyStr = new String(message.getBody());
            JSONObject json = JSONObject.parseObject(bodyStr);

            int eventType = (int) json.get("eventType");
            // 监听出块信息
            if (eventType == EventTypes.BLOCK_NOTIFY.getValue()) {
                BlockPushMessage blockPushMessage =
                        CommonTools.object2JavaBean(json, BlockPushMessage.class);
                log.info("++++++++ mq blockPushMessage: {}", blockPushMessage.toString());

                // 出块信息处理，如根据块搞查询块信息
                String uri = String.format(FrontRestTools.URI_BLOCK_BY_NUMBER,
                        blockPushMessage.getBlockNumber());
                Object blockInfo = frontRestTools.getForEntity(blockPushMessage.getGroupId(), uri,
                        Object.class);
                log.info("++++++++ mq blockInfo: {}", blockInfo.toString());

                // 监听合约事件
            } else if (eventType == EventTypes.EVENT_LOG_PUSH.getValue()) {
                EventLogPushMessage eventLogPushMessage =
                        CommonTools.object2JavaBean(json, EventLogPushMessage.class);
                log.info("++++++++ mq eventLogPushMessage: {}", eventLogPushMessage.toString());
                List<LogResult> list = CommonTools.object2JavaBean(
                        JSONArray.parse(eventLogPushMessage.getLogs()), List.class);
                log.info("++++++++ mq List<LogResult>: {}", list);

                // 合约事件处理，可根据appId做业务处理
                String appId = eventLogPushMessage.getAppId();
                log.info("++++++++ mq appId: {}", appId);

            } else {
                log.info("++++++++ mq eventType not define");
            }

            // 手动ack应答
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            log.info("++++++++ mq 消息消费成功：id：{}", message.getMessageProperties().getDeliveryTag());

        } catch (Exception e) {
            log.error("++++++++ mq 消息消费失败：id：{} Exception: {}", message.getMessageProperties().getDeliveryTag(), e);
            
            // 处理消息失败，是否将消息重新放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            // 拒绝消息
            // channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            // 多条消息被重新发送
            // channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, true);
        }
    }
}
