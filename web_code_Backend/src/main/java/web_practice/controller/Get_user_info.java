package web_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.domain.login.Get_user_infoModel;
import web_practice.service.login.Get_user_infoServiceImpl;
import web_practice.utils.Get_user_id_cookie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/Get_user_info")
public class Get_user_info extends HttpServlet
{

    Get_user_infoModel getUserInfoModel = new Get_user_infoModel();
    Get_user_infoServiceImpl getUserInfoService = new Get_user_infoServiceImpl();
    Get_user_id_cookie getUserIdCookie = new Get_user_id_cookie();
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理日期格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //获取user_id
        String user_id = getUserIdCookie.getUser_id(request);

        //插入值
        getUserInfoModel.setUser_id(user_id);

        try {
            Map<String,Object> user_data =  getUserInfoService.get_user_info(getUserInfoModel);

            //返回前端

            //json
            String user_data_json =  objectMapper.writeValueAsString(user_data);

            response.getWriter().print(user_data_json);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
