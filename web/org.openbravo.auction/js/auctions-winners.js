isc.defineClass('auctionWinnersToolBar', isc.OBToolbar);
isc.defineClass('winnersDS', isc.DataSource);
isc.defineClass('auctionWinners', isc.OBGrid);

isc.defineClass('auctionWinnersContainer', isc.VLayout);

updateButtonProperties = {
		action: function() {
			removeWinnerButton.disable();
			
			auctionWinners.setData([]);
			auctionWinners.fetchData();
	      },
	      disabled: false,
	      buttonType: 'refresh',
	      prompt: 'Refrescar'
};

removeWinnerButtonProperties = {
		action: function() {
			selectedWinners = auctionWinners.getSelectedRecords();
			
			callback = function (response, cdata, request) {
				auctionWinners.setData([]);
				auctionWinners.fetchData();
			};
			
			OB.RemoteCallManager.call('org.openbravo.auction.handler.RemoveAuctionWinnersHandler', {'winners_to_remove': selectedWinners}, {}, callback);
	      },
	      disabled: true,
	      buttonType: 'eliminate',
	      prompt: 'Cerrar subasta'
};

updateButton = isc.OBToolbarIconButton.create(updateButtonProperties);
removeWinnerButton = isc.OBToolbarIconButton.create(removeWinnerButtonProperties);

isc.auctionWinnersToolBar.addProperties({
	view: this,
	
	leftMembers: [updateButton, removeWinnerButton],
      
	rightMembers: [],
	
	initWidget: function () {
		this.Super("initWidget", arguments);
	}
});

winnersDS = isc.winnersDS.create({
    dataFormat:"xml",
    recordXPath:"/Root/*",
    dataURL:"http://localhost:8111/openbravo/auction/winners",
    fields: [
    	{name: 'auction_id'},
    	{name: 'finish_date'},
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
		{
			name: 'auction_id',
			title: 'Subasta', 
			canFilter: true, 
			filterOnKeypress: true
		},
		{
			name: 'finish_date',
			title: 'Fecha de finalización', 
			canFilter: true, 
			filterOnKeypress: true
		},
		{
			name: 'email',
			title: 'Ganador', 
			canFilter: true, 
			filterOnKeypress: true
		},
		{
			name: 'item',
			title: 'Producto subastado',
			canFilter: true,
			filterOnKeypress: true
		},
		{
			name: 'startingPrice',
			title: 'Precio final',
			canFilter: true,
			filterOnKeypress: true
		}
	],
	
	selectionAppearance: 'checkbox',
	// selectionType: 'mutiple',

	initWidget: function () {
		this.Super('initWidget', arguments);
	}
});

isc.auctionWinnersContainer.addProperties({
	initWidget: function () {
		auctionWinners = isc.auctionWinners.create({
			selectionUpdated: function() {
				if (this.anySelected()) {
					removeWinnerButton.enable();
				} else {
					removeWinnerButton.disable();
				}
			}
		});
		
		this.Super("initWidget", arguments);

		this.addMembers([isc.auctionWinnersToolBar.create(), auctionWinners]);
	}	
});
