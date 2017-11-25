Ext.define('Base.controller.supplier.CompanyController', {
    extend: 'Ext.app.Controller',
    views: [
        'supplier.CompanyGrid',
        'supplier.CompanyWindow',
        'supplier.CompanyInfoWindow'
    ],
    stores:[
        'supplier.CompanyStore'
    ],
    init: function () {
        this.control({
           'companygrid button[action=add]':{
               click:this.openAddCompanyWindow
           },
            'companywindow button[action=ok]':{
                click:this.addOrModifyCompany
            },
            'companygrid button[action=modify]':{
                click:this.modifyCompany
            },
            'companygrid button[action=delete]':{
                click:this.deleteCompany
            },
            'companygrid button[action=detail]':{
                click:this.openCompanyInfoWindow
            }
        });
    },
    openAddCompanyWindow:function(btn){
        var me=this,
            grid=btn.up('grid'),
            win=Ext.widget('companywindow');
        win.title='新增公司';
        win.show();
        grid.getSelectionModel().deselectAll();
    },
    addOrModifyCompany:function(btn){
        var win=btn.up('window'),
            form=win.down('form');
        if(form.isValid()){
            var mask= G.mask(win);
            form.submit({
                url:'../service/company/addOrModifyCompany',
                success:function(form,response){
                    console.log(arguments);
                    mask.hide();
                    var data=Ext.decode(response.response.responseText);
                    if(data.success) {
                        Ext.Msg.alert("成功","操作成功",function(){
                            win.close();
                        });
                    }else{
                        Ext.Msg.alert("成功",data.message);
                    }
                },
                failure:function(form,response){
                    mask.hide();
                    var msg=response.result.message;
                    Ext.Msg.alert('失败',msg);
                }
            });
        }
    },
    modifyCompany:function(btn){
        var grid=btn.up('grid'),
            record=grid.getSelectionModel().getSelection()[0],
            win=Ext.widget('companywindow');
        win.title='修改公司';
        win.show();
        win.down('textfield[name=companyName]').setReadOnly(true);
        win.loadCompanyData(record);
        win.on('close',function(){grid.loadCompanyData();});
    },
    deleteCompany:function(btn){
        var grid=btn.up('grid'),
            record=grid.getSelectionModel().getSelection()[0],
            companyId=record.get('id');
        Ext.MessageBox.confirm('提示','确定删除？',function(btn){
            if(btn=='yes'){
                Ext.Ajax.request({
                    url:'../service/company/deleteCompany/'+companyId,
                    method:'DELETE',
                    success:function(response){
                        var data=Ext.decode(response.responseText);
                        if(data.success) {
                            Ext.Msg.alert('成功','删除成功',function(){
                                grid.loadCompanyData();
                            });
                        }else{
                            Ext.Msg.alert("提示",data.message);
                        }
                    },
                    failure:function(){
                        console.log(arguments);
                        Ext.Msg.alert('失败','删除失败');
                    }
                });

            }
        });
    },
    openCompanyInfoWindow:function(btn){
        var grid=btn.up('grid'),
            record=grid.getSelectionModel().getSelection()[0],
            win=Ext.widget('companyinfowindow');
        win.show();

    }
});