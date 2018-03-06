package com.webdemo.service;

import com.webdemo.Utils.IDUtils;
import com.webdemo.Utils.ResultUtils;
import com.webdemo.pojo.*;
import com.webdemo.repository.ItemRepository;
import com.webdemo.repository.OrderRepository;
import com.webdemo.repository.Order_ItemReposiroty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by brandon on 2018/3/5.
 *
 * @author brandon
 */
@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Order_ItemReposiroty order_itemReposiroty;


    public Page<OrderItem> selOrder(int num, int size) {

        Pageable pageable = new PageRequest(num, size);

        return order_itemReposiroty.findAll(pageable);
    }

    /**
     * 退款操作
     *
     * @return
     */
    public Result backMoney(String orderId) {
        Result<OrderInfo> result = new Result<>();

        OrderItem orderItem = order_itemReposiroty.findById(orderId);

        if (orderItem == null) {
            return ResultUtils.error(707, "您的订单已取消，无法退款");
        }
        Order order = orderRepository.findById(orderItem.getOrderId());

        if (order.getStatus() != 5) {
            return ResultUtils.error(780, "您的订单未处在退货状态，无法退款，请先申请退货操作");
        }

        order.setStatus(6);
        Order save = orderRepository.save(order);
        if (save == null) {
            return ResultUtils.error(781, "退款失败，请稍后重试");
        }
        result.setCode(788);
        result.setMsg("退款成功，欢迎您的下次光临");

        return result;
    }


    /**
     * 退货操作
     *
     * @return
     */
    public Result backGoods(String orderId) {

        Result<OrderInfo> result = new Result<>();

        OrderItem orderItem = order_itemReposiroty.findById(orderId);

        if (orderItem == null) {
            return ResultUtils.error(707, "您的订单已取消，无法退货");
        }

        Order order = orderRepository.findById(orderItem.getOrderId());

        if (order.getStatus() > 1 && order.getStatus() < 5) {

            order.setStatus(5);
            Order save = orderRepository.save(order);
            if (save == null) {
                return ResultUtils.error(750, "退款退货申请失败，请稍后重试");
            }

            result.setCode(777);
            result.setMsg("您订单的退款退货申请已经受理，请耐心等待");
            result.setData(convertOrderInfo(order, orderItem));
            return result;
        }

        if (order.getStatus() == 1) {
            return ResultUtils.error(708, "您的交易未付款，无法退货，请到取消订单页面进行操作");
        }

        if (order.getStatus() == 5 || order.getStatus() == 6) {
            return ResultUtils.error(709, "您的订单正在退款退货，请勿重复操作");
        }

        return ResultUtils.error(710, "您的订单已经取消或已完成，无法退货，请和商家协商处理");
    }


    /**
     * 取消订单操作
     *
     * @param orderId 要取消的订单ID
     * @return
     */
    public Result<OrderInfo> calcleOrder(String orderId) {

        Result<OrderInfo> result = new Result<>();

        OrderItem orderItem = order_itemReposiroty.findById(orderId);

        if (orderItem == null) {
            return ResultUtils.error(705, "您的订单已经取消，请不要重复操作");
        }


        Order order = orderRepository.findById(orderItem.getOrderId());


        if (order.getStatus() != 1) {
            return ResultUtils.error(700, "您的订单不能取消，详情请咨询客服");
        }

        Date date = new Date();

        order.setStatus(8);//取消订单状态码
        order.setUpdateTime(date);

        Order save = orderRepository.save(order);

        order_itemReposiroty.delete(orderId);

        if (save == null) {
            return ResultUtils.error(701, "订单取消失败，请稍后重试");
        }
        result.setData(convertOrderInfo(order, orderItem));

        result.setCode(777);
        result.setMsg("您的订单取消成功");

        return result;
    }


    /**
     * 封装数据到OrderInfo中
     *
     * @param order
     * @param orderItem
     * @return
     */
    private OrderInfo convertOrderInfo(Order order, OrderItem orderItem) {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrder_id(orderItem.getId());
        orderInfo.setTitle(orderItem.getTitle());
        orderInfo.setPrice(orderItem.getPrice());
        orderInfo.setNum(orderItem.getNum());
        orderInfo.setStatus(order.getStatus());
        orderInfo.setOrderTime(order.getCreateTime());
        orderInfo.setBuyerMessage(order.getBuyerMessage());
        orderInfo.setTotal_fee(orderItem.getTotalFee());

        log.info("com.webdemo.service.OrderService.convertOrderInfo --> " + orderInfo);

        return orderInfo;
    }

    /**
     * 结账操作
     *
     * @param orderId
     * @param money
     * @return
     */
    public Result pay(String orderId, String money) {

        Result result = new Result();

        if (isPay(orderId)) {
            return ResultUtils.error(666, "您已付账，请勿重复付账！");
        }


        Order order = orderRepository.findById(order_itemReposiroty.findById(orderId).getOrderId());

        order.setPayment(new BigInteger((Integer.parseInt(money) * 100) + ""));
        order.setStatus(2);
        Date date = new Date();
        order.setPaymentTime(date);
        order.setUpdateTime(date);
        order.setEndTime(date);

        Order save = orderRepository.save(order);

        if (save == null) {
            return ResultUtils.error(600, "付款失败，请重新支付");
        }

        result.setCode(888);
        result.setMsg("本次购物结束，欢迎下次光临！");
        return result;
    }

    /**
     * 检查该账单是否已经付款
     *
     * @param orderId 要查询的订单id
     * @return true 表示已经付账， false 表示未付账
     */
    private boolean isPay(String orderId) {

        OrderItem orderItem = order_itemReposiroty.findById(orderId);

        Order order = orderRepository.findById(orderItem.getOrderId());

        return order.getStatus() == 1 ? false : true;
    }

    /**
     * 下单操作
     *
     * @param itemId
     * @param userId
     * @param num
     * @param buyerMessage
     * @return
     */
    public Result<OrderInfo> buy(String itemId, String userId, int num, String buyerMessage, int paymentType) {
        Result<OrderInfo> result = new Result<>();
        //先检查商品状态是否可以下单
        if (!checkItem(itemId, num)) {
            return ResultUtils.error(400, "下单失败，请检查商品状态是否正常或商品数量是否正常");
        }

        //商品检查通过，可以下单
        Order order = orderRepository.save(createOrder(itemId, userId, num, buyerMessage, paymentType));
        if (order == null) {
            return ResultUtils.error(500, "订单创建失败");
        }


        OrderItem orderItem = order_itemReposiroty.save(createOrderItem(itemId, order.getId(), num));
        if (orderItem == null) {
            return ResultUtils.error(501, "订单商品信息创建失败");
        }

        result.setData(convertOrderInfo(order, orderItem));

        result.setCode(200);
        if (order.getPaymentType() == 1) {
            result.setMsg("您已下单，请您尽快支付！");
        } else {
            result.setMsg("您的快递正在打包，请耐性等待哦！");
        }


        return result;
    }

    /**
     * 创建订单
     *
     * @param itemId
     * @param userId
     * @param num
     * @param buyerMessage
     * @param paymentType
     * @return
     */
    private Order createOrder(String itemId, String userId, int num, String buyerMessage, int paymentType) {

        Order order = new Order();
        order.setId(IDUtils.getItemId() + "");      //订单ID
        order.setPaymentType(paymentType);      //付款类型
        order.setStatus(1);     //订单状态，刚创建默认为 1-未付款
        order.setCreateTime(new Date());        //订单创建时间
        order.setUserId(userId);        //用户Id
        order.setBuyerMessage(buyerMessage);

        return order;
    }

    /**
     * 创建订单商品信息
     *
     * @param itemId
     * @param orderId
     * @param num
     * @return
     */
    private OrderItem createOrderItem(String itemId, String orderId, int num) {

        OrderItem orderItem = new OrderItem();

        //查询商品信息
        Item item = itemRepository.findById(itemId);

        orderItem.setId(IDUtils.getItemId() + "");
        orderItem.setItemId(itemId);
        orderItem.setOrderId(orderId);
        orderItem.setTitle(item.getTitle());
        orderItem.setPrice(item.getPrice());
        orderItem.setNum(num);
        orderItem.setTotalFee(new BigInteger((num * item.getPrice().intValue()) + ""));

        return orderItem;
    }

    /**
     * 检查商品是否符合下单条件，主要检查商品的状态和数量是否符合
     *
     * @param itemId 要查询的商品id
     * @param num    商品的下单数量
     * @return true 表示可以下单，false 表示不能下单
     */
    public boolean checkItem(String itemId, int num) {

        Item item = itemRepository.findById(itemId);

        log.info("checkItem --> " + item.toString());

        if (item.getStatus() != 1) {//商品状态不等一即为商品非正常状态，不能下单  1-正常，2-下架，3-删除
            log.warn("{} 商品非正常状态，不能下单 ,商品状态：{} （1-正常，2-下架，3-删除）", item.getId(), item.getStatus());
            return false;
        }

        if (item.getNum() < num) {//商品数量小于商品货存数量，不能下单
            log.warn("{} 商品数量 {} 小于 商品库存数量 {} ，不能下单", item.getId(), item.getNum(), num);
            return false;
        }
        return true;
    }

}
