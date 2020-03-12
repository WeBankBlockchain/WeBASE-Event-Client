/**
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package webase.event.client.mq.entity;

import lombok.Data;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.util.ObjectUtils;

@Data
public class MQDatailInfo {
    /**
     * 队列名称
     */
    private String queueName;

    /**
     * 监听容器标识
     */
    private String containerIdentity;

    /**
     * 监听是否有效
     */
    private boolean activeContainer;

    /**
     * 是否正在监听
     */
    private boolean running;

    /**
     * 活动消费者数量
     */
    private int activeConsumerCount;

    public MQDatailInfo(String queueName, SimpleMessageListenerContainer container) {
        this.queueName = queueName;
        this.running = container.isRunning();
        this.activeContainer = container.isActive();
        this.activeConsumerCount = container.getActiveConsumerCount();
        this.containerIdentity = "Container@" + ObjectUtils.getIdentityHexString(container);
    }
}
