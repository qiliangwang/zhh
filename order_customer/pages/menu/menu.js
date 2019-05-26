let app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    caterories:[],
    menuListAll: [],
    menuList: [],
    sortPos: 0
  },
  // 这里到时候要对指定的某一个菜单进行加减，然后存储一个订单得菜单数据用于下单接口
  add (e) {
  let index = e.target.dataset.index;
  let sortPos = this.data.sortPos;
  let _menuListAll = this.data.menuListAll
  _menuListAll.forEach((item, sortPosition) => {
    if(sortPosition === sortPos) {
      item.foods.forEach((iitem, i) => {
        if (i === index) {
          iitem.num += 1;
        }
      })
    }
  })
  this.setData({
    menuListAll: _menuListAll,
    menuList: _menuListAll[sortPos].foods
  })
  },
  reduce (e) {
    let index = e.target.dataset.index;
    let sortPos = this.data.sortPos;
    let _menuListAll = this.data.menuListAll
    _menuListAll.forEach(item => {
      item.foods.forEach((iitem, i) => {
        if (i === index) {
          iitem.num -= 1;
        }
      })
    })
    this.setData({
      menuListAll: _menuListAll,
      menuList: _menuListAll[index].foods
    })
  },
  changeKind (e) {
    let index = e.target.dataset.index;
    let _menuList = this.data.menuListAll[index].foods
    this.setData({
      sortPos: index,
      menuList: _menuList
    })
  },
  finish () {
    let _menuAll = this.data.menuListAll;
    let _foods = []
    _menuAll.forEach(item => {
      item.foods.forEach(iitem => {
        if(iitem.num > 0) {
          _foods.push(iitem)
        }
      })
    })
    if(_foods.length === 0) {
      let that = this;
      wx.showModal({
        title: 'Tips',
        content: '请选择您喜欢的餐饮！',
        success (res) {
          if(res.confirm) {
            let _menuList = that.data.menuListAll[0].foods
            that.setData({
              menuList: _menuList,
              sortPos: 0
            })
          } else if(res.cancel) {
            wx.navigateTo({
              url: '../index/index'
            })
          }
        }
      })
      return;
    }
    app.globalData.menuInfo = _foods
    wx.navigateTo({
      url: '../order/order',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.request({
      url: 'http://192.168.37.1:8000/sell/buyer/product/list',
      success: res => {
        if(res) {
          res.data.data.forEach(item => {
            item.foods.forEach(iitem => {
              iitem.num = 0;
            })
          })
          let _data = res.data.data
          let _caterories = []
          let index = this.data.sortPos
          let foods = _data[index].foods
          _data.forEach(item => {
            _caterories.push(item.name)
          })
          this.setData({
            menuListAll: _data,
            caterories: _caterories,
            menuList: foods
          })
        }
      }
    })
  },
})