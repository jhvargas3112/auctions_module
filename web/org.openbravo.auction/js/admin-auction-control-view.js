isc.defineClass("AUCTION_AuctionControlView", isc.OBBaseView);
isc.AUCTION_AuctionControlView.addProperties({
	initWidget: function () {
		this.newAuctionTab = isc.tab.create();

		this.Super("initWidget", arguments);

		this.addMember(this.newAuctionTab);
	}
});