Ext.define('Base.view.account.AccountGrid',{
    extend:'Base.view.BaseGrid',
    alias:'widget.accountgrid',
    title:'账号信息',
    store:'account.AccountStore',
    bbar:['->',{
        xtype:'pagingtoolbar',
        displayInfo:true,
        flex:1,
        store:'account.AccountStore'
    }],
    tbar:[
        {text:'新增用户',action:'add', iconCls:'icon-user-add'},
        {text:'注销用户',action:'delete',iconCls:'icon-user-delete',disabled:true},
        {text:'修改密码',action:'chanegPwd', iconCls:'icon-user-edit', disabled:true },
        {text:'修改用户',action:'editUser', iconCls:'icon-user-edit',disabled:true },
        {text:'设置用户权限',disabled:true,menu:[
             {text:'设为公司管理员',disabled:true },
             {text:'设为项目管理员',disabled:true},
            {text:'设为合同管理员', disabled:true},
            {text:'设为供应商管理员',disabled:true },
            {text:'设为专业部室管理员', disabled:true}
        ]},
        {text:'取消用户权限',disabled:true,menu:[
            {text:'取消公司管理员',disabled:true},
            {text:'取消项目管理员',disabled:true},
            { text:'取消合同管理员', disabled:true },
            { text:'取消供应商管理员',disabled:true },
            {text:'取消专业部室管理员',disabled:true }
        ]},
        {text:'模拟用户',disabled:true},
        '->',
        {xtype:'textfield',fieldLabel:'姓名',labelAlign:'right',labelWidth :50,name:'name'},
        {xtype:'textfield',fieldLabel:'手机号',labelAlign:'right',labelWidth :50,name:'phoneNumber'},
        {text:'查询',action:'search',iconCls:'icon-find'}
    ],
    showCellTip:true,
    columns:[
        { text:'序号', xtype:'rownumberer',width:40},
        { text:'公司', dataIndex:'person.company.name' ,flex:2.5},
        { text:'姓名', dataIndex:'person.name' ,flex:1},
        { text:'邮箱', dataIndex:'person.email' ,flex:2},
        { text:'手机号', dataIndex:'person.phoneNumber' ,flex:1},
        { text:'登录账号', dataIndex:'loginName' ,flex:1}, // 区域, 后来字面上改成 机房名称
        { text:'角色权限', dataIndex:'' ,flex:2}
    ],
    initComponent:function(){
        var me=this;
        me.callParent(arguments);
        me.on({
            afterrender:me.loadAccountData
        });
    },
    loadAccountData:function(){
        var me=this,
            store=me.getStore();
        store.loadPage(1);
    }

});