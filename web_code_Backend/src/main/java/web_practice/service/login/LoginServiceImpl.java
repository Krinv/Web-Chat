package web_practice.service.login;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.dao.login.LoginDAOImpl;
import web_practice.domain.login.LoginModel;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class LoginServiceImpl implements   LoginService
{

    //创建DAO实现类
    LoginDAOImpl loginDAO = new LoginDAOImpl();

    @Override
    public boolean is_user(LoginModel loginModel) throws SQLException {
        //获取值
        String user_id = loginModel.getUser_id() ;
        Map<String, Object> mysql_map = loginDAO.get_user_info(user_id);

        System.out.println("用户信息："+ mysql_map);
        if(!mysql_map.isEmpty())return true;
        return false;

    }

    @Override
    public boolean is_password(LoginModel loginModel) throws SQLException {
        //获取值
        String user_id = loginModel.getUser_id() ;
        String password = loginModel.getPassword() ;
        Map<String, Object> mysql_map = loginDAO.get_user_info(user_id);

        System.out.println("用户密码:"+(String)mysql_map.get("password"));
        if(password.equals((String)mysql_map.get("password")))
            return true;

        return false;
    }

    @Override
    public void create_user_id_cookie(HttpServletRequest request, HttpServletResponse response,LoginModel loginModel) {

        System.out.println("设置用户id cookie ");
        //获取user_id
        String user_id = loginModel.getUser_id();
        Cookie user_id_cookie = new Cookie("user_id",user_id);
        user_id_cookie.setPath("/");
        user_id_cookie.setMaxAge(60*60*24); //一天
        user_id_cookie.setHttpOnly(true);  //js不能访问

        response.addCookie(user_id_cookie);

        System.out.println("设置cookie成功！");
    }

    @Override
    public String insert_longin_info(HttpServletRequest request, LoginModel loginModel, int login_type) throws SQLException {
        //获取值
        String user_id = loginModel.getUser_id() ;
        String password = loginModel.getPassword() ;

        return  loginDAO.insert_login_info(request,user_id,password,login_type);

    }
}
