(function($) {

    var eventEnd = "webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend";

    var defaultOptions = {
        in:"bounce bounceIn",
        out:"bounce bounceOut"
    };

    function Animate(element,options){
        this.element = $(element);
        this.options = $.extend(true,{},options,defaultOptions);
    }

    Animate.prototype.in = function(callback){
        var self = this;
        if(self.element.hasClass("hidden")){
            self.element
                .removeClass("hidden")
                .addClass("animated "+self.options["in"])
                .one(eventEnd,function(){
                    self.element.removeClass("animated "+self.options["in"]);
                    if(callback){
                        callback();
                    }
                });
        }else{
            if(callback){
                callback();
            }
        }
    };
    Animate.prototype.out = function(callback){
        var self = this;
        if(self.element.hasClass("hidden")){
            //donothing
        }else{
            var clz = "animated "+self.options["out"];
            self.element
                .addClass(clz)
                .one(eventEnd, function(){
                    self.element.removeClass(clz);
                    self.element.addClass("hidden");
                    if(callback){
                        callback();
                    }
                });
        }
    };

    $.fn.animated = function(options){
        return new Animate(this,options);
    };
    $.fn.animatedIn = function(callback){
        return new Animate(this,{}).in(callback);
    };
    $.fn.animatedOut = function(callback){
        return new Animate(this,{}).out(callback);
    };
})(jQuery);