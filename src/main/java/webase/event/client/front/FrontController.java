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

package webase.event.client.front;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webase.event.client.front.entity.ReqContractEventRegister;
import webase.event.client.front.entity.ReqNewBlockEventRegister;

/**
 * @author marsli
 */
@Slf4j
@RestController
@RequestMapping("front")
public class FrontController {

    @Autowired
    FrontService frontService;

    @ApiOperation("注册出块事件监听配置")
    @PostMapping("newBlockEvent")
    public Object newBlockEvent(
            @Valid @RequestBody ReqNewBlockEventRegister reqNewBlockEventRegister) {
        log.info("start newBlockEvent. reqNewBlockEventRegister:{}", reqNewBlockEventRegister);
        return frontService.newBlockEvent(reqNewBlockEventRegister);
    }

    @ApiOperation("注册合约事件监听配置")
    @PostMapping("contractEvent")
    public Object contractEvent(
            @Valid @RequestBody ReqContractEventRegister reqContractEventRegister) {
        log.info("start contractEvent. reqContractEventRegister:{}", reqContractEventRegister);
        return frontService.contractEvent(reqContractEventRegister);
    }
}
