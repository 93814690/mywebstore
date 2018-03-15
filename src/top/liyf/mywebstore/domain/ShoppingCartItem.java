package top.liyf.mywebstore.domain;

public class ShoppingCartItem {

    private int itemid;
    private int sid;
    private String pid;
    private int snum;
    private Product product;

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "itemid=" + itemid +
                ", sid=" + sid +
                ", pid='" + pid + '\'' +
                ", snum=" + snum +
                ", product=" + product +
                '}';
    }
}
