package com.loujie.www.test;

import com.loujie.www.gson.JsonGsonUtils;
import com.loujie.www.httpurlconnection.HttpUrlUtils;
import com.loujie.www.okhttp.OkHttpUtils;
import com.squareup.moshi.Moshi;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @name loujie
 * @date 2019/1/29
 */
public class OkHttpTest {

    @Test
    public void urltest() {
        // 3429 3511 3816 3875 3687
        // 2532 2847 2522 2141 2237
        String url = "http://118.144.248.25:9948/openapi/test/log?mobile=158";
        long c_time = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            String result = HttpUrlUtils.GET.run(url);//OkHttpUtils.GET.run(url);//HttpUrlUtils
            System.out.println(i + ":" + result);
        }
        long e_time = System.currentTimeMillis();
        System.out.println(e_time - c_time);
    }

    @Test
    public void urlpost() {
        String url = "http://118.144.248.25:9999/getCategorySubjects?apikey=a0f7127a018342a38d00937af0355c25&cid=6&uid=10005935";
        OkHttpUtils.init();
        long c_time = System.currentTimeMillis();
        String result = OkHttpUtils.POST.run(url);
        long e_time = System.currentTimeMillis();
        System.out.println(e_time - c_time);
        System.out.println(result);
    }

    @Test
    public void jsontest() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "loujie");
        params.put("age", 12);
        System.out.println(JsonGsonUtils.toJson(params));
    }

    class P {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "P{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

    }

    @Test
    public void jsonToEntity() {
        String jsonString = "[{\"name\":\"loujie\",\"age\":12},{\"name\":\"loujie2\",\"age\":112}]";
        List<P> params = JsonGsonUtils.parse(jsonString, List.class);
        System.out.println(params);
    }

}
