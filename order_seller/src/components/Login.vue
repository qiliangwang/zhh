<template>
  <div class='container'>
    <div class='name'>餐厅管理系统</div>
    <div>
      <el-form :rules="rules" ref='loginFrom'  :model='loginInfo'>
        <el-row>
          <el-col :span='4'>
            <el-form-item label='用户名:' label-width='100px' prop='username'>
              <el-input class='wd80' size='small' v-model="loginInfo.username"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span='4'>
            <el-form-item label='密码' label-width='100px' prop='password'>
              <el-input class='wd80' v-model="loginInfo.password" size='small'></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-button type='primary' @click='login'>登入</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      loginInfo: {
        username: 'admin',
        password: '123456',
      },
      rules: {
        username: [{required: true,message: "请输入用户名", trigger: "change"}],
        password: [{required: true,message: "请输入密码", trigger: "change"}]
      }
    }
  },
  created () {
    
  },
  methods: {
    login () {
      this.$refs['loginFrom'].validate(valid => {
        if(valid) {
          let loginUrl = 'sell/seller/login'
          let data = {
            username: this.loginInfo.username,
            password: this.loginInfo.password
          }
          this.$axios({
            method: 'post',
            data: data,
            url: loginUrl,
            headers: {
              'Content-Type': 'application/json'
            }
          }).then(res => {
            if(res && res.data.code === 0) {
              this.$router.push('/orderList')
            } else {
              this.$message({
                message: res.data.msg,
                type: 'error'
              })
            }
          })
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .container {
    width: 350px;
    margin: 180px auto;
    box-shadow: 0 0 25px #cac6c6;
    border-radius: 5px;
    padding: 35px 35px 15px 35px;
    background: #ffffff;
  }
  .name{
    padding: 20px 0;
    font-weight: 700;
  }
  .wd80{
    width: 180px;
  }
</style>
