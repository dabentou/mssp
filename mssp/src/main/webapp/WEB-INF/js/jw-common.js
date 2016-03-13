function templetRepalce(html, arg) {
  var args = arguments;
  for (var i = 1; i < args.length; i++) {
    var rg = new RegExp("\\{" + i + "\\}", "gi");
    html = html.replace(rg, args[i]);
  }
  return html;
}
function setCookie(a, b, f) {
  var d;
  f.z && (d = new Date, d.setTime(d.getTime() + f.z));
  document.cookie = a + "=" + b + (f.domain ? "; domain=" + f.domain : "") + (f.path ? "; path=" + f.path : "") + (d ? "; expires=" + d.toGMTString() : "") + (f.Ba ? "; secure" : "");
}
function getCookie(a) {
  return(a = RegExp("(^| )" + a + "=([^;]*)(;|$)").exec(document.cookie)) ? a[2] : null;
}
function loadScript(url, callback) {
	  //loadScript({“url”:”http://code.jquery.com/jquery.js”,”async”:false,”charset”:”utf-8″,”cache”:false});
	  //loadScript(“http://code.jquery.com/jquery.js”);
	  var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement, script, options;
	  if (typeof url === "object") { options = url; url = undefined; }
	  var s = options || {}; url = url || s.url; s.url = url;
	  callback = callback || s.success;
	  script = document.createElement("script");
	  script.async = s.async || false;
	  script.type = "text/javascript";
	  if (s.charset) {
	    script.charset = s.charset;
	  }
	  if (s.cache === false) {
	    url = url + (/\?/.test(url) ? " & " : " ? ") + "_ = " + (new Date()).getTime();
	  }
	  script.src = url;
	  head.insertBefore(script, head.firstChild);
	  if (callback) {
	    document.addEventListener ? script.addEventListener("load", callback, false) : script.onreadystatechange = function() {
	      if (/loaded|complete/.test(script.readyState)) {
	        script.onreadystatechange = null;
	        callback();
	      }
	    };
	  }
	}
	function EditCache(fm, key) {
	  if (typeof JSON !== 'object') loadScript("http://images.tpvstore.com/html/html/upload/json2-min.js");
	  var root = $CONTEXT_PATH || "";
	  var form = $(fm);
	  this.save = function(fun, val) {
	    var json = val || form.serializeObjectToJson();
	    $.post(root + "/tool/save.cache", {val: JSON.stringify(json), key: key}, cback(fun), "json");
	  };
	  this.load = function(fun) {
	    $.post(root + "/tool/get.cache", {key: key}, function(json) {
	      if (json.data) {
	        var val = eval("(" + json.data.val + ")");
	        form.unSerializeObjectFromJson(val);
	      }
	      if (fun) {
	        fun(val, key, fm, json);
	      }
	    }, "json");
	  };
	  this.remove = function(fun) {
	    return this.save(fun,{});
	  };
	  function cback(fun) {
	    return function(json) {
	      if (fun) {
	        fun(json, key, fm);
	      }
	    };
	  }
	  ;
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
function dateformat(date, fm) {
  var val = {
    // year
    yy: date.getFullYear().toString().substring(2),
    yyyy: date.getFullYear(),
    // month
    M: date.getMonth() + 1,
    // day
    d: date.getDate(),
    // hour
    h: date.getHours(),
    H: date.getHours(),
    // minute
    m: date.getMinutes(),
    // second
    s: date.getSeconds()
  };

  val.HH = (val.H < 10 ? '0' : '') + val.H;
  val.hh = (val.h < 10 ? '0' : '') + val.h;
  val.mm = (val.m < 10 ? '0' : '') + val.m;
  val.ss = (val.s < 10 ? '0' : '') + val.s;
  val.dd = (val.d < 10 ? '0' : '') + val.d;
  val.MM = (val.M < 10 ? '0' : '') + val.M;
  
  return fm.replace(/yyyy/g, val.yyyy).replace(/yy/g, val.yy).
          replace(/MM/g, val.MM).replace(/M/g, val.M).
          replace(/dd/g, val.dd).replace(/d/g, val.d).
          replace(/hh/g, val.hh).replace(/h/g, val.h).
          replace(/HH/g, val.HH).replace(/H/g, val.H).
          replace(/mm/g, val.mm).replace(/m/g, val.m).
          replace(/ss/g, val.ss).replace(/m/g, val.s);
}
(function($) {
  $.fn.serializeObjectToJson = function() {
    /**
     * 此方法代码参考：http://css-tricks.com/snippets/jquery/serialize-form-to-json/
     */
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
      if (o[this.name]) {
        if (!o[this.name].push) {
          o[this.name] = [o[this.name]];
        }
        o[this.name].push(this.value || '');
      } else {
        o[this.name] = this.value || '';
      }
    });
    return o;
    // return $.toJSON(o);
  };
  /**
   * 按json格式添充现有form,基中json的key对应form内的元素name
   * 支持input、radio、select、textarea单值或多值
   * 参数json为json对象
   */
  $.fn.unSerializeObjectFromJson = function(json) {
    if (!json)
      return;
    var values = json;
    var form = $(this);
    /**
     * 得到现有form表单的内容，如果表单有默认值则全部清空，主要针对单值表单项，如：checkbox、radio，因为这些项如果不选中会不被序列化记录暂存，所以
     * 在反序列化时，无法处理可能存在默认值的这些表单项，所以这里做一下特殊处理
     */
    var defaultFormJsonValues = form.serializeObjectToJson();
    $.each(defaultFormJsonValues, function(key, value) {
      var object = form.find("*[name='" + key + "']");
      if (object.length == 1) {
        var ty = object.attr("type");
        if (ty && (ty.toLowerCase() == 'radio' || ty.toLowerCase() == 'checkbox')) {
          object.attr("checked", false);
        }
      }
    });
    //加载需要添充的表单内容
    $.each(values, function(key, value) {
      var object = form.find("*[name='" + key + "']");//得到form内指定name的控件
      if (object.length == 1) {
        var ty = object.attr("type");
        if (ty && (ty.toLowerCase() == 'radio' || ty.toLowerCase() == 'checkbox')) {
          if (object.val() == value) {
            object.attr("checked", true);
          }
          return true;
        } else {
          object.val(value);
        }
      } else if (object.length > 1) {
        object.each(function(i) {
          var ty = object.attr("type");
          if (ty && (ty.toLowerCase() == 'radio' || ty.toLowerCase() == 'checkbox')) {
            if ($(this).val()==value) {
              $(this).attr("checked", true);
            } else {
              $(this).attr("checked", false);
            }
          } else {
            $(this).val(value[i]);
          }
        });
      }
    });
  };
  $.extend({
    loadScript: function(js) {
      var oHead = document.getElementsByTagName('HEAD').item(0);
      var oScript = document.createElement("script");
      oScript.type = "text/javascript";
      oScript.src = js;
      oHead.appendChild(oScript);
    },
    request: function(fm, func, option) {
      option = option || {};
      $.submit(fm, function() {
        var data = arguments[0], message = data.success ? data.messages : data.errors, html = [];
        for (var item in message) {
          html.push(message[item]);
        }
        if (message.redirect && !option.noredirect) {
          location.href = message.redirect;
          return;
        }
        if (!data.success && !message.redirect) {
          alert(html.join(","));
        }
        func ? func.apply(this, arguments) : "";
      }, option);
    },
    submit: function(fm, func, option) {
      var xhr = null;
      $(fm).validationEngine("attach", {"onValidationComplete": function(f, success) {
          if (success && !xhr) {
            var fm = $(f);
            $(".submit", fm).attr("disabled", true);
            var opt = $.extend(true, {
              "url": fm.attr("action"),
              "data": fm.serialize(),
              "error": function() {
                xhr = null;
                $(":submit", fm).attr("disabled", false);
                alert("ERROR");
              }, "success": function() {
                xhr = null;
                func.apply(this, arguments);
                $(":submit", fm).attr("disabled", false);
              }, "type": "POST", "dataType": "json", "mimeType": "text/json"
            }, option);
            if (opt.dataType === "jsonp" || opt.type === "GET") {
              opt.data = encodeURI(opt.data);
            }
            xhr = $.ajax(opt);
          }
          return false;
        }
      });
    },
    crossSubmit: function(fm, func, option) {
      $.request(fm, func, $.extend(true, {dataType: "jsonp"}, option));
    }
  });
})(jQuery);