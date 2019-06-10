isc.defineClass('newAuctionContainer', isc.HLayout);
isc.newAuctionContainer.addProperties({
	initWidget: function () {
		var auctionParametersForm = isc.auctionParametersForm.create();
		
		products = isc.products.create({selectionUpdated: function() {
			if (this.anySelected()) {
				var product = this.getSelectedRecord();
				auctionParametersForm.getItem("description").setValue(
						"Nombre: " + product.name + "\n\n" +
						"Descripción: " + product.description + "\n\n" +
						"Categoría: " + product.productCategory$_identifier + "\n\n" +
						"Volumen: " + product.volume + "\n\n" +
						"Peso: " + product.weight + "\n\n");
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
			
			if (selectedAuctionType === "Subasta inglesa") {
				activateEnglishAuctionFormMode();
				setf("Subasta inglesa");
			} else if (selectedAuctionType === "Subasta japonesa") {
				auctionParametersForm.getItem("closingPrice").setDisabled(true);
				deactivatePriceUpdateFrequencyFormField();
				setf("Subasta japonesa");
			} else {
				auctionParametersForm.getItem("closingPrice").setDisabled(false);
				deactivatePriceUpdateFrequencyFormField();
				setf("Subasta holandesa");
			}
			
			products.deselectAllRecords();
		};
		
		var auctionParametersFormButtons = isc.HStack.create({
			align: 'center',
			members: [
				isc.clearButton.create({click: function() {
					auctionParametersForm.clearValues();
					activateEnglishAuctionFormMode();
					products.deselectAllRecords();}
				}),
					
				isc.submitButton.create({click: function() {
					var auctionParameters = {
							auctionType: auctionParametersForm.getItem("auctionType").getValue(),
							celebrationDate: auctionParametersForm.getItem("celebrationDate").getEnteredValue(),
							celebrationTime: auctionParametersForm.getItem("celebrationTime").getEnteredValue(),
							deadLine: auctionParametersForm.getItem("deadLine").getEnteredValue(),
							closingTime: auctionParametersForm.getItem("closingTime").getEnteredValue(),
							maximumBiddersNum: auctionParametersForm.getItem("maximumBiddersNum").getValue(),
							startingPrice: auctionParametersForm.getItem("startingPrice").getValue(),
							minimumSalePrice: auctionParametersForm.getItem("minimumSalePrice").getValue(),
							priceUpdateFrequency: auctionParametersForm.getItem("priceUpdateFrequency").getEnteredValue(),
							description: auctionParametersForm.getItem("description").getValue(),
							additionalInformation: auctionParametersForm.getItem("additionalInformation").getValue()
					};
					
					OB.RemoteCallManager.call('org.openbravo.auction.handler.PublishAuctionHandler', auctionParameters, {}, null);}
				})
			]
		});

		formLayout = isc.VLayout.create({
			width: "35%",
			layoutMargin: 20,
			members: [auctionParametersForm, auctionParametersFormButtons]
		});
		
		this.Super("initWidget", arguments);

		this.addMembers([formLayout, products]);
	}
});

isc.defineClass("tabBar", isc.OBTabSetChild);
isc.tabBar.addProperties({
	height: "100%",

	initWidget: function () {
		var newAuctionContainer = isc.newAuctionContainer.create();
		
		this.Super("initWidget", arguments);

		this.addTabs([
			{
				id: "newAuction",
				title: "Nueva subasta",
				pane: newAuctionContainer
			},

			{
				id: "AuctionInfo",
				title: "Información subasta",
				pane: null
			}]
		);
	}
});