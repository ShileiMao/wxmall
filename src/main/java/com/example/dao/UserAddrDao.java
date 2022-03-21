package com.example.dao;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

import com.example.entity.UserAddr;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddrDao extends Mapper<UserAddr> {
    @Select("select * from user_addr where userId = #{id}")
    List<UserAddr> getAddress(@Param("id") Long id);
}
