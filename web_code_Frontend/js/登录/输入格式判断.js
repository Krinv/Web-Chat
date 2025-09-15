// 密码正则表达式
// 密码：请输入6到12位数字或字母组合+.-*/%&!@#
function validatePassword() {
    let password = document.querySelector('.password').value;
    let pwRegex = /^[a-zA-Z0-9+\.\-*/%&!@#]{6,12}$/;
    let pwAlert = document.querySelector('.password-alert');

    if(password=='')
    {
        pwAlert.style.display = 'none';
        return false;
    }
    

    if (!pwRegex.test(password)&password!=null) {
        pwAlert.textContent = '格式错误'
        pwAlert.style.display = 'block';
        return false;
    } else {
        pwAlert.style.display = 'none';
        return true;
    }
}

// 账号正则表达式
// 账号：请输入12位数字
function validateid() {
    let id = document.querySelector('.user-id').value;
    let acRegex = /^\d{6,12}$/;
    let acAlert = document.querySelector('.user-id-alert');

    if(id=='') 
    {
        acAlert.style.display='none';
        return false;
    }
    
    //正则表达式判断
    if (!acRegex.test(id)) {
        acAlert.textContent = '账号必须是6到12位数字';
        acAlert.style.display = 'block';
        return false;
    } 
    
    else
    {
        acAlert.style.display = 'none';
        return true;
    }
}

// 清除警告信息
function cleanAlert(input) {
    let acAlert = document.querySelector('.alert.user-id-alert');
    let pwAlert = document.querySelector('.alert.password-alert');

    acAlert.style.display = 'none';
    pwAlert.style.display = 'none';
}

// 输入框聚焦清空警告
function setupInputListeners() {
    let idInput = document.querySelector('.user-id');
    let passwordInput = document.querySelector('.password');

    idInput.addEventListener('focus', function(event) {
        cleanAlert(event.target);
    });

    passwordInput.addEventListener('focus', function(event) {
        cleanAlert(event.target);
    });

    idInput.addEventListener('blur', function() {
        validateid();
    });

    passwordInput.addEventListener('blur', function() {
        validatePassword();
    });
}

// 提交验证
function setupFormValidation() {
    
    let form = document.querySelector('.login-box');
    form.addEventListener('submit', function(event) {
        let isidValid = validateid();
        let isPasswordValid = validatePassword();

        if (!isidValid || !isPasswordValid) {
            event.preventDefault(); // 阻止表单提交
        }
    });
}

// 初始化
function init() {
    setupInputListeners();
    setupFormValidation();
}

// DOM 加载完成后初始化
document.addEventListener('DOMContentLoaded', init);
