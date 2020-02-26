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

package webase.event.client.front.entity;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Based on EventLogUserParams
 * handle request for registering contract event log push notify
 * @author marsli
 */
@Data
@NoArgsConstructor
public class ReqContractEventRegister {

    /**
     * application which register block notify
     */
    @NotBlank(message = "appId cannot be empty")
	private String appId;

    /**
     * group id
     */
	@NotNull(message = "groupId cannot be null")
	private Integer groupId;

    /**
     * MQ info: exchange name
     */
	@NotBlank(message = "exchangeName cannot be empty")
	private String exchangeName;

    /**
     * username as queue name
     */
	@NotBlank(message = "queueName cannot be empty, usually use username")
	private String queueName;

	/**
	 * contract abi for decoder
	 */
	@NotBlank(message = "contractAbi cannot be empty")
	private String contractAbi;

    /**
     * event log push info below
     */
	@NotBlank(message = "fromBlock cannot be empty")
	private String fromBlock;

	@NotBlank(message = "toBlock cannot be empty")
	private String toBlock;

	@NotBlank(message = "contractAddress cannot be empty")
	private String contractAddress;

    /**
     * List of topics
     */
    private List<String> topicList;
}
