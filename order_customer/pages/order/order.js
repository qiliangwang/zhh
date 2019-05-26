let app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    foods: [],
    name: '张三',
    phone: '13655768976',
    address: '江西财经大学麦庐商业街',
    openid: 'ew3euwhd7sjw9diwkq',
    totalPrice: 0,
  },
  bindNameInput (e) {
    this.setData({
      name: e.detail.value,
    })
  },
  bindPhoneInput (e) {
    this.setData({
      phone: e.detail.value,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let _foods = app.globalData.menuInfo
    let _totalPrice = 0;
    _foods.forEach(item => {
      _totalPrice = _totalPrice+ (item.num * item.price)
    })
    this.setData({
      foods: _foods,
      totalPrice: _totalPrice
    })
  },
  createOrder () {
    let {name, address, openid, foods, phone} = this.data
    let _data = {
      name,
      address,
      openid,
      phone
    }
    let items = []
    foods.forEach(item=> {
      items.push({
        productId: item.id,
        productQuantity: item.num
      })
    })
    _data = Object.assign({}, _data, {items})
    wx.request({
      url: 'http://192.168.37.1:8000/sell/buyer/order/create',
      data: _data,
      method: 'POST',
      success: res => {
        wx.navigateTo({
          url: '../success/success',
        })
      }
    })
    
  }
})