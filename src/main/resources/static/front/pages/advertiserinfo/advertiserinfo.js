import { request } from "../../request/index";

// pages/advertiserinfo.js
Page({

  /**
   * Page initial data
   */
  data: {
    dataList: [],

  },

  onLoad: function (options) {

  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady: function () {

  },

  async onShow() {
    let user = wx.getStorageSync('user');
    if (!user) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      })
      return;
    }

    const response = await request({ url: '/advertiserInfo/page/all?pageNum=1&pageSize=100' });

    if(response.code === '0' && response.data.list != undefined) {
      this.setData({dataList: response.data.list})
    }
  },

})