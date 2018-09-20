$(function() {
	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
	}

	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();

		$next.slideToggle();
		$this.parent().toggleClass('open');

		if (!e.data.multiple) {
			$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
		};
	}

	var accordion = new Accordion($('#accordion'), false);

	intChecked(); //定位选中的菜单
});

/**
 * 定位选中的菜单
 */
function intChecked(){
	var pathname = window.location.pathname;
	pathname = pathname.split("/")[2];
	$("#menu").find("a").each(function(){
		var href = $(this).attr("href").split("/")[2];
			if(href==pathname){
				$(this).attr("class","active");
				$(this).parent().parent().attr("style","display: block");
				$(this).parent().parent().parent().attr("class","open active");
			}
	});
}