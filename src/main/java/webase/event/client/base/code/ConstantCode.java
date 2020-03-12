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

package webase.event.client.base.code;

/**
 * A-BB-CCC A:error level. <br/>
 * 1:system exception <br/>
 * 2:business exception <br/>
 * B:project number <br/>
 * C: error code <br/>
 */
public class ConstantCode {

    /* return success */
    public static final RetCode SUCCESS = RetCode.mark(0, "success");

    /* system exception */
    public static final RetCode SYSTEM_EXCEPTION = RetCode.mark(102000, "system exception");

    /* Business exception */
    public static final RetCode CONTAINER_NOT_EXISTS = RetCode.mark(202000, "queue's container not exists");
    public static final RetCode CONTAINER_IS_RUNNING = RetCode.mark(202001, "container is running");
    public static final RetCode CONTAINER_NOT_RUNNING = RetCode.mark(202002, "container not running");

    /* param exception */
    public static final RetCode PARAM_EXCEPTION = RetCode.mark(302000, "param exception");
}
