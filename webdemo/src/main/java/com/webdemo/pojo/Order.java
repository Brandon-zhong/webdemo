package com.webdemo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by brandon on 2018/3/5.
 *
 * @author brandon
 */
@Entity
@Data
@Table(name = "tb_order")
public class Order {

    /**
     * 订单ID
     */
    @Id
    @Column(name = "order_id")
    private String id;

    /**
     * 实付金额，单位为分
     */
    private BigInteger payment;

    /**
     * 支付类型，1、在线支付，2、货到付款
     */
    private int paymentType;

    /**
     * 订单状态，1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
     */
    private int status;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 付款时间
     */
    private Date paymentTime;

    /**
     * 订单更新时间
     */
    private Date updateTime;

    /**
     * 订单完成时间
     */
    private Date endTime;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户留言
     */
    private String buyerMessage;

}
