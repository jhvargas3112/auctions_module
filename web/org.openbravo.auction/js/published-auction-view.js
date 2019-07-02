isc.defineClass('publishedAuctionContainer', isc.HLayout);

isc.publishedAuctionContainer.addProperties({
	initWidget: function () {
		var auctionState = retrieveAuctionState();
		
		if (auctionState === "PUBLISHED") {
			var auctionInfo = retrieveAuctionInfo();
			
			var auctionInfoMembers = [
				isc.Label.create({height: 50, padding: 4, contents: "<b>Tipo de subasta:</b> " + auctionInfo.auctionType.auctionTypeName}),
				isc.Label.create({height: 50, padding: 4, contents: "<b>Fecha de celebración:</b> " + new Date(auctionInfo.celebrationDate)}),
				isc.Label.create({height: 50, padding: 4, contents: "<b>Fecha de cierre:</b> " + new Date(auctionInfo.deadLine)}),
				isc.Label.create({height: 50, padding: 4, contents: "<b>Precio de salida:</b> " + auctionInfo.startingPrice}), 
				isc.Label.create({height: 50, padding: 4, contents: "<b>Información del producto subastado</b>"}),
				isc.Label.create({height: 25, padding: 4, contents: "Nombre: " + auctionInfo.item.name}),
				isc.Label.create({height: 25, padding: 4, contents: "Categoría: " + auctionInfo.item.category}),
				isc.Label.create({height: 25, padding: 4, contents: "Volumen: " + auctionInfo.item.volume}),
				isc.Label.create({height: 25, padding: 4, contents: "Peso: " + auctionInfo.item.weight}),
				isc.Label.create({height: 50, padding: 4, contents: "<b>Información adicional:</b> " + auctionInfo.additionalInformation})
			]
			
			publishedAuctionInfo = isc.publishedAuctionInfo.create();
			
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

			publishedAuctionBuyers = isc.buyers.create();

			updateButton = isc.updateButton.create({
				click: function() {
					publishedAuctionBuyers.setData([]);
					publishedAuctionBuyers.fetchData();
				}
			});

			registeredBuyersListLayout = isc.VLayout.create({
				width: "35%",
				layoutMargin: 20,
				backgroundColor: "#ffffff",
				members: [updateButton, publishedAuctionBuyers]
			});

			this.Super("initWidget", arguments);

			this.addMembers([publishedAuctionInfo, registeredBuyersListLayout]);
		}
	}	
});

retrieveAuctionState = function() {
	request = new XMLHttpRequest();
	request.open('GET', 'http://localhost:8111/openbravo/auction/auction_state', false);
	request.send();
	return httpResponseToJSON(request.response);
}

retrieveAuctionInfo = function() {
	request = new XMLHttpRequest();
	request.open('GET', 'http://localhost:8111/openbravo/auction/auction_info', false);
	request.send();

	return httpResponseToJSON(request.response);
}

httpResponseToJSON = function(httpResponse) {
	return JSON.parse(httpResponse)
}