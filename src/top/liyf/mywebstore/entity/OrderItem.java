package top.liyf.mywebstore.entity;

import lombok.Data;

@Data
public class OrderItem {
    private int itemid;
    private String oid;
    private String pid;
    private int buynum;
    private Product product;

}
