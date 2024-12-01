/**
 * 
 */

document.addEventListener("DOMContentLoaded", function () {
    const checkBtn = document.getElementById("check-btn-id");

    checkBtn.addEventListener("click", function () {
        const loginIdInput = document.getElementById("login_id");
        const validationMsg = document.getElementById("login-id-validation");

        // Ajax 요청
        fetch("/user/checkLoginId", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: `login_id=${encodeURIComponent(loginIdInput.value.trim())}`,
        })
            .then((response) => response.json())
            .then((isAvailable) => {
                if (isAvailable) {
                    validationMsg.textContent = "사용할 수 없는 아이디입니다.";
                    validationMsg.style.color = "red";
                    loginIdInput.value = ""; // 아이디 초기화
                } else {
                    validationMsg.textContent = "사용 가능한 아이디입니다.";
                    validationMsg.style.color = "green";
                }
            })
            .catch((error) => {
                console.error("Error:", error);
                validationMsg.textContent = "오류가 발생했습니다. 다시 시도해주세요.";
                validationMsg.style.color = "red";
            });
    });
});

document.getElementById("password").addEventListener("input", function () {
    const password = this.value;
    const validationMsg = document.getElementById("password-id-validation");

    // 비밀번호 조건: 4~16자 이내, 숫자, 특수문자 포함
    const passwordRegex = /^(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{4,16}$/;

    if (!passwordRegex.test(password)) {
        validationMsg.textContent = "4~16자 이내로 작성하세요. 숫자, 특수문자를 포함해야 합니다.";
        validationMsg.style.color = "red";
    } else {
        validationMsg.textContent = ""; 
    }
});
document.getElementById("confirm-password").addEventListener("input", function () {
    const confirmPassword = this.value; 
    const firstPassword = document.getElementById("password").value; 
    const validationMsg = document.getElementById("pwdCheck-id-validation"); 

    // 비밀번호 확인 조건: 두 비밀번호가 동일해야 함
    if (confirmPassword !== firstPassword) {
        validationMsg.textContent = "비밀번호가 일치하지 않습니다.";
        validationMsg.style.color = "red";
    } else {
        validationMsg.textContent = "";
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const checkBtn = document.getElementById("check-btn-nickname");

    checkBtn.addEventListener("click", function () {
        const nicknameInput = document.getElementById("nicknickname");
        const validationMsg = document.getElementById("nickname-id-validation");

        // Ajax 요청
        fetch("/user/checkNickname", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: `nickname=${encodeURIComponent(nicknameInput.value.trim())}`,
        })
            .then((response) => response.json())
            .then((isAvailable) => {
                if (isAvailable) {
					validationMsg.textContent = "사용할 수 없는 닉네임입니다.";
                    validationMsg.style.color = "red";
                    nicknameInput.value = ""; // 아이디 초기화
                    
                } else {
                    validationMsg.textContent = "사용 가능한 닉네임입니다.";
                    validationMsg.style.color = "green";
                }
            })
            .catch((error) => {
                console.error("Error:", error);
                validationMsg.textContent = "오류가 발생했습니다. 다시 시도해주세요.";
                validationMsg.style.color = "red";
            });
    });
});
