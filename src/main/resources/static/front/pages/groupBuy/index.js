import {request} from '../../request/index.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getGroupBuy()
  },
  getGroupBuy() {
    let user = wx.getStorageSync("user");
    if (!user) {
      wx.navigateTo({
        url: '/pages/login/index.wxml',
      })
      return;
    }
  
    request({url:"/groupBuy/" + user.id}).then(res => {
      if (res.code === "0") {
        let data = res.data;
        for(let item of data) {
          if (item.fileIds) {
            let fileId = JSON.parse(item.fileIds)[0];
            item.url = 'http://localhost:8888/files/download/' + fileId;
          }
          
        }
        this.setData({
          goodsList: data
        })
      } else {
        wx.showToast({
          title: res.msg,
          icon: "error"
        })
      }
    })
  },
  handlePostOrder() {
    let user = wx.getStorageSync("user");
    if (!user) {
      wx.navigateTo({
        url: '/pages/login/index.wxml',
      })
      return;
    }

    for(let item of this.data.goodsList) {
      request({url:"/orderInfo/groupBuy", data: item, method: 'POST'}).then(res => {
        if (res.code === "0") {
          wx.navigateTo({
            url: '/pages/orderInfo/index?status=待付款'
          });
        } else {
          wx.showToast({
            title: res.msg,
            icon: "none"
          })
        }
      })
    }
  },
  del(e) {
    let user = wx.getStorageSync("user");
    if (!user) {
      wx.navigateTo({
        url: '/pages/login/index.wxml',
      })
      return;
    }
    let goodsId = e.currentTarget.dataset.goodsid;
    request({url:"/groupBuy/" + user.id + "/" + goodsId, method: 'delete'}).then(res => {
      if (res.code === "0") {
        wx.showToast({
          title: "删除成功",
        })
        this.getGroupBuy();
      } else {
        wx.showToast({
          title: res.msg,
          icon: "error"
        })
      }
    })
  }
})