import {request} from '../../request/index.js'
import QQMapWX from "../../utils/qqmap-wx-jssdk";
import { MAP_KEY } from "../../config";
import { getDistance } from "../../utils/util";
let qqmapsdk;

Page({
  data: {
    defaultImageUrl: '../../imgs/default.png',
    cart: [ ],
    totalPrice: 0,
    totalNum: 0,
    totalFee: 0,
    freeChargeDelieverAmount: 0,
    delieveryCharge: 0,
    actualDelieveryCharge: 0,
    dispatchDistance: 10,
    notes: '',
    storeAddr: '',
    addr: '',
    province: '',
    district: '',
    city: '',
    user: {}
  },
  onLoad(e) {
    //获取地图 map 实例
    this.mapCtx = wx.createMapContext('myMap')
    // 实例化API核心类
    qqmapsdk = new QQMapWX({
      key: MAP_KEY
    });
  },
  onShow(e) {
    let user = wx.getStorageSync("user");
    let url = '/pages/login/index?isTabBar=1&url=/pages/cartInfo/index';
    if (!user) {
      wx.navigateTo({
        url: url,
      })
      return;
    }

    this.setData({
      user: user
    });

    this.getCartInfo();

    request({url: '/postAddress/' + user.id,
      method: "GET"}).then(res => {
        console.log("response get address: " + JSON.stringify(res));
        if(res.code == '0') {

            this.setData({
              district: res.data.district,
              province: res.data.province,
              city: res.data.city,
              addr: res.data.addr
            })
        }
    })
  },
  // 商品的选中
  getCartInfo() {
    let user = this.data.user;
    request({url: '/cartInfo?userId=' + user.id}).then(res => {
      if (res.code === '0') {
        console.log("cart info: " + JSON.stringify(res));
        console.log("store addr: " + res.data.storeAddr);
        let cartList = res.data.goodsInfoList;
        let totalPrice = 0;
        let totalNum = 0;
        let delieveryCharge = res.data.delieveryCharge;
        let actualDelieveryCharge = 0;
        let freeChargeDelieverAmount = res.data.freeDelieveryAmount;
        let dispatchDistance = res.data.dispatchDistance;
        cartList.forEach(item => {
          totalNum += item.count;
          totalPrice += item.count * item.price * item.discount;
          let imgSrc = this.data.defaultImageUrl;
          if (item.fileIds) {
            let fileId = JSON.parse(item.fileIds)[0];
            imgSrc = 'http://localhost:8888/files/download/' + fileId;
          }
          item.url = imgSrc;
        })
        if (totalPrice < res.data.freeDelieveryAmount) {
          delieveryCharge = res.data.delieveryCharge;
          actualDelieveryCharge = res.data.delieveryCharge;
        }
        this.setData({
          cart: cartList,
          totalNum: totalNum,
          totalPrice: totalPrice.toFixed(2),
          freeChargeDelieverAmount: freeChargeDelieverAmount,
          delieveryCharge: delieveryCharge,
          actualDelieveryCharge: actualDelieveryCharge,
          dispatchDistance: dispatchDistance,
          storeAddr: res.data.storeAddr,
          totalFee: totalPrice + actualDelieveryCharge
        })

        console.log("current fee charging: " + this.data.actualDelieveryCharge);
        console.log(this.data.cart)
      }
    })
  },
  // 商品数量的编辑功能
  handleItemNumEdit(e) {
    // 1 获取传递过来的参数 
    const { operation, id } = e.currentTarget.dataset;
    // 2 获取购物车数组
    let cart = this.data.cart;
    // 3 找到需要修改的商品的索引
    const index = cart.findIndex(v => v.id === id);
    // 4 判断是否要执行删除
    if (cart[index].count === 1 && operation === -1) {
      // 4.1 弹窗提示
      wx.showModal({
        content: '您是否要删除？',
        success: (res) => {
          if (res.confirm) {
            let user = wx.getStorageSync("user");
            request({url: '/cartInfo/goods/' + user.id + '/' + id, method: 'DELETE'}).then(res => {
              if (res.code === '0') {
                let cart = this.data.cart;
                cart.splice(index, 1);
                let totalPrice = 0;
                let totalNum = 0;
                cart.forEach(item => {
                  totalNum += item.count;
                  totalPrice += item.count * item.price * item.discount;
                })

                let actualDelieveryCharge = 0;
                if (totalPrice < this.data.freeChargeDelieverAmount) {
                  actualDelieveryCharge = this.data.delieveryCharge;
                }

                
              
                this.setData({
                  cart: cart,
                  totalPrice: totalPrice.toFixed(2),
                  totalNum: totalNum,
                  actualDelieveryCharge: actualDelieveryCharge,
                  totalFee: totalPrice + actualDelieveryCharge
                })

                console.log("delievery charge: " + this.data.actualDelieveryCharge);
              } else {
                wx.showToast({
                  title: res.msg,
                  icon: 'error'
                })
              }
            })
          }
        }
      })
    } else {
      // 4  进行修改数量
      let cart = this.data.cart;
      cart[index].count += operation;
      // 重新计算一下总价
      let totalPrice = 0;
      let totalNum = 0;
      cart.forEach(item => {
        totalNum += item.count;
        totalPrice += item.count * item.price * item.discount;
      })

      let actualDelieveryCharge = 0;
      if (totalPrice < this.data.freeChargeDelieverAmount) {
        actualDelieveryCharge = this.data.delieveryCharge;
      }
      
      this.setData({
        cart: cart,
        totalPrice: totalPrice.toFixed(2),
        totalNum: totalNum,
        actualDelieveryCharge: actualDelieveryCharge,
        totalFee: totalPrice + actualDelieveryCharge
      })
    }
  },
  gotoAddressManager() {
    wx.navigateTo({
      url: '/pages/addressList/index',
    })
  },

  notesAreaInput(e) {
    console.log("user entered: " + e.detail.value);
    this.setData({
      notes: e.detail.value
    })
  },
  // 点击 结算 
  handlePay(){
    if (this.data.cart.length === 0) {
      wx.showToast({
        title:"购物车空空如也~",
        icon: "none"
      })
      return;
    }
    let user = wx.getStorageSync("user");

    let address1 = this.data.storeAddr;

  
    wx.showLoading({
      title: '计算距离中。。。',
    });
    getDistance(address1, this.data.addr, qqmapsdk).then(res => {
      console.log("from card view, distance: " + JSON.stringify(res));
      wx.hideLoading();

      if(res.status === 0) {

        let distance = res.result.elements[0].distance;
        console.log("distance: " + distance);
        if(distance / 1000.0 > this.data.dispatchDistance) {
          wx.showModal({
            content: "距离超过最大配送距离（" + this.data.dispatchDistance + "KM），修改您的地址",
            success: e => {
              console.log("tapped ok")
              wx.navigateTo({
                url: '/pages/addressList/index',
              })
            }
          })

          return;
        }
        
        let data = {userId: user.id, level: user.level, totalPrice: this.data.totalPrice, goodsList: this.data.cart, notes: this.data.notes};
        request({url: '/orderInfo', method: 'POST', data:data}).then(res => {
          console.log("submit order, result: " + JSON.stringify(res));
          if (res.code === '0') {
            wx.showToast({
              title:"加入购物车成功"
            })
            // 3 跳转到 支付页面
            wx.navigateTo({
              url: '/pages/orderInfo/index?status=待付款'
            });
          } else {
            wx.showToast({
              title: res.msg,
              icon: 'error'
            })
          }
        })
      }
    });
    return;
  }
})