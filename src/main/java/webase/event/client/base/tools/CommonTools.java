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

package webase.event.client.base.tools;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;

/**
 * common tools.
 */
@Log4j2
public class CommonTools {
    /**
     * conver object to java bean.
     */
    public static <T> T object2JavaBean(Object obj, Class<T> clazz) {
        if (obj == null || clazz == null) {
            log.warn("object2JavaBean. obj or clazz null");
            return null;
        }
        String jsonStr = JSON.toJSONString(obj);

        return JSON.parseObject(jsonStr, clazz);
    }
}
