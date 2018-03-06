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

    @PostMapping(value = "/buy")
    public Result<OrderInfo> buy(@RequestParam(name = "itemId") String itemId,
                                 @RequestParam(name = "userId") String userId,
                                 @RequestParam(name = "num") int num,
                                 @RequestParam(required = false, name = "buyerMessage", defaultValue = "") String buyerMessage,
                                 @RequestParam(name = "paymentType") int paymentType) {

        log.info("com.webdemo.controller.OrderController.buy --> " + itemId + "  " + userId + "  " + num + "  " + buyerMessage + "  " + paymentType);


        return orderService.buy(itemId, userId, num, buyerMessage, paymentType);
    }

    @PostMapping(value = "/pay")
    public Result pay(@RequestParam(name = "orderId") String orderId,
                      @RequestParam(name = "money") String money) {

        log.info("com.webdemo.controller.OrderController.pay -- >" + orderId + "  " + money);

        return orderService.pay(orderId, money);
    }

    @PostMapping(value = "/cancleOrder")
    private Result<OrderInfo> calcleOrder(@RequestParam(name = "orderId") String orderId) {

        log.info("com.webdemo.controller.OrderController.calcleOrder --> " + orderId);

        return orderService.calcleOrder(orderId);
    }


    @PostMapping(value = "/backGoods")
    private Result<OrderInfo> backGoods(@RequestParam(name = "orderId") String orderId) {

        log.info("com.webdemo.controller.OrderController.backGoods -- >" + orderId);

        return orderService.backGoods(orderId);

    }

    @PostMapping(value = "/backMoney")
    private Result<OrderInfo> backMoney(@RequestParam(name = "orderId") String orderId) {

        log.info("com.webdemo.controller.OrderController.backGoods -- >" + orderId);

        return orderService.backMoney(orderId);
    }


    @PostMapping(value = "/selOrder")
    private Page<OrderItem> selOrder(@RequestParam(name = "num", defaultValue = "0") int num, @RequestParam(name = "size", defaultValue = "10") int size) {

        return orderService.selOrder(num, size);
    }


}
