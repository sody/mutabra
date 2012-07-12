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

	skipButton: function (spec) {
		j$('#' + spec.id).click(function() {
			j$.ajax({
				url: spec.url
			});
		});
	}
});
