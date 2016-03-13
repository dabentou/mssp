$(function(){
		  $(document).ready(function(){
		    $(".news_li").after("<ul class='swap'></ul>");
		    $('.swap').html($('.news_li').html());
		    x = $('.news_li');
		    y = $('.swap');
		    h = $('.news_li').height(); 
		    setTimeout(b,3000);//滚动间隔时间 现在是3秒
		    
		    $(".first-menu>li>a").each(function(index, element) {
		            $(element).click(function(){
		        $(this).parent("li").siblings("li").removeClass("current");
		        $(this).parent("li").addClass("current").find(".second-menu").show(1000);
		      })
		        });
		  });
});
function b(){ 
    t = parseInt(x.css('top'));
    y.css('top','40px');  
    x.animate({top: t - 40 + 'px'},'slow');
    if(Math.abs(t) == h-40){
      y.animate({top:'0px'},'slow');
      z=x;
      x=y;
      y=z;
    }
    setTimeout(b,3000);//滚动间隔时间 现在是3秒
}

function setCookie(a, b, f) {
	  var d;
	  f.z && (d = new Date, d.setTime(d.getTime() + f.z));
	  document.cookie = a + "=" + b + (f.domain ? "; domain=" + f.domain : "") + (f.path ? "; path=" + f.path : "") + (d ? "; expires=" + d.toGMTString() : "") + (f.Ba ? "; secure" : "");
	}
	function getCookie(a) {
	  return(a = RegExp("(^| )" + a + "=([^;]*)(;|$)").exec(document.cookie)) ? a[2] : null;
	}
	
	
function json$ref(json){
	  var o$ref=[];
	  function is$ref(obj){
	    if("object" === typeof obj){
	      var ii = 0;
	      for(var ee in obj){
	        ii++;
	        if("object" === typeof obj[ee]){
	          if(is$ref(obj[ee])){//递归
	            o$ref.push([obj, ee]);;
	          }
	        }
	      }
	      if(ii === 1 && "string" === typeof obj['$ref']){
	        return true;
	      }
	    }
	    return false;
	  }
	  is$ref(json);
	  for(var i in o$ref){
	    var nobj = o$ref[i][0], pp = o$ref[i][1], v = nobj[pp]["$ref"];
	    if(v.indexOf("$") === 0){
	      try{ 
	        nobj[pp] = eval("(json" + v.substr(1) + ")"); 
	      }catch(e){}
	    }
	  }
	  return json;
	}

