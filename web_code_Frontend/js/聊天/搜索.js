class Search_class
{
    constructor()
    {
        this.send_search_data = ''
    }

    //添加好友
    async add_friend()
    {
        console.log("进去添加函数!");
        
        //获取对象
        const add_btn_all = document.querySelectorAll('#search-big-box .search-box .search-friend-input-add .add-friend')

        for(let i = 0 ; i < add_btn_all.length;i++)
        {
            add_btn_all[i].addEventListener('click',async()=>
            {
                const user_id = add_btn_all[i].dataset.userId

                const data = {
                    user_id : user_id
                }
                const service_url = `${window.service_url_ago}/Friend_servlet`
                try{
                    
                    const response = await fetch(service_url,
                        {
                            method:"POST",
                            credentials: "include",
                             headers: {
                            "Content-Type": "application/json",
                            },
                            body:JSON.stringify(data)
                        }
                    )

                    if(response.ok)
                    {
                        alert("添加成功");
                        
                        
                        this.get_serach_data(this.send_search_data)
                    }
                    else{
                        console.log("添加失败");
                        
                    }

                }catch(error)
                {

                }


            })
        }
    }

    //渲染隐藏盒子
    xuan_ran_add(data)
    {   
        const my_user_id = a_get_user_id()
        const add_box = document.querySelector('#search-big-box .search-box .search-friend-input-add')
        const box_str = data.map((ele,indext)=>{
            if(my_user_id == ele.user_id)return
            return `
                        <li data-user-id = ${ele.user_id}>
                            <div class="user-info">
                                    <div class="user-img" style="background-image: url('../../uploads/user_img/${ele.user_img_name}');"></div>
                                     <div class="user-name">用户名:${ele.user_name}</div>
                                     <div class="user-id">账号:${ele.user_id}</div> 
                               </div>
                                     <div class="add-friend" data-user-id = ${ele.user_id}>添加</div>
                                </li>
            `
        })
        add_box.innerHTML = box_str.join('')
        this.add_friend()
    }

    //向后端获取
    async get_serach_data(search_data)
    {
        const service_friend_url = `${window.service_url_ago}/Search_servlet`+'?search_data='+search_data;
        
        try{
             const response = await fetch(service_friend_url,{
                method: "GET",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            })


            if(response.ok)
            {
                const data = await response.json()
                console.log("搜索结果是:" +   JSON.stringify(data));
               this.xuan_ran_add(data)
            }
            else
            {
                console.log("请求失败");
                
            }
        }catch(error)
        {

        }
     
    }
    //输入时弹出隐藏盒子
    async input_add()
    {
        //获取对象
        const add_box = document.querySelector('#search-big-box .search-box .search-friend-input-add')
        const search_input =document.querySelector('#search-big-box .search-box .search-friend-input')


        search_input.addEventListener('input',async ()=>{
            const search_data = search_input.value
            this.send_search_data = search_data
            add_box.style.display = 'flex'

            if(search_data.trim() != '')
            {
                // 获取搜索数据
            this.get_serach_data(search_data)
            }
            
        })

        window.addEventListener('click',e=>
        {

            if(!add_box.contains(e.target) && !search_input.contains(e.target))
            {
                    add_box.style.display = 'none'
            }
        })
    }

    search_init()
    {
         //输入时弹出隐藏盒子
        this.input_add()


    }
}

const search_class = new Search_class()

search_class.search_init()