Ext.define('Base.view.home.NoticeGroupGrid',{
    extend:'Base.view.BaseGrid',
    alias:'widget.noticegroupgrid',
    cls:'index-grouped-grid',
    selType: 'rowmodel',
    border:true,
    bbar:['->','<a href=\"javascript:G.showMainWidget(\'noticemainpanel\');\" style="text-decoration: none;">更多..</a>'],
    features: [
        {
            ftype: 'grouping',
            groupHeaderTpl: '{name}',
            hideGroupedHeader: true,
            startCollapsed: false //展开(false)或收起(true)
        }
    ],
    store:{
        fields: ['title', 'datatype'],
        data: [
            { 'title': '通知公告1', 'datatype': '通知公告'},
            { 'title': '会议通知1', 'datatype': '会议通知'},
            { 'title': '代办事宜1', 'datatype': '代办事宜'},
            { 'title': '代办事宜2', 'datatype': '代办事宜'},
            { 'title': '代办事宜3', 'datatype': '代办事宜'}
        ],
        groupField: 'datatype'
    },
    columns:[
        {text:'公告与会议通知',dataIndex:'title',flex:1}
    ]
});