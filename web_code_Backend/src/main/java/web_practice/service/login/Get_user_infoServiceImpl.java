package web_practice.service.login;

import web_practice.dao.Get_user_infoImpl;
import web_practice.domain.login.Get_user_infoModel;

import java.sql.SQLException;
import java.util.Map;

public class Get_user_infoServiceImpl implements  Get_user_infoService{

    //初始化dao
    Get_user_infoImpl getUserInfo = new Get_user_infoImpl();
    @Override
    public Map<String, Object> get_user_info(Get_user_infoModel getUserInfoModel) throws SQLException, ClassNotFoundException {
        //获取值
        String user_id = getUserInfoModel.getUser_id();
        Map<String, Object> result_map = getUserInfo.get_user_info(user_id);
        return result_map;




    }
}
