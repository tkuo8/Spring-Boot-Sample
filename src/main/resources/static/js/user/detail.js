'use strict';

/** 画面ロード時の処理 */
document.addEventListener('DOMContentLoaded', function() {
    // 更新ボタンを押した時の処理
    const updateButton = document.getElementById('btn-update');
    if (updateButton) {
        updateButton.addEventListener('click', function(event) {
            event.preventDefault(); //デフォルトのフォーム送信を無効化
            updateUser();
        });
    }
    // 削除ボタンを押した時の処理
    const deleteButton = document.getElementById('btn-delete');
    if (deleteButton) {
        deleteButton.addEventListener('click', function(event) {
            event.preventDefault(); //デフォルトのフォーム送信を無効化
            deleteUser();
        });
    }
});



/** ユーザ更新処理 */
function updateUser() {
    // フォームの値を取得
    const formElement = document.querySelector('#user-detail-form');
    const formData = new FormData(formElement);

    // fetch APIを使った非同期通信
    fetch('/user/update', {
        method: 'PUT',
        body: new URLSearchParams(formData),
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Accept': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('ネットワークの応答が問題です');
            }
            return response.json();
        })
        .then(data => {
            //成功時の処理
            alert('ユーザを更新しました');
            // ユーザ一覧画面にリダイレクト
            window.location.href = '/user/list';
        })
        .catch(error => {
            // エラー時の処理
            alert('ユーザ更新に失敗しました');
            console.error('エラー', error);
        });
}

/** ユーザ削除処理 */
function deleteUser() {
    // フォームの値を取得
    const formElement = document.querySelector("#user-detail-form");
    const formData = new FormData(formElement);

    // fetch APIを使った非同期通信
    fetch('/user/delete', {
        method: 'DELETE',
        body: new URLSearchParams(formData),
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Accept': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('ネットワークの応答が問題です');
            }
            return response.json();
        })
        .then(data => {
            // 成功時の処理
            alert('ユーザを削除しました');
            // ユーザ一覧画面にリダイレクト
            window.location.href = '/user/list';
        })
        .catch(error => {
            // エラー時の処理
            alert('ユーザ削除に失敗しました');
            console.error('エラー：', error);
        })
}