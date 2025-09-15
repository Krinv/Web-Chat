package web_practice.service.login.talk;

import web_practice.domain.login.TalkModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TalkService
{
    //获取房间信息
    List<Map<String,Object>> get_room_data(TalkModel talkModel ) throws SQLException, ClassNotFoundException;

    //获取指定房间Id的聊天信息
    List<Map<String,Object>> get_talk_data(TalkModel talkModel ) throws SQLException, ClassNotFoundException;
    //插入聊天信息
    String insert_talk_info(TalkModel talkModel) throws SQLException;

    int insert_room_info(TalkModel talkModel) throws SQLException;
}
