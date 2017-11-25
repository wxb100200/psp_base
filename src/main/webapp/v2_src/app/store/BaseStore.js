Ext.define('Base.store.BaseStore', {
    extend: 'Ext.data.Store',
    autoLoad:false,
    pageSize: 50,             // window['G']['default']['pageSize'], //50,//G.default.pageSize ,
    remoteFilter:true,
    proxy: {
        type: 'psprest'
    }

});
