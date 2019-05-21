isc.defineClass('products', isc.OBGrid);
isc.products.addProperties({
	showFilterEditor: true,

	dataProperties: {
		useClientFiltering: false
	},

	gridFields: [
		{
			name: 'name',
			canFilter: true,
			filterOnKeypress: true
		},

		{
			name: 'description',
			canFilter: true,
			filterOnKeypress: true
		},

		{
			name: 'standardCost',
			canFilter: true,
			filterOnKeypress: true
		}],

		setDataSource: function (ds) {
			this.Super('setDataSource', [ds, this.gridFields]);
			this.refreshFields();
			this.sort('name');
			this.fetchData();
		},

		initWidget: function () {
			this.Super('initWidget', arguments);
			OB.Datasource.get('A5B355FDBB514902ADE25D0D09950C84', this, null, true);
		}
});
