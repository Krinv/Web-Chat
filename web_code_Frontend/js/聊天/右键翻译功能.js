class Translation_class
{

    constructor()
    {
        this.active_dom = null
        this.old_text = ''
        this.user_vip = 0
    }
  
        


    async right_main()
    {

        //获取用户vip等级
        this.user_vip = await this.get_user_vip()
        // 右键
        this.right_menu()

        
    }


    //发送
    async send_baidu()
    {
        const service_url = `${window.service_url_ago}/Translation_servlet`
        const data = {
            old_text : this.old_text 
        }
        const response = await fetch(service_url,{
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        if(response.ok)
        {
            const data = await response.json()
            const trans_result = data.trans_result;
            const new_text = trans_result[0].dst;

            return new_text
        }
        return null
      
    }

    //获取用户vip等级
    async  get_user_vip()
    {
        await get_user_info_class.init_class()
        const user_vip = get_user_info_class.get_user_vip_rank()
        console.log("用户vip等级为:" + user_vip);
        
        return user_vip
    }

    //点击翻译按钮
    click_translation()
    {
        //获取对象
        const translation_btn = document.querySelector('#translation-btn')
        const message_box = this.active_dom.querySelector('.m-content') == null ? this.active_dom :  this.active_dom.querySelector('.m-content')

        translation_btn.addEventListener('click',async()=>
        {
            //判断是不是svip 2 3
            
            if(this.user_vip < 5)
            {
                alert("请升级到svip5或sivp6才能使用该功能!")
                return ;
            }
             // 禁用按钮，防止重复点击
            translation_btn.disabled = true;
            //没翻译过才能翻译
            if(!message_box.classList.contains('translated'))
            {
                this.old_text =  message_box.innerText
                const new_text = await this.send_baidu()
                
    
                //hr 分隔线
                const hr = document.createElement('hr')
                const new_text_p =  document.createElement('p')
                new_text_p.style.color = '#fff'
                new_text_p.innerText = new_text
    
                message_box.appendChild(hr)
                message_box.appendChild(new_text_p)
                
    
                //将翻译后的盒子添加类
                message_box.classList.add('translated')
            }
            // 翻译完成后恢复按钮
             translation_btn.disabled = false;
            


        })
        
        
    }

    // 右键
    right_menu()
    {
        //获取对象
        const right_box = document.querySelector('.big-box .right-box .right-box-mid ')
       
        right_box.addEventListener('contextmenu',e=>
        {
             //阻止默认对象 右键
            e.preventDefault()
            let isInMineContent = false;
            let isInOthersContent = false;
            const my_message_boxes = document.querySelectorAll('.message-mine-content')
            const other_message_boxes = document.querySelectorAll('.message-others-content')
             // 遍历所有的 .message-mine-content 元素，检查点击目标是否在这些元素内
            my_message_boxes.forEach(box => {
                if (box.contains(e.target)) {
                isInMineContent = true;
                }
            });

            // 遍历所有的 .message-others-content 元素，检查点击目标是否在这些元素内
            other_message_boxes.forEach(box => {
                if (box.contains(e.target)) {
                isInOthersContent = true;
                }
            });

            if(isInMineContent || isInOthersContent)
            {
                this.active_dom= e.target
                //右键盒子
                const right_box = document.querySelector('.big-box .right-box .right-box-mid .right-box-mid-right .right-function-box')
                const message_box =e.target

                // 获取值
                const message_top = message_box.getBoundingClientRect().top + 50
                const message_left = message_box.offsetLeft  + 100
                console.log('高:' + message_top + "  宽:" + message_left);
                
                right_box.style.top = message_top + 'px'
                right_box.style.left = message_left + 'px'
                right_box.style.display = 'flex' 

                //点击翻译按钮
                this.click_translation()
            }
            
        })

        //点击其他位置消失
        right_box.addEventListener('click',e=>
        {
            const right_box = document.querySelector('.big-box .right-box .right-box-mid .right-box-mid-right .right-function-box')
           
            if(!right_box.contains(e.target)  )
            {
                
                right_box.style.display = 'none' 
            }
        })
    }
}
const translation_class = new Translation_class()

translation_class.right_main()