class Vip_style_class
{

    

    // 初始化
    vip_style_init()
    {
        //聊天头像下面标志
        setInterval(this.vip_display_style,1000)

        //聊天名字
        setInterval(this.vip_name_style,500)


        //头像边框 光晕
       
        // this.vip_style()


        
    }
   
    //聊天名字 vip_name
    vip_name_style()
    {
        // vip5
        const vip5_name = document.querySelectorAll('.vip5-name')



        // vip6
        const vip6_name_all = document.querySelectorAll('.vip6-name')

        for(let i = 0 ; i < vip6_name_all.length;i++)
        {   
            const random_color1  = Math.random() * (255)
            const random_color2  = Math.random() * (255)
            const random_color3  = Math.random() * (255)
            vip6_name_all[i].style.textShadow = `0 0 10px rgb(${random_color3},${random_color3},${random_color1})`
            vip6_name_all[i].style.color =  `rgb(${random_color2},${random_color2},${random_color1})`
         }

    }


    //vip-display的样式  头像下面标识
    vip_display_style()
    {
        //获取 vip4-6的所有vip-display
        const vip_display_all = document.querySelectorAll('.vip4-display,.vip5-display,.vip6-display')


        for(let i = 0 ; i < vip_display_all.length;i++)
        {

            const random_color1  = Math.random() * (255)
            const random_color2  = Math.random() * (255)
            const random_color3  = Math.random() * (255)
            vip_display_all[i].style.backgroundColor = `rgb(${random_color3},${random_color3},${random_color1})`
            vip_display_all[i].style.color =  `rgb(${random_color2},${random_color2},${random_color1})`
        }
    }

    //vip 风格样式
    vip_style()
    {
        //对象获取
        const vip_all = document.querySelectorAll('.vip-display')
        const vip_name_all = document.querySelectorAll('.vip-name')
        const vip_talk_logo_all = document.querySelectorAll('.vip-talk-logo')
        const room_name_all = document.querySelectorAll('.room-name') 
        
        //vip名字颜色数组
        const vip_data = [
            { level: 1, name: "普通会员", rgb: [169, 169, 169]  },
            { level: 2, name: "青铜会员", rgb: [205, 127, 50] },
            { level: 3, name: "白银会员", rgb: [192, 192, 192] },
            { level: 4, name: "黄金会员", rgb: [255, 215, 0] },
            { level: 5, name: "钻石会员", rgb: [0, 191, 255] },
            { level: 6, name: "至尊会员", rgb: [255, 0, 0] }
        ];
        
        for(let i = 0 ; i < vip_all.length ; i++)
        {
            
            if(room_data[i].vip != 0)
            {
                if(room_data[i].vip > 3)
                {
                                  //vip标志变色
                    setInterval(() =>
                        {
                            const random_color1  = Math.random() * (255)
                            const random_color2  = Math.random() * (255)
                            const random_color3  = Math.random() * (255)
                            //vip标志
                            vip_all[i].style.color =  `rgb(${random_color1},${random_color2},${random_color3})`
                            vip_all[i].style.backgroundColor =  `rgb(${random_color3},${random_color2},${random_color1})`
                            

                        },1000 ) 

                
                }
                if(room_data[i].vip > 4)
                {
                    room_name_all[i].style.fontFamily = '楷体'
                    room_name_all[i].style.fontSize = '20px'
                }
                if(room_data[i].vip > 5)
                    {
                        setInterval(()=>
                            {
                                
                                const random_color1  = Math.random() * (255)
                                const random_color2  = Math.random() * (255)
                                const random_color3  = Math.random() * (255)
                                    // vip名字
                                    vip_name_all[i].style.color =  `rgb(${random_color1},${random_color2},${random_color3})`
                                    vip_name_all[i].style.boxShadow =  'inset 0 0 10px rgb(244, 227, 43)'
                            },300)
                            room_name_all[i].style.color = `rgb(${vip_data[room_data[i].vip - 1].rgb.join(', ')})`;
                            room_name_all[i].style.fontWeight = room_data[i].vip / 3 > 1 ? '900' : '400' 
                    }

                else
                {
     
                                //名字颜色划分
                    room_name_all[i].style.color = `rgb(${vip_data[room_data[i].vip - 1].rgb.join(', ')})`;
                    room_name_all[i].style.fontWeight = room_data[i].vip / 3 > 1 ? '900' : '400' 
                }
    
                

            }
        }
    }
} 
const vip_style_class= new Vip_style_class()
vip_style_class.vip_style_init()