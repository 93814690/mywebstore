package top.liyf.mywebstore.entity;

public class Message {
    private String mesage;

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mesage='" + mesage + '\'' +
                '}';
    }
}
