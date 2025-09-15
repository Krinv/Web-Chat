package web_practice.service.login;

import web_practice.dao.SearchDAOImpl;
import web_practice.domain.login.SearchModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SearchServiceImpl implements SearchService{
    SearchDAOImpl searchDAO = new SearchDAOImpl();
    @Override
    public List<Map<String, Object>> get_user_info(SearchModel searchModel) throws SQLException {
        String search_data  = searchModel.getSearch_data();
        String user_id = searchModel.getUser_id();
        return searchDAO.get_user_info(search_data,user_id);
    }
}
