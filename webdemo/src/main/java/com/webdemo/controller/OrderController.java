package com.webdemo.controller;

import com.webdemo.pojo.Order;
import com.webdemo.pojo.OrderInfo;
import com.webdemo.pojo.OrderItem;
import com.webdemo.pojo.Result;
import com.webdemo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by brandon on 2018/3/5.
 *
 * @author brandon
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;


    /**
     * 下单操作
     *
     * @param itemId       要下单商品id
     * @param userId       下单的用户id
     * @param num          下单的商品数量
     * @param buyerMessage 用户下单时候的留言信息（可选）
     * @param paymentType  下单时候的字符方式 1-在线支付，2-货到付款
     * @return 返回下单的结果
     */
    @PostMapping(value = "/buy")
    public Result<OrderInfo> buy(@RequestParam(name = "itemId") String itemId,
                                 @RequestParam(name = "userId") String userId,
                                 @RequestParam(name = "num") int num,
                                 @RequestParam(required = false, name = "buyerMessage", defaultValue = "") String buyerMessage,
                                 @RequestParam(name = "paymentType") int paymentType) {

        log.info("com.webdemo.controller.OrderController.buy --> " + itemId + "  " + userId + "  " + num + "  " + buyerMessage + "  " + paymentType);

        return orderService.buy(itemId, userId, num, buyerMessage, paymentType);
    }

    /**
     * 支付操作
     *
     * @param orderId 要进行支付操作的订单id
     * @param money   支付的金额
     * @return 返回支付的结果
     */
    @PostMapping(value = "/pay")
    public Result pay(@RequestParam(name = "orderId") String orderId,
                      @RequestParam(name = "money") String money) {

        log.info("com.webdemo.controller.OrderController.pay -- >" + orderId + "  " + money);

        return orderService.pay(orderId, money);
    }

    /**
     * 取消订单
     *
     * @param orderId 要取消订单的订单号
     * @return 返回取消订单的结果
     */
    @PostMapping(value = "/cancleOrder")
    private Result<OrderInfo> calcleOrder(@RequestParam(name = "orderId") String orderId) {

        log.info("com.webdemo.controller.OrderController.calcleOrder --> " + orderId);

        return orderService.calcleOrder(orderId);
    }


    /**
     * 退货操作
     *
     * @param orderId 要执行退货操作的订单id
     * @return 返回退货的结果
     */
    @PostMapping(value = "/backGoods")
    private Result<OrderInfo> backGoods(@RequestParam(name = "orderId") String orderId) {

        log.info("com.webdemo.controller.OrderController.backGoods -- >" + orderId);

        return orderService.backGoods(orderId);

    }

    /**
     * 退款操作
     *
     * @param orderId 要执行退款操作的订单id
     * @return 返回退款的结果
     */
    @PostMapping(value = "/backMoney")
    private Result<OrderInfo> backMoney(@RequestParam(name = "orderId") String orderId) {

        log.info("com.webdemo.controller.OrderController.backGoods -- >" + orderId);

        return orderService.backMoney(orderId);
    }


    /**
     * 查询订单信息-分页
     *
     * @param num  查第几页的订单信息
     * @param size 每页展示的数据量
     * @return 返回查询的订单信息
     */
    @PostMapping(value = "/selOrder")
    private Page<OrderItem> selOrder(@RequestParam(name = "num", defaultValue = "0") int num, @RequestParam(name = "size", defaultValue = "10") int size) {

        return orderService.selOrder(num, size);
    }


}
