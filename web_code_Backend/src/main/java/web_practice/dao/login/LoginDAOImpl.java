package web_practice.dao.login;

import com.alibaba.druid.pool.DruidDataSource;
import jakarta.servlet.http.HttpServletRequest;
import web_practice.domain.login.LoginModel;
import web_practice.utils.DruidConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginDAOImpl implements LoginDAO
{

    void  main()
    {
        LoginModel loginModel = new LoginModel();
        String user_account = loginModel.getUser_id();
        String password = loginModel.getPassword();
    }
    @Override
    public Map<String,Object> get_user_info(String user_id) throws  SQLException {

        // 获取数据库连接
        Connection connection = DruidConfig.getConnection();

        Map<String, Object> result = new HashMap<>();

// SQL 查询语句
        String sql_select = "SELECT * FROM users_info WHERE user_id = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(sql_select);
            statement.setString(1, user_id);
            rs = statement.executeQuery();

            if (rs.next()) {
                result.put("user_id", rs.getString("user_id"));
                result.put("password", rs.getString("password"));
            }
        } catch (SQLException e) {

            throw new SQLException("查询用户信息失败", e);
        } finally {
            // 确保资源在使用完后关闭
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("关闭 ResultSet 异常: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("关闭 PreparedStatement 异常: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("关闭 Connection 异常: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    @Override
    public String insert_login_info(HttpServletRequest request, String user_id, String password, int login_type) throws SQLException {
        // 获取数据库连接
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DruidConfig.getConnection();
            String user_ip = getClientIp(request);

            // SQL 插入语句
            String sql_insert = "INSERT INTO login_info (user_ip, user_id, password, login_type) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql_insert);
            statement.setString(1, user_ip);
            statement.setString(2, user_id);
            statement.setString(3, password);
            statement.setInt(4, login_type);
            statement.executeUpdate();
            System.out.println("成功！");
        } catch (SQLException e) {
            // 捕获 SQL 异常并打印
            System.err.println("数据库操作异常: " + e.getMessage());
            e.printStackTrace();  // 打印异常栈信息
        } catch (Exception e) {
            // 捕获其他类型的异常并打印
            System.err.println("未知异常: " + e.getMessage());
            e.printStackTrace();  // 打印异常栈信息
        } finally {
            // 确保资源在使用完后关闭
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("关闭 PreparedStatement 异常: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("关闭 Connection 异常: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return "成功插入登录信息";  // 如果没有异常，返回成功信息
    }

    private String getClientIp(HttpServletRequest request) {
        // 获取客户端真实 IP 地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            String[] ip_array = ip.split(",");
            return ip_array[0]; // 返回第一个 IP 地址
        }
        return request.getRemoteAddr(); // 如果没有 X-Forwarded-For，返回 getRemoteAddr 的值
    }
}
