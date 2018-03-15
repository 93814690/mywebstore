package top.liyf.mywebstore.service.impl;

import top.liyf.mywebstore.dao.OrderDao;
import top.liyf.mywebstore.dao.OrderItemDao;
import top.liyf.mywebstore.dao.ProductDao;
import top.liyf.mywebstore.dao.ShoppingDao;
import top.liyf.mywebstore.dao.impl.OrderDaoImpl;
import top.liyf.mywebstore.dao.impl.OrderItemDaoImpl;
import top.liyf.mywebstore.dao.impl.ProductDaoImpl;
import top.liyf.mywebstore.dao.impl.ShoppingDaoImpl;
import top.liyf.mywebstore.domain.*;
import top.liyf.mywebstore.service.OrderService;
import top.liyf.mywebstore.util.TransactionManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private ShoppingDao shoppingDao = new ShoppingDaoImpl();
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao itemDao = new OrderItemDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();

    @Override
    public Message createOrder(Order order, String[] pids) throws SQLException {
        Message message = new Message();
        long currentTimeMillis = System.currentTimeMillis();
        order.setOid(currentTimeMillis + "" + order.getUid());
        order.setState(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        order.setOrdertime(date);

        List<OrderItem> orderItemList = new ArrayList<>();
        ShoppingCart cartByUid = shoppingDao.getCartByUid(order.getUid());
        int sid = cartByUid.getSid();
        for (String pid : pids) {
            ShoppingCartItem cartItem = shoppingDao.getCartItem(sid, pid);
            OrderItem item = new OrderItem();
            item.setOid(order.getOid());
            item.setPid(pid);
            item.setBuynum(cartItem.getSnum());
            orderItemList.add(item);
        }

        TransactionManager.startTransaction();
        try {
            orderDao.addOrder(order);

            for (OrderItem item : orderItemList) {
                itemDao.addItem(item);
                productDao.consumeProductNum(item.getPid(), item.getBuynum());
                //创建订单前检测数量
                Product product = productDao.findProductByPid(item.getPid());
                if (product.getPnum() < item.getBuynum()) {
                    message.setMesage(product.getPname() + "库存不足！");
                    throw new Exception();
                }
                shoppingDao.deleteItemByPid(cartByUid.getSid(), item.getPid());
            }
            TransactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionManager.rollback();
            message.setMesage("订单创建失败！");
        } finally {
            TransactionManager.release();
        }

        return message;
    }

    @Override
    public List<Order> getOrderList(int uid) throws SQLException {
        return orderDao.getOrderList(uid);
    }

    @Override
    public Boolean cancelOrder(String oid) throws SQLException {

        boolean flag = false;
        List<OrderItem> orderItemList = itemDao.getOrderItemList(oid);

        TransactionManager.startTransaction();
        try {
            orderDao.updateOrderState(oid, 0);
            for (OrderItem item : orderItemList) {
                productDao.resumeProductNum(item.getPid(), item.getBuynum());
            }

            TransactionManager.commit();
            flag = true;
        } catch (Exception e) {
            TransactionManager.rollback();
        } finally {
          TransactionManager.release();
        }

        return flag;
    }

    @Override
    public Boolean deleteOrder(String oid) throws SQLException {
        return orderDao.updateOrderState(oid, -1);
    }

    @Override
    public Order getOrder(String oid) throws SQLException {
        return orderDao.getOrder(oid);
    }

    @Override
    public Boolean successOrder(String oid) throws SQLException {
        return orderDao.updateOrderState(oid, 2);
    }
}
