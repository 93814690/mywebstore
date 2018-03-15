package top.liyf.mywebstore.entity;

public class OrderItem {
    private int itemid;
    private String oid;
    private String pid;
    private int buynum;
    private Product product;

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemid=" + itemid +
                ", oid='" + oid + '\'' +
                ", pid='" + pid + '\'' +
                ", buynum=" + buynum +
                ", product=" + product +
                '}';
    }
}
