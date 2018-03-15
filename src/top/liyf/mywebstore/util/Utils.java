package top.liyf.mywebstore.util;

import top.liyf.mywebstore.domain.User;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static boolean deleteImg(String path) {
        path = "mywebstore/" + path;
        String newPath = new File(path).getAbsolutePath();
        newPath = newPath.replace("bin", "webapps");
        File file = new File(newPath);
        if (file.exists()) {
            if (file.isFile()) {
                System.out.println("---delete---"+newPath);
                return file.delete();
            }
        }
        return false;
    }

    public static Boolean notNUll(Object... params) {
        for (Object o : params) {
            if (o == null) {
                return false;
            } else if ((o instanceof String) && "".equals(((String) o).trim())) {
                return false;
            }
        }
        return true;
    }

    public static String newPassword(String password, String time) {
        String newPassword = "";
        try {

            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] cipherData = md5.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            for(byte cipher : cipherData) {
                String toHexStr = Integer.toHexString(cipher & 0xff);
                builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
            }
            newPassword = builder.toString();
//            System.out.println("---1---newPassword = " + newPassword);

            //二次md5
            cipherData = md5.digest((newPassword + time).getBytes());
            builder = new StringBuilder();
            for(byte cipher : cipherData) {
                String toHexStr = Integer.toHexString(cipher & 0xff);
                builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
            }
            newPassword = builder.toString();
//            System.out.println("===2===newPassword = " + newPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return newPassword;
    }

    public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
}
