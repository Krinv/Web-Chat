package web_practice.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DruidConfig
{
    private static DruidDataSource dataSource;

    static {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/web_practice");
        dataSource.setUsername("web_manager");
        dataSource.setPassword("npc..886");
        dataSource.setInitialSize(100);
        dataSource.setMaxActive(800);
        dataSource.setMinIdle(100);
        dataSource.setMaxWait(60000);


        dataSource.setTimeBetweenEvictionRunsMillis(500);

        // 设置空闲连接的最小空闲时间为 5 分钟（300秒），超过此时间的连接会被回收
        dataSource.setMinEvictableIdleTimeMillis(500);

        // 设置空闲连接检测时使用的 SQL 查询，确保连接仍然有效
        dataSource.setValidationQuery("SELECT 1");

        // 设置连接是否在空闲时检测有效性
        dataSource.setTestWhileIdle(true);
    }

    public static DruidDataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
