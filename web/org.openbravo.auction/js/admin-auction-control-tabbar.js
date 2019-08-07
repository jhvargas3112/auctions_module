isc.defineClass("tabBar", isc.OBTabSetChild);
isc.tabBar.addProperties({
	height: "100%",

	initWidget: function () {
		this.Super("initWidget", arguments);

		this.addTabs([
			{
				id: "newAuction",
				title: "Nueva subasta",
				pane: isc.newAuctionContainer.create()
			},
			{
				id: "auctionsInfo",
				title: "Información subastas",
				pane: isc.publishedAuctionContainer.create()
			},
			{
				id: "auctionWinners",
				title: "Ganadores",
				pane: isc.auctionWinnersContainer.create()
			}]
		);
	}
});