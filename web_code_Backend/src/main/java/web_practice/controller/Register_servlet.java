package web_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.domain.login.LoginModel;
import web_practice.service.login.LoginServiceImpl;
import web_practice.service.login.RegisterSeviceImpl;
import web_practice.utils.DruidConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Register_servlet")
public class Register_servlet extends HttpServlet
{
    LoginServiceImpl loginService = new LoginServiceImpl();
    RegisterSeviceImpl registerSevice = new RegisterSeviceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //返回数据
        Map<String,Object> response_map = new HashMap<>();
        String response_str = "";
        int login_type = 0;   //999代表不插入
        //初始化JSON
        ObjectMapper objectMapper = new ObjectMapper();
        //初始化对象
        LoginModel loginModel = new LoginModel();



        //获取前端Map
        Map<String,Object> request_map =  objectMapper.readValue(request.getInputStream(), Map.class);
        System.out.println(request_map);
        String user_id = (String)request_map.get("user_id");
        String password = (String)request_map.get("password");

        System.out.println("注册账号:" + user_id);
        System.out.println("注册密码:" + password);
        loginModel.setUser_id(user_id);
        loginModel.setPassword(password);

        try {
            if(loginService.is_user(loginModel))
            {
                response_str = "账户已存在！";
                login_type = 999;
            }

            else
            {
                response_str = "注册成功！";
                login_type = -2;

                registerSevice.insert_users_info(loginModel);
                loginService.insert_longin_info(request,loginModel,login_type);

            }

            response_map.put("response_str",response_str);


            //返回Json对象
            String response_json = objectMapper.writeValueAsString(response_map);

            response.getWriter().print(response_json);





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
