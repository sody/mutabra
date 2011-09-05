Tapestry.Initializer.dialog = function(spec) {
	j$("#" + spec.id).dialog(spec.options);
};

Tapestry.Initializer.dialoglink = function(spec) {
	j$('#' + spec.id).click(function() {
		j$('#' + spec.dialogId).dialog('open');
		return false;
	});
};


