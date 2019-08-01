isc.defineClass('publishedAuctionContainer', isc.HLayout);

isc.publishedAuctionContainer.addProperties({
	initWidget: function () {
		publishedAuctionInfo = isc.publishedAuctionInfo.create();
		
		updateButton = isc.updateButton.create({
			click: function() {
				auctions.setData([]);
				auctions.fetchData();
			}
		});
		
		buyers = isc.buyers.create();
		
		registeredBuyersListLayout = isc.VLayout.create({
			width: "35%",
			layoutMargin: 20,
			backgroundColor: "#ffffff",
			members: [updateButton, buyers]
		});
		
		auctions = isc.auctions.create({selectionUpdated: function() {
			if (this.anySelected()) {
				selectedItem = this.getSelectedRecord();
				
				publishedAuctionInfo.setMembers([]);
				
				var auctionInfo = retrieveAuctionInfo(selectedItem.auction_id);
				
				var auctionInfoMembers = [
					isc.Label.create({height: 50, padding: 4, contents: "<b>Tipo de subasta:</b> " + auctionInfo.auctionType.auctionTypeName}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Fecha de celebración:</b> " + new Date(auctionInfo.celebrationDate)}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Fecha de cierre:</b> " + new Date(auctionInfo.deadLine)}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Precio de salida:</b> " + auctionInfo.startingPrice + " €"}), 
					isc.Label.create({height: 50, padding: 4, contents: "<b>Información del producto subastado</b>"}),
					isc.Label.create({height: 25, padding: 4, contents: "Nombre: " + auctionInfo.item.name}),
					isc.Label.create({height: 25, padding: 4, contents: "Categoría: " + auctionInfo.item.category}),
					isc.Label.create({height: 25, padding: 4, contents: "Descripción: " + auctionInfo.item.description}),
					isc.Label.create({height: 25, padding: 4, contents: "Volumen: " + auctionInfo.item.volume}),
					isc.Label.create({height: 25, padding: 4, contents: "Peso: " + auctionInfo.item.weight + " gr"}),
					isc.Label.create({height: 50, padding: 4, contents: "<b>Información adicional:</b> " + auctionInfo.additionalInformation})
				]
				
				switch (auctionInfo.auctionType.auctionTypeEnum) {
					case 'ENGLISH':
						publishedAuctionInfo.addMembers(auctionInfoMembers);
						break;
					case 'DUTCH':
						publishedAuctionInfo.addMembers((auctionInfoMembers.slice(0 ,4)
								.concat(isc.Label.create({height: 50, padding: 4, contents: "<b>Mínimo precio de venta:</b> " + auctionInfo.minimumSalePrice}))
								.concat(auctionInfoMembers.splice(4, 9))))
						break;
					case 'JAPANESE':
						publishedAuctionInfo.addMembers(auctionInfoMembers);
						break;
				}
				
				buyersDS.dataURL ='http://localhost:8111/openbravo/auction/buyers?auction_id=' + selectedItem.auction_id;
				buyers.setData([]);
				buyers.fetchData();
			}
		}});
		
		this.Super("initWidget", arguments);

		this.addMembers([auctions, publishedAuctionInfo, registeredBuyersListLayout]);
	}	
});

retrieveAuctionInfo = function(auction_id) {
	request = new XMLHttpRequest();
	request.open('GET', 'http://localhost:8111/openbravo/auction/auction_info?auction_id=' + auction_id, false);
	request.send();

	return httpResponseToJSON(request.response);
}

httpResponseToJSON = function(httpResponse) {
	return JSON.parse(httpResponse)
}