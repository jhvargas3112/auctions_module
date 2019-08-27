isc.defineClass('publishedAuctiontToolBar', isc.OBToolbar);
isc.defineClass('publishedAuctionHLayout', isc.HLayout);

isc.defineClass('publishedAuctionContainer', isc.VLayout);

updateButtonProperties = {
		action: function() {
			publishedAuctionInfo.setMembers([]);
			
			var currentSelectedRecordIndex = auctions.getRecordIndex(auctions.getSelectedRecord());
			
			auctions.setData([]);
			auctions.fetchData();
			
			setTimeout(function(){ auctions.selectRecord(currentSelectedRecordIndex); }, 100);
	      },
	      disabled: false,
	      buttonType: 'refresh',
	      prompt: 'Refrescar'
};

closeAuctionButtonProperties = {
		action: function() {
			if (auctionInfo.auctionState.auctionStateEnum !== "PUBLISHED") {
				callback = function (response, cdata, request) {
					publishedAuctionInfo.setMembers([]);
					
					auctions.setData([]);
					auctions.fetchData();
					
					setTimeout(function(){ auctions.selectRecord(0); }, 100);
				};	
				
				OB.RemoteCallManager.call('org.openbravo.auction.handler.CloseAuctionHandler', {'auction_id': auctionId}, {}, callback);
			}
	      },
	      disabled: true,
	      buttonType: 'eliminate',
	      prompt: 'Cerrar subasta'
};

updateButton = isc.OBToolbarIconButton.create(updateButtonProperties);
closeAuctionButton = isc.OBToolbarIconButton.create(closeAuctionButtonProperties);

isc.publishedAuctiontToolBar.addProperties({
	view: this,
	
	leftMembers: [updateButton, closeAuctionButton],
      
	rightMembers: [],
	
	initWidget: function () {
		this.Super("initWidget", arguments);
	}
});

isc.publishedAuctionHLayout.addProperties({
	initWidget: function () {
		publishedAuctionInfo = isc.publishedAuctionInfo.create();
		
		buyers = isc.buyers.create();
		
		auctions = isc.auctions.create({selectionUpdated: function() {
			if (this.anySelected()) {
				selectedItem = this.getSelectedRecord();
				
				publishedAuctionInfo.setMembers([]);
				
				auctionId = selectedItem.auction_id;
				
				auctionInfo = retrieveAuctionInfo(selectedItem.auction_id);
				
				var auctionInfoMembers = [
					isc.Label.create({height: 50, padding: 4, contents: "<b>Tipo de subasta:</b> " + auctionInfo.auctionType.auctionTypeName}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Máximo número de postores:</b> " + auctionInfo.maximumBiddersNum}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Fecha de celebración:</b> " + new Date(auctionInfo.celebrationDate)}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Fecha de cierre:</b> " + new Date(auctionInfo.deadLine)}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Precio de salida:</b> " + auctionInfo.startingPrice + " €"}), 
					isc.Label.create({height: 50, padding: 4, contents: "<b>Información del bien subastado</b>"}),
					isc.Label.create({height: 25, padding: 4, contents: "Nombre: " + auctionInfo.item.name}),
					isc.Label.create({height: 25, padding: 4, contents: "Categoría: " + auctionInfo.item.category}),
					isc.Label.create({height: 25, padding: 4, contents: "Descripción: " + auctionInfo.item.description}),
					isc.Label.create({height: 25, padding: 4, contents: "Volumen: " + auctionInfo.item.volume}),
					isc.Label.create({height: 25, padding: 4, contents: "Peso: " + auctionInfo.item.weight + " gr"}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Información adicional:</b> " + auctionInfo.additionalInformation})
				]
				
				switch (auctionInfo.auctionType.auctionTypeEnum) {
					case 'ENGLISH':
						publishedAuctionInfo.setMembers(auctionInfoMembers);
						break;
					case 'DUTCH':
						publishedAuctionInfo.setMembers((auctionInfoMembers.slice(0 ,4)
								.concat(isc.Label.create({height: 50, padding: 4, contents: "<b>Mínimo precio de venta:</b> " + auctionInfo.minimumSalePrice + " €"}))
								.concat(auctionInfoMembers.splice(4, 9))))
						break;
					case 'JAPANESE':
						publishedAuctionInfo.setMembers((auctionInfoMembers.slice(0 ,4)
								.concat(isc.Label.create({height: 50, padding: 4, contents: "<b>Máximo precio de venta:</b> " + auctionInfo.maximumSalePrice + " €"}))
								.concat(auctionInfoMembers.splice(4, 9))))
						break;
				}
				
				if (auctionInfo.auctionState.auctionStateEnum === "CANCELLED") {
					closeAuctionButton.enable();
				} else if (auctionInfo.auctionState.auctionStateEnum === "FINISHED_WITHOUT_WINNER") {
					closeAuctionButton.enable();
				} else if (auctionInfo.auctionState.auctionStateEnum === "FINISHED_WITH_WINNER") {
					publishedAuctionInfo.addMember({height: 50, padding: 4, contents: "<b>Ganador:</b> " + auctionInfo.auctionBuyers[0].email});
					publishedAuctionInfo.addMember({height: 50, padding: 4, contents: "<b>Precio de cierre:</b> " + auctionInfo.currentPrice + " €"});
					closeAuctionButton.enable();
				} else if (auctionInfo.auctionState.auctionStateEnum === "PUBLISHED") {
					closeAuctionButton.disable();
				} else if (auctionInfo.auctionState.auctionStateEnum === "IT_IS_CELEBRATING") {
					closeAuctionButton.disable();
				}
				
				buyersDS.dataURL ='http://192.168.0.157:8111/openbravo/auction/buyers?auction_id=' + selectedItem.auction_id;
				buyers.setData([]);
				buyers.fetchData();
			}
		}});
		
		this.Super("initWidget", arguments);

		this.addMembers([auctions, publishedAuctionInfo, buyers]);
	}	
});

isc.publishedAuctionContainer.addProperties({
	initWidget: function () {
		this.Super("initWidget", arguments);
		
		this.addMembers([isc.publishedAuctiontToolBar.create(), isc.publishedAuctionHLayout.create()]);
	}
});

retrieveAuctionInfo = function(auction_id) {
	request = new XMLHttpRequest();
	request.open('GET', 'http://192.168.0.157:8111/openbravo/auction/auction_info?auction_id=' + auction_id, false);
	request.send();

	return httpResponseToJSON(request.response);
}

httpResponseToJSON = function(httpResponse) {
	return JSON.parse(httpResponse)
}