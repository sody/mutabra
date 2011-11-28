(function($) {

	var selectedDescription,selectedCard;

	$.widget("mutabra.description", {
		options: {
			selected: false
		},

		_create: function() {
			var self = this;
			this.cardElement = $('#' + this.options.card);
			this.fieldElement = $('#' + this.options.field);
			this.actionsElement = $('#' + this.options.actions);

			if (this.cardElement) {
				this.cardElement.click(function() {
					if (selectedDescription) {
						selectedDescription.option('selected', false);
					}

					selectedDescription = self;
					selectedDescription.option('selected', true);
				});

				if (this.options.selected) {
					this.cardElement.click();
				}
			}

			if (this.fieldElement) {
				this.fieldElement.click(function() {
					if (selectedDescription) {
						selectedDescription.option('selected', false);
					}

					selectedDescription = self;
					selectedDescription.option('selected', true);
				});

				if (this.options.selected) {
					this.fieldElement.click();
				}
			}
		},

		_setOption: function(key, value) {
			if (key === "selected") {
				if (value) {
					this.element.show();
					if (this.fieldElement) {
						this.fieldElement.attr('class', function(index, attr) {
							return attr + ' ui-state-active';
						});
					}
					if (this.actionsElement) {
						this.actionsElement.show();
					}
				} else {
					this.element.hide();
					if (this.fieldElement) {
						this.fieldElement.attr('class', function(index, attr) {
							return attr.replace(' ui-state-active', '');
						});
					}
					if (this.actionsElement) {
						this.actionsElement.hide();
					}
				}
			}

			$.Widget.prototype._setOption.apply(this, arguments);
		},

		destroy: function() {
			$.Widget.prototype.destroy.call(this);
		}
	});

	$.widget("mutabra.card", {
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
			this.element.click(function() {
				if (selectedCard) {
					selectedCard.option('selected', false);
				}

				selectedCard = self;
				selectedCard.option('selected', true);
			});
		},

		_setOption: function(key, value) {
			if (key === "selected") {
				var possible = this._getPossible();
				if (value) {
					this.element.addClass('ui-state-highlight');
					if (possible.filter()) {
						possible.attr('class', function(index, attr) {
							return attr + ' ui-state-highlight';
						});
					}
				} else {
					this.element.removeClass('ui-state-highlight');
					if (possible) {
						possible.attr('class', function(index, attr) {
							return attr.replace(' ui-state-highlight', '');
						});
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
