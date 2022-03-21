package com.example.dao;

import com.example.entity.StoreConfig;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StoreConfigDao extends Mapper<StoreConfig> {
    public List<StoreConfig> selectAllConfigs();

    public List<StoreConfig> selectConfigByKey(@Param("keyName") String keyName);
}
