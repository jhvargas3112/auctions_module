isc.ClassFactory.defineClass("AUCTION_AuctionControlView", isc.VLayout);
isc.AUCTION_AuctionControlView.addProperties({

	labelContent: 'Label content should be passed in as a parameter',

	width: '100%',
	height: '100%',

	align: 'center',
	defaultLayoutAlign: 'center',

	initWidget: function() {

		this.children = [isc.Label.create({
			height: 1,
			width: 1,
			overflow: 'visible',
			align: "center",
			valign: "center",
			contents: this.labelContent})];

		this.Super("initWidget", arguments);
	}
});