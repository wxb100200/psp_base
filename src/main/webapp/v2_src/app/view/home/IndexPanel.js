Ext.define('Base.view.home.IndexPanel',{
   extend:'Ext.panel.Panel',
    alias:'widget.indexpanel',
    border:false,
    layout:{
        type:'vbox',
        align:'stretch'
    },
    items:[
        {
            layout:'border',
            border:false,
            flex:1,
            items:[
                {
                    xtype:'noticegroupgrid',
                    margins:'5 0 5 5',
                    bodyStyle: 'border-width:0px 1px 0px 1px',
                    region:'west',
                    flex:1
                },
                {
                    xtype:'panel',
                    margins:'5 0 5 5',
                    region:'center',
                    title:'center',
                    flex:2
                },
                {
                    xtype:'panel',
                    margins:'5 5 5 5',
                    region:'east',
                    title:'east',
                    flex:1
                }
            ]

        },
        {
            xtype:'panel',
            flex:1,
            title:'asfasddasfas'
        }
    ]

});