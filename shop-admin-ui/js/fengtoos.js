/*
 *@正则验证工具
*/
window.regTools = {
    /*密码验证*/
    isSafePwd: function (value) {
        var reg = /^[a-zA-Z0-9_\*\.\?\+\$\^\[\]\(\)\{\}\|\\\/]{6,30}$/;
        if (reg.test(value)) {
            return true;
        }
        return false;
    },
    /*判断value是否为6为数值*/
    isSixNum: function (value) {
        var reg = /^\d{6}$/;
        if (reg.test(value)) {
            return true;
        }
        return false;
    },
    /*
     *@验证 邮箱 或者 手机号
    */
    isPhoneOrMail: function (value) {
        value = $.trim(value);
        if (CONST.reg.phone.test(value) || CONST.reg.mail.test(value)) {
            return true;
        }
        return false;
    },
    /*
     *@联系方式验证
     *@规则1：手机号：11位数字，以1开头。
     *@规则2：区号+号码，区号以0开头，3位或4位； 号码由7位或9位数字组成
    */
    isPhoneStrong: function (value) {
        var reg = /^1[2|3|4|5|6|7|8|9][0-9]\d{8}|((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
        if (reg.test($.trim(value))) {
            return true;
        }
        return false;
    },
    /*邮箱*/
    isMail: function (value) {
        var reg = CONST.reg.mail;
        if (reg.test($.trim(value))) {
            return true;
        }
        return false;
    },
    /*长度是否为空*/
    isLengthNull: function (value) {
        if ($.trim(value).length > 0) {
            return true;
        }
        return false;
    },
    /*
     *@非空验证
    */
    isNull: function (value) {
        var reg = /^(\s*\S+)|(\S+)|(\s*\S+\s*)$/;
        if (reg.test(value)) {
            return true;
        }
        return false;
    },
    /*
     *@手机号验证
     */
    isMobilePhone: function (value) {
        var reg = /^1[2|3|4|5|6|7|8|9][0-9]\d{8}?$/;
        if (reg.test($.trim(value))) {
            return true;
        }
        return false;
    }
};


// 常用工具
window.fengtoos = {
    /*
     *@跨页面传参并跳转方法
     *@address: 需要跳转的页面地址
     *@params: JSON格式，传递的参数
    */
    sendParam: function (params) {
        var _params = {
            address: "",
            param: {}
        };
        params = $.extend({}, _params, params);

        var urls = params.address + "?";
        var k = "";
        for (var key in params.param) {
            var z = key + "=" + params.param[key] + "&";
            urls += z;
        }
        var address = urls.substring(0, urls.length - 1);
        setTimeout(function () {
            window.location.href = address;
        }, 100);

    },
    /*
     *@获取地址中的参数
     *@name:参数名称
    */
    getUrlSearchParams: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return (r[2]);
        return "";
    },
    /*json格式工具*/
    JSON: {
        isJson: function (obj) {/*判断是否为json格式*/
            var isjson = typeof (obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
            return isjson;
        },
        parse: function (s) {
            if (!this.isJson(s)) {
                try {
                    return JSON.parse(s);
                } catch (e) {
                    try {
                        return eval("(" + s + ")");
                    } catch (e) {
                        return {};
                    }
                }
            } else {
                return s;
            }
        },
        stringify: function (obj) {
            try {
                return JSON.stringify(obj);
            } catch (e) {
                if (obj instanceof Object) {
                    var sOutput = "";
                    if (obj.constructor === Array) {
                        for (var nId = 0; nId < obj.length; nId++) {
                            sOutput += this.stringify(obj[nId]) + ",";
                        }
                        return "[" + sOutput.substr(0, sOutput.length - 1) + "]";
                    }
                    if (obj.toString !== Object.prototype.toString) {
                        return "\"" + obj.toString().replace(/"/g, "\\$&") + "\"";
                    }
                    for (var sProp in obj) {
                        if (obj.hasOwnProperty(sProp)) {
                            sOutput += "\"" + sProp.replace(/"/g, "\\$&") + "\":" + this.stringify(obj[sProp]) + ",";
                        }
                    }
                    return "{" + sOutput.substr(0, sOutput.length - 1) + "}";
                }
                return typeof obj === "string" ? "\"" + obj.replace(/"/g, "\\$&") + "\"" : String(obj);
            }
        }
    },

    /**
     * 初始化表单数据(根据表单id)
     * @param url
     * @param formId
     */
    initForm: function (params) {
        var _params = {
            url: 0,
            formId: "",
        };
        params = $.extend({}, _params, params);
        $.ajax({
            url: params.url,
            type: 'GET',
            async: false,
            success: function (e) {
                if (e != null) {
                    $.map($(params.formId).serializeArray(), function (item, index) {
                        $(params.formId + ' #' + item.name).val(e[item.name]);
                    });
                } else {
                    alert('初始化数据失败,数据异常!');
                }
            },
            error: function (e) {
                alert('网络故障,请求失败!');
            }
        });
    },
    /*
     *@通用ajax方法
     *@params{
     onlineUrl:""
     offlineUrl:""
     }
    */
    server: function (params) {
        var _params = {
            ajaxCount: 0,
            url: "",
            offlineUrl: "",
            type: "post",//默认是post
            async: false,
            data: {},//服务器数据参数
            success: null,//成功后的回调函数
            error: null,//失败后的回调函数
            beforeSend: function (xhr) {

            }
        };
        params = $.extend({}, _params, params);

        var _selfFn = (arguments.callee);
        $.ajax({
            type: params.type,
            url: params.url,
            data: params.data,
            async: params.async,
            beforeSend: function(xhr){
                if(this.url.endsWith('/oauth/token')){
                    params.beforeSend(xhr);
                    return true;
                }
                if (getAuth() == null){
                    window.location.href = 'login.html'
                }
                var auth = getAuth();
                if(auth != null){
                    xhr.setRequestHeader("Authorization", auth.token_type + ' ' + auth.access_token);
                } else {
                    return false;
                }
                return true;
            },
            // function(xhr){
            //    xhr.setRequestHeader('Access-Control-Allow-Origin','*');
            // },
            success: function (data) {
                if (typeof (params.success) == "function") {
                    params.success(data);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

                params.ajaxCount++;

                if (typeof (params.error) == "function") {
                    params.error();
                    return false;
                }

                var status = parseInt(XMLHttpRequest.status);
                if(status == 401 && XMLHttpRequest.responseJSON.error =='invalid_token'){
                    refreshToken();
                }else{
                    fengtoos.msg({content: XMLHttpRequest.responseJSON.msg, icon: 2})
                }
                // if ((status == 404 || status == 405 ) && $.trim(params.offlineUrl).length > 0) {
                //
                //     if (params.onlineUrl.search(/.json/) == -1) {//自身调用一次
                //         var tempUrl = params.onlineUrl;
                //         params.onlineUrl = params.offlineUrl;
                //         params.offlineUrl = tempUrl;
                //         params.type = "get";
                //     }
                //
                //     if (params.ajaxCount <= 1) {
                //         //自身调用,有且只调用一次
                //         _selfFn(params);
                //     }
                //
                // } else {
                //     //alert(XMLHttpRequest + " " + textStatus + " " + errorThrown);
                // }
            }
        });

    },
    // $dom: 进行倒计时的元素
    // expire：倒计时的时间
    // className：进入倒计时元素的样式
    timeCountDown: function ($dom, expire, className) {
        $dom.addClass(className);
        $dom.html(expire + "秒");
        var timer = setInterval(function () {
            expire--;
            if (expire < 0) {
                $dom.html("获取验证码");
                $dom.removeClass(className);
                clearInterval(timer);
                return;
            }
            $dom.html(expire + "秒");
        }, 1000);
    },

    msg: function (params) {
        var _params = {
            content: '操作成功',
            icon: 1,
            time: 1500,
            go: null
        };
        params = $.extend({}, _params, params);
        layer.msg(params.content, {icon: params.icon, time: params.time}, params.go);
    }
}