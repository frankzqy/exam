"function"!=typeof Array.prototype.map&&(Array.prototype.map=function(a,b){var c=[];if("function"==typeof a)for(var d=0,e=this.length;e>d;d++)c.push(a.call(b,this[d],d,this));return c}),Array.prototype.indexOf||(Array.prototype.indexOf=function(a){var b=this.length>>>0,c=Number(arguments[1])||0;for(c=0>c?Math.ceil(c):Math.floor(c),0>c&&(c+=b);b>c;c++)if(c in this&&this[c]===a)return c;return-1}),window.console=window.console||function(){var a={};return a.log=a.warn=a.debug=a.info=a.error=a.time=a.dir=a.profile=a.clear=a.exception=a.trace=a.assert=function(){},a}();var JPlaceHolder={_check:function(){return"placeholder"in document.createElement("input")},init:function(){this._check()||this.fix()},fix:function(){$(":input[placeholder]").each(function(a,b){var c=$(this),d=c.attr("placeholder"),e=d;""!=c.val()&&(d=""),c.wrap($("<div></div>").css({position:"relative",zoom:"1",border:"none",background:"none",padding:"none",margin:"none"}));var f=c.position(),g=c.outerHeight(!0),h=c.css("padding-left"),i=$("<span></span>").text(d).css({position:"absolute",zIndex:"3",left:f.left,top:f.top,height:g,lineHeight:g+"px",paddingLeft:h,color:"#ccc"}).appendTo(c.parent());c.focusin(function(a){i.hide()}).focusout(function(a){c.val()||i.show()}).change(function(a){c.val()?i.hide():(d=e,i.text(d),i.show())}),i.click(function(a){i.hide(),c.focus()})})}};$(function(){JPlaceHolder.init()}),$(function(){window.PIE&&$(".ue-pie").each(function(){PIE.attach(this)})}),function(a){a.fn.draggable=function(){var b=a(this),c=a(this).next();b.addClass("ue-drag-left"),c.addClass("ue-drag-right"),b.after("<div class='ue-drag-middle'></div><div class='ue-drag-back'></div>");var d=a(".ue-drag-middle"),e=a(".ue-drag-back");d[0].style.left=b[0].offsetWidth-3+"px",e[0].style.left=b[0].offsetWidth-3+"px",d[0].onmouseover=function(){e[0].style.display="block",d[0].style.zIndex=30},e[0].onmouseover=function(){e[0].style.display="block",e[0].style.cursor="w-resize"},e[0].onmouseout=function(){e[0].style.display="none",e[0].style.cursor=""},d[0].onmouseout=function(){e[0].style.display="none"},d[0].onmousedown=function(){e[0].style.display="none"};var f=a("iframe");e[0].onmousedown=function(a){var g=(a||event).clientX;return e[0].style.display="block",e[0].style.cursor="w-resize",f.hide(),e[0].left=e[0].offsetLeft,document.onmousemove=function(a){e[0].style.display="block",e[0].style.cursor="w-resize";var b=e[0].left+((a||event).clientX-g),a=a||window.event;a.target||a.srcElement;return maxT=document.body.clientWidth,0>b&&(b=0),b>maxT&&(b=maxT),e[0].style.left=b+"px",!1},document.onmouseup=function(){document.onmousemove=null,document.onmouseup=null,f.show(500),c[0].style.left=e[0].style.left,d[0].style.left=e[0].style.left,b[0].style.width=e[0].style.left,d[0].style.left=b[0].offsetWidth-3+"px",e[0].style.left=b[0].offsetWidth-3+"px",e.css({display:"none"}),e[0].releaseCapture&&e[0].releaseCapture()},e[0].setCapture&&e[0].setCapture(),!1}}}(jQuery);