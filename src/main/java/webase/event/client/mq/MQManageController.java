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

import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webase.event.client.base.controller.BaseController;
import webase.event.client.mq.entity.MQDatailInfo;

/**
 * MQ管理控制器
 */
@RestController
@RequestMapping("/mqManage")
public class MQManageController extends BaseController {

    @Autowired(required = false)
    private MQManageService mqClientMonitor;
    
    /**
     * 获取消息队列信息
     * @return
     */
    @ApiOperation("获取消息队列信息")
    @GetMapping("getAllMessageQueueDetail")
    public List<MQDatailInfo> getAllMessageQueueDetail() {
        return mqClientMonitor.getAllMessageQueueDetail();
    }

    /**
     * 重启对消息队列的监听
     * @param queueName
     * @return
     */
    @ApiOperation("重启对消息队列的监听")
    @GetMapping("restartMessageListener")
    public boolean restartMessageListener(String queueName) {
        return mqClientMonitor.restartMessageListener(queueName);
    }

    /**
     * 停止对消息队列的监听
     * @param queueName
     * @return
     */
    @ApiOperation("停止对消息队列的监听")
    @GetMapping("stopMessageListener")
    public boolean stopMessageListener(String queueName) {
        return mqClientMonitor.stopMessageListener(queueName);
    }
}
