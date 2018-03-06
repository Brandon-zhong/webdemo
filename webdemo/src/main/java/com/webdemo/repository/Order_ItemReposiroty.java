package com.webdemo.repository;

import com.webdemo.pojo.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by brandon on 2018/3/5.
 *
 * @author brandon
 */
@Repository
public interface Order_ItemReposiroty extends JpaRepository<OrderItem, String> {

    /**
     * 根据
     *
     * @param id
     * @return
     */
    OrderItem findById(String id);


}
