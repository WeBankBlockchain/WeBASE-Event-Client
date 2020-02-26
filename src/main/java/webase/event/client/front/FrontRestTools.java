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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import webase.event.client.base.exception.BaseException;
import webase.event.client.base.properties.ConstantProperties;

/**
 * about http request for WeBASE-Front.
 */
@Log4j2
@Service
public class FrontRestTools {

    public static final String FRONT_BASE_URL = "http://%s/WeBASE-Front/%s";
    public static final String EVENT_NEW_BLOCK_EVENT = "event/newBlockEvent";
    public static final String EVENT_CONTRACT_EVENT = "event/contractEvent";
    public static final String GROUP_LIST = "web3/groupList";
    public static final String URI_BLOCK_BY_NUMBER = "web3/blockByNumber/%1d";

    // 不需要在url中包含groupId的
    private static final List<String> URI_NOT_CONTAIN_GROUP_ID =
            Arrays.asList(EVENT_NEW_BLOCK_EVENT, EVENT_CONTRACT_EVENT);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConstantProperties cproperties;

    /**
     * get from front for entity.
     */
    public <T> T getForEntity(Integer groupId, String uri, Class<T> clazz) {
        return restTemplateExchange(groupId, uri, HttpMethod.GET, null, clazz);
    }

    /**
     * post from front for entity.
     */
    public <T> T postForEntity(Integer groupId, String uri, Object params, Class<T> clazz) {
        return restTemplateExchange(groupId, uri, HttpMethod.POST, params, clazz);
    }

    /**
     * delete from front for entity.
     */
    public <T> T deleteForEntity(Integer groupId, String uri, Object params, Class<T> clazz) {
        return restTemplateExchange(groupId, uri, HttpMethod.DELETE, params, clazz);
    }

    /**
     * restTemplate exchange.
     */
    private <T> T restTemplateExchange(Integer groupId, String uri, HttpMethod method, Object param,
            Class<T> clazz) {
        String url = buildFrontUrl(groupId, uri, method);// build url
        try {
            HttpEntity entity = buildHttpEntity(param);// build entity
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, clazz);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            JSONObject error = JSONObject.parseObject(e.getResponseBodyAsString());
            log.error("http request fail. error:{}", JSON.toJSONString(error));
            throw new BaseException(error.getInteger("code"), error.getString("errorMessage"));
        }
    }

    /**
     * build url of front service.
     */
    private String buildFrontUrl(Integer groupId, String uri, HttpMethod httpMethod) {
        uri = uriAddGroupId(groupId, uri);
        String url = String.format(FRONT_BASE_URL, cproperties.getFrontIpPort(), uri)
                .replaceAll(" ", "");
        return url;
    }

    /**
     * append groupId to uri.
     */
    public static String uriAddGroupId(Integer groupId, String uri) {
        if (groupId == null || StringUtils.isBlank(uri)) {
            return null;
        }

        final String tempUri = uri.contains("?") ? uri.substring(0, uri.indexOf("?")) : uri;

        long count = URI_NOT_CONTAIN_GROUP_ID.stream().filter(u -> u.contains(tempUri)).count();
        if (count > 0) {
            return uri;
        }
        return groupId + "/" + uri;
    }

    /**
     * build httpEntity
     */
    public static HttpEntity buildHttpEntity(Object param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String paramStr = null;
        if (Objects.nonNull(param)) {
            paramStr = JSON.toJSONString(param);
        }
        HttpEntity requestEntity = new HttpEntity(paramStr, headers);
        return requestEntity;
    }
}
