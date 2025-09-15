


  //展示对应的section   隐藏其他section
  function display_section(goal_li)
  {
    const menuItems = document.querySelectorAll(".left-box ul li");

    const titleElement = document.querySelector(".right-top-title");
      if(goal_li.className == 'friend')
      {
          const friend_room_class = new Friend_room_class();
          friend_room_class.xuan_ran_friend_main();
      }
      else if(goal_li.className == 'message')
      {
          const  room_class = new Room_class()
          room_class.xuan_ran_room_main()
          console.log("阿达啊哦哦哦哦哦");
          

      }
      for(let i = 0 ; i < menuItems.length;i ++)
      {   
          console.log(`#${menuItems[i].className}-big-box`);
          if(menuItems[i] === goal_li )
              document.querySelector(`#${menuItems[i].className}-big-box`).style.display = 'flex'
          else
              document.querySelector(`#${menuItems[i].className}-big-box`).style.display = 'none'
      }
  }


function left_nav_init() {
    // 获取所有左侧菜单项
    const menuItems = document.querySelectorAll(".left-box ul li");

    const titleElement = document.querySelector(".right-top-title");

    left_function_init()

    //初始化函数
    function left_function_init()
    {
        //初始化消息显示其他隐藏
        display_section(document.querySelector('.left-box .message'))
    }

  


    // 添加所有列表事件监听
    menuItems.forEach((item) => {
        item.addEventListener("click", () => {
            const newTitle = item.getAttribute("data-title");

            //展示位置
            titleElement.textContent = newTitle
            //展示
            display_section(item)
        });
    });
}
//点击栏目切换右侧标题栏信息
document.addEventListener("DOMContentLoaded",left_nav_init );