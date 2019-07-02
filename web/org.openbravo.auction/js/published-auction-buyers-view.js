isc.defineClass('buyers', isc.OBGrid);
isc.defineClass('buyersDS', isc.DataSource);

buyersDS =isc.buyersDS.create({
    dataFormat:"xml",
    recordXPath:"/Root/*",
    dataURL:"http://localhost:8111/openbravo/auction/registered_buyers",
    fields: [
		{name: 'email'}
	]
})

isc.buyers.addProperties({
	showFilterEditor: false,
	dataSource: buyersDS,
	autoFetchData: true,

	fields: [
		{
			name: 'email',
			title: 'Compradores inscritos en la subasta',
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
