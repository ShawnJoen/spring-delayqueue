package com.queue.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	private static final int CONNECT_TIMEOUT = 30000;
	private static final int CONNECT_REQUEST_TIMEOUT = 30000;
	private static final int SOCKET_TIMEOUT = 5000;
	private static final String CHARSET_UTF8 = "UTF-8";

	public static String get(String url) {
		logger.info("Http Get请求地址:{}", url);
		
		HttpGet httpGet = new HttpGet(url.trim());
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT)
				.build();
		httpGet.setConfig(requestConfig);
		
		String returnValue = null;
		try {
			HttpResponse httpResponse = HttpClientBuilder
					.create()
					.build()
					.execute(httpGet);
			returnValue = EntityUtils.toString(httpResponse.getEntity(), CHARSET_UTF8);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			
			httpGet.releaseConnection();
		}
		
		return returnValue;
	}
	
	/**
	 * 传递Key/Value参数
	 * */
	public static String post(String url, List<NameValuePair> params) {
		logger.info("Http Post请求地址:{}, 请求值:{}", url, params);
		
		HttpPost httpPost = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		String returnValue = null;
		try {
			httpPost = new HttpPost(url.trim());
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(CONNECT_TIMEOUT)
					.setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
					.setSocketTimeout(SOCKET_TIMEOUT)
					.build();
			httpPost.setConfig(requestConfig);
			client = HttpClients.createDefault();  
            httpPost.setEntity(new UrlEncodedFormEntity(params, CHARSET_UTF8));  
            response = client.execute(httpPost);  
            returnValue = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			
			httpPost.releaseConnection();
			
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        }
		
		return returnValue;
	}
	
	/**
	 * 传递Json/Xml参数
	 */
	public static String postJsonOrXml(String url, Map<String, String> params) {
		
		HttpPost httpPost = null;
		String returnValue = null;
		try {
			String paramsEntity = buildQuery(params);
			
			logger.info("Http Post请求地址:{}, 请求值:{}", url, paramsEntity);
			
			httpPost = new HttpPost(url.trim());
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(CONNECT_TIMEOUT)
					.setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
					.setSocketTimeout(SOCKET_TIMEOUT)
					.build();
			httpPost.setConfig(requestConfig);
			StringEntity se = new StringEntity(paramsEntity, CHARSET_UTF8);
			httpPost.setEntity(se);
			//httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
			HttpResponse response = HttpClientBuilder
					.create()
					.build()
					.execute(httpPost);
			returnValue = EntityUtils.toString(response.getEntity(), CHARSET_UTF8);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {  
			
			httpPost.releaseConnection(); 
        }
		
		return returnValue;
	}

	/**
	 * 重新组合Http请求参数
	 * 
	 * @param params
	 *            参数map
	 * @param charset
	 *            字符集
	 * @return
	 * @throws IOException
	 */
	private static String buildQuery(Map<String, String> params) throws IOException {
		if (null == params || params.isEmpty()) {
			return null;
		}
		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;
		for (Entry<String, String> entry : entries) {
			String key = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if (areNotEmpty(key, value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}
				query.append(key).append("=").append(URLEncoder.encode(value, CHARSET_UTF8));
			}
		}
		return query.toString();
	}
	
    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     * 
     * @param value 待检查的字符串
     * @return true/false
     */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
    /**
     * 检查指定的字符串列表是否不为空。
     */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}
}