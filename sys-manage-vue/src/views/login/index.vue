<template>
  <div class="page-login">
    <!-- 顶部logo -->
    <div class="logo">
      <a href="https://vitejs.dev" target="_blank">
        <img src="/vite.svg" class="logo" alt="Vite logo" />
      </a>
    </div>
    <div class="page-login--wrapper">
      <div class="page-login--content">
        <el-card v-loading="loading" shadow="never" class="login-form">
          <div class="login-title">
            <p>{{ VITE_APP_TITLE }}</p>
            <p>{{ VITE_APP_VERSION }}</p>
          </div>
          <div class="login-header">
            <div class="title">账号登录</div>
          </div>
          <div class="login-header-bottom" />
          <el-form
            ref="loginFormRef"
            :model="loginFormData"
            :rules="commonFormRules"
            label-position="top"
            @keyup.enter="handleLogin"
          >
            <el-form-item prop="account" :rules="commonFormRules.required">
              <el-input placeholder="请输入登录账号" v-model.trim="loginFormData.account" :prefix-icon="User" />
            </el-form-item>
            <el-form-item prop="pwd" :rules="commonFormRules.required">
              <el-input
                placeholder="请输入登录密码"
                v-model="loginFormData.pwd"
                :prefix-icon="Lock"
                show-password
              />
            </el-form-item>
            <el-button class="button-login" :disabled="loading" type="primary" @click="handleLogin">登 录</el-button>
          </el-form>
        </el-card>
      </div>
      <!-- 底部版权信息 -->
      <div class="copyright">
        <span>Copyright</span>
        <span>2024</span>
        <span>HaoHaoDaYouXi</span>
        <span>技术支持：2601183227@qq.com</span>
        <span>
          <a target="_blank" href="http://beian.miit.gov.cn/">备案号：xxxxxxx</a>
        </span>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue"
import { useRouter } from "vue-router"
import { type FormInstance } from "element-plus"
import { User, Lock } from "@element-plus/icons-vue"
import { useUserStore } from "@/store/modules/user"
import { type LoginReq } from "@/api/login/types/login"
import commonFormRules from "@/utils/rules"

const VITE_APP_TITLE = import.meta.env.VITE_APP_TITLE
const VITE_APP_VERSION = import.meta.env.VITE_APP_VERSION
const router = useRouter()

/** 登录表单元素的引用 */
const loginFormRef = ref<FormInstance | null>(null)
/** 登录按钮 Loading */
const loading = ref(false)
/** 登录表单数据 */
const loginFormData: LoginReq = reactive({
  account: "admin",
  pwd: "123456"
})

/** 登录逻辑 */
const handleLogin = () => {
  loginFormRef.value?.validate((valid: boolean, fields) => {
    if (valid) {
      loading.value = true
      useUserStore()
        .login(loginFormData)
        .then(() => {
          router.push("/")
        })
        .catch(() => {
          loginFormData.pwd = ""
        })
        .finally(() => {
          loading.value = false
        })
    } else {
      console.error("表单校验不通过", fields)
    }
  })
}
</script>
<style lang="scss" scoped>
.logo {
  position: absolute;
  left: 1.6%;
  top: 14px;
  z-index: 2;

  img {
    display: block;
    width: 226px;
    height: 56px;
    object-fit: contain;
    cursor: pointer;
  }
}

.page-login {
  user-select: none;
  background-color: #fff;

  .page-login--wrapper {
    position: absolute;
    top: 0;
    right: 0;
    left: 0;
    bottom: 0;
    z-index: 1;
    min-height: 683px;
    background-image: url("./img/bg.png");
    background-position: center center;
    background-size: cover;
    background-repeat: no-repeat;

    .page-login--content {
      position: absolute;
      top: 50%;
      right: 13.64%;
      z-index: 1;
      transform: translateY(calc(-50% - 22px));

      .el-card {
        width: 446px;
        min-height: 530px;
        border-radius: 6px;
        padding: 0;
        --el-card-padding: 0;
      }
    }

    .login-form {
      .login-title {
        padding: 30px 0 20px;
        text-align: center;
        background: #f6faff;

        p:nth-of-type(1) {
          line-height: 1;
          font-size: 24px;
          color: #38393d;
        }

        p:nth-of-type(2) {
          margin-top: 4px;
          font-size: 14px;
          color: #a2a5ac;
          line-height: 24px;
        }
      }

      .login-header {
        display: flex;
        justify-content: space-between;
        vertical-align: bottom;
        font-family:
          PingFang SC-Medium,
          PingFang SC;
        font-weight: 400;
        font-size: 16px;
        color: #a2a5ac;
        margin-bottom: 3px;
        padding: 48px 48px 0;

        .title {
          font-size: 22px;
          color: #2f2f2f;
        }
      }

      .login-header-bottom {
        width: 40px;
        height: 5px;
        background: linear-gradient(90deg, #5ca5fd 11%, #266dff 100%);
        border-radius: 4px;
        opacity: 1;
        margin-bottom: 20px;
        margin-left: 48px;
      }

      .el-form {
        padding: 0 48px;

        .el-input--prefix {
          font-size: 16px;
          font-family:
            PingFang SC-Medium,
            PingFang SC;
          font-weight: 400;
          color: #c0c4cc;
          line-height: 48px;
          --el-input-height: 48px;
        }

        .el-button {
          padding: 17px 15px;
          font-size: 20px;
          font-family:
            PingFang SC-Medium,
            PingFang SC;
          font-weight: 400;
          background-color: #2975f9;
          border-color: #2975f9;
        }

        .button-login {
          width: 100%;
          height: 55px;
          margin-top: 15px;
        }
      }
    }
  }
}

.copyright {
  position: absolute;
  left: 50%;
  bottom: 0;
  transform: translateX(-50%);
  z-index: 2;
  padding: 12px 0 11px;
  font-size: 0;
  word-break: break-all;
  text-align: center;

  span {
    margin-left: 10px;
    font-size: 14px;
    color: #9e9e9e;
    white-space: nowrap;

    &:nth-of-type(1) {
      margin-left: 0;
    }
  }

  .item-privacyPolicy {
    cursor: pointer;

    &:hover {
      text-decoration: underline;
      color: #5ca5fd;
    }
  }
}
</style>
