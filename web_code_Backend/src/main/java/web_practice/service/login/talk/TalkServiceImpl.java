package web_practice.service.login.talk;

import com.fasterxml.jackson.core.base.ParserBase;
import web_practice.dao.talk.TalkDAO;
import web_practice.dao.talk.TalkDAOImpl;
import web_practice.domain.login.TalkModel;
import web_practice.utils.DruidConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TalkServiceImpl implements TalkService{

    //初始化dao
    TalkDAOImpl talkDAO = new TalkDAOImpl();

    @Override
    public List<Map<String, Object>> get_room_data(TalkModel talkModel ) throws SQLException, ClassNotFoundException {

        //获取值
        String user_id  = talkModel.getUser_id();

        //获取值
        List<Map<String, Object>> result = talkDAO.get_room_data(user_id);



        return result;
    }

    //获取指定房间id的 聊天信息
    @Override
    public List<Map<String, Object>> get_talk_data(TalkModel talkModel) throws SQLException, ClassNotFoundException {
        //获取值
        int room_id = talkModel.getRoom_id();


        //调用DAO
        List<Map<String, Object>> talk_data_list = talkDAO.get_talk_data(room_id);


        return talk_data_list;
    }

    @Override
    public String insert_talk_info(TalkModel talkModel) throws SQLException {
        //获取值
        String user_id = talkModel.getUser_id();
        int room_id = talkModel.getRoom_id();
        String talk_content = talkModel.getTalk_content();

        //调用DAO
        String result_str = talkDAO.insert_talk_info(user_id,talk_content,room_id);


        return result_str;

    }

    @Override
    public int insert_room_info(TalkModel talkModel) throws SQLException {

        String user_id_a = talkModel.getUser_id_a();
        String user_id_b = talkModel.getUser_id_b();

        return talkDAO.insert_room_info(user_id_a,user_id_b);
    }

}
