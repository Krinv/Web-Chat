package web_practice.dao;

import web_practice.domain.login.Get_user_infoModel;

import java.sql.SQLException;
import java.util.Map;

public interface Get_user_infoDAO
{
    Map<String,Object> get_user_info(String user_id) throws SQLException, ClassNotFoundException;
}
