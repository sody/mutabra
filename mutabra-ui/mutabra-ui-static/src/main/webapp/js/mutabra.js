T5.extendInitializer({
	"jquery.ui": function() {
		(function($) {
			$(".ui-button").each(function() {
				var label = $(this).find(".ui-button-text").html();
				var primary = $(this).find(".ui-button-icon-primary")
						.removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
						.attr("class");
				var secondary = $(this).find(".ui-button-icon-secondary")
						.removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
						.attr("class");
				$(this).button({
					label: label,
					icons: {
						primary: primary,
						secondary: secondary
					}
				});
			});
		})(jQuery);
	},

	dialog: function(spec) {
		j$("#" + spec.id).dialog(spec.options);
	},

	dialoglink : function(spec) {
		j$('#' + spec.id).click(function() {
			j$('#' + spec.dialogId).dialog(spec.action);
			return false;
		});
	}
});
