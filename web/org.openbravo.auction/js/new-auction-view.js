isc.defineClass('newAuctionContainer', isc.HLayout);
isc.newAuctionContainer.addProperties({
	initWidget: function () {
		this.formLayout = isc.VLayout.create({
			width: "35%",
			layoutMargin: 20,
			members: [isc.form.create()]
		});

		this.products = isc.products.create();

		this.Super("initWidget", arguments);

		this.addMembers([this.formLayout, this.products]);
	}
});

isc.defineClass("tab", isc.OBTabSetChild);
isc.tab.addProperties({
	height: "100%",

	initWidget: function () {
		this.newAuctionContainer = isc.newAuctionContainer.create();

		this.Super("initWidget", arguments);

		this.addTabs([
			{
				id: "newAuction",
				title: "Nueva subasta",
				pane: this.newAuctionContainer

			},
			
			{
				id: "AuctionInfo",
				title: "Información subasta",
				pane: null
			}]
		);
	}
});