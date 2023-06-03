// 在後端有設置攔截器，如果沒有在session內找到userId，表示未登入
// 然後後端會發回應讓前端redirect到http://localhost:8080/ (index.html再redirect到登入頁面)
// 則axios的回應資料的responseURL會為登入頁面的url(userLogin.html)
// 可是axios沒辦法自動實施redirect
// 因此讓他可以redirect的方法就是比較responseURL是否等於登入頁面的url(http://localhost:/)
// 參數respUrl為axios回應的responseURL

function checkLogin(respUrl){
    let indexUrl = location.origin + '/'; //登入頁面url
    if(respUrl == indexUrl){
        alert("尚未登入，請登入後再進行操作")
        location.href = indexUrl;
    }
}