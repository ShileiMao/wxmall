import {request} from "../../request/index.js";
import QQMapWX from "../../utils/qqmap-wx-jssdk";
import { MAP_KEY } from "../../config";
let qqmapsdk;

// pages/addressList/index.js.js
Page({

  /**
   * Page initial data
   */
  data: {
    district: '',
    province: '',
    city: '',
    longitude: '',
    latitude: '',
    isLogin: 0,
    user: {}
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {
    //获取地图 map 实例
    this.mapCtx = wx.createMapContext('myMap')
    // 实例化API核心类
    qqmapsdk = new QQMapWX({
      key: MAP_KEY
    });
  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady: function () {

  },

  /**
   * Lifecycle function--Called when page show
   */
  onShow: function () {
    let user = wx.getStorageSync('user');
    if (!user) {
      return
    }

    console.log("user: " + JSON.stringify(user));
    this.setData({
      isLogin: 1,
      userinfo: user
    })

    let self = this;
    request({url: '/postAddress/' + user.id,
      method: "GET"}).then(res => {
        console.log("response get address: " + JSON.stringify(res));
        if(res.code == '0') {

          console.log("self: " + JSON.stringify(self));

          console.log("self: " + JSON.stringify(self));
            self.setData({
              district: res.data.district,
              province: res.data.province,
              city: res.data.city,
              addr: res.data.addr
            })
        }
    })
    console.log("province 1: " + this.data.province);
    console.log("city: 1" + this.data.city);
    console.log("longituede1: " + this.data.longitude);
  },

  selectAddress() {
    wx.navigateTo({
      url: '/pages/addressManage/index',
    })
  }
})