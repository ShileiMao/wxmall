package com.example.dao;

import com.example.entity.CartNotes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CartNotesDao extends Mapper<CartNotes> {
    @Select("SELECT * FROM cart_notes WHERE user_id = #{userId}")
    public List<CartNotes> searchNotes(@Param("userId") Long userId);
}
