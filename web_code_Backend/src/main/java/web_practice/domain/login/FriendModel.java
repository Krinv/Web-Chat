package web_practice.domain.login;

public class FriendModel
{
    private String user_id;
    private int change_type;
    private String my_user_id;
    private String friend_user_id;

    public String getMy_user_id() {
        return my_user_id;
    }

    public void setMy_user_id(String my_user_id) {
        this.my_user_id = my_user_id;
    }

    public String getFriend_user_id() {
        return friend_user_id;
    }

    public void setFriend_user_id(String friend_user_id) {
        this.friend_user_id = friend_user_id;
    }

    public int getChange_type() {
        return change_type;
    }

    public void setChange_type(int change_type) {
        this.change_type = change_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
