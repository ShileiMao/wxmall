import { request } from "../../request/index";

// pages/advertiserdetails/advertiserdetails.js
Page({

  /**
   * Page initial data
   */
  data: {
    advertiseInfo: {},
    advertContent: ''
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: async function (options) {
    let id = options.id;
    
    const result = await request({url: '/advertiserInfo/' + id});

    if(result.code === '0' && result.data != undefined) {
      
      let jsonStr = this.formRichText(result.data.content);
      // let jsonObj = JSON.parse(jsonStr);
      this.setData({advertiseInfo: result.data, advertContent: jsonStr})
    }
  },

  formRichText: function(html){
      let newContent= html.replace(/<img[^>]*>/gi,function(match,capture){
          match = match.replace(/style="[^"]+"/gi, '').replace(/style='[^']+'/gi, '');
          match = match.replace(/width="[^"]+"/gi, '').replace(/width='[^']+'/gi, '');
          match = match.replace(/height="[^"]+"/gi, '').replace(/height='[^']+'/gi, '');
          return match;
      });
      newContent = newContent.replace(/style="[^"]+"/gi,function(match,capture){
          match = match.replace(/width:[^;]+;/gi, 'max-width:100%;').replace(/width:[^;]+;/gi, 'max-width:100%;');
          return match;
      });
      newContent = newContent.replace(/<br[^>]*\/>/gi, '');
      newContent = newContent.replace(/\<img/gi, '<img style="max-width:100%;height:auto;display:block;margin-top:0;margin-bottom:0;"');
      return newContent;
  }

})