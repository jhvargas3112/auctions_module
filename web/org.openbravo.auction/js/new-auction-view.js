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

		function activateEnglishAuctionFormMode() {
			auctionParametersForm.getItem("minimumSalePrice").setDisabled(true);
			auctionParametersForm.getItem("numberOfRounds").setDisabled(true);
		};

		function deactivateNumberOfRoundsFormField() {
			auctionParametersForm.getItem("numberOfRounds").setDisabled(false);
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
				deactivatenumberOfRoundsFormField();
				setf("Japonesa");
			} else {
				auctionParametersForm.getItem("minimumSalePrice").setDisabled(false);
				deactivateNumberOfRoundsFormField();
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
							'auctionType': auctionParametersForm.getItem("auctionType").getValue(),
							'celebrationDate': auctionParametersForm.getItem("celebrationDate").getEnteredValue(),
							'celebrationTime': celebrationTimeFormField.hourItem.getValue() + ':' +
							celebrationTimeFormField.minuteItem.getValue() + ':' +
							celebrationTimeFormField.secondItem.getValue(),
							'deadLine': auctionParametersForm.getItem("deadLine").getEnteredValue(),
							'closingTime': closingTimeFormField.hourItem.getValue() + ':' +
							closingTimeFormField.minuteItem.getValue() + ':' +
							closingTimeFormField.secondItem.getValue(),
							'maximumBiddersNum': auctionParametersForm.getItem("maximumBiddersNum").getValue(),
							'auctionItem': {name: selectedItem.name, description: selectedItem.description, category: selectedItem.productCategory$_identifier, volume: selectedItem.volume, weight: selectedItem.weight},
							'startingPrice': auctionParametersForm.getItem("startingPrice").getValue(),
							'minimumSalePrice': auctionParametersForm.getItem("minimumSalePrice").getValue(),
							'numberOfRounds': auctionParametersForm.getItem("numberOfRounds").getEnteredValue(),
							'additionalInformation': auctionParametersForm.getItem("additionalInformation").getValue(),
					};
					
					if (auctionParametersForm.getItem("auctionType").getValue() === 'Japanese') {
						delete auctionParameters['deadLine'];
					}
					
					publishAuction(auctionParameters);	
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
	OB.RemoteCallManager.call('org.openbravo.auction.handler.PublishAuctionHandler', auctionParameters, {}, null);
}

