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
		OB.Datasource.get('F31BD3B6E940437EA52815504A0EB542', this, null, true);
	}
});
