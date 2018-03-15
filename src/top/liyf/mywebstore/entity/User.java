package top.liyf.mywebstore.entity;

import lombok.Data;

import java.util.UUID;

@Data
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


}
