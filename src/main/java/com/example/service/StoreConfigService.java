package com.example.service;

import com.example.common.ResultCode;
import com.example.dao.StoreConfigDao;
import com.example.entity.StoreConfig;
import com.example.exception.CustomException;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StoreConfigService {
    @Resource
    private StoreConfigDao storeConfigDao;

    public List<StoreConfig> getAllConfigs() {
        return storeConfigDao.selectAllConfigs();
    }

    public StoreConfig getConfig(String keyName) {
        List<StoreConfig> configs = storeConfigDao.selectConfigByKey(keyName);

        if (configs.isEmpty()) {
            throw new CustomException(ResultCode.GET_CONFIG_FAILED);
        }

        return configs.get(0);
    }

    public void update(StoreConfig storeConfig) {
        storeConfigDao.updateByPrimaryKeySelective(storeConfig);
    }
}
