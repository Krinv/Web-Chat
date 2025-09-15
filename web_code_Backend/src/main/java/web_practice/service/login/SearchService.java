package web_practice.service.login;

import web_practice.domain.login.SearchModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    List<Map<String,Object>> get_user_info(SearchModel searchModel) throws SQLException;
}
