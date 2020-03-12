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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import webase.event.client.base.code.ConstantCode;
import webase.event.client.base.exception.BaseException;
import webase.event.client.mq.entity.MQDatailInfo;

/**
 * MQ客户端监控器
 */
@Component
public class MQManageService {

    @Autowired
    private RabbitListenerEndpointRegistry registry;

    /**
     * queue2ContainerAllMap初始化标识
     */
    private volatile boolean hasInit = false;

    /**
     * 所有的队列监听容器MAP
     */
    private final Map<String, SimpleMessageListenerContainer> allQueue2ContainerMap =
            new ConcurrentHashMap<>();
    
    /**
     * 重启对消息队列的监听
     * 
     * @param queueName
     * @return
     */
    public boolean restartMessageListener(String queueName) {
        SimpleMessageListenerContainer container = findContainerByQueueName(queueName);
        if (container.isRunning()) {
            throw new BaseException(ConstantCode.CONTAINER_IS_RUNNING);
        }        
        container.start();
        return true;
    }

    /**
     * 停止对消息队列的监听
     * 
     * @param queueName
     * @return
     */
    public boolean stopMessageListener(String queueName) {
        SimpleMessageListenerContainer container = findContainerByQueueName(queueName);
        if (!container.isRunning()) {
            throw new BaseException(ConstantCode.CONTAINER_NOT_RUNNING);
        }
        container.stop();
        return true;
    }

    /**
     * 获取消息队列信息
     * 
     * @return
     */
    public List<MQDatailInfo> getAllMessageQueueDetail() {
        List<MQDatailInfo> queueDetailList = new ArrayList<>();
        getQueue2ContainerAllMap().entrySet().forEach(entry -> {
            String queueName = entry.getKey();
            SimpleMessageListenerContainer container = entry.getValue();
            MQDatailInfo deatil = new MQDatailInfo(queueName, container);
            queueDetailList.add(deatil);
        });

        return queueDetailList;
    }

    /**
     * 根据队列名查找消息监听容器
     * 
     * @param queueName
     * @return
     */
    private SimpleMessageListenerContainer findContainerByQueueName(String queueName) {
        String key = StringUtils.trim(queueName);
        SimpleMessageListenerContainer container = getQueue2ContainerAllMap().get(key);
        if (container == null) {
            throw new BaseException(ConstantCode.CONTAINER_NOT_EXISTS);
        }  
        return container;
    }

    private Map<String, SimpleMessageListenerContainer> getQueue2ContainerAllMap() {
        if (!hasInit) {
            synchronized (allQueue2ContainerMap) {
                if (!hasInit) {
                    registry.getListenerContainers().forEach(container -> {
                        SimpleMessageListenerContainer simpleContainer =
                                (SimpleMessageListenerContainer) container;
                        Arrays.stream(simpleContainer.getQueueNames())
                                .forEach(queueName -> allQueue2ContainerMap
                                        .putIfAbsent(StringUtils.trim(queueName), simpleContainer));
                    });
                    hasInit = true;
                }
            }
        }
        return allQueue2ContainerMap;
    }
}
