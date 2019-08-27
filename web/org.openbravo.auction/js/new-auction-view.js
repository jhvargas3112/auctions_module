// New auction tab content

isc.defineClass('newAuctionContainer', isc.HLayout);

auctionParametersForm = isc.auctionParametersForm.create();

isc.newAuctionContainer.addProperties({
	initWidget: function () {
		var selectedItem = null;

		items = isc.items.create({selectionUpdated: function() {
			if (this.anySelected()) {
				selectedItem = this.getSelectedRecord();
				auctionParametersForm.getItem("description").setValue(
						"Nombre: " + selectedItem.name + "\n\n" +
						"Categoría: " + selectedItem.productCategory$_identifier + "\n\n" +
						"Descripción: " + selectedItem.description + "\n\n" +
						"Volumen: " + selectedItem.volume + "\n\n" +
						"Peso: " + selectedItem.weight);
			}
		}});

		var numberOfRounds = auctionParametersForm.getItem("numberOfRounds");

		activateEnglishAuctionFormMode = function() {
			auctionParametersForm.getItem("minimumSalePrice").setDisabled(true);
			auctionParametersForm.getItem("maximumSalePrice").setDisabled(true);
			auctionParametersForm.getItem("numberOfRounds").setDisabled(true);
		};

		setAuctionType = function(auctionType) {
			auctionParametersForm.clearValues();
			auctionParametersForm.getItem("auctionType").setValue(auctionType);
		};

		activateEnglishAuctionFormMode();

		auctionParametersForm.getItem("auctionType").changed = function() {
			var selectedAuctionType = auctionParametersForm.getItem("auctionType").getValue();

			if (selectedAuctionType === "Inglesa") {
				activateEnglishAuctionFormMode();
				setAuctionType("Inglesa");
			} else if (selectedAuctionType === "Holandesa") {
				auctionParametersForm.getItem("maximumSalePrice").setDisabled(true);
				auctionParametersForm.getItem("minimumSalePrice").setDisabled(false);
				auctionParametersForm.getItem("numberOfRounds").setDisabled(false);
				setAuctionType("Holandesa");
			} else if (selectedAuctionType === "Japonesa") {
				auctionParametersForm.getItem("minimumSalePrice").setDisabled(true);
				auctionParametersForm.getItem("maximumSalePrice").setDisabled(false);
				auctionParametersForm.getItem("numberOfRounds").setDisabled(false);
				setAuctionType("Japonesa");
			}

			items.deselectAllRecords();
		};

		var auctionParametersFormButtons = isc.HStack.create({
			align: 'center',
			members: [
				isc.clearButton.create({click: function() {
					auctionParametersForm.clearValues();
					activateEnglishAuctionFormMode();
					items.deselectAllRecords();}
				}),
				isc.submitButton.create({click: function() {
					var celebrationTimeFormField = auctionParametersForm.getItem("celebrationTime");
					var closingTimeFormField = auctionParametersForm.getItem("closingTime");
					
					var auctionType = {};
					
					var minimumSalePrice = null;
					
					var auctionParameters = {};
					
					switch (auctionParametersForm.getItem("auctionType").getValue()) {
						case 'Inglesa':
							auctionType = {'auctionTypeEnum': 'ENGLISH', 'auctionTypeName': auctionParametersForm.getItem("auctionType").getValue()};
							
							auctionParameters = {
									'auctionType': auctionType,
									'auctionState': {'auctionStateEnum': 'PUBLISHED', 'auctionStateName': 'Publicada'},
									'celebrationDate': moment(moment(auctionParametersForm.getItem("celebrationDate").getEnteredValue()).format("YYYY-MM-DD") + ' ' +
													   celebrationTimeFormField.hourItem.getValue() + ':' +
													   celebrationTimeFormField.minuteItem.getValue() + ':' +
													   celebrationTimeFormField.secondItem.getValue()).valueOf(),
									'deadLine': moment(moment(auctionParametersForm.getItem("deadLine").getEnteredValue()).format("YYYY-MM-DD") + ' ' +
												closingTimeFormField.hourItem.getValue() + ':' +
												closingTimeFormField.minuteItem.getValue() + ':' +
												closingTimeFormField.secondItem.getValue()).valueOf(),
									'maximumBiddersNum': auctionParametersForm.getItem("maximumBiddersNum").getValue(),
									'item': {name: selectedItem.name, description: selectedItem.description, category: selectedItem.productCategory$_identifier, volume: selectedItem.volume, weight: selectedItem.weight},
									'startingPrice': auctionParametersForm.getItem("startingPrice").getValue(),
									'numberOfRounds': auctionParametersForm.getItem("numberOfRounds").getEnteredValue(),
									'currentPrice': auctionParametersForm.getItem("startingPrice").getEnteredValue(),
									'additionalInformation': auctionParametersForm.getItem("additionalInformation").getValue(),
									"auctionBuyers": []
							};
							break;
						case 'Holandesa':
							auctionType = {'auctionTypeEnum': 'DUTCH', 'auctionTypeName': auctionParametersForm.getItem("auctionType").getValue()};
							minimumSalePrice = auctionParametersForm.getItem("minimumSalePrice").getValue();
							
							auctionParameters = {
									'auctionType': auctionType,
									'auctionState': {'auctionStateEnum': 'PUBLISHED', 'auctionStateName': 'Publicada'},
									'celebrationDate': moment(moment(auctionParametersForm.getItem("celebrationDate").getEnteredValue()).format("YYYY-MM-DD") + ' ' +
													   celebrationTimeFormField.hourItem.getValue() + ':' +
													   celebrationTimeFormField.minuteItem.getValue() + ':' +
													   celebrationTimeFormField.secondItem.getValue()).valueOf(),
									'deadLine': moment(moment(auctionParametersForm.getItem("deadLine").getEnteredValue()).format("YYYY-MM-DD") + ' ' +
												closingTimeFormField.hourItem.getValue() + ':' +
												closingTimeFormField.minuteItem.getValue() + ':' +
												closingTimeFormField.secondItem.getValue()).valueOf(),
									'maximumBiddersNum': auctionParametersForm.getItem("maximumBiddersNum").getValue(),
									'item': {name: selectedItem.name, description: selectedItem.description, category: selectedItem.productCategory$_identifier, volume: selectedItem.volume, weight: selectedItem.weight},
									'startingPrice': auctionParametersForm.getItem("startingPrice").getValue(),
									'minimumSalePrice': minimumSalePrice,
									'numberOfRounds': auctionParametersForm.getItem("numberOfRounds").getEnteredValue(),
									'currentPrice': auctionParametersForm.getItem("startingPrice").getEnteredValue(),
									'additionalInformation': auctionParametersForm.getItem("additionalInformation").getValue(),
									"auctionBuyers": []
							};
							break;
						case 'Japonesa':
							auctionType = {'auctionTypeEnum': 'JAPANESE', 'auctionTypeName': auctionParametersForm.getItem("auctionType").getValue()};
							maximumSalePrice = auctionParametersForm.getItem("maximumSalePrice").getValue();
							
							auctionParameters = {
									'auctionType': auctionType,
									'auctionState': {'auctionStateEnum': 'PUBLISHED', 'auctionStateName': 'Publicada'},
									'celebrationDate': moment(moment(auctionParametersForm.getItem("celebrationDate").getEnteredValue()).format("YYYY-MM-DD") + ' ' +
													   celebrationTimeFormField.hourItem.getValue() + ':' +
													   celebrationTimeFormField.minuteItem.getValue() + ':' +
													   celebrationTimeFormField.secondItem.getValue()).valueOf(),
									'deadLine': moment(moment(auctionParametersForm.getItem("deadLine").getEnteredValue()).format("YYYY-MM-DD") + ' ' +
												closingTimeFormField.hourItem.getValue() + ':' +
												closingTimeFormField.minuteItem.getValue() + ':' +
												closingTimeFormField.secondItem.getValue()).valueOf(),
									'maximumBiddersNum': auctionParametersForm.getItem("maximumBiddersNum").getValue(),
									'item': {name: selectedItem.name, description: selectedItem.description, category: selectedItem.productCategory$_identifier, volume: selectedItem.volume, weight: selectedItem.weight},
									'startingPrice': auctionParametersForm.getItem("startingPrice").getValue(),
									'maximumSalePrice': maximumSalePrice,
									'numberOfRounds': auctionParametersForm.getItem("numberOfRounds").getEnteredValue(),
									'currentPrice': auctionParametersForm.getItem("startingPrice").getEnteredValue(),
									'additionalInformation': auctionParametersForm.getItem("additionalInformation").getValue(),
									"auctionBuyers": []
							};
							break;
					}

					publishAuction(auctionParameters);
					
					auctions.setData([]);
					auctions.fetchData();
				}})
			]
		});

		formLayout = isc.VLayout.create({
			width: "35%",
			layoutMargin: 20,
			members: [auctionParametersForm, auctionParametersFormButtons]
		});

		this.Super("initWidget", arguments);

		this.addMembers([formLayout, items]);
	}
});

publishAuction = function(auctionParameters) {
	callback = function (response, cdata, request) {
		alert("¡La subasta ha sido publicada!");
	};
	
	OB.RemoteCallManager.call('org.openbravo.auction.handler.PublishAuctionHandler', auctionParameters, {}, callback);
}

