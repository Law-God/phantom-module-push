package com.phantom.module.push;

import com.phantom.module.push.yunba.YunbaPush;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 张志凯 https://github.com/Law-God/phantom-module-email
 * phantom-module-push
 * com.phantom.module.push.PushPropertiesTest
 * 2016-12-22 15:05
 */
public class PushPropertiesTest {

    private YunbaPush push;

    @Before
    public void connect() throws Exception {
        push = new YunbaPush();
        push.onSocketConnectAck();
    }

    @Test
    public void test(){
        Assert.assertNotNull(PushProperties.getProperty("yunba.appkey"));
    }

    @Test
    public void push() throws Exception {
        JSONObject jsonObject = new JSONObject(new HashMap(){{put("success",true);}});
        push.onConnAck(jsonObject);

    }
}
