package web_practice.dao.login;

import web_practice.domain.login.LoginModel;
import web_practice.utils.DruidConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class RegisterDAOImpl implements RegisterDAO{
    @Override
    public String insert_users_info(LoginModel loginModel) throws SQLException {
        //连接
        Connection connection = DruidConfig.getConnection();

        //提取值
        String user_id = loginModel.getUser_id();
        String password = loginModel.getPassword();


        //随机获取一个用户头像
        Random random = new Random();
        int ran_number = random.nextInt(5);
        String user_img_name = "default_" +ran_number + ".jpg";
        //sql语句
        String sql_insert ="INSERT INTO  users_info (user_id,user_name,password,user_img_name) VALUES (?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql_insert))
        {
                statement.setString(1,user_id);
                //默认的用户名为账号
                statement.setString(2,user_id);
                statement.setString(3,password);
                statement.setString(4,user_img_name);
                statement.executeUpdate();

        } catch (SQLException e) {
            // 捕获 SQL 异常并打印
            System.err.println("数据库操作异常: " + e.getMessage());
            e.printStackTrace();  // 打印异常栈信息
        } catch (Exception e) {
            // 捕获其他类型的异常并打印
            System.err.println("未知异常: " + e.getMessage());
            e.printStackTrace();  // 打印异常栈信息
        }

        return "成功插入登录信息";  // 如果没有异常，返回成功信息
    }
}
