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

package webase.event.client.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webase.event.client.front.entity.ReqNewBlockEventRegister;
import webase.event.client.front.entity.ReqContractEventRegister;

@Service
public class FrontService {

    @Autowired
    private FrontRestTools frontRestTools;

    public Object getGroupList() {
        Integer groupId = Integer.MAX_VALUE;
        return frontRestTools.getForEntity(groupId, FrontRestTools.GROUP_LIST, Object.class);
    }

    public Object newBlockEvent(
            ReqNewBlockEventRegister reqNewBlockEventRegister) {
        return frontRestTools.postForEntity(reqNewBlockEventRegister.getGroupId(),
                FrontRestTools.EVENT_NEW_BLOCK_EVENT, reqNewBlockEventRegister, Object.class);
    }

    public Object contractEvent(ReqContractEventRegister reqContractEventRegister) {
        return frontRestTools.postForEntity(reqContractEventRegister.getGroupId(),
                FrontRestTools.EVENT_CONTRACT_EVENT, reqContractEventRegister, Object.class);
    }
}
