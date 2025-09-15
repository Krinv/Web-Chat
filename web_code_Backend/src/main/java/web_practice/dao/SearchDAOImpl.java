package web_practice.dao;

import web_practice.utils.DruidConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SearchDAOImpl implements SearchDAO{
    @Override
    public List<Map<String, Object>> get_user_info(String search_data,String user_id) throws SQLException {

        //连接
        Connection connection = DruidConfig.getConnection();
        List<Map<String, Object>> result_list = new ArrayList<>();
        search_data = "%"+ search_data + "%";
        //sql语句
        String sql_select = "SELECT user_name, user_id,user_img_name\n" +
                "FROM users_info u\n" +
                "WHERE (user_id LIKE ? OR user_name LIKE ?)\n" +
                "AND NOT EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM friend_info f\n" +
                "    WHERE (f.user_id_a = u.user_id AND f.user_id_b = ?)\n" +
                "       OR (f.user_id_b = u.user_id AND f.user_id_a = ? )\n" +
                ");";
        try(PreparedStatement statement = connection.prepareStatement(sql_select))
        {
            statement.setString(1,search_data);
            statement.setString(2,search_data);
            statement.setString(3,user_id);
            statement.setString(4,user_id);

            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                Map<String,Object> row = new HashMap<>();

                row.put("user_name",rs.getString("user_name"));
                row.put("user_id",rs.getString("user_id"));
                row.put("user_img_name",rs.getString("user_img_name"));


                result_list.add(row);

            }
            return result_list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
