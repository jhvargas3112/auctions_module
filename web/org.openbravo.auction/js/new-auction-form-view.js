isc.defineClass("auctionParametersForm", isc.DynamicForm);
isc.auctionParametersForm.addProperties({
	titleOrientation: 'left',
	numCols: 1,
	titlePrefix: '<b>',
	titleSuffix: '</b>',
	timeFormatter: 'to24HourTime',

	initWidget: function () {
		this.Super("initWidget", arguments);

		this.setFields([
			{
				name: "auctionType",
				title: "Tipo de subasta:", 
				editorType: "select", 
				valueMap:["Inglesa", "Holandesa", "Japonesa"],
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
				name: "deadLine",
				title: "Fecha de cierre:",
				editorType: "date"
			},
			
			{
				name: "closingTime",
				title: "Hora de cierre:",
				type: "time",
				useTextField: false
			},
			
			{
				name: "maximumBiddersNum",
				title: "Máximo número de postores:",
				editorType: "SpinnerItem", 
				keyPressFilter: "[0-9]",
				defaultValue: 2, min: 2, step: 1 // Se pensó en establecer un máximo de 10 postores en cada subasta, pero dejaremos este planteamiento para cuando se programe la parte de los agentes.
			},

			{
				name: "startingPrice",
				title: "Precio de salida (€):",
				editorType: "SpinnerItem",
				keyPressFilter: "[0-9.]",
				defaultValue: 0.01, min: 0.01, step: 0.01
			},
			
			{
				name: "minimumSalePrice",
				title: "Mínimo precio de venta (€):",
				editorType: "SpinnerItem",
				keyPressFilter: "[0-9.]",
				defaultValue: 0.01, min: 0.01, step: 0.01
			},
			
			{
				name: "numberOfRounds",
				title: "Número de rondas:",
				editorType: "SpinnerItem",
				keyPressFilter: "[0-9.]",
				defaultValue: 1, min: 1, step: 1
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
