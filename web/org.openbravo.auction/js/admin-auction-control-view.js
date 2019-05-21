isc.defineClass("AUCTION_AuctionControlView", isc.OBBaseView);
isc.AUCTION_AuctionControlView.addProperties({
	initWidget: function () {
		var newAuctionTab = isc.tabBar.create();
		this.Super("initWidget", arguments);
		this.addMember(newAuctionTab);
	}
});