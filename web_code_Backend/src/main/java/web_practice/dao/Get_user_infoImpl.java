package web_practice.dao;

import web_practice.domain.login.Get_user_infoModel;
import web_practice.utils.DruidConfig;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Get_user_infoImpl implements Get_user_infoDAO{
    @Override
    public Map<String, Object> get_user_info(String user_id) throws SQLException, ClassNotFoundException {
        //数据map
        Map<String,Object> result_map = new HashMap<>();
        //连接数据库
        Class.forName("com.mysql.cj.jdbc.Driver");
        //数据库URL
        String sql_url  = "jdbc:mysql://localhost:3306/web_practice";
        //数据库用户名
        String sql_user = "web_manager";
        //数据库密码
        String sql_password = "npc..886";

        //连接数据库
        Connection connection = DriverManager.getConnection(sql_url,sql_user,sql_password);

        //sql语句
        String sql_select = "SELECT * FROM users_info WHERE user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql_select))
        {
            statement.setString(1,user_id);

            ResultSet rs = statement.executeQuery();

            //遍历好看点
            while(rs.next())
            {
                result_map.put("user_id",user_id);
                result_map.put("user_img_name",rs.getString("user_img_name"));
                result_map.put("user_only_type",rs.getString("user_online_type"));
                result_map.put("user_vip_rank",rs.getInt("user_vip_rank"));
                result_map.put("user_money",rs.getInt("user_money"));

            }
            return result_map;
        } catch (SQLException e) {
            // 捕获 SQL 异常并打印
            System.err.println("数据库操作异常: " + e.getMessage());
            e.printStackTrace();  // 打印异常栈信息
            return result_map;
        }

    }
}
