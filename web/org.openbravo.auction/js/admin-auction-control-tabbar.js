isc.defineClass("tabBar", isc.OBTabSetChild);
isc.tabBar.addProperties({
	height: "100%",

	initWidget: function () {
		this.Super("initWidget", arguments);

		this.addTab(
			{
				id: "newAuction",
				title: "Nueva subasta",
				pane: isc.newAuctionContainer.create()
			});
	}
});