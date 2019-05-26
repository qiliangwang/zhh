<template>
    <div>
        <el-table :data='tableData' border :header-cell-style="{background: '#eee'}">
            <el-table-column align="center" prop='productId' label='商品id'></el-table-column>
            <el-table-column align="center" prop='productName' label='名称'></el-table-column>
            <el-table-column align="center" prop='productIcon' label='图片'>
                <template slot-scope="scope">
                    <div class='icon'>
                        <img :src="scope.row.productIcon"/>
                    </div>
                </template>
            </el-table-column>
            <el-table-column align="center" prop='productPrice' label='单价（元）'></el-table-column>
            <el-table-column align="center" prop='productStock' label='库存'></el-table-column>
            <el-table-column align="center" prop='productDescription' label='描述'></el-table-column>
            <el-table-column align="center" prop='categoryType' label='类目'>
                <template slot-scope="scope">
                    {{scope.row.categoryType | categoryTypeText}}
                </template>
            </el-table-column>
            <el-table-column align="center" prop='createTime' label='创建时间'></el-table-column>
            <el-table-column align="center" prop='updateTime' label='修改时间'></el-table-column>
            <el-table-column align="center" label='操作'>
                <template slot-scope="scope">
                    <el-button @click='modify(scope.row)' type="text">修改</el-button>
                    <el-button v-if="scope.row.productStatus === 0" @click='offSale(scope.row)' type="text">下架</el-button>
                    <el-button v-if="scope.row.productStatus !==0" @click='onSale(scope.row)' type="text">上架</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class='page'>
            <el-pagination class="mt20" :current-page="pageIndex" :page-sizes="pageNum" :page-size="pageSize" layout="sizes,total, prev, pager, next, jumper" :total="totalCount" @current-change="changePage" @size-change="handleSizeChange"></el-pagination>
        </div>
        <!-- 商品修改弹框 -->
        <el-dialog title="商品详情" :visible.sync="dialogVisible">
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
        </el-dialog>
    </div>
</template>
<style scoped>
    .page{
        padding: 20px 0;
        text-align: right;
    }
    .icon {
        width: 100px;
        height: 100px;
    }
    .icon img{
        width: 100%;
        height: 100%;
    }
</style>

<script>
import qs from 'qs'
export default {
    data () {
        return {
            tableData: [],
            pageIndex:1,
			pageSize:10,
			pageNum:[10,20,50],
            totalCount:0,
            dialogVisible: false,
            productInfo: {
                productId: '',
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
    created () {
        this.getProductList()
    },
    filters: {
      categoryTypeText (val) {
          let obj = {

          }
          return val
      }  
    },
    methods: {
        handleSizeChange(pageSize){
			this.pageSize = pageSize;
			this.getProductList()
		},
		//跳转某一页
		changePage(val){
			this.pageIndex = val;
			this.getProductList()
		},
        getProductList () {
            let url = 'sell/seller/product/list'
            this.$axios({
                method: 'get',
                params: {
                    page: this.pageIndex,
                    size: this.pageSize
                },
                url,
            }).then(res => {
                if(res) {
                    this.tableData = res.data.data.content;
                    this.totalCount = res.data.data.totalElements
                    this.tableData.forEach(item => {
                        item.createTime = new Date(item.createTime).toLocaleString()
                        item.updateTime = new Date(item.updateTime).toLocaleString()
                    })
                }
            })
        },
        modify (row) {
            let url = 'sell/seller/product/detail'
            this.$axios({
                method: 'get',
                params: {
                    productId: row.productId
                },
                url,
            }).then(res => {
                let data = res.data.data
                this.productInfo = {
                    productId: data.productId,
                    productName: data.productName,
                    productPrice: data.productPrice,
                    productIcon: data.productIcon,
                    productDescription: data.productDescription,
                    productStock: data.productStock,
                    categoryType: data.categoryType
                }
                this.dialogVisible = true
            })
        }, 
        submit () {
            let url = 'sell/seller/product/save'

            this.$axios({
                method: 'post',
                data: qs.stringify(this.productInfo),
                url,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(res => {
                if(res) {
                    this.$message({
                        message: '修改成功',
                        type: 'success'
                    })
                    this.getProductList()
                    this.dialogVisible = false;
                }
            })
        },
        onSale(row) {
            let url = 'sell/seller/product/on_sale'
            this.$confirm('是否上架该商品？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios({
                    method: 'get',
                    params: {
                        productId: row.productId
                    },
                    url,
                }).then(res => {
                    if(res) {
                         this.$message({
                            type: 'success',
                            message: '上架成功!'
                        });
                        this.getProductList()
                    }
                })
            })
        },
        offSale (row) {
            let url = 'sell/seller/product/off_sale'
            this.$confirm('是否下架该商品？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios({
                    method: 'get',
                    params: {
                        productId: row.productId
                    },
                    url,
                }).then(res => {
                    if(res) {
                         this.$message({
                            type: 'success',
                            message: '下架成功!'
                        });
                        this.getProductList()
                    }

                })
            })
        },
    }
      
}
</script>
