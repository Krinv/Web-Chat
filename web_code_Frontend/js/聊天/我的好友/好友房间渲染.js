

let f_data;
let currentFriendID;
let currentFriendData;
let currentFriend;

//字母排序函数
function sortFriendsByFirstLetter(friendData) {
    return friendData.sort((a, b) => {
        const nameA = a.friend_name?.trim().toLowerCase() || "";
        const nameB = b.friend_name?.trim().toLowerCase() || "";
        return nameA.localeCompare(nameB);
    });
}

// 好友房间类
class Friend_room_class {
    // 信息量获取
    constructor() {
        this.n_get_friend_data = 0;
    }

    // 向后端获取朋友数据
    async get_friend_data() {
        // 这里先给一个默认数据，接口如果获取失败，就会用默认数据
        let friend_data = [
            {
                user_id:"111",
                intimacy:"9",
                establish_date:"44",

            },
        ];

        const request_type=-1;

        // 朋友url
        const service_friend_url = `${window.service_url_ago}/Friend_servlet`+'?request_type='+request_type;

        console.log('获取好友列表中')

        try {
            const response = await fetch(service_friend_url, {
                method: "GET",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (response.ok) {
                const data = await response.json();
                f_data=data;
                friend_data = data;           // 假设后端返回的数据与 friend_data 结构一致
                currentFriendData=data;
                this.n_get_friend_data = 1;   // 标记获取成功
                console.log("获取朋友数据成功：", data);

                console.log("姐u共为撒大苏打：" +   JSON.stringify(data));
            } else {
                console.error("朋友数据 get 失败");
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        }

        return friend_data;
    }

    // 渲染函数
    xuan_ran_friend(friend_data) {
        // 将获取到的 friend_data 数组映射成对应的 HTML
        const friend_data_map = friend_data.map(function (ele, index) {
            return `
                <li class="a-friend-box" data-friend-id="${ele.user_id}">
                    <div class="friend-img" 
                        style="background-image: url(/uploads/user_img/${ele.user_img_name});">
                    </div>
                    <div class="friend-info">
                        <div class="friend-name">
                            ${ele.user_name}
                        </div>
                    </div>
                </li>
            `;
        });

        // 获取容器并进行渲染
        const friend_list_box = document.querySelector(
            "#friend-big-box .friend-index-box .friend-list-box"
        );
        if (friend_list_box) {
            friend_list_box.innerHTML = friend_data_map.join("");
        }
    }

    // 点击好友增加 active，并执行相应操作
    click_friend() {
        const friend_all = document.querySelectorAll(
            "#friend-big-box .friend-index-box .friend-list-box .a-friend-box"
        );

        for (let i = 0; i < friend_all.length; i++) {
            friend_all[i].addEventListener("click", () => {
                // 先清除所有 active
                this.add_active(i);

                //获取简介相关常量

                const f_intro_box=document.querySelector("#friend-big-box .friend-info-box");
                f_intro_box.style.display='flex';

                //当前选中的好友

                currentFriend=currentFriendData[i];

                currentFriendID=friend_all[i].getAttribute('data-friend-id');

                console.log('当前账户对象id：'+currentFriendID);

                // DOM元素获取
                const f_name=document.querySelector('#friend-big-box .friend-info-box .friend-introduction-section .f-intro-content .f-intro-name');
                const f_intimacy=document.querySelector('#friend-big-box .friend-info-box .friend-introduction-section .f-intro-content .f-intro-intimacy .introduction-intimacy');
                const f_vip=document.querySelector('#friend-big-box .friend-info-box .friend-introduction-section .f-intro-content .f-intro-vip .introduction-vip-rank');
                const f_avatar=document.querySelector('#friend-big-box .friend-info-box .friend-introduction-section .f-intro-avatar');


                f_name.textContent=f_data[i].user_name;
                f_intimacy.textContent=f_data[i].intimacy;
                f_vip.textContent=f_data[i].user_vip_rank;
                f_avatar.src='/uploads/user_img/'+f_data[i].user_img_name;

                change_intimacy(currentFriend);


            });
        }
    }

    // 点击增加active
    add_active(active_index) {
        const friend_all = document.querySelectorAll(
            "#friend-big-box .friend-index-box .friend-list-box .a-friend-box"
        );

        for (let i = 0; i < friend_all.length; i++) {
            if (i === active_index) {
                friend_all[i].classList.add("active");
            } else {
                friend_all[i].classList.remove("active");
            }
        }
    }

    // 朋友主函数
    async xuan_ran_friend_main() {
        let friend_data = await this.get_friend_data();
        console.log("获取朋友信息完成");

        //进行排序
        friend_data=sortFriendsByFirstLetter(friend_data);

        // 成功获取到数据后进行渲染
        this.xuan_ran_friend(friend_data);

        // 绑定点击事件
        this.click_friend();

        // 重置获取标记
        this.n_get_friend_data = 0;
    }
}

// 初始化并执行
const friend_room_class = new Friend_room_class();
friend_room_class.xuan_ran_friend_main();

