isc.defineClass('items', isc.OBGrid);
isc.items.addProperties({
	showFilterEditor: true,

	dataProperties: {
		useClientFiltering: false
	},

	gridFields: [
		{
			name: 'name',
			title: 'Nombre',
			canFilter: true,
			filterOnKeypress: true
		},

		{
			name: 'description',
			title: 'Descripción',
			canFilter: true,
			filterOnKeypress: true
		}],

	setDataSource: function (ds) {
		this.Super('setDataSource', [ds, this.gridFields]);
		this.refreshFields();
		this.sort('name');
		this.fetchData();
	},
		
	selectionAppearance: 'checkbox',
	
	selectionType: 'single',

	initWidget: function () {
		this.Super('initWidget', arguments);
		OB.Datasource.get('35F3E346A43A483482E2D793593C7FB2', this, null, true);
	}
});
