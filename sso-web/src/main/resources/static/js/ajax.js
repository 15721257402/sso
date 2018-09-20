/*
 *  Ajax 统一全局配置
 *  ajax 判断是否登录，没有登录跳转返回地址
 */
$(function () {
    $.ajaxSetup({
        complete: function (XMLHttpRequest, textStatus) {
            var url = XMLHttpRequest.getResponseHeader("redirectUrl");
            if (url) {
                window.location.href = url;
            }
        }
    });
});

