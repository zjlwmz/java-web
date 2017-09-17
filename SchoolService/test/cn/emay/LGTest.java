/**
 * 
 */
package cn.emay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.nutz.json.Json;

import cn.emay.common.client.LocalHttpClient;
import cn.emay.common.response.ResponeResultModel;

/**
 * @author zjlWm
 * @date
 *
 */
public class LGTest {

	@Test
	public void test(){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri("http://165.244.60.222:7840/provideWebCall.do?serviceID=OnSiteClaimStatus&serverID=gsfs.kic&P_CORPORATION_CODE=LGECH&P_SALES_MODEL_CODE=&P_SERIAL_NO=&P_COUNTRY_CODE=CHN&P_GCM_COUNTRY_CODE=CN&P_LANGUAGE_CODE=zh&P_SERVICE_REQUEST_NO=RNC150624059003&P_PHONE_NO=153368289")
				.build();
		ResponeResultModel result=LocalHttpClient.executeJsonResult(httpUriRequest, ResponeResultModel.class);
		System.out.println(Json.toJson(result));
		
	}
	
	
	/** 
     * post方式提交表单（模拟用户登录请求） 
     */  
	@Test
    public void postForm() {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://165.244.60.222:7840/provideWebCall.do");  
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("serviceID", "OnSiteClaimStatus"));  
        formparams.add(new BasicNameValuePair("serverID", "gsfs.kic"));  
        formparams.add(new BasicNameValuePair("P_CORPORATION_CODE", "LGECH")); 
        formparams.add(new BasicNameValuePair("P_SALES_MODEL_CODE", "")); 
        formparams.add(new BasicNameValuePair("P_SERIAL_NO", ""));
        
        
        formparams.add(new BasicNameValuePair("P_COUNTRY_CODE", "CHN")); 
        
        formparams.add(new BasicNameValuePair("P_CORPORATION_CODE", "CN")); 
        
        
        formparams.add(new BasicNameValuePair("P_LANGUAGE_CODE", "zh")); 
        formparams.add(new BasicNameValuePair("P_SERVICE_REQUEST_NO", "RNC150624059003")); 
        formparams.add(new BasicNameValuePair("P_PHONE_NO", "153368289")); 
        
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out.println("--------------------------------------");  
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                    System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
}
