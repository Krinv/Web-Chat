package web_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.domain.login.SearchModel;
import web_practice.service.login.SearchServiceImpl;
import web_practice.utils.Get_user_id_cookie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/Search_servlet")
public class Search_servlet extends HttpServlet
{
    SearchServiceImpl searchService = new SearchServiceImpl();
    SearchModel searchModel = new SearchModel();
    Get_user_id_cookie getUserIdCookie = new Get_user_id_cookie();

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取值
        String search_data =  request.getParameter("search_data");
        String user_id = getUserIdCookie.getUser_id(request);
        //设置值
        searchModel.setSearch_data(search_data);
        searchModel.setUser_id(user_id);
        try {
            List<Map<String ,Object>> result_list  =  searchService.get_user_info(searchModel);

            System.out.println("结果：" + result_list);
            //返回后端
            response.getWriter().print(objectMapper.writeValueAsString(result_list));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
