package com.webdemo.pojo;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * 返回给客户端的订单信息实体类
 * Created by brandon on 2018/3/5.
 *
 * @author brandon
 */
@Data
public class OrderInfo {

    /**
     * 订单ID
     */
    private String order_id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品单价
     */
    private BigInteger price;

    /**
     * 商品数量
     */
    private int num;

    /**
     * 订单总金额
     */
    private BigInteger Total_fee;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 订单状态
     */
    private int status;

    /**
     * 用户留言
     */
    private String buyerMessage;

}
