package com.example.dao;

import com.example.entity.GoodsAddress;
import com.example.entity.UserAddr;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GoodsAddressDao extends Mapper<GoodsAddress> {
    @Select("select * from goods_address where goodsId = #{id}")
    List<GoodsAddress> getAddress(@Param("id") Long id);
}

