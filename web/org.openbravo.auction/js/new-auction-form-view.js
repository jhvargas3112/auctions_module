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
				name: "maxBidders",
				title: "Máximo número de postores:",
				editorType: "SpinnerItem", 
				keyPressFilter: "[0-9]",
				defaultValue: 2, min: 2, step: 1 // Se pensó en establecer un máximo de 10 postores en cada subasta, pero dejaremos este planteamiento para cuando se programe la parte de los agentes.
			},

			{
				name: "startPrice",
				title: "Precio inicial (€):",
				editorType: "SpinnerItem",
				keyPressFilter: "[0-9.]",
				defaultValue: 0.00, min: 0.00, step: 0.01
			},
			
			{
				name: "closingPrice",
				title: "Precio de cierre (€):",
				editorType: "SpinnerItem",
				keyPressFilter: "[0-9.]",
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
				width:400,
				canFocus: false,
				showFocused: false,
				canEdit: false
			},
			
			{
				name: "additionalInformation",
				title: "Información adicional:",
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
