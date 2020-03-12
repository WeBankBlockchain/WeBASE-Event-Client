/**
 * Copyright 2014-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package webase.event.client.front;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import webase.event.client.BaseTest;
import webase.event.client.front.entity.ReqNewBlockEventRegister;
import webase.event.client.front.entity.ReqContractEventRegister;

@WebAppConfiguration
public class FrontControllerTest extends BaseTest {

	private MockMvc mockMvc;
	private Integer groupId = 1;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp()  {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	/**
     * need to create exchange and queue with routing key in mq
     * @throws Exception
     */
    @Test
    public void testRegisterBlockNotify() throws Exception {
        ReqNewBlockEventRegister param = new ReqNewBlockEventRegister();
        param.setExchangeName("exchange_group1");
        param.setQueueName("alice");
        param.setAppId("appId001");
        param.setGroupId(groupId);
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/front/blockNotify").
                        content(JSON.toJSONString(param)).
                        contentType(MediaType.APPLICATION_JSON)
                );
        resultActions.
                andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
        System.out
                .println("response:" + resultActions.andReturn().getResponse().getContentAsString());
    }

	/**
	 * need to create exchange and queue with routing key in mq
	 * @throws Exception
	 */
	@Test
	public void testRegisterEventLogPush() throws Exception {
		ReqContractEventRegister param = new ReqContractEventRegister();
		param.setGroupId(groupId);
		param.setAppId("appId3");
		param.setExchangeName("exchange_group1");
		param.setQueueName("alice");
		param.setContractAbi("[{\"constant\":false,\"inputs\":[{\"name\":\"n\",\"type\":\"string\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"n\",\"type\":\"string\"}],\"name\":\"set2\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"}],\"name\":\"SetName\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"name\",\"type\":\"string\"}],\"name\":\"SetName2\",\"type\":\"event\"}]");
		param.setFromBlock("latest");
		param.setToBlock("latest");
		param.setContractAddress("0x8ec4f530256ad3ee3957b2bdccc6d58252ecf29d");
		List<String> topics = new ArrayList<>();
		topics.add("SetName(string)");
		param.setTopicList(topics);
		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.post("/front/eventLogPush").
						content(JSON.toJSONString(param)).
						contentType(MediaType.APPLICATION_JSON)
				);
		resultActions.
				andExpect(MockMvcResultMatchers.status().isOk()).
				andDo(MockMvcResultHandlers.print());
		System.out
				.println("response:" + resultActions.andReturn().getResponse().getContentAsString());
	}
}
