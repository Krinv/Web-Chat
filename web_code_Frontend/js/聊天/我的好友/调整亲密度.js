function change_intimacy(currentFriend)
{
    // 按钮选择
const addButton = document.querySelector('.intimacy-add');
const minusButton = document.querySelector('.intimacy-minus');

console.log( "士大夫："+JSON.stringify(addButton) );

if (!addButton || !minusButton) {
    console.error("按钮元素未找到");
}

let currentIntimacy = currentFriend.intimacy;



    //发送消息函数
    const btn_all = document.querySelector('#friend-big-box > div.friend-info-box > div.friend-buttons-section > button.f-chat-button') 


    console.log("迪迦：：：：：：：：：：：" + JSON.stringify(btn_all));
    


    
        btn_all.addEventListener('click',function()
        {
            console.log("点击了666666666666::" + currentFriend.user_id);
            
            send_message(currentFriend.user_id)
        })
    

//active
function add_active(active_index)
{

    //
    console.log("进入调整active! ：" + JSON.stringify(active_index));
    
    const room_all = document.querySelectorAll('.big-box .right-box .right-box-mid .right-box-mid-left .room-big-box .a-room-box')
    for(let i = 0 ; i < room_all.length ; i ++)
    {
        if(room_all[i] == active_index)
            room_all[i].classList.add('active')
        else
            room_all[i].classList.remove('active')
    }
}
//发送消息函数
async function send_message(friend_id)
{
     const service_url = `${window.service_url_ago}/Talk_room`
     const data = {
        friend_user_id : friend_id
     }
    
     try{

        
        const response  =  await fetch(service_url,{
            method: "POST",
            credentials: "include",  
                headers: {
                    'Content-Type': 'application/json',
                },
                body:JSON.stringify(data)
        })

        if(response.ok)
        {
            const room_id = await response.text()
            console.log("按时到ii你：" + room_id);
            
            
                const active_index =document.querySelector(`.a-room-box[data-room-id="${room_id}"]`)
                console.log("active::::::::" + JSON.stringify(active_index));
                
                //跳转到消息界面
                display_section(document.querySelector('.left-box .message'))

                //指定 active
                const  room_class = new Room_class()
                room_class.xuan_ran_room_main()
                add_active(active_index)
       
            
        }


     }catch(error)
     {
        console.error("建立房间失败:", error);
     }
}



// 发送亲密度增减函数
async function sendIntimacyRequest(requestType) {
    
    
    
    const serviceFriendUrl = `${window.service_url_ago}/Friend_servlet?request_type=${requestType}&friend_user_id=${currentFriend.user_id}`;
    console.log('现有用户'+currentFriend.user_id);

    console.log('进入函数');

    const MIN_INTIMACY = 1;
    const MAX_INTIMACY = 10;

    if(requestType===0)
    {
        currentIntimacy--;
    }
    else if(requestType==1)
    {
        currentIntimacy++;
    }

    if ((requestType === 0 && currentIntimacy < MIN_INTIMACY) ||
        (requestType === 1 && currentIntimacy > MAX_INTIMACY)) {
        console.log("亲密度已达到边界，不进行操作");
        return;
    }

    try {
        const response = await fetch(serviceFriendUrl, {
            method: 'GET',
            credentials: "include",
            headers: {
                        "Content-Type": "application/json",
                    },
        });
        
        if (!response.ok) {
            throw new Error(`网络响应错误 ${response.status}`);
        }
        else
        {
            const f_intro_intimacy=document.querySelector('.introduction-intimacy');
            f_intro_intimacy.textContent=currentIntimacy;
            console.log("亲密度修改成功");
        }

    } catch (error) {
        console.error("操作失败", error);
        // 可以在页面上显示错误提示
    }
}

// 事件监听
addButton.addEventListener('click', () => {
    console.log('加号亲密度');
    sendIntimacyRequest(1);
});

minusButton.addEventListener('click', () => {
    console.log('减号亲密度');
    sendIntimacyRequest(0);
});

}

