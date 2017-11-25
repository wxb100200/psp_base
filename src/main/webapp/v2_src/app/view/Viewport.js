Ext.define('Base.view.Viewport', {
    extend: 'Ext.container.Viewport',
    border:false,
    requires:[
        'Base.view.HeadView',
        'Base.view.MenuView',
        'Base.view.FooterView',
        'Base.view.BodyView'
    ],
    layout: {
        type: 'vbox',
        align:'stretch'
    },
    items: [
        {
            xtype:'headview'
        },
        {
            xtype:'menuview'
        },
        {
            xtype:'bodyview',
            flex:1
        },
        {
            xtype:'footerview'
        }
    ]
});
