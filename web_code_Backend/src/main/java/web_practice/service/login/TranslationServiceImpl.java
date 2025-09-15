package web_practice.service.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import web_practice.domain.login.TranslationModel;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class TranslationServiceImpl implements TranslationService {


    private static final String APP_ID = "20241229002240718"; // 替换为你的 App ID
    private static final String SECURITY_KEY = "UJ3uhuX_gzQV8LMwwVTf"; // 替换为你的 Security Key
    private static final String API_URL = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String get_new_text(TranslationModel translationModel) {

        try {
            //要翻译的文本
            String q = translationModel.getOld_text();
            //目标语言是中文
            String to = "zh";
            //原始语言 自动
            String from = "auto";

            //构造签名
            String salt = String.valueOf(new Random().nextInt(1000000));
            String sign = APP_ID + q + salt + SECURITY_KEY;

            //MD5加密
            sign = md5(sign);


            //编码URL参数  百度翻译要求为UTF-8编码
            String encodeQ = URLEncoder.encode(q, "UTF-8");
            String encodeFrom = URLEncoder.encode(from, "UTF-8");
            String encodeTo = URLEncoder.encode(to, "UTF-8");
            String encodeAPPID = URLEncoder.encode(APP_ID, "UTF-8");
            String encodeSalt = URLEncoder.encode(salt, "UTF-8");
            String encodeSign = URLEncoder.encode(sign, "UTF-8");


            //构造URL
            String request_url = API_URL + "?q=" + encodeQ +
                    "&from=" + encodeFrom + "&to="+encodeTo+
                    "&appid="+encodeAPPID + "&salt="+encodeSalt+
                    "&sign=" + encodeSign;

            //发送请求并返回结果
            String response = send_request(request_url);
            System.out.println("翻译结果为：" + response);
            return response;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;

        }


    }

    private String send_request(String requestUrl) throws URISyntaxException, IOException {
        HttpURLConnection connection = (HttpURLConnection) new java.net.URL(requestUrl).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    // MD5 加密函数
    private static String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
