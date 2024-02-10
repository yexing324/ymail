const {defineConfig} = require('@vue/cli-service')

module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: true,
    devServer: {
        client: {
            overlay: false
        },
        proxy: {
            '/api': {
                target: 'http://127.0.0.1',
                changeOrigin: true,
                logLevel: 'debug',
                pathRewrite: {
                    '^/api': '/ymail'
                }
            },
            "/core":{
                target: 'http://127.0.0.1:81',
                changeOrigin: true,
                logLevel: 'debug',
                pathRewrite: {
                    '^/core': '/ymail'
                }
            }
        }
    }
})