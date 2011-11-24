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
			var possible;
			if (spec.target_type < 1) {
				possible = j$('path.empty');
				if (possible) {
					possible.mouseover(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class') + ' highlighted');
					});

					possible.mouseout(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class').replace('highlighted', ''));
					});

					possible.click(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class').replace('highlighted', ''));
						possible.unbind('mouseover');
						possible.unbind('mouseout');
						card.removeClass('ui-state-highlight');
					});
				}
			}
			else if (spec.target_type <= 3) {
				if (spec.target_type == 1) {
					possible = j$('path.busy');
				} else if (spec.target_type == 2) {
					possible = j$('path.enemy');
				} else if (spec.target_type == 3) {
					possible = j$('path.friend');
				}
				if (possible) {
					possible.mouseover(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class') + ' highlighted');
					});

					possible.mouseout(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class').replace('highlighted', ''));
					});

					possible.click(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class').replace('highlighted', ''));
						possible.unbind('mouseover');
						possible.unbind('mouseout');
						card.removeClass('ui-state-highlight');
					});
				}
			}
			else if (spec.target_type <= 6) {
				if (spec.target_type == 4) {
					possible = j$('path.busy');
				} else if (spec.target_type == 5) {
					possible = j$('path.enemy');
				} else if (spec.target_type == 6) {
					possible = j$('path.friend');
				}
				if (possible) {
					possible.mouseover(function() {
						possible.attr('class', possible.attr('class') + ' highlighted');
					});

					possible.mouseout(function() {
						possible.attr('class', possible.attr('class').replace('highlighted', ''));
					});

					possible.click(function() {
						possible.attr('class', possible.attr('class').replace('highlighted', ''));
						possible.unbind('mouseover');
						possible.unbind('mouseout');
						card.removeClass('ui-state-highlight');
					});
				}
			}
			else if (spec.target_type <= 9) {
				if (spec.target_type == 7) {
					possible = j$('path.hero');
				} else if (spec.target_type == 8) {
					possible = j$('path.hero.enemy');
				} else if (spec.target_type == 9) {
					possible = j$('path.hero.friend');
				}
				if (possible) {
					possible.mouseover(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class') + ' highlighted');
					});

					possible.mouseout(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class').replace('highlighted', ''));
					});

					possible.click(function() {
						var selected = j$(this);
						selected.attr('class', selected.attr('class').replace('highlighted', ''));
						possible.unbind('mouseover');
						possible.unbind('mouseout');
						card.removeClass('ui-state-highlight');
					});
				}
			}
		});
	}
});
