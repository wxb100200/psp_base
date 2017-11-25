Ext.define('Base.view.supplier.CompanyGrid',{
    extend:'Base.view.BaseGrid',
    alias:'widget.companygrid',
    title:'公司列表',
    store:'supplier.CompanyStore',
    bbar:['->',{
        xtype:'pagingtoolbar',
        displayInfo:true,
        flex:1,
        store:'supplier.CompanyStore'
    }],
    tbar:[
        {text:'新增公司',action:'add', iconCls:'icon-user-add'},
        {text:'修改公司',action:'modify',iconCls:'icon-user-edit',disabled:true},
        {text:'删除公司',action:'delete', iconCls:'icon-user-delete', disabled:true },
        {text:'查看公司详情',action:'detail',disabled:true },
        '->',
        {xtype:'textfield',fieldLabel:'公司名称',labelAlign:'right',name:'companyName'},
        {xtype:'combo',fieldLabel:'公司类型',labelAlign:'right',name:'companyType',displayField:'name',valueField:'value',
            store:{
                fields:['name','value'],
                data:[
                    {name:'全部',value:'all'},
                    {name:'供应商',value:'supplier'},
                    {name:'中国移动',value:'chinaMobile'},
                    {name:'设计公司',value:'design'},
                    {name:'施工公司',value:'construction'},
                    {name:'监理公司',value:'supervision'},
                    {name:'审计公司',value:'audit'},
                    {name:'其他',value:'其他'}
                ]
        }},
        {text:'查询',action:'search',iconCls:'icon-find'}

    ],
    showCellTip:true,
    selType:'checkboxmodel',
    selModel:{
        mode:'SINGLE',
        showHeaderCheckbox:false,
        allowDeselect:true,
        listeners:{
            selectionchange:function(selModel,selected){
                var grid=selModel.view.up('grid'),
                    btnModify=grid.down('button[action=modify]'),
                    btnDelete=grid.down('button[action=delete]'),
                    btnDetail=grid.down('button[action=detail]');
                btnModify.disable();
                btnDelete.disable();
                btnDetail.disable();
                if(selModel.getCount()<=0) return;
                btnModify.enable();
                btnDelete.enable();
                btnDetail.enable();
            }
        }
    },
    columns:[
        { text:'序号', xtype:'rownumberer',width:40},
        { text:'公司名称', dataIndex:'name' ,flex:2},
        { text:'公司类型', dataIndex:'type' ,flex:1,renderer: G.companyTypeRenderer},
        { text:'公司地址', dataIndex:'address' ,flex:3},
        { text:'创建时间', dataIndex:'createTime' ,flex:1,xtype:'datecolumn',format:'Y-m-d'}
    ],
    initComponent:function(){
        var me=this;
        me.callParent(arguments);
        me.on({
            afterrender:me.loadCompanyData
        });
        var btnSearch=me.down('button[action=search]');
        btnSearch.on({
            click:function(){
                me.loadCompanyData();
            }
        });
    },
    loadCompanyData:function(){
        var me=this,
            store=me.getStore();
        store.clearFilter(true);
        var filters=[];

        var companyName=me.down('textfield[name=companyName]').getValue();
        if(companyName){
            filters.push(
                new Ext.util.Filter({
                    property:'name',
                    value:companyName,
                    operator:'~'
                })
            );
        }

        var companyType=me.down('textfield[name=companyType]').getValue();
        if(companyType !='all' && companyType){
            filters.push(
                new Ext.util.Filter({
                    property:'type',
                    value:companyType,
                    operator:'~'
                })
            );
        }

        if(filters.length > 0){
            store.addFilter(filters,false);//给store添加一个过滤器
        }
        store.loadPage(1);
    }

});