package top.liyf.mywebstore.entity;

import lombok.Data;

@Data
public class Product {
    private String pid;
    private String pname;
    private double estoreprice;
    private double markprice;
    private int pnum;
    private int cid;
    private String imgurl;
    private String description;
    private Category category;

}
