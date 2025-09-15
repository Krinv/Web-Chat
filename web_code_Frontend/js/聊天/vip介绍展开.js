class Vip_introduce_expend_class
{

    // 原理
    /*
        通过看访问的li的等级 若是1级则将其下级菜单显示
        规则  该1级菜单的类与后面各级菜单都有同一个自定义团队属性 例如都有 data-team="vip1"
        
        当点击一个li时判断是否 有expend类有则可以展开 则获取其等级 将比它低一级的
        子菜单显示或隐藏  格式为   团队名+英文级别(one/two)  

        例如点击  vip1-one  将打开或隐藏  vip1-two
    
    */

    //等级数组
    rank =['','one','two','three']


    Vip_introduce_expend_init()
    {
        //初始化 展开类
        this.expend_init()

        //展开功能函数
        this.expend_function()

   
    }





    //实现电梯功能
    vip_lift()
    {
        //获取对象
        //获取active的信息  
        const active_li = document.querySelector('.select-vip li.active')
        const right_box = document.querySelector('.big-box #vip-introduce-big-box .vip-introduce-right-box')



        //获取active团队
        const active_team = active_li.dataset.team
        const active_rank =  active_li.dataset.rank
        
        //获取该active的最低等级的第一个孩子
        const first_son = this.find_lowest_son(active_team)
       
        
        if(first_son.style.display != '' && first_son.style.display != 'none'
             && active_rank != first_son.dataset.rank)
        {    console.log('最低：',first_son.className + ':::' + first_son.innerHTML);
        
            console.log('aaagggg:'+ `h1[data-content="${active_team}-${first_son.innerText}"]`);
            
            //获取孩子对应的右边盒子
            const right_son = right_box.querySelector(`h1[data-content="${active_team}-${first_son.innerText}"]`)
            //获取这个孩子距离顶部的高度
            const right_son_top = right_son.offsetTop
            console.log("高度:" +right_son_top);

            right_box.scrollTo({
                top: right_son_top, 
                behavior: 'smooth'  // 启用平滑滚动
            });
        }
        else if( active_rank == first_son.dataset.rank)
        {
            const right_son = right_box.querySelector(`h1[data-content="${active_team}-${active_li.innerText}"]`)
            //获取这个孩子距离顶部的高度
            const right_son_top = right_son.offsetTop
            console.log("高度:" +right_son_top);

            right_box.scrollTo({
                top: right_son_top, 
                behavior: 'smooth'  // 启用平滑滚动
            });
        }
        
        
    }


    //查找最低等级的孩子的第一个
    find_lowest_son(team)
    {
        //获取团队的所有对象
        const li_all = document.querySelectorAll(`.select-vip li[data-team="${team}"]`)
        //获取最后一个孩子
        const last_son = li_all[li_all.length - 1 ]
        const lowest_rank = last_son.dataset.rank

        return document.querySelector(`.select-vip li[data-team="${team}"][data-rank="${lowest_rank}"]`)
    }


    //添加active
    add_active(active_li)
    {
        //获取对象
        const li_all = document.querySelectorAll('.big-box #vip-introduce-big-box .vip-introduce-left-box .select-vip li')
        for(let i = 0 ; i < li_all.length ; i ++)
            li_all[i].classList.remove('active')
        active_li.classList.add('active')
    }



    //关闭所有后代
    close_sons(team,my_li)
    {
        //获取团队成员
        const team_li_all = document.querySelectorAll(`.big-box #vip-introduce-big-box .vip-introduce-left-box li[data-team=${team}]`)
        //获取自己的等级
        const my_rank = my_li.dataset.rank
        for(let i = 0 ; i < team_li_all.length ; i ++)
        {
            const rank = team_li_all[i].dataset.rank

            //如果你的等级比我低那么关闭  即为 数字比我大
            if(my_rank < rank)
                team_li_all[i].style.display = 'none'
        }
        
    }

    //打开所有父亲
    open_parents(team,my_li)
    {
        //获取团队成员
        const team_li_all = document.querySelectorAll(`.big-box #vip-introduce-big-box .vip-introduce-left-box li[data-team=${team}]`)
        //获取自己的等级
        const my_rank = my_li.dataset.rank
        for(let i = 0 ; i < team_li_all.length ; i ++)
        {
            const rank = team_li_all[i].dataset.rank

            //如果你的等级比我高那么打开  即为 数字比我低
            if(my_rank > rank)
                team_li_all[i].style.display = 'block'
        }
    }

    //为每个类为expend前面添加  〉
    expend_init()
    {
        //获取对象
        const expend_all = document.querySelectorAll('.big-box #vip-introduce-big-box .vip-introduce-left-box li.expend')
        for(let i =  0; i < expend_all.length ; i++)
        {
            expend_all[i].innerText = '〉' +  expend_all[i].innerText
        }
    }


    // 各级别展开逻辑
    expend_function()
    {
        //获取对象
        const li_all = document.querySelectorAll('.big-box #vip-introduce-big-box .vip-introduce-left-box .select-vip li')

        //获取对象的公共部分
        const common = '.big-box  #vip-introduce-big-box .vip-introduce-left-box .select-vip .'

        for(let j = 0 ; j < li_all.length;j ++)
        {
            li_all[j].addEventListener('click',()=>
            {
               
                //获取自己
                const my_li = li_all[j]
                 //添加active
                 this.add_active(my_li)
                // 检测是否含expend 若是有则进行展开逻辑
                if(my_li.classList.contains('expend'))
                {
                    
                    //获取自己的等级
                    const my_rank = my_li.dataset.rank
                    //获取自己的团队
                    const my_team = my_li.dataset.team
        
                    
                    //获取孩子们
                    const my_sons = document.querySelectorAll(common + my_team + '-' + this.rank[parseInt(my_rank)+1])
        
    
                    
                
                    
                    //外联的display 初始化为 ''
                    if(my_sons[0].style.display == 'none' || my_sons[0].style.display == '')
                    {
                        //将前面的〉变为﹀ 
                        my_li.textContent = '﹀ '+ my_li.dataset.content
                       for(let i = 0 ; i < my_sons.length ; i++)
                       {
                            const my_son = my_sons[i]
                            my_son.style.display = 'block'
                       }
                       
                       
   
                    }
                        
                    //隐藏
                    else
                    {
                        //将前面的〉变为﹀ 
                        my_li.textContent = '〉'+ my_li.dataset.content
                        this.close_sons(my_team,my_li)
                       
                        
                    }
               
                
                }
                    // 电梯功能
                    this.vip_lift() 
            })
        }
    }
}
const vip_introduce_expend_class = new Vip_introduce_expend_class()
vip_introduce_expend_class.Vip_introduce_expend_init()