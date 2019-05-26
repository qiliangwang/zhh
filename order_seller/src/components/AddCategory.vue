<template>
    <div class='container'>
        <el-form :rules="rules" :model='categoryInfo' ref='categoryForm'>
            <el-row>
                <el-col :span='4'>
                    <el-form-item label='名字' prop="categoryName" label-width="120px">
                        <el-input class='wd80' v-model="categoryInfo.categoryName" size='small'></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span='4'>
                    <el-form-item label='type' prop="categoryType" label-width="120px">
                        <el-input class='wd80' size='small' v-model="categoryInfo.categoryType"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div>
            <el-button type='primary' @click='submit'>保存</el-button>
        </div>
    </div>
</template>
<style scoped>
    .container{
        width: 350px;
        margin: 80px auto;
        box-shadow: 0 0 25px #cac6c6;
        border-radius: 5px;
        padding: 35px 35px 15px 35px;
        background: #ffffff;
    }
    .wd80{
        width: 180px;
    }
</style>

<script>
import qs from 'qs'
export default {
    data () {
        return {
            categoryInfo: {
                categoryName: '',
                categoryType: ''
            },
            rules: {
                categoryName: [{required: true,message: "请输入类目名称", trigger: "change"}],
                categoryType: [{required: true,message: "请输入类目type", trigger: "change"}]
            }
        }
    },
    methods: {
        submit () {
            let url = 'sell/seller/category/save'
            this.$axios({
                method: 'POST',
                data: qs.stringify(this.categoryInfo),
                url,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }) .then(res => {
                if(res) {
                    this.$message({
                        message: '成功新增一条类目',
                        type: 'success'
                    })
                }
            })
        }
    }
}
</script>

