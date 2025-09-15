package web_practice.service.login;

import web_practice.dao.FriendDAOImpl;
import web_practice.domain.login.FriendModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class FriendServiceImpl implements FriendService{

    FriendDAOImpl friendDAO = new FriendDAOImpl();
    @Override
    public List<Map<String, Object>> get_friend_info(FriendModel friendModel) throws SQLException {

        String user_id = friendModel.getUser_id();
        return friendDAO.get_friend_info(user_id);

    }

    @Override
    public String change_intimacy(FriendModel friendModel) throws SQLException {

        String my_user_id = friendModel.getMy_user_id();
        String friend_user_id = friendModel.getFriend_user_id();
        int change_type = friendModel.getChange_type();

        return friendDAO.change_intimacy(my_user_id,friend_user_id,change_type);
    }

    //添加好友
    @Override
    public String add_friend(FriendModel friendModel) throws SQLException {
        String my_user_id  = friendModel.getMy_user_id();
        String friend_user_id = friendModel.getFriend_user_id();

        return friendDAO.insert_friend_info(my_user_id,friend_user_id);

    }
}
