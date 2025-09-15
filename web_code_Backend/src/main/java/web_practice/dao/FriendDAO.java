package web_practice.dao;

import web_practice.domain.login.FriendModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface FriendDAO
{
    //获取用户好友列表
    List<Map<String, Object>> get_friend_info(String user_id) throws SQLException;

    //修改用户的亲密度   change_type 0 降低  1 提升
    String change_intimacy(String my_user_id,String friend_user_id,int change_type) throws SQLException;

    //增加好友
    String insert_friend_info(String my_user_id,String friend_user_id) throws SQLException;
}
