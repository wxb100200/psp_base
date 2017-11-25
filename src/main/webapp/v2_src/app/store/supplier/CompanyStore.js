Ext.define('Base.store.supplier.CompanyStore', {
    extend: 'Base.store.BaseStore',
    model: 'Base.model.supplier.CompanyModel',
    proxy: {
        type: 'psprest',
        url: "../service/entity/Company:(id,createTime,type,name,formerName,address)"
    }
});