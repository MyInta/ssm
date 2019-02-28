$(function(){
	/**
	 * 1、得到所有有关错误提示的元素，遍历并实现判断显示功能
	 */
	$(".labelError").each(function(){
		showError($(this));
	});
	/**
	 * 2、实现光标离开提交按钮时候按钮图标的转换功能
	 */
	$("#submitBtn").hover(
			function(){
				$("#submitBtn").attr("src","/goods/images/regist2.jpg");
				},
			function(){
				$("#submitBtn").attr("src","/goods/images/regist1.jpg");	
				}
	);
	/**
	 * 3、实现输入框获得焦点后，去掉后面的错误信息
	 */
	$(".inputClass").focus(
			function(){
				var labelId=$(this).attr("id")+"Error";//通过输入框找到label的ID
				$("#"+labelId).text("");//将label的内容清空
				showError($("#"+labelId));//隐藏没有错误信息的label
			}
	);
	/**
	 * 4、失去焦点后进行校验
	 */
	$(".inputClass").blur(
			function(){
				var text = $(this).attr("id");
				var funId ="varidate"+text.substring(0,1).toUpperCase()+text.substring(1)+"()";
				eval(funId);
			}
	);
	/**
	 * 5、提交表单校验
	 * varidateLoginname();
	 * varidateLoginpass();
	 * varidateReloginPass();
	 * varidateEmail();
	 * varidateVerifyCode();
	 * 
	 */
	$("#registForm").submit(
//			function(){
//				var bool=true;
//				$(".inputClass").each(
//						function(){
//							var text = $(this).attr("id");
//							var funId ="varidate"+text.substring(0,1).toUpperCase()+text.substring(1)+"()";
//							if(!eval(funId)){
//								bool=false;
//							}
//						}
//				);
//				return bool;	
//			}
			function(){
				var bool=true;
				if(!varidateLoginname()){
					bool=false;
				}
				if(!varidateLoginpass()){
					bool=false;
				}
				if(!varidateReloginPass()){
					bool=false;
				}
				if(!varidateEmail()){
					bool=false;
				}
				if(!varidateVerifyCode()){
					bool=false;
				}
				return bool;
			}
			);
});
/**
 * 遍历每个元素，判断存在内容则显示，无则不显示
 * @param ele
 * @returns
 */
function showError(ele){
	var text = ele.text();//获取元素内容
	if(!text){//如果没有内容
		ele.css("display","none");//隐藏元素
	}else{//如果有内容
		ele.css("display","");//显示元素
	}
}
/**
 * 获取<img>
 * 修改路径（通过加入时间的方式来确保其不重复）
 * @returns
 */
function _hyz(){
	$("#imgVerifyCode").attr("src","/goods/VerifyCodeServlet?a="+new Date().getTime());
}
/**
 * 校验登陆名称
 * @returns
 */
function varidateLoginname(){
	var id = "loginname";
	var value = $("#"+id).val();//获取输入框内的值
	//1、非空
	if(!value){
		/**
		 * 获取对应label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("登录名不可以为空");
		showError($("#"+id+"Error"));
		return false;
	}
	//2、长度
	if(value.length>12||value.length<3){
		$("#"+id+"Error").text("登录名长度请控制在3-12位之间");
		showError($("#"+id+"Error"));
		return false;
	}
	//3、是否注册了
	$.ajax({
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateLoginName",loginname:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//异步请求，true的话不等服务器返回，直接进行下一步
		cache:false,
		success:function(result){
			if(!result){//如果已注册
				$("#"+id+"Error").text("登录名已注册");
				showError($("#"+id+"Error"));
				return false;	
			}
		}
	});
	return true;
}
/**
 * 校验登陆密码
 * @returns
 */
function varidateLoginpass(){
	var id = "loginpass";
	var value = $("#"+id).val();//获取输入框内的值
	//1、非空
	if(!value){
		/**
		 * 获取对应label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("密码不可以为空");
		showError($("#"+id+"Error"));
		return false;
	}
	//2、长度
	if(value.length>12||value.length<6){
		$("#"+id+"Error").text("密码长度请控制在6-12位之间");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}
/**
 * 再次校验登陆密码
 * @returns
 */
function varidateReloginPass(){
	var id = "reloginPass";
	var value = $("#"+id).val();//获取输入框内的值
	//1、非空
	if(!value){
		/**
		 * 获取对应label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("二次密码不可以为空");
		showError($("#"+id+"Error"));
		return false;
	}
	//2、是否和一次密码相同
	if(value!=$("#loginpass").val()){
		$("#"+id+"Error").text("二次密码不匹配");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}
/**
 * 校验Email
 * @returns
 */
function varidateEmail(){
	var id = "email";
	var value = $("#"+id).val();//获取输入框内的值
	//1、非空
	if(!value){
		/**
		 * 获取对应label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("邮箱地址不可以为空");
		showError($("#"+id+"Error"));
		return false;
	}
	//2、邮箱格式校验"^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$"
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)){
		$("#"+id+"Error").text("邮箱格式出错");
		showError($("#"+id+"Error"));
		return false;
	}
	//3、是否注册了
	$.ajax({
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateEmail",email:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//异步请求，true的话不等服务器返回，直接进行下一步
		cache:false,
		success:function(result){
			if(!result){//如果已注册
				$("#"+id+"Error").text("邮箱地址已注册");
				showError($("#"+id+"Error"));
				return false;
			}
		}
	});
	return true;
}
/**
 * 校验验证码
 * @returns
 */
function varidateVerifyCode(){
	var id = "verifyCode";
	var value = $("#"+id).val();//获取输入框内的值
	//1、非空
	if(!value){
		/**
		 * 获取对应label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").test("验证码不可以为空");
		showError($("#"+id+"Error"));
		return false;
	}
	//2、长度
	if(value.length!=4){
		$("#"+id+"Error").text("验证码长度请控制在4位");
		showError($("#"+id+"Error"));
		return false;
	}
	//3、是否正确校验
	$.ajax({
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxValidateVerifyCode",verifyCode:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//异步请求，true的话不等服务器返回，直接进行下一步
		cache:false,
		success:function(result){
			if(!result){//如果不符合校验结果
				$("#"+id+"Error").text("校验失败");
				showError($("#"+id+"Error"));
				return false;
			}
		}
	});
	return true;
}
