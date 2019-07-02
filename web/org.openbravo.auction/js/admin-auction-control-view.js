RPCManager.allowCrossDomainCalls = true;

isc.defineClass("AUCTION_AuctionControlView", isc.OBBaseView);
isc.AUCTION_AuctionControlView.addProperties({
	initWidget: function () {
		var auctionState = "NOT_EXIST";
		
		try {
			auctionState = retrieveAuctionState();
		} catch (err) {
			console.log(err);	
		}
		
		if (auctionState === "NOT_EXIST") {
			newAuctionTabBar = isc.tabBar.create();
			this.Super("initWidget", arguments);
			this.addMember(newAuctionTabBar);
		}

		if (auctionState === "PUBLISHED") {
			newAuctionTabBar = isc.tabBar.create();
			this.Super("initWidget", arguments);
			this.addMember(newAuctionTabBar);
			
			newAuctionTabBar.addTab({id: "auctionInfo", title: "Información subasta", pane: isc.publishedAuctionContainer.create()});
			newAuctionTabBar.getTab(0).setDisabled(true);
			newAuctionTabBar.selectTab(1);
		}
	}
});

retrieveAuctionState = function() {
	try {
		request = new XMLHttpRequest();
		request.open('GET', 'http://localhost:8111/openbravo/auction/auction_state', false);
		request.send();
		return httpResponseToJSON(request.response);
	} catch (err) {
		throw 'NETWORK_ERROR';
	}
}

httpResponseToJSON = function(httpResponse) {
	return JSON.parse(httpResponse)
}