import {request} from '../../request/index.js'
import {config} from '../../request/config'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    obj: {},
    goodsId: 0,
    swiperList: [],
    commentList: [],
    title: "ye",
    allowComment: 0,

    dialogData: { //传向子组件自定义的弹窗内容：默认
      title: '评价', //标题
      content: '输入您的评论', //内容
      cancelText: '取消', //取消按钮内容
      confirmText: '确定', //确认按钮内容,
      isShowCancelBtn: true, //是否显示“取消”按钮，默认显示
      cancelEvent: '_cancelEvent', //绑定点击取消按钮后的事件
      confirmEvent: '_confirmEvent', //绑定点击确认按钮后的事件
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const id = options.id;
    const allowComment = 0;

    console.log("options: " + JSON.stringify(options));
    if (options.allowComment != null && options.allowComment != undefined) {
      allowComment = options.allowComment;
    }
    this.setData({
      goodsId: id,
      allowComment: allowComment
    })
    // 获取数据
    this.getDetail(id);
    this.getComment(id);
  },
  getDetail(id) {
    request({url: '/goodsInfo/' + id}).then(res => {
      if(res.code === '0') {
        let obj = res.data;
        let swiperList = [];
        if(obj.fileIds) {
          let list = JSON.parse(obj.fileIds);
          list.forEach(item => {
            let imgObj = {};
            imgObj.fileId = item;
            imgObj.imgSrc = 'http://localhost:8888/files/download/' + item;
            swiperList.push(imgObj);
          });
        }
        if (swiperList.length === 0) {
          swiperList.push({imgSrc:"../../imgs/default.png"});
          swiperList.push({imgSrc:"../../imgs/default.png"});
        }
        this.setData({
          obj,
          swiperList
        })
      } else {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        })
      }
    })
  },
  getComment(goodsId) {
    request({url:"/commentInfo/all/" + goodsId}).then(res => {
      if (res.code === "0") {
        let list = res.data;
        list.forEach(item => {
          item.url = '../../imgs/comment.png';
        })
        this.setData({
          commentList: res.data
        })
      } else {
        wx.showToast({
          title: res.msg,
          icon: 'error'
        })
      }
    })
  },
  addComment() {
    let dialog = this.selectComponent("#commentDialog");
  
    dialog.showDialog();
  },
  handleCartAdd() {
    let user = wx.getStorageSync("user");
    let url = '/pages/login/index?isTabBar=0&url=/pages/goodsInfo/index$id-' + this.data.goodsId;
    console.log(url)
    if (!user) {
      wx.navigateTo({
        url: url,
      })
      return;
    }
    let data = {userId: user.id, level: user.level, goodsId:this.data.goodsId, count: 1};
    request({url:"/cartInfo", data:data, method:"POST"}).then(res => {
      if (res.code === "0") {
        wx.showToast({
          title:"加入购物车成功"
        })
      } else {
        wx.showToast({
          title: res.msg,
          icon: "error"
        })
      }
    })
  },
  handleCollect() {
    let user = wx.getStorageSync("user");
    let url = '/pages/login/index?isTabBar=0&url=/pages/goodsInfo/index$id-' + this.data.goodsId;

    if (!user) {
      wx.navigateTo({
        url: url,
      })
      return;
    }
    let data = {userId: user.id, level: user.level, foreignId:this.data.goodsId};
    request({url:"/collectInfo", data:data, method:"POST"}).then(res => {
      if (res.code === "0") {
        wx.showToast({
          title:"收藏成功"
        })
      } else {
        wx.showToast({
          title: res.msg,
          icon: "error"
        })
      }
    })
  },
  handleGroupBuy() {
    let user = wx.getStorageSync("user");
    if (!user) {
      wx.navigateTo({
        url: '/pages/login/index.wxml',
      })
      return;
    }
    let data = {userId: user.id, goodsId: this.data.goodsId, goodsName: this.data.obj.name};
    request({url:"/groupBuy", data:data, method:"POST"}).then(res => {
      if (res.code === "0") {
        wx.showToast({
          title:"提交成功"
        })
      } else {
        wx.showToast({
          title: res.msg,
          icon: 'none'
        })
      }
    })
  },

  //取消事件
  _cancelEvent(e) {
  
    console.log('父组件index.js——取消事件——获取子组件传递过来的值：', JSON.stringify(e));
  },

  //确认事件
  _confirmEvent(e) {
    let user = wx.getStorageSync("user");
    if (!user) {
      wx.navigateTo({
        url: '/pages/login/index.wxml',
      })
      return;
    }

    
  
    let comment = e.detail.comment;
    let rate = e.detail.rate;

    console.log("confirm rate: " + rate);

    let data = {content: comment, goodsId: this.data.goodsId, userId: user.id, level: rate};
    request({url: '/commentInfo', method: 'POST', data: data}).then(res => {
        console.log("comment result: " + JSON.stringify(res));

        this.getComment(this.data.goodsId);
    })
  }
})