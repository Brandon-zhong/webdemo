package com.webdemo.repository;


import com.webdemo.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by brandon on 2018/3/5.
 *
 * @author brandon
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

    /**
     * 根据id查找订单信息
     *
     * @param id
     * @return
     */
    Order findById(String id);


}
