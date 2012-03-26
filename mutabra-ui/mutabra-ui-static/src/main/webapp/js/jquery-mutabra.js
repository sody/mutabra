(function($) {
	$.mutabra = $.mutabra || {};
}(jQuery));

(function($) {

	var selected, selectedField, selectedCard;
	var applyHandler = function(event) {
		var field = $(event.target).data('field');
		if (selectedCard && field) {
			selectedCard.apply(field);
			selectedField.disable();
		}
	};
	var escapeHandler = function(event) {
		if (event.keyCode == $.ui.keyCode.ESCAPE && selectedCard) {
			selectedCard.cancel();
		}
	};

	$.widget('mutabra.field', {
		options: {
			x: 0,
			y: 0,
			selected: false,
			disabled: false,
			empty: true
		},

		_create: function() {
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
		},

		select: function() {
			if (!this.options.empty && !selectedCard) {
				selected = this.description;
				if (selectedField) {
					selectedField.option('selected', false);
				}
				selectedField = this;
				selectedField.option('selected', true);
			}
		},

		showDescription: function() {
			if (!this.options.empty) {
				if (selected) {
					selected.hide();
				}
				this.description.show();
			}
		},

		hideDescription: function() {
			if (!this.options.empty) {
				this.description.hide();
				if (selected) {
					selected.show();
				}
			}
		},

		_setOption: function(key, value) {
			switch (key) {
				case 'selected':
					if (value) {
						this.element.attr('class', function(index, attr) {
							return attr + ' active';
						});
						if (this.actions) {
							this.actions.show();
						}
					} else {
						this.element.attr('class', function(index, attr) {
							return attr.replace(' active', '');
						});
						if (this.actions) {
							this.actions.hide();
						}
					}
					break;
				case 'disabled':
					if (value) {
						this.element.attr('class', function(index, attr) {
							return attr + ' disabled';
						});
						this.description.addClass('disabled');
						this.actions.find(':mutabra-card').each(function() {
							$(this).data('card').disable();
						});
					} else {
						this.element.attr('class', function(index, attr) {
							return attr.replace(' disabled', '');
						});
						this.description.removeClass('disabled');
						this.actions.find(':mutabra-card').data('card').enable();
					}
					break;
			}

			$.Widget.prototype._setOption.apply(this, arguments);
		},

		destroy: function() {
			$.Widget.prototype.destroy.call(this);
		}
	});

	$.widget('mutabra.card', {
		options: {
			url: '',
			massive: false,
			supports_enemy_side: false,
			supports_friend_side : false,
			supports_empty_point : false,
			supports_hero_point : false,
			supports_creature_point: false
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
			if (!this.options.disabled) {
				selected = this.description;
				if (selectedCard) {
					selectedCard.option('selected', false);
				}
				selectedCard = this;
				selectedCard.option('selected', true);
				$(document).keydown(escapeHandler);
			}
		},

		cancel: function() {
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
			var url = this.options.url;
			$.ajax({
				url: url,
				data: {
					x: field.option('x'),
					y: field.option('y')
				}
			});
			this.cancel();
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
			switch (key) {
				case 'selected':
					var possible = this._getPossible();
					if (value) {
						this.element.addClass('active');
						if (possible) {
							possible.attr('class', function(index, attr) {
								return attr + ' highlight';
							});
							possible.click(applyHandler);
						}
					} else {
						this.element.removeClass('active');
						if (possible) {
							possible.attr('class', function(index, attr) {
								return attr.replace(' highlight', '');
							});
							possible.unbind('click', applyHandler);
						}
					}
					break;
				case 'disabled':
					if (value) {
						this.element.addClass('disabled');
						this.description.addClass('disabled');
					} else {
						this.element.removeClass('disabled');
						this.description.removeClass('disabled');
					}
					break;
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
			if (this.options.supports_creature_point) {
				fullSelector = fullSelector ? fullSelector + ',' + selector + '.creature' : selector + '.creature';
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
