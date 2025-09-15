package web_practice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web_practice.domain.login.FriendModel;
import web_practice.service.login.FriendServiceImpl;
import web_practice.utils.Get_user_id_cookie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/Friend_servlet")
public class Friend_servlet extends HttpServlet
{
    FriendModel friendModel = new FriendModel();
    Get_user_id_cookie getUserIdCookie = new Get_user_id_cookie();
    FriendServiceImpl friendService = new FriendServiceImpl();

    ObjectMapper objectMapper = new ObjectMapper();
    //获取好友列表


    //前端有请求type  0 1 标识降低或提升亲密度  -1为获取数据
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        //获取前端的值
        int request_type = Integer.parseInt((String)request.getParameter("request_type")) ;

        //获取用户id
        String user_id = getUserIdCookie.getUser_id(request);

        //插入值
        friendModel.setUser_id(user_id);

        try {
        if(request_type == -1)
        {
            List<Map<String,Object>> result_list = null;


                result_list = friendService.get_friend_info(friendModel);
                String result_json =  objectMapper.writeValueAsString(result_list);


                //写入前端
                response.getWriter().print(result_json);
            }


        else
        {

            String friend_user_id = (String) request.getParameter("friend_user_id");
            friendModel.setChange_type(request_type);
            friendModel.setFriend_user_id(friend_user_id);
            friendModel.setMy_user_id(user_id);
            String result_str = null;

                result_str = friendService.change_intimacy(friendModel);
            System.out.println("不傻乎乎：" + result_str);
            //写入前端
            response.getWriter().print(result_str);
        }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }

    }


    //添加好友
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //获取值
        Map<String,Object> request_map =  objectMapper.readValue(request.getInputStream(),Map.class);

        String friend_user_id =(String) request_map.get("user_id");
        String my_user_id = getUserIdCookie.getUser_id(request);


        //插入值
        friendModel.setFriend_user_id(friend_user_id);
        friendModel.setMy_user_id(my_user_id);

        //执行
        try {

            String result_str = friendService.add_friend(friendModel);

            System.out.println( result_str);
            //写入前端

            response.getWriter().print(result_str);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
}
