package com.example.vo;

import com.example.entity.CartInfo;
import com.example.entity.CartNotes;
import com.example.entity.GoodsInfo;

import java.io.Serializable;
import java.util.List;

public class CartListInfo implements Serializable {
    private List<GoodsInfo> goodsInfoList;

    private float freeDelieveryAmount;

    private float delieveryCharge;

    private float dispatchDistance;

    private CartNotes cartNotes;

    private String storeAddr;

    public CartListInfo(List<GoodsInfo> goodsInfoList, float freeDelieveryAmount, float delieveryCharge, float dispatchDistance) {
        this.goodsInfoList = goodsInfoList;
        this.freeDelieveryAmount = freeDelieveryAmount;
        this.delieveryCharge = delieveryCharge;
        this.dispatchDistance = dispatchDistance;
    }

    public List<GoodsInfo> getGoodsInfoList() {
        return goodsInfoList;
    }

    public float getDelieveryCharge() {
        return delieveryCharge;
    }

    public float getFreeDelieveryAmount() {
        return freeDelieveryAmount;
    }

    public void setDelieveryCharge(float delieveryCharge) {
        this.delieveryCharge = delieveryCharge;
    }

    public void setFreeDelieveryAmount(float freeDelieveryAmount) {
        this.freeDelieveryAmount = freeDelieveryAmount;
    }

    public void setGoodsInfoList(List<GoodsInfo> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
    }

    public float getDispatchDistance() {
        return dispatchDistance;
    }

    public void setDispatchDistance(float dispatchDistance) {
        this.dispatchDistance = dispatchDistance;
    }

    public void setCartNotes(CartNotes cartNotes) {
        this.cartNotes = cartNotes;
    }

    public CartNotes getCartNotes() {
        return cartNotes;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }
}
