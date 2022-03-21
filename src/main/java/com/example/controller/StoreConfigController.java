package com.example.controller;

import com.example.common.Result;
import com.example.common.config.StoreConfigs;
import com.example.entity.CommentInfo;
import com.example.entity.StoreConfig;
import com.example.service.StoreConfigService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/storeConfig")
public class StoreConfigController {
    @Resource
    StoreConfigService storeConfigService;

    @GetMapping("/all")
    public Result<List<StoreConfig>> page() {

        List<StoreConfig> all = storeConfigService.getAllConfigs();
        return Result.success(all);
    }

    @PostMapping("/update")
    public Result<List<StoreConfig>>update(@RequestBody StoreConfig config) {
        storeConfigService.update(config);

        return page();
    }
}
