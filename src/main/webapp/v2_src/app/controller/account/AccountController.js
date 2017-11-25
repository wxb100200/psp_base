Ext.define('Base.controller.account.AccountController', {
    extend: 'Ext.app.Controller',
    views: [
        'account.AccountGrid',
        'account.AccountWindow'
    ],
    stores:[
        'account.AccountStore'
    ],
    init:function(){
        this.control({
            'accountgrid button[action=add]':{
                click:this.openAddAccountWindow
            },
            'accountwindow button[action=ok]':{
                click:this.addAccount
            }
        });
    },
    openAddAccountWindow:function(btn){
        var grid=btn.up('grid'),
            win=Ext.widget('accountwindow');
        win.show();
    },
    addAccount:function(btn){
        var win=btn.up('window'),
            form=win.down('form');
        if(form.isValid()){
            var mask= G.mask(win);
            form.submit({
                url:'../service/account/addAccount',
                success:function(form,response){
                    mask.hide();
                    Ext.Msg.alert('成功','操作成功');
                },
                failure:function(form,response){
                    mask.hide();
                    Ext.Msg.alert('失败','操作失败');

                }
            });
        }
    }
});