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

	card: function(spec) {
		j$('#' + spec.id).click(function() {
			var card = j$(this);
			card.addClass('ui-state-highlight');

			var target_type = spec.target_type;
			var fullSelector;
			var selector = 'path';
			if (!target_type.supports_enemy_side) {
				selector += '.friend';
			}
			if (!target_type.supports_friend_side) {
				selector += '.enemy';
			}
			if (target_type.supports_empty_point) {
				fullSelector = selector + '.empty';
			}
			if (target_type.supports_hero_point) {
				fullSelector = fullSelector ? fullSelector + ',' + selector + '.hero' : selector + '.hero';
			}
			if (target_type.supports_summon_point) {
				fullSelector = fullSelector ? fullSelector + ',' + selector + '.summon' : selector + '.summon';
			}
			if (!fullSelector) {
				fullSelector = selector;
			}

			var possible = j$(fullSelector);
			if (possible) {
				possible.mouseover(function() {
					var selected = target_type.massive ? possible : j$(this);
					selected.attr('class', function(index, attr) {
						return attr + ' highlighted';
					});
				});

				possible.mouseout(function() {
					var selected = target_type.massive ? possible : j$(this);
					selected.attr('class', function(index, attr) {
						return attr.replace(' highlighted', '');
					});
				});

				possible.click(function() {
					var selected = target_type.massive ? possible : j$(this);
					selected.attr('class', function(index, attr) {
						return attr.replace(' highlighted', '');
					});
					possible.unbind('mouseover');
					possible.unbind('mouseout');
					card.removeClass('ui-state-highlight');
				});
			}
		});
	}
});
