isc.defineClass('products', isc.OBGrid);
isc.products.addProperties({
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
		},

		{
			name: 'standardCost',
			title: 'Precio',
			canFilter: false,
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
		OB.Datasource.get('A5B355FDBB514902ADE25D0D09950C84', this, null, true);
	}
});
