


let currentIntervalId = null;

class Room_class
{
    //获取信息信号量  为1标识获取成功
    constructor() {
        this.n_get_room_data = 0
    }


    //获取room_data
    async get_room_data()
    {
        let room_data = [
            {
                room_img_name:'login-bgc.jpg',room_name : '聊天9群',room_id : 1,
                room_detail : '这是一个好玩的群',vip:'0'
            }
        ]

        
        const service_url = `${window.service_url_ago}/Talk_room`
        console.log('1111111111');
        //向后端获取
        try {
            // 向后端发送请求并等待响应
            const response = await fetch(service_url, {
                method: "GET",
                credentials: "include",  
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            console.log('asdasd');
            
            // 检查响应是否成功
            if (response.ok) {
                // 获取并解析 JSON 数据
                const data = await response.json();
                room_data = data;  // 更新 room_data
                this.n_get_room_data = 1;  // 标记数据已成功获取
                console.log('Fetched data:', data);
            } else {
                console.error('Failed to fetch data');
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }

        return room_data;  
    }
    // 主函数
    async xuan_ran_room_main()
    {
        let room_data = await this.get_room_data()
        console.log('2222222222222');
        
        this.xuan_ran_room(room_data)
        //点击房间
        this.click_room()
        this.n_get_room_data = 0
    
    }

   //渲染
    xuan_ran_room(room_data)
    {
        const room_data_map = room_data.map(function(ele,index)
        {
            return `
            <li class="a-room-box" data-vip=${ele.vip}  data-room-id=${ele.room_id}>
                                    <div class="room-img ${ele.vip >= 5 ? 'vip'+ele.vip +'-halo':''}" style="background-image: url(/uploads/user_img/${ele.room_img_name});">
                                        <div class="vip${ele.vip}-display vip-display"  style="display: ${ele.vip != 0 ?'block' : 'none'};">
                                            ${ele.vip > 3 ? 'svip' + (ele.vip - 3) : 'vip' + ele.vip} 
                                        </div>
                                        
                                    </div>
                                    <div class="room-info">
                                        <div class="room-name vip-name vip${ele.vip}-name">
                                            ${ele.room_name}
                                            <div class="vip-logo vip${ele.vip}-logo" style="display: ${ele.vip != 0 ?'block' : 'none'};">
                                            </div>
                                        </div>
                                        <div class="room-detail">
                                            ${ele.room_detail}
                                        </div>
                                    </div>

                                </li>
            `
        })

        const room_big_box  = document.querySelector('.big-box .right-box .right-box-mid .right-box-mid-left .room-big-box')

        room_big_box.innerHTML = room_data_map.join('')
    }


    //点击 增加active类  其他去除
    add_active(active_index)
    {

        //
        console.log("进入调整active!");
        
        const room_all = document.querySelectorAll('.big-box .right-box .right-box-mid .right-box-mid-left .room-big-box .a-room-box')
        for(let i = 0 ; i < room_all.length ; i ++)
        {
            if(i == active_index)
                room_all[i].classList.add('active')
            else
                room_all[i].classList.remove('active')
        }
    }


    //点击房间
    click_room()
    {
        const room_all = document.querySelectorAll('.big-box .right-box .right-box-mid .right-box-mid-left .room-big-box .a-room-box')
        for(let i = 0 ; i < room_all.length ; i ++)
        {
            
            room_all[i].addEventListener('click',() =>
            {
                console.log('aaa');
                
                //消息列表获取
                const messageList=document.querySelector('.message-list');
                //清除消息列表内容
                messageList.innerHTML='';
                //清除已渲染id列表
                renderedMessageIds.clear();


                this.add_active(i)
            
                const chatName=document.querySelector('.chat-name');
                
                if(chatName)
                {
                    chatName.textContent=room_all[i].querySelector('.room-name').childNodes[0].nodeValue.trim();
                    
                }
                else
                {
                    console.log('none');
                }

                const chatBox=document.querySelector('.chat-box');
                chatBox.style.display='flex';

                const room_id = get_active_room_id()
                //聊天函数
                talk_function();
                
                if (currentIntervalId) {
                    clearInterval(currentIntervalId);
                    currentIntervalId = null;
                }
                currentIntervalId=setInterval(()=>get_talk_data(room_id),1000)
            });
        }
    }
}
const  room_class = new Room_class()
room_class.xuan_ran_room_main()
