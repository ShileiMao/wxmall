package com.example.controller;

import com.example.common.Result;
import com.example.dao.GoodsInfoDao;
import com.example.dao.GroupBuyDao;
import com.example.entity.GoodsInfo;
import com.example.entity.GroupBuy;
import com.example.exception.CustomException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/groupBuy")
public class GroupBuyController {

    @Resource
    private GroupBuyDao groupBuyDao;

    @Resource
    private GoodsInfoDao goodsInfoDao;


    @PostMapping
    public Result buy(@RequestBody GroupBuy groupBuy) {
        Long goodsId = groupBuy.getGoodsId();
        Long userId = groupBuy.getUserId();
        GroupBuy buy = groupBuyDao.findByUserIdAndGoodsId(userId, goodsId);
        if (buy != null) {
            throw new CustomException("-1", "该商品正在拼团中");
        }
        GoodsInfo goodsInfo = goodsInfoDao.selectByPrimaryKey(goodsId);
        groupBuy.setPrice(0.5 * goodsInfo.getPrice());
        groupBuyDao.insertSelective(groupBuy);
        return Result.success();
    }

    @GetMapping("/{userId}")
    public Result<List<GroupBuy>> findByUserId(@PathVariable Long userId) {
        List<GroupBuy> list = groupBuyDao.findByUserId(userId);
        for (GroupBuy groupBuy : list) {
            Long goodsId = groupBuy.getGoodsId();
            GoodsInfo goodsInfo = goodsInfoDao.selectByPrimaryKey(goodsId);
            groupBuy.setFileIds(goodsInfo.getFileIds());

            List<GroupBuy> buys = groupBuyDao.findByGoodsId(goodsId);
            if (buys.size() < 3) {
                groupBuy.setStatus("拼团人数不足");
            } else {
                groupBuy.setStatus("拼团人数已满");
            }
        }
        return Result.success(list);
    }

    @DeleteMapping("/{userId}/{goodsId}")
    public Result delete(@PathVariable Long userId, @PathVariable Long goodsId) {
        groupBuyDao.deleteByGoodsId(userId, goodsId);
        return Result.success();
    }
}
