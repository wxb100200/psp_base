Ext.define('Base.controller.MainController', {
    extend: 'Ext.app.Controller',
    views:[
        'Viewport'
    ],

    showMainWidget:function(alias,options){
        if(alias.indexOf('widget.')<0){
            alias='widget.'+alias;
        }
        var bodyView=Ext.getCmp('bodyview'),
            obj=Ext.createByAlias(alias);
        bodyView.removeAll(true);//从父容器中移除所有子组件.
        bodyView.add(obj);//向当前容器中添加组件

        var str = alias.substring(7); // remove prefix "widget."
        if(options){
            str += Ext.encode(options);
        }
        G.historyAdding=true;
        Ext.History.add(str);
    }
});
