package top.liyf.mywebstore.entity;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingCart {

    private int sid;
    private int uid;
    private List<ShoppingCartItem> shoppingItems;


}
