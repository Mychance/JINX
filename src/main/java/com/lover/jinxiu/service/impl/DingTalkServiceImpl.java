package com.lover.jinxiu.service.impl;

import com.lover.jinxiu.service.DingTalkService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DingTalkServiceImpl implements DingTalkService {
    //master test push
    @Override
    public String getAddressByIP(String ip) throws IOException {
        String address = "";
        String str = getResult("http://ip.taobao.com/service/getIpInfo.php?ip="+ip, "utf-8");
        System.out.println(str);

        JSONObject result = JSONObject.fromObject(str);

        JSONObject data = (JSONObject) result.get("data");

        String code = String.valueOf(result.get("code"));

        if(code.equals("0")) {
            address = data.get("country")+"--" +data.get("region") +data.get("area")+"--" +data.get("city")+"--" +data.get("isp");
        }else {
            address = "IP地址有误";
        }
        return address;
    }

    public String getResult(String urlStr, String encoding) throws IOException {
        URL url = null;
        HttpURLConnection conn = null;
        url = new URL(urlStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(2000);

        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while((line=reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();

        return buffer.toString();
    }
}
