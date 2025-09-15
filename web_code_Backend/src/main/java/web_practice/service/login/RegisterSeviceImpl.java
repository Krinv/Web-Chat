package web_practice.service.login;

import web_practice.dao.login.RegisterDAO;
import web_practice.dao.login.RegisterDAOImpl;
import web_practice.domain.login.LoginModel;

import java.sql.SQLException;

public class RegisterSeviceImpl implements RegisterSevice{
    @Override
    public String insert_users_info(LoginModel loginModel) throws SQLException {

        RegisterDAOImpl registerDAO = new RegisterDAOImpl();

        registerDAO.insert_users_info(loginModel);
        return "注册成功！";
    }
}
