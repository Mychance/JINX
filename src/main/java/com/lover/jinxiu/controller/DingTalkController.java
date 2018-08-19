package com.lover.jinxiu.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/dingding")
public class DingTalkController {
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=cea3ac486f9fbbf9799ed526887824bba96f6a001420b7cb39e11f5b53a53be7";

    @RequestMapping("jinxiu")
    @ResponseBody
    public void agree(HttpServletRequest request,String msg) throws ClientProtocolException, IOException{
        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        String ip = getClientIP(request);
        String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \""+getAddressByIP(ip)+ip+":"+msg+"\"}}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }
    
    /*** 
     * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP, 
     * @param request 
     * @return 
     */  
    public static String getClientIP(HttpServletRequest request) {  
        String fromSource = "X-Real-IP";  
        String ip = request.getHeader("X-Real-IP");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Forwarded-For");  
            fromSource = "X-Forwarded-For";  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            fromSource = "Proxy-Client-IP";  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            fromSource = "WL-Proxy-Client-IP";  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            fromSource = "request.getRemoteAddr";  
        }  
//        appLog.info("App Client IP: "+ip+", fromSource: "+fromSource);  
        return ip;  
    }  
    
    public static String getAddressByIP(String ip) {
    	String address = "";
    	try {
			String str = getResult("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+ip, "utf-8");
			System.out.println(str);
			
			JSONObject result = JSONObject.fromObject(str);
			
			JSONObject data = (JSONObject) result.get("data");
			
			String code = String.valueOf(result.get("code"));
			
			if(code.equals("0")) {
				address = data.get("country")+"--" +data.get("region") +data.get("area")+"--" +data.get("city")+"--" +data.get("isp");
			}else {
				address = "IP地址有误";
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			address = "获取IP地址异常："+e.getMessage();
		}
    	return address;
    	
    }
    
    public static String getResult(String urlStr, String encoding) throws IOException {
    	URL url = null;
    	HttpURLConnection conn = null;
    	url = new URL(urlStr);
    	conn = (HttpURLConnection) url.openConnection();
    	conn.setConnectTimeout(2000);
//    	conn.setReadTimeout(2000);
//    	conn.setDoOutput(true);
    	conn.setDoInput(true);
    	conn.setRequestMethod("GET");
    	conn.setUseCaches(false);
    	conn.connect();
//    	DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//    	dos.writeBytes(content);
//    	dos.flush();
//    	dos.close();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
    	StringBuffer buffer = new StringBuffer();
    	String line = "";
    	while((line=reader.readLine()) != null) {
    		buffer.append(line);
    	}
    	reader.close();
    	
    	return buffer.toString();
    }
	
    public static void main(String[] args) {
    	System.out.println(getAddressByIP("119.29.1.127"));
	}
}
