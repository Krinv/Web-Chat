package web_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.dao.login.LoginDAOImpl;

import web_practice.domain.login.LoginModel;
import web_practice.service.login.LoginServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet("/Login_servlet")
public class Login_servlet  extends HttpServlet
{

      LoginServiceImpl loginService = new LoginServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        System.out.println("进入登录验证！");
        //json  对象
        ObjectMapper objectMapper = new ObjectMapper();

        //获取前端map
        Map<String, Object> request_map = objectMapper.readValue(request.getInputStream(),Map.class) ;
        System.out.println("11111111111");
        //获取值
        String user_id = (String)request_map.get("user_id");
        String password = (String) request_map.get("password");


        //初始化对象
        LoginModel loginModel = new LoginModel();

        //存入值
        loginModel.setUser_id(user_id);
        loginModel.setPassword(password);

        boolean is_user = false;
        boolean is_password = false;
        //获取判断用户是否存在的布尔值
        try {
             is_user =  loginService.is_user(loginModel) ;
             is_password = loginService.is_password(loginModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String response_str = "";
        int login_type = 0;
        //如果存在则下一步
        if(is_user)
        {
            if(is_password)
            {
                response_str = "登录成功！";

                //设置cookie
                loginService.create_user_id_cookie(request,response,loginModel);

                //设状态为1
                login_type = 1;

            }
            else
            {
                response_str = "密码错误！";
            }
        }
        else
        {
            System.out.println("账号不存在");
            response_str = "账号不存在！";
            login_type=999;
        }
        System.out.println(response_str);
        //向信息表中插入数据
        try {
            if(login_type != 999)
            {
                System.out.println(loginService.insert_longin_info(request,loginModel,login_type));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Map<String,Object> response_map = new HashMap<>();
        response_map.put("response_str",response_str);

        String response_json = objectMapper.writeValueAsString(response_map);


        response.getWriter().print(response_json);

    }
}
