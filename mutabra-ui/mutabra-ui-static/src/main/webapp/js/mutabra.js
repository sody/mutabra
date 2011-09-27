Tapestry.Initializer.menu = function(spec) {
	j$(".ui-button", "#" + spec.id).button();
};

Tapestry.Initializer.dialog = function(spec) {
	j$("#" + spec.id).dialog(spec.options);
};

Tapestry.Initializer.dialoglink = function(spec) {
	j$('#' + spec.id).click(function() {
		j$('#' + spec.dialogId).dialog(spec.action);
		return false;
	});
};
