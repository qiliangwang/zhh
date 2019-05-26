<template>
    <div>
        <el-row>
            <div class='head'>
                <div class='logo'>
                    <img src='../assets/logo.png'/>
                </div>
                <span class='name'>餐厅收银后台管理系统</span>
            </div>
        </el-row>
        <el-row>
            <el-col :span='4'>
                <div>
                    <el-menu router :default-active="$route.path" text-color="#000" @open='openHandle'>
                        <el-menu-item index="/orderList">
                            <span slot="title">订单管理</span>
                        </el-menu-item>
                        <el-submenu index="/1">
                            <template slot="title">
                                <span>商品管理</span>
                            </template>
                            <el-menu-item-group>
                                <template slot="title">操作</template>
                                <el-menu-item index='/productList'>列表查询</el-menu-item>
                                <el-menu-item index='/addProduct'>商品新增</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                        <el-submenu index="/2">
                            <template slot="title">
                                <span>类目管理</span>
                            </template>
                            <el-menu-item-group>
                                <template slot="title">操作</template>
                                <el-menu-item index="/categoryList">列表查询</el-menu-item>
                                <el-menu-item index="/addCategory">类目新增</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                        <el-menu-item index="/loginOut">
                            <span slot="title">登出</span>
                        </el-menu-item>
                    </el-menu>
                </div>
            </el-col>
            <el-col :span='20'>
                <div class='part'>
                    <router-view/>
                </div>
            </el-col>
        </el-row>
    </div>
    
</template>
<script>
export default {
    data () {
        return {

        }
    },
    created () {

    },
    methods: {
        openHandle (key, keypath) {
            console.log(key)
            console.log(keypath)
        }
    }
    
}
</script>
<style scoped>
    .head{
        line-height: 100px;
        background: #1f2d3d;
        color: #fff;
        font-weight: 700;
        font-size: 24px;
        display: flex;
        justify-content: space-between;
    }
    .logo{
        width: 100px;
        height: 100px;
        border-radius: 50%;
        overflow: hidden;
        margin-left: 90px;
        margin-top: 0px;
    }
    .logo img{
        width: 100%;
        height: 100%
    }
    .name{
        width: 100%;
        text-align: center;
    }
    .part{
        margin: 20px 0 0 20px
    }
</style>

