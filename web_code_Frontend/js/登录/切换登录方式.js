
// 下划线动画效果
function toggle_move_line(type)
{
    //获取对象
    const move_line = document.querySelector('.login-box .select-box .move-line')
    const wechat_btn = document.querySelector('.login-box .select-box .wechat-btn')
    const default_btn = document.querySelector('.login-box .select-box .default-btn')

    //点击了wechat
    if(type == 'wechat')
    {
        const x_location = wechat_btn.offsetLeft
        move_line.style.left = x_location + 6 + 'px'
    }
    // 如果是 默认的
    else if('default')
    {
        const x_location = default_btn.offsetLeft
        move_line.style.left = x_location + 6 + 'px'
    }
}



//切换主函数
function toggle_login_type()
{
    //获取对象
    const wechat_box = document.querySelector('.login-box .wechat-login')
    const default_box = document.querySelector('.login-box .default-login')

    const wechat_btn = document.querySelector('.login-box .select-box .wechat-btn')
    const default_btn = document.querySelector('.login-box .select-box .default-btn')

    //点击按钮时
    wechat_btn.addEventListener('click',()=>{
        wechat_box.style.display = 'flex'
        default_box.style.display = ' none'
        toggle_move_line('wechat')
    })

    default_btn.addEventListener('click',()=>{
        wechat_box.style.display = 'none'
        default_box.style.display = 'flex'
        toggle_move_line('default')
    })
}
toggle_login_type()