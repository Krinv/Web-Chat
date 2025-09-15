// 进行登录判断  判断源是什么
function judge_ip()
{
    let currentUrl = window.location.href; // 获取当前浏览器地址栏中的完整 URL
    let targetUrl = '';  // 用于存储最终的目标 URL
    
    // 正则表达式匹配
    let ipRegex1 = /^http:\/\/192\.168\.153\.1:8080/;   // 匹配 http://192.168.153.1:8080
    let ipRegex2 = /^http:\/\/localhost:8080/;           // 匹配 http://localhost:8080
    let ipRegex3 = /^http:\/\/10\.215\.0\.113:8080/;     // 匹配 http://10.215.0.113:8080
    
    // 判断地址栏中的 URL 是否匹配正则
    if (ipRegex1.test(currentUrl)) {
      targetUrl = 'http://192.168.153.1:8080';
    } else if (ipRegex2.test(currentUrl)) {
        targetUrl = 'http://localhost:8080';
    } else if (ipRegex3.test(currentUrl)) {
        targetUrl = 'http://10.215.0.113:8080';
    } else {
        targetUrl = 'http://localhost:8080';// 默认情况下直接使用当前 URL
    }
    
    console.log("浏览器访问URL为:"+targetUrl);  // 打印目标 URL
    return targetUrl
}
window.service_url_ago =  judge_ip()