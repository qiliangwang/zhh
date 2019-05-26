<template>
    <div>
      <el-button type='primary' @click='loginOut'>登出</el-button>
    </div>
</template>
<script>
export default {
    data () {
        return {

        }
    },
    methods: {
        loginOut () {
            let url = 'sell/seller/logout'
            this.$axios({
                method: 'get',
                url,
            }).then(res => {
                if(res) {
                    this.$router.push('/login')
                }
            })
        }
    }
}
</script>

