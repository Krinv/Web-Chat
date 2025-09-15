class Shop_class
{

    constructor() {
        this.user_data = {
            user_id: '',
            user_img_name: '',
            user_money: 0,
            user_vip_rank: 0,
            user_online_type: ''
        };
    }
    async get_user_data()
    {
        await get_user_info_class.init_class()
        this.user_id = get_user_info_class.get_user_id()
        this.user_money = get_user_info_class.get_user_money()
        this.user_vip_rank = get_user_info_class.get_user_vip_rank()
        
    }

    //欢迎信息渲染
    xuan_ran_welcome()
    {
        //获取对象
        const welcome = document.querySelector('#shop-big-box .top-box .welcome-box')
        const vip = this.user_vip_rank
        console.log('vip:' + vip);
        
        let name = ''
        if(vip != 0)
        {
            name = vip >= 4 ? 'sivp'+ (vip - 3):'vip' + vip
            welcome.innerHTML = `尊敬的<h1 style="display: inline-block; color: red;">${name}</h1>,您好！`
        }
        else
        {   
            welcome.innerHTML = '你好'
        }


    }
    //渲染金钱
    xuan_ran_money()
    {
        //获取对象
        const money_number = document.querySelector('#shop-big-box .top-box .money-box .money-number')

        money_number.innerText = this.user_money
    }

    //渲染主函数
    async shop_mian()
    {
        // 获取数据
        await this.get_user_data()


        //渲染金钱
        this.xuan_ran_money()

        //欢迎信息渲染
        this.xuan_ran_welcome()
        
    }
}


const shop_class = new Shop_class()

shop_class.shop_mian()