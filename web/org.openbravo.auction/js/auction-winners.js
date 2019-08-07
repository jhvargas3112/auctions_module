isc.defineClass('winnersDS', isc.DataSource);
isc.defineClass('auctionWinners', isc.OBGrid);

isc.defineClass('auctionWinnersContainer', isc.HLayout);

winnersDS = isc.winnersDS.create({
    dataFormat:"xml",
    recordXPath:"/Root/*",
    dataURL:"http://192.168.0.157:8111/openbravo/auction/winners",
    fields: [
		{name: 'email'},
		{name: 'item'},
		{name: 'startingPrice'}
	]
});

isc.auctionWinners.addProperties({
	showFilterEditor: true,
	dataSource: winnersDS,
	autoFetchData: true,

	fields: [
		{name: 'email',title: 'Ganador'},
		{name: 'item', title: 'Producto subastado'},
		{name: 'startingPrice', title: 'Precio final'}
	],

	initWidget: function () {
		this.Super('initWidget', arguments);
	}
});

isc.defineClass("updateAuctionWinnersButton", isc.OBFormButton);
isc.updateAuctionWinnersButton.addProperties({
	title: 'Actualizar listado',

	initWidget: function () {
		this.Super("initWidget", arguments);
	}
});

isc.auctionWinnersContainer.addProperties({
	initWidget: function () {
		updateAuctionWinnersButton = isc.updateAuctionWinnersButton.create({
			click: function() {
				auctions.setData([]);
				auctions.fetchData();
			}
		});
		
		auctionWinners = isc.auctionWinners.create();
		
		auctionWinnersListLayout = isc.VLayout.create({
			width: "*",
			layoutMargin: 20,
			backgroundColor: "#ffffff",
			members: [updateAuctionWinnersButton, auctionWinners]
		});
		
		this.Super("initWidget", arguments);

		this.addMember(auctionWinnersListLayout);
	}	
});
