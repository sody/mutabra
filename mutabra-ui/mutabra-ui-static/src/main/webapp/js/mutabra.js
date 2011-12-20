T5.extendInitializers({
	button: function(id) {
		(function($) {
			// create buttons from every element of 'ui-button' class
			$("#" + id).each(function() {
				// get element
				var element = $(this);
				// find element representing primary icon ang parse it's class attribute
				var primary = element.find(".ui-button-icon-primary")
						.removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
						.attr("class");
				// find element representing secondary icon ang parse it's class attribute
				var secondary = element.find(".ui-button-icon-secondary")
						.removeClass("ui-button-icon-primary ui-button-icon-secondary ui-icon")
						.attr("class");
				// find element representing button text and get it's content
				var label = element.find(".ui-button-text").html();
				// check if element should have text
				var text = !element.hasClass("ui-button-icon-only") && !element.hasClass("ui-button-icons-only");
				// if element is label we should create button from input it references for
				if (element.is("label")) {
					element = $("#" + element.attr("for"));
				}
				// create button
				element.button({
					text: text,
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
		var d = j$("#" + spec.id).dialog(spec.options);
		if (spec.options.autoDestroy) {
			d.bind("dialogclose", function() {
				d.remove().dialog("destroy");
			});
		}
	},

	dialoglink : function(spec) {
		j$('#' + spec.id).click(function() {
			j$('#' + spec.dialogId).dialog(spec.action);
			return false;
		});
	},

	carousel: function(spec) {
		j$('#' + spec.id).jcarousel({
			scroll: 1,
			start: spec.selected,
			itemVisibleInCallback: function(carousel, item, index) {
				if (spec.hiddenId) {
					j$('#' + spec.hiddenId).val(spec.values[index - 1]);
				}
				if (spec.callback) {
					spec.callback(carousel, item, index);
				}
			}
		});
	},

	updateChecker: function(spec) {
		var updateCheckerHandler = function() {
			j$.ajax({
				url: spec.url,
				data: {
					check_hero: spec.hero_id
				},
				dataType: 'json',
				success: function(data, status, xhr) {
					if (data && data['ready']) {
						location.reload();
					}
				}
			});
		};

		setInterval(updateCheckerHandler, 10000);
	},

	field: function(spec) {
		j$('#' + spec.id).field({
			x: spec.x,
			y: spec.y,
			selected: spec.selected,
			empty: spec.empty,
			disabled: spec.disabled,
			descriptionId: spec.id + '_description',
			actionsId: spec.id + '_actions'
		});
	},

	card: function(spec) {
		j$('#' + spec.id).card({
			url: spec.url,
			selected: false,
			descriptionId: spec.id + '_description',
			massive: spec.massive,
			supports_enemy_side: spec.supports_enemy_side,
			supports_friend_side: spec.supports_friend_side,
			supports_empty_point: spec.supports_empty_point,
			supports_hero_point: spec.supports_hero_point,
			supports_creature_point: spec.supports_creature_point
		});
	}
});
