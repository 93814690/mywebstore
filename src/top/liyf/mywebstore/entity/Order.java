package top.liyf.mywebstore.entity;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String oid;
    private double money;
    private String recipients;
    private String tel;
    private String address;
    private int state;
    private String ordertime;
    private int uid;
    private List<OrderItem> orderItemList;

}
