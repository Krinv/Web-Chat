// 阻止默认表单提交行为
document.querySelector('.submit').addEventListener('click', async (event) => {
    event.preventDefault(); // 修正调用

    // 获取用户输入
    const userId = document.querySelector('.login-box .default-login .user-id').value.trim();
    const passWord = document.querySelector('.login-box .default-login .password').value.trim();

    // 构造请求数据对象
    const data = {
        user_id: userId,
        password: passWord
    };

    // 服务端 URL
    const login_service_url = `${window.service_url_ago}/Login_servlet`;

    console.log("开始发送");

    // 发送请求
    fetch(login_service_url, { // 修正 URL
        method: "POST", // 请求方法
        headers: {
            "Content-Type": "application/json", // 设置请求头格式
            
        },
        credentials: "include", // 包含凭证
        body: JSON.stringify(data) // 转为 JSON 格式发送
    })
    
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP 错误！状态: ${response.status}`);
        }
        return response.json(); // 解析响应为 JSON
    })
    .then(data => {
        console.log(data);
        console.log("发送完成，响应数据：", data);
        const response_str = data['response_str'];

        handleResponse(response_str);
    })
    .catch(error => {
        console.error("请求失败：", error);
    });
});

//登录成功处理
function loginSuccessful(){
    //跳转test.html
    location.href="聊天.html";
}

//密码错误处理
function passwordError(){
    let pwAlert=document.querySelector('.password-alert');
    console.log('aaa:'+pwAlert);
    pwAlert.innerText='密码错误';
    pwAlert.style.display='block';
}


//账号不存在处理
function IdMissing(){
    let acAlert=document.querySelector('.user-id-alert');
    console.log("账号不存在函数"+acAlert);
    acAlert.innerText='账号不存在';
    acAlert.style.display='block';
}



//response_str处理函数
function handleResponse(response_str){
    console.log('12eqwaw');
    switch(response_str){

        case "登录成功！":
            loginSuccessful();
            break;
        case "密码错误！":
            passwordError();
            break;
        case "账号不存在！":
            IdMissing();
            break;
    }
}