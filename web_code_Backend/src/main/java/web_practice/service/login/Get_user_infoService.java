package web_practice.service.login;

import web_practice.domain.login.Get_user_infoModel;

import java.sql.SQLException;
import java.util.Map;

public interface Get_user_infoService
{
    Map<String,Object> get_user_info(Get_user_infoModel getUserInfoModel) throws SQLException, ClassNotFoundException;
}
