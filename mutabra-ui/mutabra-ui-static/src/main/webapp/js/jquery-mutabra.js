(function($) {
	$.mutabra = $.mutabra || {};
}(jQuery));

(function($) {

	var selected, selectedField, selectedCard;
	var applyHandler = function(event) {
		var field = $(event.target).data('field');
		if (selectedCard && field) {
			selectedCard.apply(field);
		}
	};
	var escapeHandler = function(event) {
		if (event.keyCode == $.ui.keyCode.ESCAPE && selectedCard) {
			selectedCard.escape();
		}
	};

	$.widget('mutabra.field', {
		options: {
			selected: false,
			disabled: true
		},

		_create: function() {
			if (!this.options.disabled) {
				var self = this;
				this.description = $('#' + this.options.descriptionId);
				this.actions = $('#' + this.options.actionsId);

				this.element
						.mouseover(function() { self.showDescription(); })
						.mouseout(function() { self.hideDescription(); })
						.click(function() { self.select(); });

				if (this.options.selected) {
					self.select();
					selected.show();
				}
			}
		},

		select: function() {
			if (!selectedCard) {
				selected = this.description;
				if (selectedField) {
					selectedField.option('selected', false);
				}
				selectedField = this;
				selectedField.option('selected', true);
			}
		},

		showDescription: function() {
			if (selected) {
				selected.hide();
			}
			this.description.show();
		},

		hideDescription: function() {
			this.description.hide();
			if (selected) {
				selected.show();
			}
		},

		_setOption: function(key, value) {
			if (key === 'selected') {
				if (value) {
					this.element.attr('class', function(index, attr) {
						return attr + ' ui-state-active';
					});
					if (this.actions) {
						this.actions.show();
					}
				} else {
					this.element.attr('class', function(index, attr) {
						return attr.replace(' ui-state-active', '');
					});
					if (this.actions) {
						this.actions.hide();
					}
				}
			}

			$.Widget.prototype._setOption.apply(this, arguments);
		},

		destroy: function() {
			$.Widget.prototype.destroy.call(this);
		}
	});

	$.widget('mutabra.card', {
		options: {
			massive: false,
			supports_enemy_side: false,
			supports_friend_side : false,
			supports_empty_point : false,
			supports_hero_point : false,
			supports_summon_point: false
		},

		_create: function() {
			var self = this;
			this.description = $('#' + this.options.descriptionId);

			this.element
					.mouseover(function() { self.showDescription(); })
					.mouseout(function() { self.hideDescription(); })
					.click(function() { self.select(); });
		},

		select: function() {
			selected = this.description;
			if (selectedCard) {
				selectedCard.option('selected', false);
			}
			selectedCard = this;
			selectedCard.option('selected', true);
			$(document).keydown(escapeHandler);
		},

		escape: function() {
			// unselect card
			this.option('selected', false);
			selectedCard = null;
			selected.hide();

			//select and show selectedfield
			selectedField.select();
			selected.show();
			$(document).unbind('keydown', escapeHandler);
		},

		apply: function(field) {
			alert('Card:' + this.element.attr('id') + ' Field:' + field.element.attr('id'));
			this.escape();
		},

		showDescription: function() {
			if (selected) {
				selected.hide();
			}
			this.description.show();
		},

		hideDescription: function() {
			this.description.hide();
			if (selected) {
				selected.show();
			}
		},

		_setOption: function(key, value) {
			if (key === 'selected') {
				var possible = this._getPossible();
				if (value) {
					this.element.addClass('ui-state-highlight');
					if (possible) {
						possible.attr('class', function(index, attr) {
							return attr + ' ui-state-highlight';
						});
						possible.click(applyHandler);
					}
				} else {
					this.element.removeClass('ui-state-highlight');
					if (possible) {
						possible.attr('class', function(index, attr) {
							return attr.replace(' ui-state-highlight', '');
						});
						possible.unbind('click', applyHandler);
					}
				}
			}

			$.Widget.prototype._setOption.apply(this, arguments);
		},

		_getPossible: function() {
			var fullSelector;
			var selector = 'path';
			if (!this.options.supports_enemy_side) {
				selector += '.friend';
			}
			if (!this.options.supports_friend_side) {
				selector += '.enemy';
			}
			if (this.options.supports_empty_point) {
				fullSelector = selector + '.empty';
			}
			if (this.options.supports_hero_point) {
				fullSelector = fullSelector ? fullSelector + ',' + selector + '.hero' : selector + '.hero';
			}
			if (this.options.supports_summon_point) {
				fullSelector = fullSelector ? fullSelector + ',' + selector + '.summon' : selector + '.summon';
			}
			if (!fullSelector) {
				fullSelector = selector;
			}
			return $(fullSelector);
		},

		destroy: function() {
			$.Widget.prototype.destroy.call(this);
		}
	});

}(jQuery) );
