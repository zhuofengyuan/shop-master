// var base_path = 'http://zm.zsmls.com/';
var base_path = 'http://localhost:8080/';
// var ittun_path = 'http://fengtoos.ittun.com/';
var image_path = base_path + 'file/image/';
var upload_path = base_path + 'file'
var oauth_server = base_path;
var redirect_uri = base_path + 'view/index';
var client_id = 'client_2';
var client_secret = '123456';
var token_storage = localStorage;//sessionStorage

if (getAuth() == null && !window.location.href.endsWith('/login.html')) {
    window.top.location.href = 'login.html'
}

function getToken() {
    return {"Authorization": getAuth().token_type + ' ' + getAuth().access_token}
}

function getAuth() {
    var auth = token_storage.getItem('auth');
    return JSON.parse(auth);
}

function saveAuth(sResponse) {
    token_storage.setItem("auth", JSON.stringify(sResponse));
}

function clearAuth() {
    token_storage.removeItem('auth');
}

function saveUser(user) {
    token_storage.setItem("user", JSON.stringify(user));
}

function getUser() {
    var auth = token_storage.getItem('user');
    return JSON.parse(auth);
}

function clearUser() {
    token_storage.removeItem("user");
}

function logout() {
    fengtoos.server({
        url: oauth_server + "oauth/token",
        type: 'delete',
        data: {
            'access_token': getAuth().access_token
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Basic ' + Base64.encode(client_id + ':' + client_secret));
        },
        success: function (resp) {
            clearAuth();
            clearUser();
            window.location.href = 'login.html'
        }
    })
}

function fetchToken() {
    var url = window.location.toString();

    var data = {
        'grant_type': 'password',
        'redirect_uri': redirect_uri
    };
    $.ajax({
        url: oauth_server + "/oauth/token",
        type: "post",
        data: data,
        async: false,
        contentType: 'application/x-www-form-urlencoded',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Basic ' + Base64.encode(client_id + ':' + client_secret));
        },
        success: function (sResponse) {
            saveAuth(sResponse);
            //window.location.href = redirect_uri;
        },
        error: function (a, b, c) {
            console.log(a, b, c);
        }
    });
}

function refreshToken() {
    var auth = getAuth();
    var data = {
        'client_id': client_id,
        'client_secret': client_secret,
        'grant_type': 'refresh_token',
        'refresh_token': auth.refresh_token
    };
    fengtoos.server({
        url: oauth_server + "oauth/token",
        data: data,
        async: false,
        contentType: 'application/x-www-form-urlencoded',
        success: function (sResponse) {
            saveAuth(sResponse);
        },
        error: function(){
            clearAuth()
            clearUser()
            window.location.href = base_path
        }
    });

    fengtoos.server({
        url: oauth_server + 'auth/principal',
        type: 'get',
        success: function (result) {
            if(result){
                saveUser(result);
            }
        }
    });
}

function checkToken() {
    $.ajax({
        url: oauth_server + "/oauth/check_token",
        type: "get",
        async: false,
        data: {'token': getAuth().access_token},
        contentType: 'application/x-www-form-urlencoded',
        success: function (sResponse) {
            console.log('check_token : ' + sResponse);
        },
        error: function (a, b, c) {
            console.log(a.responseJSON);
        }
    });
}

function reflushAADToken(){
    //重新刷新权限
    fengtoos.server({
        url: base_path + 'auth/principal',
        type: 'get',
        success: function(r){
            saveUser(r);
        }
    })
}