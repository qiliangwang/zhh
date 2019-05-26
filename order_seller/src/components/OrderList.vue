<template>
    <div>
        <el-table :data='tableData' border :header-cell-style="{background: '#eee'}">
            <el-table-column align="center" prop='orderId' label='订单id'></el-table-column>
            <el-table-column align="center" prop='buyerName' label='姓名'></el-table-column>
            <el-table-column align="center" prop='buyerPhone' label='手机号'></el-table-column>
            <el-table-column align="center" prop='buyerAddress' label='地址'></el-table-column>
            <el-table-column align="center" prop='orderAmount' label='金额（元）'></el-table-column>
            <el-table-column align="center" prop='orderStatus' label='订单状态'>
                <template slot-scope="scope">
                    {{scope.row.orderStatus | orderStatusText}}
                </template>
            </el-table-column>
            <el-table-column align="center" prop='payStatus' label='支付状态'>
                <template slot-scope="scope">
                    {{scope.row.payStatus| payStatusText }}
                </template>
            </el-table-column>
            <el-table-column align="center" prop='updateTime' label='创建时间'></el-table-column>
            <el-table-column label='操作' align="center">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.orderStatus!==0" @click='orderDetail(scope.row)' type="text">详情</el-button>
                    <el-button v-if="scope.row.orderStatus === 0" @click='orderCheck(scope.row)' type="text">结算</el-button>
                    <el-button v-if="scope.row.orderStatus === 0" @click='orderCancel(scope.row)' type="text">取消</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class='page'>
            <el-pagination class="mt20" :current-page="pageIndex" :page-sizes="pageNum" :page-size="pageSize" layout="sizes,total, prev, pager, next, jumper" :total="totalCount" @current-change="changePage" @size-change="handleSizeChange"></el-pagination>
        </div>
        <!-- 订单详情 -->
        <el-dialog title="订单详情" :visible.sync="dialogVisible">
            <el-form>
                <el-row>
                    <el-col>
                        <el-form-item label="订单Id">
                            <span>{{details.orderId}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col>
                        <el-form-item label="订单状态">
                            <span>{{details.orderStatus | orderStatusText}}</span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="购买人姓名">
                            <span>{{details.buyerName}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col>
                        <el-form-item label="购买人联系方式">
                            <span>{{details.buyerPhone}}</span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="订单创建时间">
                            <span>{{details.createTime}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col>
                        <el-form-item label="支付状态">
                            <span>{{details.payStatus|payStatusText}}</span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <el-form-item label="订单所包含餐饮">
                            <span v-for="(item, i) in details.orderDetailList" :key='i'>{{item.productName}}    {{item.productPrice}}X{{item.productQuantity}}</span>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div>
                <el-button type='primary' @click='close'>确定</el-button>
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
export default {
    data () {
        return {
            tableData: [],
            pageIndex:1,
			pageSize:10,
			pageNum:[10,20,50],
			totalCount:0,
            dialogVisible: false,
            details: {}
        }
    },
    filters: {
        orderStatusText (val) {
            const obj = {
                '0': '新订单',
                '1': '已完结',
                '2': '已取消'
            }
            return obj[val]
        },
        payStatusText (val) {
            const obj = {
                '0': '待支付',
                '1': '支付完成'
            }
            return obj[val]
        }
    },
    created () {
        this.getOrderList()
    },
    mounted () {
        let that = this;
        var websocket = null;
        if('WebSocket' in window) {
            websocket = new WebSocket('ws://localhost:8000/sell/webSocket');
        }else {
            alert('该浏览器不支持websocket!');
        }
        websocket.onopen = function (event) {
            console.log('建立连接');
        }
        websocket.onclose = function (event) {
            console.log('连接关闭');
        }
        websocket.onmessage = function (event) {
            // 
            that.$confirm('您已有新的订单，是否查看？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                that.getOrderList()
            })
            // $('#myModal').modal('show');
            // document.getElementById('notice').play();
        }
        websocket.onerror = function () {
            alert('websocket通信发生错误！');
        }
        window.onbeforeunload = function () {
            websocket.close();
        }
    },
    methods: {
        handleSizeChange(pageSize){
			this.pageSize = pageSize;
			this.getOrderList()
		},
		//跳转某一页
		changePage(val){
			this.pageIndex = val;
			this.getOrderList()
		},
        getOrderList () {
            let url = 'sell/seller/order/list'
            this.$axios({
                method: 'get',
                params: {
                    size: this.pageSize,
                    page: this.pageIndex
                },
                url,
            }).then(res => {
                if(res) {
                    this.tableData = res.data.data.content
                    this.totalCount = res.data.data.totalElements
                }
            })
        },
        orderDetail (row) {
            let url = 'sell/seller/order/detail'
            this.$axios({
                method: 'get',
                params: {
                    orderId: row.orderId
                },
                url,
            }).then(res => {
                if(res) {
                    this.details = res.data.data
                    this.dialogVisible = true
                }
            })
        },
        orderCheck (row) {
            let url = 'sell/seller/order/finish'
            this.$confirm('是否结算该订单？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios({
                    method: 'get',
                    params: {
                        orderId: row.orderId
                    },
                    url,
                }).then(res => {
                    if(res) {
                         this.$message({
                            type: 'success',
                            message: '结算成功!'
                        });
                        this.getOrderList()
                    }
                })
            })
        },
        orderCancel (row) {
            let url = 'sell/seller/order/cancel'
            this.$confirm('是否取消该订单？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$axios({
                    method: 'get',
                    params: {
                        orderId: row.orderId
                    },
                    url,
                }).then(res => {
                    if(res) {
                         this.$message({
                            type: 'success',
                            message: '订单取消成功!'
                        });
                        this.getOrderList()
                    }
                })
            })
        },
        close () {
            this.dialogVisible = false;
        }
    }
}
</script>
