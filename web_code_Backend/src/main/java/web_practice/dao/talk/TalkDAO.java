package web_practice.dao.talk;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TalkDAO
{

    //获取房间信息
    List<Map<String,Object>> get_room_data(String user_id) throws SQLException, ClassNotFoundException;

    //获取聊天信息
    List<Map<String,Object>> get_talk_data(int room_id) throws SQLException, ClassNotFoundException;

    //插入房间信息
    int insert_room_info(String user_id_a,String user_id_b) throws SQLException;

    //插入聊天信息
    String insert_talk_info(String user_id,String talk_content,int room_id) throws SQLException;
}
