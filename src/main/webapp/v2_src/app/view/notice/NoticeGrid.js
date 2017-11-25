Ext.define('Base.view.notice.NoticeGrid',{
    extend:'Base.view.BaseGrid',
    alias:'widget.noticegrid',
    title:'公告信息',
    store:'',
    bbar:['->',{
        xtype:'pagingtoolbar',
        displayInfo:true,
        flex:1,
        store:''
    }],
    tbar:[

    ],
    columns:[
        { text:'序号', xtype:'rownumberer',width:30},
        { text:'站点名称', dataIndex:'siteName' ,flex:1},
        { text:'规划编号', dataIndex:'planCode' ,flex:1},
        { text:'站址', dataIndex:'address' ,flex:1},
        { text:'机房名称', dataIndex:'area' ,flex:1}, // 区域, 后来字面上改成 机房名称
        { text:'类型', dataIndex:'type' ,flex:1},
        { text:'经纬度', dataIndex:'latitude' ,flex:1},
        { text:'设计要求完成时间', dataIndex:'requiredDesignTime',xtype:'datecolumn',format:'Y-m-d'  ,flex:1},
        { text:'监理要求完成时间', dataIndex:'requiredSupervisionTime',xtype:'datecolumn',format:'Y-m-d'  ,flex:1},
        { text:'建设内容', dataIndex:'buildContent' ,flex:1},
        { text:'设计单位', dataIndex:'designCompany' ,flex:1},
        { text:'设计状态',dataIndex:'designStatus',width:80},
        { text:'监理单位', dataIndex:'supervisionCompany' ,flex:1},
        { text:'优化中心提供时间', dataIndex:'optimizeTime',xtype:'datecolumn',format:'Y-m-d' ,flex:1}
    ],
    initComponent:function(){
        var me=this;
        me.callParent(arguments);
        me.on({
            afterrender:me.loadNoticeGridData
        });
    },
    loadNoticeGridData:function(){
        var me=this,
            store=me.getStore();

        store.loadPage(1);
    }

});