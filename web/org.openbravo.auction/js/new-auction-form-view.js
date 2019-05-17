isc.defineClass("form", isc.DynamicForm);
isc.form.addProperties({
	titleOrientation: 'left',
	numCols: 1,
	titlePrefix: '<b>',
	titleSuffix: '</b>',
	initWidget: function () {
		this.Super("initWidget", arguments);

		this.setFields([
			{
				name: "auctionType", title: "Tipo de subasta:", 
				editorType: "select", 
				valueMap:[ "Subasta inglesa", "Subasta holandesa", "Subasta japonesa"]
			},

			{
				title: "Máximo número de postores:",
				shouldSaveValue: false,
				editorType: "SpinnerItem", 
				writeStackedIcons: false,
				defaultValue: 2, min: 2, max: 10, step: 1
			},

			{
				name: "celebrationDate",
				title: "Fecha de celebración:",
				editorType: "date"
			},

			{
				name: "celebrationTime",
				title: "Hora de celebración:",
				type: "time",
				useTextField: false
			},

			{
				title: "Precio base (€):",
				shouldSaveValue: false,
				editorType: "SpinnerItem",
				writeStackedIcons: false,
				defaultValue: 0.00, min: 0.00, step: 0.01
			},

			{
				name: "description",
				title: "Descripción",
				editorType: "textArea",
				width:400,
			}]
		);
	}
});