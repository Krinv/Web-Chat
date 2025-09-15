package web_practice.domain.login;

public class TalkModel {
    private String user_id;
    private int room_id;
    private String user_id_a;
    private String user_id_b;

    public String getTalk_content() {
        return talk_content;
    }

    public void setTalk_content(String talk_content) {
        this.talk_content = talk_content;
    }

    private String talk_content;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getUser_id_a() {
        return user_id_a;
    }

    public void setUser_id_a(String user_id_a) {
        this.user_id_a = user_id_a;
    }

    public String getUser_id_b() {
        return user_id_b;
    }

    public void setUser_id_b(String user_id_b) {
        this.user_id_b = user_id_b;
    }
}
