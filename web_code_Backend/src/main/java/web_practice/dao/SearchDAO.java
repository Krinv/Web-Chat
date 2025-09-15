package web_practice.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SearchDAO {

    List<Map<String,Object> >get_user_info(String search_data,String user_id) throws SQLException;
}
