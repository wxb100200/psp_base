Ext.define('Base.view.supplier.CompanyWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.companywindow',
    title: '公司',
    width: 500,
    modal: true,
    closable: true,
    items:{
        xtype:'form',
        bodyPadding:'5 5 5 10',
        border:false,
        layout:{
            type:'vbox',
            align:'stretch'
        },
        defaults:{
            xtype:'textfield',
            labelWidth:60,
            labelAlign:'right',
            margin:'5 5 0 0',
            allowBlank:false
        },
        items:[
            {name:'companyId',xtype:'hidden'},
            {name:'companyName',fieldLabel:'公司名称'},
            {name:'companyType',fieldLabel:'类型',xtype:'combo',displayField:'name',valueField:'value',
                store:{
                    fields:['name','value'],
                    data:[
                        {name:'供应商',value:'supplier'},
                        {name:'中国移动',value:'chinaMobile'},
                        {name:'设计公司',value:'design'},
                        {name:'施工公司',value:'construction'},
                        {name:'监理公司',value:'supervision'},
                        {name:'审计公司',value:'audit'},
                        {name:'其他',value:'其他'}
                    ]
                }

            },
            {name:'companyAddress',fieldLabel:'地址'}
        ]
    },
    bbar:['->',
        {
            text: '提交',
            action: 'ok',
            iconCls:'icon-ok'
        },
        {
            text: '关 闭',
            iconCls:'icon-cancel',
            action: 'back',
            handler: function (btn) {
                btn.up('window').close();
            }
        }
    ],
    loadCompanyData:function(record){
        var me=this;
        me.down('hidden[name=companyId]').setValue(record.get('id'));
        me.down('textfield[name=companyName]').setValue(record.get('name'));
        me.down('textfield[name=companyType]').setValue(record.get('type'));
        me.down('textfield[name=companyAddress]').setValue(record.get('address'));
    }
});