isc.defineClass('auctions', isc.OBGrid);
isc.defineClass('auctionsDS', isc.DataSource);

auctionsDS =isc.auctionsDS.create({
    dataFormat:"json",
    dataURL:'http://192.168.0.157:8111/openbravo/auction/auctions',
    fields: [
		{name: 'auction_id'},
		{name: 'auction_state'}
	]
});

isc.auctions.addProperties({
	showFilterEditor: false,
	dataSource: auctionsDS,
	autoFetchData: true,

	fields: [
		{
			name: 'auction_id',
			title: 'ID subasta',
		},
		{
			name: 'auction_state',
			title: 'Estado actual',
		}
	],
	
	selectionAppearance: 'checkbox',
	selectionType: 'single',
	emptyMessage: 'Aún no se ha publicado ninguna subasta.',

	initWidget: function () {
		this.Super('initWidget', arguments);
	}
});
