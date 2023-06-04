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

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/login.png)

### 註冊
會員註冊，每個輸入框都有做格式校驗

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/register.png)

### 主頁面(客戶)

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/main.png)

### 主頁面(管理員)

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/adminMain.png)

### 查詢與修改個人資料
進到頁面後就會自動查詢個人資料並顯示在頁面上
如果要修改資料就點擊編輯按鈕(藍色鉛筆按鈕)，接著會跳出一個表單，會先自動將當前個人資料填入框中，使用者再對想修改的地方進行修改

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/userInfo.png)

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/updateUserInfo.png)

### 申請帳戶
在主頁點擊申請帳戶按鈕，就可以進行帳戶申請，如果已申請帳戶且在審核中，則不能再申請，必須等到前一次申請審核結束後才能再申請下一個帳戶

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/applyAccountSuccess.png)

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/applyAccountFail.png)

### 查詢帳戶與交易紀錄
進入頁面後下拉式選單會把用戶所擁有的帳戶顯示出來，用戶選取要查詢的帳戶後，就會顯示該帳戶的訊息與交易的歷史紀錄

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/accountInfo1.png)

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/accountInfo2.png)

### 轉帳
進入頁面後下拉式選單會將用戶所擁有的帳戶顯示出來，用戶選取欲轉帳之帳戶，會顯示帳戶的餘額，接著再填寫對方帳戶名與金額，點選轉帳按鈕後，即可完成轉帳操作

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/transfer.png)


### 存款
進入頁面後下拉式選單會將用戶所擁有的帳戶顯示出來，用戶選取欲存款之帳戶，會顯示帳戶的餘額，接著再填寫金額，點選存款按鈕後，即可完成存款操作

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/deposit.png)


### 提款
進入頁面後下拉式選單會將用戶所擁有的帳戶顯示出來，用戶選取欲提款之帳戶，會顯示帳戶的餘額，接著再填寫金額，點選提款按鈕後，即可完成提款操作

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/withdraw.png)


### 管理員審核帳戶
進入頁面後，會自動查出目前所有待審核的帳戶，點擊審核通過或拒絕按鈕，來完成審核帳戶的操作

![image](https://github.com/tonyaszx11/springboot-bank-system/blob/master/img/reviewAccount.png)

## 啟動系統
1. 電腦需安裝Java與Mysql
2. clone此專案
3. 打開mysql執行bank-system.sql，來創建表與加入測試資料
4. 到專案根目錄打開cmd，執行以下代碼
```
mvnw clean package
```
5. 到target目錄下建立一個application.yml檔案，並輸入以下代碼來設置資料庫的登入資訊(url, username, password)與server port(預設為8080)，如下範例  
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
5. 在target目錄打開cmd，執行以下代碼，伺服器就啟動完成了
```
java -jar bank_system-1.jar
```
  

