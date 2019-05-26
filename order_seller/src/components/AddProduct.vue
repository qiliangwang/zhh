<template>
    <div class='container'>
        <el-form :model="productInfo" ref='productForm' :rules='rules'>
            <el-row>
                <el-col>
                    <el-form-item label="名称" label-width="120px" prop="productName">
                        <el-input v-model="productInfo.productName" class='wd200' size='small'></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item label="价格（元）" label-width="120px" prop="productPrice">
                        <el-input v-model="productInfo.productPrice" class='wd200' size='small'></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item label="库存" label-width="120px" prop="productStock">
                        <el-input v-model="productInfo.productStock" class='wd200' size='small'></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item label="描述" label-width="120px" prop="productDescription">
                        <el-input  v-model="productInfo.productDescription" class='wd200' size='small'></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item label="图片地址" label-width="120px" prop="productIcon">
                        <el-input v-model="productInfo.productIcon" class='wd200' size='small'></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col>
                    <el-form-item label="类目" label-width="120px" prop="categoryType">
                        <el-input v-model="productInfo.categoryType" class='wd200' size='small'></el-input>
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
    .wd200{
        width: 500px;
    }
    .container{
        width: 680px;
        margin: 40px auto;
        box-shadow: 0 0 25px #cac6c6;
        border-radius: 5px;
        padding: 35px 35px 15px 35px;
        background: #ffffff;
    }
</style>

<script>
import qs from 'qs'
export default {
    data () {
        return {
            productInfo: {
                productName: '',
                productPrice: '',
                productIcon: '',
                productDescription: '',
                productStock: '',
                categoryType: ''
            },
            rules: {
                productName: [{required: true,message: "请输入名称", trigger: "change"}],
                productPrice: [{required: true,message: "请输入价格", trigger: "change"}],
                productIcon: [{required: true,message: "请输入图片地址", trigger: "change"}],
                productDescription: [{required: true,message: "请输入描述", trigger: "change"}],
                productStock: [{required: true,message: "请输入库存", trigger: "change"}],
                categoryType: [{required: true,message: "请输入类目", trigger: "change"}]
            }
        }
    },
    methods: {
        submit () {
            this.$refs['productForm'].validate(valid => {
                if(valid) {
                    let url = 'sell/seller/product/save'
                    this.$axios({
                        method: 'POST',
                        data: qs.stringify(this.productInfo),
                        url,
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }).then(res => {
                        if(res) {
                            this.$message({
                                message: '成功新增一条商品',
                                type: 'success'
                            })
                            // this.productInfo = {
                            //     productName: '',
                            //     productPrice: '',
                            //     productIcon: '',
                            //     productDescription: '',
                            //     productStock: '',
                            //     categoryType: ''
                            // }
                        }
                    })
                }
            })
        }
    }
}
</script>

