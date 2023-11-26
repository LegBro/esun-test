# 玉山銀行Java工程師工程師甄選
日期:11/26

前端：esun-test-vue

後端：esun-test-spring-boot

## 前端Vue.js
專案結構
* views：頁面
* layouts：給頁面套用的佈局元件
* components：頁面中會用到的UI組件
* assets：全域css(scss)檔案
* types：本地端使用的資料模型(domain model)
* dto：伺服器端傳來的資料模型(data model)
* services：API管理

撰寫風格
* Composition API
* script setup

使用套件
* scss (預處理器)
* typescript (強型別)
* vue-router (路由控制)
* axios (Restful API)

## 後端Spring boot
基礎設置
* MySQL (port:3306)
* server application (port:8080)

專案結構
* DB：資料庫匯出之sql檔，供閱覽者匯入
* constants：共用層，放置一些字串常量
* controllers：表達層，接收與回應request
* sevices：業務邏輯層，處理repository從資料庫取回的資料進行處理並輸出。
* repositories：資料存取層，和資料庫進行互動。
* entities：資料庫輸出的對應資料模型。
* fromJson：由controllers自request接收到的資料型態。

提醒
* 為了方便提供閱覽者測試，resources > application.properties中已將使用者設定為root
