package web_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.domain.login.TranslationModel;
import web_practice.service.login.TranslationServiceImpl;

import java.io.IOException;
import java.util.Map;

@WebServlet("/Translation_servlet")
public class Translation_servlet extends HttpServlet
{
    ObjectMapper objectMapper = new ObjectMapper();

    TranslationModel translationModel = new TranslationModel();
    TranslationServiceImpl translationService = new TranslationServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //获取前端
        Map<String,Object> request_map = objectMapper.readValue(request.getInputStream(),Map.class);

        //获取值
        String old_text = (String)request_map.get("old_text");

        //设置值
        translationModel.setOld_text(old_text);

        //获取json
        String result_json = translationService.get_new_text(translationModel);

        //返回给前端
        response.getWriter().print(result_json);
    }
}
