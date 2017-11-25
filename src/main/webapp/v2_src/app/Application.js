Ext.define('Base.Application', {
    name: 'Base',

    extend: 'Ext.app.Application',

    views: [
        'BaseGrid'
    ],

    controllers: [
        'supplier.CompanyController',
        'account.AccountController',
        'home.IndexController',
        'notice.MeetingController',
        'notice.NoticeController',
        'MainController'
    ],

    stores: [
        'BaseStore'
    ],
    requires:[
        'Base.G',
        'Ext.LoadMask',
        'Ext.util.History',
        'Ext.window.MessageBox',
        'Ext.data.*',
        'Ext.draw.Text',
        'Ext.form.*',
        'Ext.grid.*',
        'Ext.chart.*',
        'Ext.tip.*',
        'Ext.selection.*',
        'Ext.selection.CheckboxModel',
        'Ext.panel.*',
        'Ext.layout.*',
        'Ext.layout.container.Border',
        'Ext.grid.plugin.RowEditing',
        'Base.proxy.Rest'
    ],
    launch: function() {
        Ext.History.init();
        Ext.History.on('change', function(token) {
            //跳出死循环
            if(G.historyAdding){
                G.historyAdding=false;
                return;
            }
            if(token){
                var url=document.location.href,
                    str=url.substring(url.indexOf('#')+1),
                    alias,options;
                if(str.indexOf('{')<0){
                    alias=str;
                }else{
                    alias=str.substring(0,str.indexOf('{'));
                    options=Ext.decode(str.substring(str.indexOf('{'),str.indexOf('}')+1));
                }
                G.showMainWidget(alias,options);
            }
        });
        if(G.historyAdding==false){
            var url=document.location.href,
                str=url.substring(url.indexOf('#')+1),
                alias,options;
            if(str.indexOf('{')<0){
                alias=str;
            }else{
                alias=str.substring(0,str.indexOf('{'));
                options=Ext.decode(str.substring(str.indexOf('{'),str.indexOf('}')+1));
            }
            G.showMainWidget(alias,options);
        }

    }
});
