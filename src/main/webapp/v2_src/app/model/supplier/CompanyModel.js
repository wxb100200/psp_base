/**
 * 新的无线站点的Model, 站点由PMS同步而来
 */
Ext.define('Base.model.supplier.CompanyModel', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id', type: 'int' },
        { name: 'createTime', type: 'date',dateFormat:'time' },
        { name: 'changeTime', type: 'date',dateFormat:'time' },

        { name: 'type', type: 'string' },
        { name: 'name', type: 'string' },
        { name: 'formerName', type: 'string' },
        { name: 'address', type: 'string' }
    ]
});
