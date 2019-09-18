<template>
  <div id="app">
    <img src="./assets/logo.png">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted () {
    this.checkLoginStatus()
  },
  updated: function () {
    this.checkLoginStatus()
  },
  methods: {
    checkLoginStatus: function () {
      const loginFlag = sessionStorage.getItem('loginFlag')
      console.info(loginFlag)
      if (!loginFlag) {
        localStorage.removeItem('vue-core-security')
        localStorage.removeItem('USER')
        localStorage.removeItem('token')
        if (this.$router.currentRoute.name !== 'Login') {
          this.$router.push('/')
        }
      }
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
