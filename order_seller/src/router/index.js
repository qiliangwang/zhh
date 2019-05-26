import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import LoginOut from '@/components/LoginOut'
import ProductList from '@/components/ProductList'
import AddProduct from '@/components/AddProduct'
import CategoryList from '@/components/CategoryList'
import AddCategory from '@/components/AddCategory'
import OrderList from '@/components/OrderList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/',
      component: Home,
      children: [
        {path: '/orderList', name: 'OrderList', component: OrderList},
        {path: '/productList', name: 'ProductList', component: ProductList},
        {path: '/addProduct', name: 'AddProduct', component: AddProduct},
        {path: '/categoryList', name: 'CategoryList', component: CategoryList},
        {path: '/addCategory', name: 'AddCategory', component: AddCategory},
        {path: '/loginOut', name: 'lohinOut', component: LoginOut}
      ]
    }
  ]
})
