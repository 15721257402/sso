$(function() {  
      
    //自定义密码校验规则  
    $.validator.addMethod("password", function(value, element, params) {  
        //6~12位数字和字母的组合 (不能包含空格、特殊字符) 
    	if( value==null || value.trim()=="" ){
			return false;
		}
		var reg1 = /^(?![^a-zA-Z]+$)(?!\D+$).{6,12}$/;
		var reg2 = /^[0-9a-zA-Z]{6,12}$/;
		return this.optional(element) || ( (reg1.test(value)) && (reg2.test(value)) );  
    }, $.validator.format("密码必须是6~12位数字和字母的组合"));  
  
    //自定义不等于校验规则  
	jQuery.validator.addMethod("notEquals", function(value, element,params) {
		if( value != params ){
			return true;
		}else{
			return false;
		}
	});
    
    //自定义电话号校验规则 
	jQuery.validator.addMethod("telphoneCheck", function(value, element) {
    	if(value==""){
    		return true;
    	}
        var telphoneRule = /^[0-9]{3,5}-[0-9]{7,8}$/;
 		return this.optional(element) || (telphoneRule.test(value));
 		});
    
    //自定义手机号校验规则  
	jQuery.validator.addMethod("mobile", function(value, element) {
    	if(value==""){
    		return true;
    	}
    	 var mobileRule = /^(1[1-8][0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|170)\d{8}$/;
 		return this.optional(element) || (mobileRule.test(value));
 		}, "手机号码不正确");
	
	//自定义不能晚于当前日期校验规则
	jQuery.validator.methods.compareDateTo = function(value, element, param) {
   	 var date1 = (param=="")?new Date():new Date(Date.parse($(param).val()));
   	 var date2 = new Date(Date.parse(value));
        return  this.optional(element) ||date1 > date2;
    };
    
    //自定义校验规则（最长长度为10位,由字母与数字组成）
    jQuery.validator.addMethod("coodCheck", function(value, element) { 
		 var chrnum =/^[0-9A-Za-z]{1,10}$/; 
			return this.optional(element) || (chrnum.test(value)); 
			return false;
	});
    
    //自定义校验规则（最长长度为20位,由字母与数字组成）
    jQuery.validator.addMethod("coodCheck20", function(value, element) { 
		 var chrnum =/^[0-9A-Za-z]{1,20}$/; 
			return this.optional(element) || (chrnum.test(value)); 
			return false;
	});
    
    //自定义校验规则（长度为38位,不允许输入中文）
    jQuery.validator.addMethod("coodCheck38", function(value, element) { 
		 var chrnum =/^[0-9A-Za-z{}-]{1,38}$/; 
			return this.optional(element) || (chrnum.test(value)); 
			return false;
	});
});  