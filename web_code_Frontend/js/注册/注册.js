// 账号格式验证
function validateid() {
    const id = document.querySelector('.user-id').value;
    const acRegex = /^\d{6,12}$/;
    const acAlert = document.querySelector('.alert.user-id-alert');
    
    if(id=='')
    {
        acAlert.style.display = 'none';
        return false;
    }

    if (!acRegex.test(id)) {
        acAlert.textContent = '账号必须是6到12位数字';
        acAlert.style.display = 'block';
        return false;
    } else {
        acAlert.style.display = 'none';
        return true;
    }
}

// 密码格式验证
function validatePassword() {
    const password = document.querySelector('.password').value;
    const pwRegex = /^[a-zA-Z0-9+\.\-*/%&!@#]{6,12}$/;
    const pwAlert = document.querySelector('.alert.password-alert');

    if(password=='')
    {
        pwAlert.style.display = 'none';
        return false;
    }

    if (!pwRegex.test(password)&password!=null) {
        pwAlert.textContent = '密码格式错误';
        pwAlert.style.display = 'block';
        return false;
    } else {
        pwAlert.style.display = 'none';
        return true;
    }
}

// 再次输入密码验证
function validateApassword() {
    const password = document.querySelector('.password').value;
    const againPassword = document.querySelector('.again-password').value;
    const apwAlert = document.querySelector('.alert.again-password-alert');

    if(againPassword=='')
    {
        apwAlert.style.display = 'none';
        return false;
    }

    if (againPassword !== password) {
        apwAlert.textContent = '两次输入的密码不一致';
        apwAlert.style.display = 'block';
        return false;
    } else {
        apwAlert.style.display = 'none';
        return true;
    }
}

// 清除警告信息
function cleanAlert() {
    document.querySelectorAll('.alert').forEach(alert => {
        alert.style.display = 'none';
    });
}

// 聚焦与失焦事件监听器
function setupInputListeners() {
    const inputs = document.querySelectorAll('.user-id, .password, .again-password');

    inputs.forEach(input => {
        input.addEventListener('focus', cleanAlert);

        input.addEventListener('blur', () => {
            if (input.classList.contains('user-id')) validateid();
            if (input.classList.contains('password')) validatePassword();
            if (input.classList.contains('again-password')) validateApassword();
        });
    });
}

// 表单提交验证
function setupFormValidation() {
    const form = document.querySelector('.register-box');
    form.addEventListener('submit', function(event) {
        const isidValid = validateid();
        const isPasswordValid = validatePassword();
        const isApasswordValid = validateApassword();

        if (!isidValid || !isPasswordValid || !isApasswordValid) {
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
