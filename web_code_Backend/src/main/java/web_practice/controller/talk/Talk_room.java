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

@WebServlet("/Talk_room")
public class Talk_room  extends HttpServlet
{
    //存储信息
    TalkModel talkModel = new TalkModel();
    //获取cookie
    Get_user_id_cookie getUserIdCookie = new Get_user_id_cookie();
    //service
    TalkServiceImpl talkService = new TalkServiceImpl();
    //json
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //处理日期格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);



        //获取user_id
        String user_id = getUserIdCookie.getUser_id(request);

        //设置user_id
        talkModel.setUser_id(user_id);

        try {

            //获取数组
            List<Map<String,Object>> room_data_list = talkService.get_room_data(talkModel);

            //转化为json
            String room_data_json =  objectMapper.writeValueAsString(room_data_list);

            //返回给后端
            response.getWriter().print(room_data_json);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    //创建聊天房间
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取前端数据
        Map<String,Object> request_map = objectMapper.readValue(request.getInputStream(),Map.class);
        String friend_user_id = (String)request_map.get("friend_user_id");
        String my_user_id = getUserIdCookie.getUser_id(request);

        System.out.println("fffff::::::::"+friend_user_id);
        //插入值
        talkModel.setUser_id_a(my_user_id);
        talkModel.setUser_id_b(friend_user_id);


        try {
            int room_id = talkService.insert_room_info(talkModel);
            //返回前端
            response.getWriter().print(room_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
