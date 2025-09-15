package web_practice.domain.login;

public class SearchModel
{
    private String search_data;
    private  String user_id ;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSearch_data() {
        return search_data;
    }

    public void setSearch_data(String search_data) {
        this.search_data = search_data;
    }
}
