package com.webdemo.repository;

import com.webdemo.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by brandon on 2018/3/5.
 *
 * @author brandon
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    /**
     * 根据 id 查找商品信息
     *
     * @param
     * @return
     */
    Item findById(String id);

    /**
     * 查找所有的商品信息
     *
     * @return
     */
    @Override
    List<Item> findAll();
}
