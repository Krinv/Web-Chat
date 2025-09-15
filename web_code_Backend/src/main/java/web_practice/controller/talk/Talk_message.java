package web_practice.controller.talk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.domain.login.TalkModel;
import web_practice.service.login.talk.TalkServiceImpl;
import web_practice.utils.Get_user_id_cookie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/Talk_message")
public class Talk_message extends HttpServlet {

    //存储信息
    TalkModel talkModel = new TalkModel();
    //获取cookie
    Get_user_id_cookie getUserIdCookie = new Get_user_id_cookie();
    //service
    TalkServiceImpl talkService = new TalkServiceImpl();
    //json
    ObjectMapper objectMapper = new ObjectMapper();


    //发送消息
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        System.out.println();
        //获取前端的值
        Map<String,Object> request_map = objectMapper.readValue(request.getInputStream(),Map.class);
        String talk_content  =(String ) request_map.get("talk_content");
        int room_id =  Integer.parseInt((String)request_map.get("room_id"));
        //获取用户id
        String user_id = getUserIdCookie.getUser_id(request);

        //装入容器
        talkModel.setUser_id(user_id);
        talkModel.setRoom_id(room_id);
        talkModel.setTalk_content(talk_content);

        try {
            String result_str = talkService.insert_talk_info(talkModel);

            System.out.println(result_str);

            //返回后端
            response.getWriter().print(result_str);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    //获取对应房间的信息

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //处理日期格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //获取前端的值
        int room_id =Integer.parseInt( request.getParameter("room_id"));

        //获取用户id
        String user_id = getUserIdCookie.getUser_id(request);

        //装入容器
        talkModel.setRoom_id(room_id);
        talkModel.setUser_id(user_id);

        try {
            List<Map<String,Object>> talk_data_list = talkService.get_talk_data(talkModel);


            //json
            String talk_data_json = objectMapper.writeValueAsString(talk_data_list);

            //返回前端
            response.getWriter().print(talk_data_json);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
