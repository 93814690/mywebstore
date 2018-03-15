package top.liyf.mywebstore.test;

import org.junit.Test;
import top.liyf.mywebstore.service.UserService;
import top.liyf.mywebstore.service.impl.UserServiceImpl;

import java.sql.SQLException;

public class UserTest {
    private UserService userService = new UserServiceImpl();

    @Test
    public void addProductTest() {
        try {
            Boolean usernameExist = userService.usernameExist("aaa");
            System.out.println("usernameExist = " + usernameExist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
