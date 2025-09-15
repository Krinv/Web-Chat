package web_practice.dao.login;

import web_practice.domain.login.LoginModel;

import java.sql.SQLException;

public interface RegisterDAO {
    String insert_users_info(LoginModel loginModel) throws SQLException;
}
