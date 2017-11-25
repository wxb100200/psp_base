Ext.define('Base.view.BodyView',{
   extend:'Ext.panel.Panel',
    alias:'widget.bodyview',
    layout:'fit',
    id:'bodyview',
    border:false,
    items:{
        xtype:'indexpanel'
    }

});