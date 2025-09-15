package web_practice.service.login;

import web_practice.domain.login.FriendModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface FriendService
{
    List<Map<String,Object>> get_friend_info(FriendModel friendModel) throws SQLException;
    String change_intimacy(FriendModel friendModel) throws SQLException;
    String add_friend(FriendModel friendModel) throws SQLException;
}
