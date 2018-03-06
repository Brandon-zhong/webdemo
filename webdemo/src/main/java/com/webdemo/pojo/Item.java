package com.webdemo.pojo;

import lombok.Data;

import javax.persistence.Column;
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
@Table(name = "tb_item")
public class Item {

    @Id
    @Column(name = "item_id")
    private String id;

    private String title;
    private BigInteger price;
    private int num;
    private int status;


}
