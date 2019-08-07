isc.defineClass('buyersDS', isc.DataSource);
isc.defineClass('buyers', isc.OBGrid);

buyersDS = isc.buyersDS.create({
    dataFormat:"json",
    fields: [
		{name: 'email'}
	]
});

isc.buyers.addProperties({
	showFilterEditor: true,
	dataSource: buyersDS,
	autoFetchData: true,

	fields: [
		{
			name: 'email',
			title: 'Participantes de la subasta',
		}],
	
	emptyMessage: 'Aún no se ha apuntado ningún comprador a esta subasta.',

	initWidget: function () {
		this.Super('initWidget', arguments);
	}
});
