package top.liyf.mywebstore.entity;

import java.util.UUID;

public class User {

    private int uid;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String birthday;
    private String updatetime;
    private User error;
    private String state;
    private String activecode;
    private String checkcode;

    public User() {
        super();
        state = "0";
        UUID uuid = UUID.randomUUID();
        activecode = uuid.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", error=" + error +
                ", state='" + state + '\'' +
                ", activecode='" + activecode + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActivecode() {
        return activecode;
    }

    public void setActivecode(String activecode) {
        this.activecode = activecode;
    }

    public User getError() {
        return error;
    }

    public void setError(User error) {
        this.error = error;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime.replace(".0", "");
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
}
