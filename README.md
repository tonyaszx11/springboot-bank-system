# springboot-bank-system
用springboot製作的一個簡單的銀行管理系統，提供用戶轉帳與存提款的服務

## 使用技術
* 前端
  - HTML
  - CSS
  - Javascript
  - Vue
  - Ajax(Axios套件)

* 後端 
  - Java 
  - Springboot
  - Mysql
  - Mybatis

## 系統功能
* User模塊
  - 會員登入與註冊
  - 查詢與修改個人資料

* Account模塊
  - 申請銀行帳戶
  - 管理員審核帳戶申請
  - 查詢銀行帳戶與交易紀錄
  - 轉帳
  - 存款
  - 提款
  - 紀錄交易

## 系統介紹與展示

### 會員登入
客戶與管理員輸入帳號與密碼來進行登入，登入後會判斷用戶是客戶或管理員來引導到相對應的主頁面

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/61350164-38f1-4fd9-b9ad-d439deb62fb3)

### 註冊
會員註冊，每個輸入框都有做格式校驗

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/4fb2b1a9-1af8-47ee-ab17-b5aed7c17117)

### 主頁面(客戶)

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/e75b6abf-310f-42f7-b4cc-2b0e60d5c3ae)

### 主頁面(管理員)

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/4fbc2549-b3b7-4de8-bb6d-3cf2ea639852)

### 查詢與修改個人資料
進到頁面後就會自動查詢個人資料並顯示在頁面上
如果要修改資料就點擊編輯按鈕(藍色鉛筆按鈕)，接著會跳出一個表單，會先自動將當前個人資料填入框中，使用者再對想修改的地方進行修改

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/2cb69333-75b7-4913-b12d-e432c9d56838)

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/46f6eea8-0eac-40c4-b493-8b064c0186cc)

### 申請帳戶
在主頁點擊申請帳戶按鈕，就可以進行帳戶申請，如果已申請帳戶且在審核中，則不能再申請，必須等到前一次申請審核結束後才能再申請下一個帳戶

![帳戶申請成功 ](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/81e62dec-634f-4bb5-9937-f897e28c7c79)

![帳戶申請失敗](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/33ba5818-0d2a-4b4e-ae05-821503cd8ed5)

### 查詢帳戶與交易紀錄
進入頁面後下拉式選單會把用戶所擁有的帳戶顯示出來，用戶選取要查詢的帳戶後，就會顯示該帳戶的訊息與交易的歷史紀錄

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/c1e20614-53e7-49a2-a443-52ccd9b96a9c)

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/60dadc0e-8876-433d-83df-4e7ceb458cff)

### 轉帳
進入頁面後下拉式選單會將用戶所擁有的帳戶顯示出來，用戶選取欲轉帳之帳戶，會顯示帳戶的餘額，接著再填寫對方帳戶名與金額，點選轉帳按鈕後，即可完成轉帳操作

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/b7c6a8df-04e3-4fd1-a105-d0d0c788e7e9)


### 存款
進入頁面後下拉式選單會將用戶所擁有的帳戶顯示出來，用戶選取欲存款之帳戶，會顯示帳戶的餘額，接著再填寫金額，點選存款按鈕後，即可完成存款操作

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/0e9d0d29-64e3-46f5-85b0-36a8fcba0a06)


### 提款
進入頁面後下拉式選單會將用戶所擁有的帳戶顯示出來，用戶選取欲提款之帳戶，會顯示帳戶的餘額，接著再填寫金額，點選提款按鈕後，即可完成提款操作

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/4770603c-3432-498b-a3de-985e2fcb9faa)


### 管理員審核帳戶
進入頁面後，會自動查出目前所有待審核的帳戶，點擊審核通過或拒絕按鈕，來完成審核帳戶的操作

![image](https://github.com/tonyaszx11/springboot-bank-system/assets/135413819/bfa87d2d-0dda-462f-b3f2-e15799bd8ae5)

## 執行與運行系統
1. 電腦需安裝Java與Mysql
2. clone或下載此專案
3. 到target目錄下打開application.yml設置資料庫的登入資訊(url, username, password)與server port(預設為8080)，如下範例  
注意yml格式，:號後面需加一個空格再填入資訊
```
server:
  port: 

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/bank
    username: root
    password: 123456
```
5. 在target目錄打開cmd，執行
```
java -jar
```

  

