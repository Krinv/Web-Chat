package web_practice.domain.login;

public class Get_user_infoModel
{
    private String user_id;
    private String user_img_name;
    private String user_only_type;
    private  int user_vip_rank;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_img_name() {
        return user_img_name;
    }

    public void setUser_img_name(String user_img_name) {
        this.user_img_name = user_img_name;
    }

    public String getUser_only_type() {
        return user_only_type;
    }

    public void setUser_only_type(String user_only_type) {
        this.user_only_type = user_only_type;
    }

    public int getUser_vip_rank() {
        return user_vip_rank;
    }

    public void setUser_vip_rank(int user_vip_rank) {
        this.user_vip_rank = user_vip_rank;
    }

    public int getUser_money() {
        return user_money;
    }

    public void setUser_money(int user_money) {
        this.user_money = user_money;
    }

    private  int user_money;
}
