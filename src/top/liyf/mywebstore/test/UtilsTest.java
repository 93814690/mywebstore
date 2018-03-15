package top.liyf.mywebstore.test;

import org.junit.Test;
import top.liyf.mywebstore.util.Utils;

public class UtilsTest {

    @Test
    public void notNullTest() {
        Boolean notNUll = Utils.notNUll("   ");
        System.out.println("notNUll = " + notNUll);
    }
}
