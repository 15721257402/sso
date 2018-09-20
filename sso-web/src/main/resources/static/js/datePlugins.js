$(function(){
    if ($.datepicker){
        $.datepicker.setDefaults({
            closeText: '关闭',
            prevText: '<上月',
            nextText: '下月>',
            currentText: '今天',
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月'],
            monthNamesShort: ['一', '二', '三', '四', '五', '六',
                '七', '八', '九', '十', '十一', '十二'],
            dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
            weekHeader: '周',
            dateFormat: 'yy/mm/dd',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: true,
            yearSuffix: '年',
            monthSuffix: '月'
        });
    }

    $('.timeTool').datepicker({
        onSelect: function(dateText,inst){
            // location.href = "investmentManagement.htm?stime="+$('#beginTimeStr').val()+"&etime="+$('#endTimeStr').val();
        }
    });

    var index = 0; //存储选中导航栏的下标
  	// 获取缓存的下标
    if( window.sessionStorage.getItem("val") ){
    	index = window.sessionStorage.getItem("val")
    }

    $('#candan li a').removeClass("cur");
    $('#candan li').eq(index).find("a").addClass("cur");


	$('#candan li a').click(function(){

        index = $(this).parents("b").index();
        // 将下标存入缓存
        window.sessionStorage.setItem("val",index);

        $('#candan li a').removeClass("cur");
        $(this).addClass("cur");
	 });


    // $('#candan li').eq(index).find("a").addClass("cur");
});