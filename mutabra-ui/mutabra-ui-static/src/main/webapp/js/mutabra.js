T5.extendInitializers({

	modal: function(spec) {
		j$("#" + spec.id).modal('show');
	},

	chooser: function(spec) {
		j$("#" + spec.id).on("slid", function() {
			var hiddenValue = j$(this).find(".active").data("hidden");
			j$("#" + spec.hiddenId).val(hiddenValue);
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
	},

	skipButton: function (spec) {
		j$('#' + spec.id).click(function() {
			j$.ajax({
				url: spec.url
			});
		});
	}
});
