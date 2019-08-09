RPCManager.allowCrossDomainCalls = true;

isc.defineClass("AUCTION_AuctionControlView", isc.OBBaseView);
isc.AUCTION_AuctionControlView.addProperties({
	initWidget: function () {
		OB.RemoteCallManager.call('org.openbravo.auction.handler.StartOpenbravoAuctionRestServerHandler', {}, {}, null);
		
		newAuctionTabBar = isc.tabBar.create();
		this.Super("initWidget", arguments);
		this.addMember(newAuctionTabBar);
	}
});