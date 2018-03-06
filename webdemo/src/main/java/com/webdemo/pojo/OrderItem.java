package com.webdemo.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Created by brandon on 2018/3/5.
 *
 * @author brandon
 */
@Entity
@Data
@Table(name = "tb_order_item")
public class OrderItem {

    @Id
    private String id;

    /**
     * 商品ID
     */
    private String itemId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品单价，单位为：分
     */
    private BigInteger price;

    /**
     * 商品数量
     */
    private int num;

    /**
     * 商品总金额，单位为：分
     */
    private BigInteger totalFee;


}
