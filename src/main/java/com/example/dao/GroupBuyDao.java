package com.example.dao;

import com.example.entity.GroupBuy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GroupBuyDao extends Mapper<GroupBuy> {

    @Select("select * from groupBuy where userId = #{userId}")
    List<GroupBuy> findByUserId(Long userId);

    @Delete("delete from groupBuy where userId = #{userId} and goodsId = #{goodsId}")
    void deleteByGoodsId(Long userId, Long goodsId);

    @Select("select * from groupBuy where userId = #{userId} and goodsId = #{goodsId}")
    GroupBuy findByUserIdAndGoodsId(Long userId, Long goodsId);

    @Select("select * from groupBuy where goodsId = #{goodsId}")
    List<GroupBuy> findByGoodsId(Long goodsId);
}
