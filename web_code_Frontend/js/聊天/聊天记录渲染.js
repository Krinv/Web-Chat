// 接收后端的聊天记录进行渲染

let lastMyloadingImg=null;


//已渲染数据列表
let renderedMessageIds=new Set();

//当前账户头像
let accountAvatar;

//调用
async function a_get_user_id()
{
    await get_user_info_class.init_class()
    const user_id = get_user_info_class.get_user_id()
    console.log('user_id:' + user_id
    );
    
    return user_id
}


async function get_talk_data(room_id)
{

 
    
    const service_url = `${window.service_url_ago}/Talk_message?room_id=${room_id}`

    try{
        const response = await fetch(service_url,{
            method: "GET",
            credentials: "include",  
            headers: {
                'Content-Type': 'application/json',
            },
        });


        if(response.ok)
        {
            const data = await response.json()
            
            chatHistory(data)

        }
        else
            console.log("失败获取聊天信息");
            
    }
    catch(error)
    {
            console.log("报错："+ error);
            
    }
}


async function chatHistory(dataset){

   

    
    
    if (!Array.isArray(dataset) || dataset.length === 0) {
        // console.log("无聊天记录可渲染");
        return;
    }
    else
    {
        dataset.sort((a, b) => a.talk_id - b.talk_id);
    }

     // 当前登陆账户id
    const AId= await a_get_user_id();

    //消息列表获取
    const messageList=document.querySelector('.message-list');

    for(let i=0;i<dataset.length;i++)
    {
        const data=dataset[i];
        
        //当前信息用户名
        const currentUserName=data.user_name;
        //当前信息头像
        const currentAvatar='uploads/user_img/'+data.user_img_name;
        //当前信息用户id
        const currentUserId=data.user_id;

        //信息内容
        const talkContent=data.talk_content;
        //信息id
        const talkId=data.talk_id;
        //信息时间
        const talkDate=data.talk_date;
        //vip
        const user_vip  = data.vip 

        //判断是否已渲染
        if(renderedMessageIds.has(talkId))
            {
                continue;
            }
    
        renderedMessageIds.add(talkId);


        //判断消息归属
        //我的信息
        if(currentUserId==AId)
        {
            //新我的消息列表项
            let newMyMessage=document.createElement('li');
            newMyMessage.classList.add('message-mine');

            //消息容器
            let messageMineContent=document.createElement('div');
            messageMineContent.classList.add('message-mine-content');
            //消息文字
            let messageText=document.createElement('p');
            messageText.classList.add('m-content');
            //消息时间
            let messageTime=document.createElement('p');
            messageTime.classList.add('message-mine-time');
            //头像
            let myAvatar=document.createElement('img');
            myAvatar.classList.add('message-avatar');

            
            let loadingWebp=document.createElement('img');
            loadingWebp.classList.add('loading');
            loadingWebp.src='img/loding.webp';
            loadingWebp.style.display='none';

            //参数导入
            messageText.innerText=talkContent;
            messageTime.innerText=talkDate;
            myAvatar.src=currentAvatar;
            accountAvatar=currentAvatar;
            messageMineContent.dataset.talkId = talkId
            if(user_vip > 4)
            {
                myAvatar.classList.add(`${user_vip == 5 ? 'vip5-halo' : (user_vip == 6? 'vip6-halo':'')}`)
                if(user_vip == 6)
                {
                    const div = document.createElement('div')
                    div.classList.add('vip6-content-img')
                    div.style.backgroundImage = `url('../img/vip6-content-img${parseInt(Math.random() * 4)}.gif')`
                    messageMineContent.appendChild(div)

                }
               
            }
            messageMineContent.style.position = 'relative'
            messageMineContent.style.backgroundColor =   user_vip == 5 ? 'rgba(112, 207, 17, 0.5)' : (user_vip == 6? 'rgba(213, 126, 21, 0.3)':'rgb(92,166,250)')
 
            
            
            //组装
            messageMineContent.appendChild(messageText);
            messageMineContent.appendChild(messageTime);
            messageMineContent.appendChild(loadingWebp);
            newMyMessage.appendChild(messageMineContent);
            newMyMessage.appendChild(myAvatar);

            lastMyloadingImg=loadingWebp;

            //添加到消息列表
            messageList.appendChild(newMyMessage);


            setTimeout(() => {
                newMyMessage.scrollIntoView({ behavior: 'smooth',block:'end'});
            }, 0);
        }
        // 他人信息
        else if(currentUserId!=AId)
        {
             //新他人消息列表项
            let newOtherMessage=document.createElement('li');
            newOtherMessage.classList.add('message-others');

            //他人头像
            let othersAvatar=document.createElement('img');
            othersAvatar.classList.add('message-avatar');
            //新他人信息容器
            let messageOthersContent=document.createElement('div');
            messageOthersContent.classList.add('message-others-content')
            //新消息内容
            let messageText=document.createElement('p');
            messageText.classList.add('m-content');
            //新消息时间
            let messageTime=document.createElement('p');
            messageTime.classList.add('message-others-time');

            //参数导入
            messageText.innerText=talkContent;
            messageTime.innerText=talkDate;
            othersAvatar.src = currentAvatar
            messageOthersContent.dataset.talkId = talkId


            //vip样式
            if(user_vip > 4)
                {
                    othersAvatar.classList.add(`${user_vip == 5 ? 'vip5-halo' : (user_vip == 6? 'vip6-halo':'')}`)
                    if(user_vip == 6)
                        {
                            const div = document.createElement('div')
                            div.classList.add('vip6-content-img')

                            div.style.backgroundImage = `url('../img/vip6-content-img${parseInt(Math.random() * 4)}.gif')`
                            messageOthersContent.appendChild(div)
                            
                        }
                }
                messageOthersContent.style.position = 'relative'
            //组装
            messageOthersContent.appendChild(messageText);
            messageOthersContent.appendChild(messageTime)
            newOtherMessage.appendChild(othersAvatar);
            newOtherMessage.appendChild(messageOthersContent);

            //添加到消息列表
            messageList.appendChild(newOtherMessage);

            setTimeout(() => {
                newOtherMessage.scrollIntoView({ behavior: 'smooth',block:'end' });
            }, 0);
        }
    }

}