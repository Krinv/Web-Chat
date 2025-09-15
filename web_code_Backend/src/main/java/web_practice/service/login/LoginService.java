package web_practice.service.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.domain.login.LoginModel;

import java.sql.SQLException;

public interface LoginService
{
    //是否存在用户名
    boolean is_user(LoginModel loginModel) throws SQLException;

    //判断密码是否符合
    boolean is_password(LoginModel loginModel) throws SQLException;

    void create_user_id_cookie(HttpServletRequest request, HttpServletResponse response, LoginModel loginModel);

    String insert_longin_info(HttpServletRequest request,LoginModel loginModel,int login_type) throws SQLException;
}
