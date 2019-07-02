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
						"Información: " + selectedItem.description + "\n\n" +
						"Volumen: " + selectedItem.volume + "\n\n" +
						"Peso: " + selectedItem.weight);
			}
		}});

		var priceUpdateFrequency = auctionParametersForm.getItem("priceUpdateFrequency");

		function activateEnglishAuctionFormMode() {
			auctionParametersForm.getItem("minimumSalePrice").setDisabled(true);
			auctionParametersForm.getItem("priceUpdateFrequency").setDisabled(true);
			priceUpdateFrequency.hourItem.setDisabled(true);
			priceUpdateFrequency.minuteItem.setDisabled(true);
			priceUpdateFrequency.secondItem.setDisabled(true);
		};

		function deactivatePriceUpdateFrequencyFormField() {
			auctionParametersForm.getItem("priceUpdateFrequency").setDisabled(false);
			priceUpdateFrequency.hourItem.setDisabled(false);
			priceUpdateFrequency.minuteItem.setDisabled(false);
			priceUpdateFrequency.secondItem.setDisabled(false);
		};

		function setf(auctionType) {
			auctionParametersForm.clearValues();
			auctionParametersForm.getItem("auctionType").setValue(auctionType);
		};

		activateEnglishAuctionFormMode();

		auctionParametersForm.getItem("auctionType").changed = function() {
			var selectedAuctionType = auctionParametersForm.getItem("auctionType").getValue();

			if (selectedAuctionType === "Inglesa") {
				activateEnglishAuctionFormMode();
				setf("Inglesa");
			} else if (selectedAuctionType === "Japonesa") {
				auctionParametersForm.getItem("minimumSalePrice").setDisabled(true);
				deactivatePriceUpdateFrequencyFormField();
				setf("Japonesa");
			} else {
				auctionParametersForm.getItem("minimumSalePrice").setDisabled(false);
				deactivatePriceUpdateFrequencyFormField();
				setf("Holandesa");
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

					var auctionParameters = {
							auctionType: auctionParametersForm.getItem("auctionType").getValue(),
							celebrationDate: auctionParametersForm.getItem("celebrationDate").getEnteredValue(),
							celebrationTime: celebrationTimeFormField.hourItem.getValue() + ':' +
							celebrationTimeFormField.minuteItem.getValue() + ':' +
							celebrationTimeFormField.secondItem.getValue(),
							deadLine: auctionParametersForm.getItem("deadLine").getEnteredValue(),
							closingTime: closingTimeFormField.hourItem.getValue() + ':' +
							closingTimeFormField.minuteItem.getValue() + ':' +
							closingTimeFormField.secondItem.getValue(),
							maximumBiddersNum: auctionParametersForm.getItem("maximumBiddersNum").getValue(),
							auctionItem: {name: selectedItem.name, description: selectedItem.description, category: selectedItem.productCategory$_identifier, volume: selectedItem.volume, weight: selectedItem.weight},
							startingPrice: auctionParametersForm.getItem("startingPrice").getValue(),
							minimumSalePrice: auctionParametersForm.getItem("minimumSalePrice").getValue(),
							priceUpdateFrequency: auctionParametersForm.getItem("priceUpdateFrequency").getEnteredValue(),
							additionalInformation: auctionParametersForm.getItem("additionalInformation").getValue(),
					};
					
					publishAuction(auctionParameters).then(function() {changeView();});	
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

changeView = function() {
	setTimeout(function(){ 
		newAuctionTabBar.addTab({id: "auctionInfo", title: "Información subasta", pane: isc.publishedAuctionContainer.create()});
		newAuctionTabBar.getTab(0).setDisabled(true);
		newAuctionTabBar.selectTab(1);
    }, 10000);  
}

publishAuction = function(auctionParameters) {
	return new Promise(function (fulfill, reject) {
		fulfill({ value: auctionParameters, result: OB.RemoteCallManager.call('org.openbravo.auction.handler.PublishAuctionHandler', auctionParameters, {}, null) });
	});
}

