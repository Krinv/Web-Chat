package web_practice.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class Get_user_id_cookie
{

    private String user_id ;

    //获取用户user_id cookie
    public String getUser_id(HttpServletRequest request) {

        Cookie [] cookies = request.getCookies();

        //遍历
        if(cookies != null)
        {
            for(Cookie cookie : cookies)
            {
                if("user_id".equals(cookie.getName()))
                {
//                    System.out.println("找到用户id cookie:" + cookie.getValue() );
                    return cookie.getValue();
                }
            }
        }
       // System.out.println("未找到用户id cookie");
        return null;



    }

}
