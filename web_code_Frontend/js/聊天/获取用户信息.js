class Get_user_info_class
{
    //用户信息盒子
    constructor() {
        this.user_data = {
            user_id: '',
            user_img_name: '',
            user_money: 0,
            user_vip_rank: 0,
            user_online_type: ''
        };
    }


    //异步初始化函数
    async init_class()
    {
        await this.get_user_data()
    }

    //获取用户信息函数
    async get_user_data()
    {
    
        const service_url = `${window.service_url_ago}/Get_user_info`

        try{
            const response = await fetch(service_url,{
                method: "GET",
                credentials: "include",  
                headers: {
                    'Content-Type': 'application/json',
                }
            })

            if(response.ok)
            {
                //获取后端发送的数据
                const data = await response.json()
                
                this.user_data  = data
            }
            else
            {
                console.log("获取当前用户信息失败!");  
            }
            
        }
        catch(error)
        {
            console.log("报错："+ error);
            
        }
    }

    // 获取用户 ID
    get_user_id() {
        return this.user_data.user_id;
    }

    // 获取用户图片名称
    get_user_img_name() {
        return this.user_data.user_img_name;
    }

    // 获取用户余额
    get_user_money() {
        return this.user_data.user_money;
    }

    // 获取用户 VIP 等级
    get_user_vip_rank() {
        return this.user_data.user_vip_rank;
    }

    // 获取用户唯一类型
    get_user_online_type() {
        return this.user_data.user_online_type;
    }
}

const get_user_info_class = new Get_user_info_class


