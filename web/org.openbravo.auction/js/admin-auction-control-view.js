RPCManager.allowCrossDomainCalls = true;

isc.defineClass("AUCTION_AuctionControlView", isc.OBBaseView);
isc.AUCTION_AuctionControlView.addProperties({
	initWidget: function () {
		newAuctionTabBar = isc.tabBar.create();
		this.Super("initWidget", arguments);
		this.addMember(newAuctionTabBar);
	}
});

/* retrieveAuctionState = function() {
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
} */