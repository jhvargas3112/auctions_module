isc.defineClass('buyers', isc.OBGrid);
isc.defineClass('buyersDS', isc.DataSource);

buyersDS = isc.buyersDS.create({
    dataFormat:"json",
    fields: [
		{name: 'email'}
	]
});

isc.buyers.addProperties({
	showFilterEditor: false,
	dataSource: buyersDS,
	autoFetchData: true,

	fields: [
		{
			name: 'email',
			title: 'Participantes de la subasta',
		}],

	initWidget: function () {
		this.Super('initWidget', arguments);
	}
});

isc.defineClass("updateButton", isc.OBFormButton);
isc.updateButton.addProperties({
	title: 'Actualizar listado',

	initWidget: function () {
		this.Super("initWidget", arguments);
	}
});
