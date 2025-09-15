function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}


function talk_function() {
    console.log("被点击的id:" + get_active_room_id());

    // 限制字数和统计剩余字数
    const textarea = document.querySelector(".message-input");
    const charCount = document.querySelector(".char-count");

    textarea.addEventListener('input', () => {
        const currentLength = textarea.value.length;
        charCount.textContent = `${currentLength}/1000`;
    });

    // 实现聊天消息显示内容渲染
    const sendButton = document.querySelector('.send-button');
    const messageInput = document.querySelector('.message-input');
    const messageList = document.querySelector('.message-list');

    // 按下按钮发送
    sendButton.addEventListener('click', async () => { // 将回调函数声明为 async
        const messageContent = messageInput.value.trim(); // 获取输入框内容

        if (messageContent) {
            // 禁用发送按钮以防止重复发送
            sendButton.disabled = true;

            // 新消息列表项
            const newMessage = document.createElement('li');
            newMessage.classList.add('message-mine');

            // 新我的消息容器
            const messageMineContent = document.createElement('div');
            messageMineContent.classList.add('message-mine-content');

            // 新消息内容
            const messageText = document.createElement('p');
            messageText.classList.add('m-content');
            messageText.textContent = messageContent;

            // 新消息时间
            const messageTime = document.createElement('p');
            messageTime.classList.add('message-mine-time');
            const currentTime = new Date().toLocaleString('zh-CN', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit'
            });
            messageTime.textContent = currentTime;

            // 我的头像
            const userAvatar = document.createElement('img');
            userAvatar.classList.add('message-avatar');

            console.log('avatar'+accountAvatar);
            userAvatar.src = accountAvatar; // 替换为实际头像路径

            // 加载动图
            const loadingWebp = document.createElement('img');
            loadingWebp.classList.add('loading');
            loadingWebp.src = 'img/错误.png';

            // 组装
            messageMineContent.appendChild(messageText);
            messageMineContent.appendChild(messageTime);
            messageMineContent.appendChild(loadingWebp);
            newMessage.appendChild(messageMineContent);
            newMessage.appendChild(userAvatar);

            // 清空输入框
            messageInput.value = '';

            // 清空限制字数统计
            charCount.textContent = `0/1000`;

            // 滚动到最新消息
            // 使用 setTimeout 确保浏览器完成渲染
            setTimeout(() => {
                newMessage.scrollIntoView({ behavior: 'smooth',block:'end'});
            }, 0);

            // 发送数据组装
            const data = {
                talk_content: messageContent,
                room_id: get_active_room_id()
            };

            try {
                const response = await fetch(`${window.service_url_ago}/Talk_message`, {
                    method: 'POST',
                    credentials: "include",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    throw new Error(`服务器响应状态码：${response.status}`);
                }

                const result = await response.text();
                console.log('消息发送成功:', result);

                await delay(1000);
                loadingWebp.style.display = 'none';

                if(lastMyloadingImg)
                    {
                        lastMyloadingImg.style.display='block';
                
                        setTimeout(()=>{
                            lastMyloadingImg.style.display='none';
                        },1000);
                    }

            }
            catch (error) {
                console.error('发送消息时出错:', error);
                messageList.appendChild(newMessage);
                loadingWebp.src='img/loding.webp';

                await delay(3000);
                // 替换加载图标为错误图标
                loadingWebp.src = 'img/错误.png'; 
            }
            finally {
                // 重新启用发送按钮
                sendButton.disabled = false;
            }
        }
    });

    // 按下 Enter 键发送
    messageInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            sendButton.click();
        }
    });

}

// 获取房间id
function get_active_room_id() {
    const active_room = document.querySelector('.big-box .right-box .right-box-mid .right-box-mid-left .room-big-box .active')
    const room_id = active_room.dataset.roomId
    console.log("name:" + active_room.className);
    return room_id;
}




