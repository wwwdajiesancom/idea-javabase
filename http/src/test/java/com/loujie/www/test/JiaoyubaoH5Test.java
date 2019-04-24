package com.loujie.www.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.loujie.www.okhttp.OkHttpUtils;

/**
 * h5端的限制
 * 
 * @author loujie
 *
 */
public class JiaoyubaoH5Test {

	String url_prefix = "http://www.loujie.com/openapi/boxh5";

	@Test
	public void captchaTest() {
		// 1.图片验证码
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String resultString = OkHttpUtils.POST.run(this.getUrl("/captcha"), paraMap);
		System.out.println(resultString);
	}
	
	

	String getUrl(String url_suffix) {
		return this.url_prefix + url_suffix;
	}

}
