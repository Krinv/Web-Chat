package web_practice.service.login;

import web_practice.domain.login.LoginModel;

import java.sql.SQLException;

public interface RegisterSevice {
    String insert_users_info(LoginModel loginModel) throws SQLException;
}
