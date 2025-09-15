package web_practice.dao.talk;

import web_practice.utils.DruidConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalkDAOImpl implements TalkDAO
{

    //获取房间信息
    @Override
    public List<Map<String, Object>> get_room_data(String user_id) throws SQLException, ClassNotFoundException {

        List<Map<String, Object>> result = new ArrayList<>();
        //连接  不使用德鲁伊
        Class.forName("com.mysql.cj.jdbc.Driver");
        //数据库URL

        String sql_url  = "jdbc:mysql://localhost:3306/web_practice";
        //数据库用户名
        String sql_user = "web_manager";
        //数据库密码
        String sql_password = "npc..886";

        //连接数据库
        Connection connection = DriverManager.getConnection(sql_url,sql_user,sql_password);

        System.out.println("查询的user_id:" + user_id);
        //sql语句
        String sql_select = "SELECT \n" +
                "    u.user_id AS room_user_id,\n"+
                "    u.user_name AS room_user_name, \n" +
                "    u.user_img_name AS room_img_name, \n" +
                "    u.user_vip_rank AS room_user_vip_rank, \n" +
                "    u.user_online_type,\n" +
                "    r.*  \n"+
                "FROM \n" +
                "    room_info r\n" +
                "JOIN \n" +
                "    users_info u\n" +
                "    ON (r.user_id_a = u.user_id OR r.user_id_b = u.user_id)\n" +
                "WHERE \n" +
                "    (r.user_id_a = ? OR r.user_id_b = ?) \n" +
                "    AND (r.user_id_a != ? OR r.user_id_b !=  ?)" +
                "    AND (u.user_id != ?);";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(sql_select);
            statement.setString(1, user_id);
            statement.setString(2, user_id);
            statement.setString(3, user_id);
            statement.setString(4, user_id);
            statement.setString(5, user_id);
            //获取数据
            rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                row.put("room_id", rs.getInt("room_id"));
                row.put("room_user_id", rs.getString("room_user_id"));
                row.put("vip", rs.getInt("room_user_vip_rank"));
                row.put("room_img_name", rs.getString("room_img_name"));
                row.put("room_date", rs.getString("room_date"));
                row.put("room_name", rs.getString("room_user_name"));
                row.put("room_detail", rs.getString("room_detail"));

                //添加到结果
                result.add(row);
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database query failed: " + e.getMessage(), e);
        } finally {
            System.out.println("用户:" + user_id + "关闭连接获取房间信息数据库！");

            // 确保资源在使用完后关闭
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 关闭 ResultSet 异常
            }

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 关闭 PreparedStatement 异常
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 关闭 Connection 异常
            }
        }

    }

    //获取聊天信息
    @Override
    public List<Map<String, Object>> get_talk_data(int room_id) throws SQLException, ClassNotFoundException {
        //连接
        Class.forName("com.mysql.cj.jdbc.Driver");
        //数据库URL
        String sql_url  = "jdbc:mysql://localhost:3306/web_practice";
        //数据库用户名
        String sql_user = "web_manager";
        //数据库密码
        String sql_password = "npc..886";

        //连接数据库
        Connection connection = DriverManager.getConnection(sql_url,sql_user,sql_password);

        List<Map<String, Object>> talk_data_list = new ArrayList<>();
//sql 语句
        String sql_select = "SELECT u.user_name, u.user_img_name, " +
                "u.user_id, u.user_vip_rank, " +
                "t.talk_content, t.talk_id, t.talk_date " +
                "FROM talk_info t " +
                "JOIN users_info u ON t.user_id = u.user_id " +
                "WHERE t.room_id = ? AND u.user_id = t.user_id";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(sql_select);
            statement.setInt(1, room_id);

            rs = statement.executeQuery();

            //遍历
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("user_name", rs.getString("user_name"));
                row.put("user_img_name", rs.getString("user_img_name"));
                row.put("vip", rs.getString("user_vip_rank"));
                row.put("talk_content", rs.getString("talk_content"));
                row.put("talk_id", rs.getString("talk_id"));
                row.put("talk_date", rs.getString("talk_date"));
                row.put("user_id", rs.getString("user_id"));
                talk_data_list.add(row);
            }
            return talk_data_list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database query failed: " + e.getMessage(), e);
        } finally {
            // 确保资源在使用完后关闭
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 关闭 ResultSet 异常
            }

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 关闭 PreparedStatement 异常
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 关闭 Connection 异常
            }
        }

    }

    @Override
    public int insert_room_info(String user_id_a, String user_id_b) throws SQLException {

        //创建连接
        Connection connection = DruidConfig.getConnection();




        // 先查询是否已经存在相同的房间
        String sql_check = "SELECT room_id " +
                "FROM room_info " +
                "WHERE (user_id_a = ? AND user_id_b = ?) " +
                "OR (user_id_a = ? AND user_id_b = ?) " +
                "LIMIT 1";

        try (PreparedStatement statement1 = connection.prepareStatement(sql_check)) {
            statement1.setString(1, user_id_a);
            statement1.setString(2, user_id_b);
            statement1.setString(3, user_id_b);
            statement1.setString(4, user_id_a);

            try (ResultSet rs = statement1.executeQuery()) {
                if (rs.next()) {
                    // 如果已经存在房间，返回现有的 room_id
                    int room_id = rs.getInt("room_id");
                    System.out.println("已存在房间，room_id: " + room_id);
                    return room_id;
                } else {
                    // 如果没有房间，则插入新记录
                    String sql_insert = "INSERT INTO room_info (user_id_a, user_id_b, room_detail) " +
                            "SELECT ?, ?, '聊天' FROM DUAL " +
                            "WHERE NOT EXISTS ( " +
                            "    SELECT 1 FROM room_info " +
                            "    WHERE (user_id_a = ? AND user_id_b = ?) " +
                            "    OR (user_id_a = ? AND user_id_b = ?) " +
                            ") LIMIT 1";

                    try (PreparedStatement statement2 = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)) {
                        statement2.setString(1, user_id_a);
                        statement2.setString(2, user_id_b);
                        statement2.setString(3, user_id_a);
                        statement2.setString(4, user_id_b);
                        statement2.setString(5, user_id_a);
                        statement2.setString(6, user_id_b);

                        int rows = statement2.executeUpdate();
                        if (rows > 0) {
                            // 获取生成的 room_id
                            try (ResultSet generatedKeys =statement2.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    int room_id = generatedKeys.getInt(1);
                                    System.out.println("新创建的 room_id: " + room_id);
                                    return room_id;
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String insert_talk_info(String user_id, String talk_content, int room_id) throws SQLException {

        //连接数据库
        Connection connection = DruidConfig.getConnection();

//sql语句
        String sql_insert = "INSERT INTO talk_info (room_id,user_id,talk_content) VALUES (?,?,?)";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql_insert);
            statement.setInt(1, room_id);
            statement.setString(2, user_id);
            statement.setString(3, talk_content);

            if (statement.executeUpdate() > 0) {
                return "用户:" + user_id + "聊天信息插入成功";
            } else {
                return "用户:" + user_id + "聊天信息插入失败";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 捕获 SQL 异常并打印
            return "Error: " + e.getMessage();
        } finally {
            // 确保资源在使用完后关闭
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 关闭 PreparedStatement 异常
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // 关闭 Connection 异常
            }
        }

    }
}
