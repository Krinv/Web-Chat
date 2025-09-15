package web_practice.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import web_practice.utils.DruidConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendDAOImpl implements FriendDAO{
    private static final Log log = LogFactory.getLog(FriendDAOImpl.class);

    @Override
    public List<Map<String, Object>> get_friend_info(String user_id) throws SQLException {

        List<Map<String, Object>> result_list = new ArrayList<>();

        //连接
        Connection connection = DruidConfig.getConnection();

        //Sql语句
        String sql_select = "SELECT u.user_id, u.user_name, u.user_img_name, u.user_vip_rank, u.user_gander,\n" +
                "       MAX(f.intimacy) AS intimacy, MAX(f.establish_date) AS establish_date\n" +
                "FROM friend_info f\n" +
                "INNER JOIN users_info u ON (f.user_id_a = u.user_id OR f.user_id_b = u.user_id)\n" +
                "WHERE (f.user_id_a = ? OR f.user_id_b = ?)\n" +
                "  AND u.user_id != ?\n" +
                "GROUP BY u.user_id, u.user_name, u.user_img_name, u.user_vip_rank, u.user_gander";
        try(PreparedStatement statement = connection.prepareStatement(sql_select))
        {
            statement.setString(1,user_id);
            statement.setString(2,user_id);
            statement.setString(3,user_id);

            ResultSet rs = statement.executeQuery();

            //遍历获取数据
            while(rs.next())
            {
                //map
                Map<String,Object> row = new HashMap<>();

                row.put("user_id",rs.getString("user_id"));
                row.put("intimacy",rs.getString("intimacy"));
                row.put("establish_date",rs.getString("establish_date"));
                row.put("user_img_name",rs.getString("user_img_name"));
                row.put("user_name",rs.getString("user_name"));
                row.put("user_vip_rank",rs.getString("user_vip_rank"));
                row.put("user_gander",rs.getString("user_gander"));

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

    @Override
    public String change_intimacy(String my_user_id, String friend_user_id,int change_type) throws SQLException {

        //连接
        Connection connection = DruidConfig.getConnection();

        String sql_change = "";
        //sql语句
        if(change_type == 0)
            sql_change = "UPDATE friend_info\n" +
                    "SET intimacy = intimacy - 1\n" +
                    "WHERE (user_id_a = ? AND user_id_b = ?) OR (user_id_a = ? AND user_id_b = ?)\n" +
                    "AND intimacy <= 10;";
        else
            sql_change ="UPDATE friend_info\n" +
                    "SET intimacy = intimacy + 1\n" +
                    "WHERE (user_id_a = ? AND user_id_b = ?) OR (user_id_a = ? AND user_id_b = ?)\n" +
                    "AND intimacy <= 10;";
        try(PreparedStatement statement = connection.prepareStatement(sql_change))
        {
                statement.setString(1,my_user_id);
                statement.setString(2,friend_user_id);
                statement.setString(3,friend_user_id);
                statement.setString(4,my_user_id);

            System.out.println("my:" + my_user_id + "  frien: " + friend_user_id);
            System.out.println("进入修改亲密度");
                if(statement.executeUpdate() > 0)
                    return "修改成功!";
                return "修改失败!";
        }catch (SQLException e)
        {
            e.printStackTrace();
            return "修改失败！";
        }
    }

    @Override
    public String insert_friend_info(String my_user_id, String friend_user_id) throws SQLException {

        //连接
        Connection connection = DruidConfig.getConnection();

        //sql语句
        String sql_insert = "INSERT INTO friend_info (user_id_a,user_id_b) VALUES (?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql_insert)) {
            statement.setString(1,my_user_id);
            statement.setString(2,friend_user_id);

            if(statement.executeUpdate() > 0)
            {
                return "插入成功！";
            }
            else return "插入失败!";
        }catch (SQLException e)
        {
            e.printStackTrace();
            return "插入失败！";
        }

    }
}
