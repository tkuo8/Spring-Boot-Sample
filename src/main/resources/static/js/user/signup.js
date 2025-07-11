/**
 * 
 */
'user strict';

/** 画面ロード時の処理 */
document.addEventListener('DOMContentLoaded', function() {
    // 登録ボタンを押した時の処理
    const signupButton = document.getElementById('btn-signup');
    if (signupButton) {
        signupButton.addEventListener('click', function(event) {
            event.preventDefault(); //デフォルトのフォーム送信を無効化
            signupUser();
        });
    }
});

/** ユーザ登録処理 */
function signupUser() {

    // バリデーション結果をクリア
    removeValidResult();

    // フォームの値を取得
    const formElement = document.querySelector('#signup-form');
    const formData = new FormData(formElement);

    // fetch APIでajax通信
    fetch('/user/signup/rest', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            // ajax成功時の処理
            console.log(data);
            if (data.result === 90) {
                // validationエラー時の処理
                Object.entries(data.errors).forEach(([key, value]) => {
                    reflectValidResult(key, value);
                });
            } else if (data.result === 0) {
                alert('ユーザを登録しました');
                // ログイン画面にリダイレクト
                window.location.href = '/login';
            }
        })
        .catch(error => {
            // ajax失敗時の処理
            alert('ユーザー登録に失敗しました');
            console.error(error);
        });
}

/** バリデーション結果をクリア */
function removeValidResult() {
    const invalidElements = document.querySelectorAll('.is-invalid');
    invalidElements.forEach(el => el.classList.remove('is-invalid'));

    const invalidFeedback = document.querySelectorAll('.invalid-feedback', '.text-danger');
    invalidFeedback.forEach(el => el.remove());
}

/** バリデーション結果の反映 */
function reflectValidResult(key, value) {
    if (key === 'gender') {
        // 性別の場合
        const genderInputs = document.querySelectorAll(`input[name=${key}]`);
        genderInputs.forEach(input => input.classList.add('is-invalid'));
        const parentElement = genderInputs[0].parentNode.parentNode;
        const errorMessage = document.createElement('div');
        errorMessage.className = 'text-danger';
        errorMessage.textContent = value;
        parentElement.appendChild(errorMessage);
    } else {
        // 性別以外の場合
        const input = document.getElementById(key);
        if (input) {
            input.classList.add('is-invalid');
            const errorMessage = document.createElement('div');
            errorMessage.className = 'invalid-feedback';
            errorMessage.textContent = value;
            input.after(errorMessage);
        }
    }
}