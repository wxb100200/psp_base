Ext.define('Base.G',{
    statics:{
        historyAdding:false,
        getHome:function(){
            return this.getController("MainController");
        },
        getController: function (name) {
            return Base.app.controllers.get(name);
        },
        showMainWidget:function(alias,options,addHistory){
            return this.getHome().showMainWidget(alias,options,addHistory);
        },
        mask: function (component, msg) {
            var myMask = new Ext.LoadMask({target: component, msg: msg || "正在加载数据，请耐心等待...", autoRender: true});
            myMask.show();
            return myMask;
        },
        companyTypeRenderer:function(val){
            switch (val){
                case 'supplier':return '供应商';
                case 'chinaMobile':return '中国移动';
                case 'design':return '设计公司';
                case 'construction':return '施工公司';
                case 'supervision':return '监理公司';
                case 'audit':return '审计公司';
                case 'c':return '其他';
            }
        }/*,
        default:{
            pageSize: 50,
            gridEmptyText: '[空白]',
            noImageText: '[无图片]'
        }*/
    }
});
G=Base.G;
