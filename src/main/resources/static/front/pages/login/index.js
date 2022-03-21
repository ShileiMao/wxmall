import {request} from "../../request/index.js";
Page({
  data: {
    username: "",
    password: "",
    url: "",
    isTabBar: 0
  },

  onLoad: function (options) {
    let url = options.url.replace("$", "?").replace("-","=");
    let isTabBar = options.isTabBar;
    this.setData({
      username: "",
      password: "",
      url: url,
      isTabBar: isTabBar
    })
  },

  onName(e) {
    this.setData({
      username: e.detail.value
    })
  },

  onPassword(e) {
    this.setData({
      password: e.detail.value
    })
  },

  login(e) {
    let url = this.data.url;
    let isTabBar = this.data.isTabBar;
    let data = {name:this.data.username, password: this.data.password}
    request({url:"/login", data:data, method:"POST"}).then(res => {
      console.log(res)
      if (res.code === "0") {
        wx.showToast({
          title: '登陆成功',
          mask: true
        })
        // 存到localStorage里
        wx.setStorageSync('user', res.data)
        setTimeout(() => {
          if (isTabBar === "0") {
            wx.navigateTo({
              url: url,
            })
          } else {
            wx.switchTab({
              url: url,
            })
          }
        }, 1500);
      }
    })
  },
  async loginWechat(e) {
    console.log("请求微信登陆");
    // wx.getUserInfo({
    //   lang: 'en',
    //   success: (resp) => {
    //     console.log("resp: " + JSON.stringify(resp));
    //     wx.showModal({
    //       content: 'getUserInfo: \n' + JSON.stringify(resp)
    //     });
    //   }
    // })
    
    try {
      const wxLoginResult = await wx.login();
      console.log("login result: " + JSON.stringify(wxLoginResult));
      const userInfo = await wx.getUserInfo({lang: 'zh_CN'});  
      console.log("userInfo: " + JSON.stringify(userInfo));

      const url =  "/wechatLogin?code=" + wxLoginResult.code + "&nickName=" + userInfo.userInfo.nickName + "&gender=" + userInfo.userInfo.gender;
      console.log("request url: " + url);
      const loginResult = await request({url: url});
      console.log("登陆成功： " + JSON.stringify(loginResult));
  
      let isTabBar = this.data.isTabBar;

      wx.showToast({
        title: '登陆成功',
        mask: true
      })
      // 存到localStorage里
      wx.setStorageSync('user', loginResult)
      setTimeout(() => {
        if (isTabBar === "0") {
          wx.navigateTo({
            url: url,
          })
        } else {
          wx.switchTab({
            url: '/pages/user/index',
          })
        }
      }, 1500);
    } catch (error) {
      console.error(error);
    }
    // {
    //   success: (resp) => {
    //     if (resp.code) {
    //       //发起网络请求
    //       console.log("登陆成功：" + resp.code);
    //       request({url: "/wechatLogin?code=" + resp.code}).then(resp => {
    //         if(resp.code === '0') {
    //           console.log("login response from server: " + JSON.stringify(resp));
    //           return;
    //         } 

    //         console.log("login response from server: " + JSON.stringify(resp));
    //       })
    //     } else {
    //       console.log('登录失败！' + res.errMsg)
    //     }
    //   }
    // })
  }
})