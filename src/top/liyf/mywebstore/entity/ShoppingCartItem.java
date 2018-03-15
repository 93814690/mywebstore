package top.liyf.mywebstore.entity;

import lombok.Data;

@Data
public class ShoppingCartItem {

    private int itemid;
    private int sid;
    private String pid;
    private int snum;
    private Product product;

}
