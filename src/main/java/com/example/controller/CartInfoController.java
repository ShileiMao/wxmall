package com.example.controller;

import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.common.config.StoreConfigs;
import com.example.dao.CartNotesDao;
import com.example.entity.CartInfo;
import com.example.entity.CartNotes;
import com.example.entity.GoodsInfo;
import com.example.entity.StoreConfig;
import com.example.exception.CustomException;
import com.example.service.CartInfoService;
import com.example.service.StoreConfigService;
import com.example.vo.CartListInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.geom.Arc2D;
import java.awt.geom.FlatteningPathIterator;
import java.util.List;

@RestController
@RequestMapping(value = "/cartInfo")
public class CartInfoController {
    @Resource
    private CartInfoService cartInfoService;

    @Resource
    private StoreConfigService storeConfigService;

    @Resource
    private CartNotesDao cartNotesDao;

    /**
     * 查询所有购物车（不分页）
     *
     * @return 购物车list
     */
    @GetMapping
    public Result<CartListInfo> findAll(@RequestParam Long userId) {
        List<GoodsInfo> cartInfos = cartInfoService.findAll(userId);
        StoreConfig freeDelieverAmount = storeConfigService.getConfig(StoreConfigs.Config.FREE_DISPATCH_AMOUNT.name);
        StoreConfig dispatchFee = storeConfigService.getConfig(StoreConfigs.Config.DISPATCH_FEE.name);
        StoreConfig dispatchDistance = storeConfigService.getConfig(StoreConfigs.Config.DISPATCH_DISTANCE.name);
        StoreConfig storeAddr = storeConfigService.getConfig(StoreConfigs.Config.STORE_ADDR.name);

        float freeDelieveryAmount = StoreConfigs.convertToType(freeDelieverAmount.getValue(), Float.TYPE);
        float delieveryFee = StoreConfigs.convertToType(dispatchFee.getValue(), Float.TYPE);
        float distance = StoreConfigs.convertToType(dispatchDistance.getValue(), Float.TYPE);
        String storeAddrStr = StoreConfigs.convertToType(storeAddr.getValue(), String.class);

        CartListInfo cartListInfo = new CartListInfo(cartInfos, freeDelieveryAmount, delieveryFee, distance);

        List<CartNotes> notes = cartNotesDao.searchNotes(userId);
        if(!notes.isEmpty()) {
            cartListInfo.setCartNotes(notes.get(0));
        }

        if(storeAddrStr != null) {
            cartListInfo.setStoreAddr(storeAddrStr);
        }

        return Result.success(cartListInfo);
    }

    @PostMapping("/addNotes")
    public void addNotes(@RequestBody CartNotes notes) {
        int id = cartNotesDao.insert(notes);
        System.out.println("Inset notes: " + id);
    }

    /**
     * 查询所有购物车（分页）
     *
     * @return 购物车list
     */
    @GetMapping("/page")
    public Result<PageInfo<CartInfo>> findAll(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        return Result.success(cartInfoService.findPageDetails(pageNum, pageSize, request));
    }

    /**
     * 根据id获取购物车
     *
     * @param id 购物车id
     * @return 购物车信息
     */
    @GetMapping("/{id}")
    public Result<CartInfo> findById(@PathVariable Long id) {
        return Result.success(cartInfoService.findById(id));
    }

    /**
     * 添加购物车
     *
     * @param detailInfo 购物车信息
     * @return 购物车信息
     */
    @PostMapping
    public Result<CartInfo> add(@RequestBody CartInfo detailInfo) {
        return Result.success(cartInfoService.add(detailInfo));
    }

    /**
     * 更新购物车详情
     *
     * @param detailInfo 商品购物车信息
     * @return 商品购物车信息
     */
    @PutMapping
    public Result<CartInfo> update(@RequestBody CartInfo detailInfo) {
        if (detailInfo.getId() == null) {
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        return Result.success(cartInfoService.update(detailInfo));
    }

    /**
     * 删除购物车
     *
     * @param id 商品id
     * @return result
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        cartInfoService.delete(id);
        return Result.success();
    }

    /**
     * 删除购物车
     *
     * @param userId 用户
     * @param goodsId 商品id
     * @return result
     */
    @DeleteMapping("/goods/{userId}/{goodsId}")
    public Result deleteGoods(@PathVariable Long userId, @PathVariable Long goodsId) {
        cartInfoService.deleteGoods(userId, goodsId);
        return Result.success();
    }

    /**
     * 删除购物车
     *
     * @param userId 用户id
     * @return result
     */
    @DeleteMapping("/empty/{userId}")
    public Result empty(@PathVariable Long userId) {
        cartInfoService.empty(userId);
        return Result.success();
    }
}
