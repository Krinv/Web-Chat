document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('.register-box');

    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // 阻止表单默认提交行为

        // 获取输入框对象
        const user_id_input = document.querySelector('.user-id');
        const password_input = document.querySelector('.password');
        const again_password_input = document.querySelector('.again-password');

        // 获取输入值并去除首尾空格
        const user_id = user_id_input.value.trim();
        const password = password_input.value.trim();
        const again_password = again_password_input.value.trim();

        // 简单的表单验证
        if (!user_id || !password || !again_password) {
            alert("请填写所有必填字段。");
            return;
        }

        if (password !== again_password) {
            alert("两次输入的密码不一致。");
            return;
        }

        // 可以在这里添加更多的验证逻辑，如密码强度等

        const data = {
            user_id: user_id,
            password: password
        };
        console.log('发送的数据:', data);

        const service_url = `${window.service_url_ago}/Register_servlet`;

        try {
            const response = await fetch(service_url, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json", // 设置请求头格式
                },
                credentials: "include", // 包含凭证
                body: JSON.stringify(data) // 转为 JSON 格式发送
            });

            if (!response.ok) {
                throw new Error(`服务器返回错误: ${response.status}`);
            }

            const responseData = await response.json();
            console.log('服务器响应:', responseData);

            // 根据服务器返回的 'response_str' 判断是否注册成功
            if (responseData['response_str'] === "注册成功！") {
                
                const message=document.querySelector('.again-password-alert');
                message.textContent=responseData['response_str'];
                message.style.display='block';
                
                // 清除输入框内容
                user_id_input.value = "";
                password_input.value = "";
                again_password_input.value = "";

            } else {
                // 处理注册失败的情况
                const message=document.querySelector('.again-password-alert');
                message.textContent=responseData['response_str'];
                message.style.display='block';
            }
        } catch (error) {
            console.error('发生错误:', error);
            alert(`发生错误: ${error.message}`);
        }
    });
});
