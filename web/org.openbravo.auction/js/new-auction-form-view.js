isc.defineClass("auctionParametersForm", isc.DynamicForm);
isc.auctionParametersForm.addProperties({
	titleOrientation: 'left',
	numCols: 1,
	titlePrefix: '<b>',
	titleSuffix: '</b>',

	initWidget: function () {
		this.Super("initWidget", arguments);

		this.setFields([
			{
				name: "auctionType",
				title: "Tipo de subasta:", 
				editorType: "select", 
				valueMap:["Subasta inglesa", "Subasta holandesa", "Subasta japonesa"],
				defaultToFirstOption: true
			},

			{
				name: "maxBidders",
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
				name: "startPrice",
				title: "Precio inicial (€):",
				// shouldSaveValue: false,
				editorType: "SpinnerItem",
				// writeStackedIcons: false,
				defaultValue: 0.00, min: 0.00, step: 0.01
			},
			
			{
				name: "closingPrice",
				title: "Precio de cierre (€):",
				// shouldSaveValue: false,
				editorType: "SpinnerItem",
				// writeStackedIcons: false,
				defaultValue: 0.00, min: 0.00, step: 0.01
			},
			
			{
				name: "priceUpdateFrequency",
				title: "Frecuencia actualización precio:",
				type: "time",
				useTextField: false
			},

			{
				name: "description",
				title: "Descripción:",
				editorType: "textArea",
				width:400
			}]
		);
	}
});

isc.defineClass("submitButton", isc.OBFormButton);
isc.submitButton.addProperties({
	title: 'Publicar subasta',

	initWidget: function () {
		this.Super("initWidget", arguments);
	}
});

isc.defineClass("clearButton", isc.OBFormButton);
isc.clearButton.addProperties({
	title: 'Limpiar',

	initWidget: function () {
		this.Super("initWidget", arguments);
	}
});
