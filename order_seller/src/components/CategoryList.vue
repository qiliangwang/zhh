<template>
    <div>
        <el-table :data='tableData' border :header-cell-style="{background: '#eee'}">
            <el-table-column align="center" prop='categoryId' label='类目id'></el-table-column>
            <el-table-column align="center" prop='categoryName' label='名称'></el-table-column>
            <el-table-column align="center" prop='categoryType' label='type'></el-table-column>
            <el-table-column align="center" prop='createTime' label='创建时间'></el-table-column>
            <el-table-column align="center" prop='updateTime' label='修改时间'></el-table-column>
            <el-table-column align="center" label='操作'>
                <template slot-scope="scope">
                    <el-button @click='modify(scope.row, 1)' type="text">修改</el-button>
                    <el-button @click='modify(scope.row, 2)' type="text">查看</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 类目修改弹窗 -->
        <el-dialog title="类目详情" :visible.sync="dialogVisible">
            <el-form :model="category" ref='categoryForm' :rules='rules'>
                <el-row>
                    <el-col>
                        <el-form-item label="类目名称：" label-width="120px">
                            <el-input :disabled="type==2" v-model="category.categoryName"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="类目Type：" label-width="120px">
                            <el-input :disabled="type==2" v-model="category.categoryType"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div>
                <el-button type='primary' @click='submit'>保存</el-button>
            </div>
        </el-dialog>
        
    </div>
</template>
<style scoped>
    .page{
        padding: 20px 0;
        text-align: right;
    }
</style>

<script>
import qs from 'qs'
export default {
    data () {
        return {
            tableData: [],
            category: {
                name: '',
                type: ''
            },
            type: 0,
            dialogVisible: false,
            rules: {
                name: [{required: true,message: "请输入名称", trigger: "change"}],
                type: [{required: true,message: "请输入Type", trigger: "change"}],
            }
        }
    },
    created () {
        this.getCategoryList()
    },
    methods: {
        getCategoryList () {
            let url = 'sell/seller/category/list'
            this.$axios({
                method: 'get',
                params: {
                    size: this.pageSize,
                    page: this.pageIndex
                },
                url,
            }).then(res => {
                if(res) {
                   res.data.data.forEach(item => {
                        item.createTime = new Date(item.createTime).toLocaleString()
                        item.updateTime = new Date(item.updateTime).toLocaleString()
                    })
                    this.tableData = res.data.data
                }
            })
        },
        modify(row, type) {
            this.category = {
                categoryName: row.categoryName,
                categoryType: row.categoryType,
                categoryId: row.categoryId
                
            }
            this.type = type
            this.dialogVisible = true
        },
        submit() {
            if(this.type == 2) {
                this.dialogVisible = false
                return;
            }
            let url = 'sell/seller/category/save'
            this.$axios({
                method: 'post',
                data: qs.stringify(this.category),
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
                    this.getCategoryList()
                    this.dialogVisible = false;
                }
            })
        }
      
    }
}
</script>


