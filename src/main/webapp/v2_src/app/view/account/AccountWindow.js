Ext.define('Base.view.account.AccountWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.accountwindow',
    title: '新增用户',
    width: 300,
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
            {name:'personName',fieldLabel:'姓名'},
            {name:'phoneNumber',fieldLabel:'手机'},
            {name:'email',fieldLabel:'邮箱'},
            {name:'loginName',fieldLabel:'登录账号'}
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