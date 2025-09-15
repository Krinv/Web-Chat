package web_practice.dao.login;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Map;

public interface LoginDAO
{
     //获取用户id 密码
     Map<String,Object> get_user_info(String user_id) throws SQLException;

    //插入数据到登录信息表 返回错误或正确
    String insert_login_info(HttpServletRequest request,String user_id,String password,int login_type) throws SQLException;

}
