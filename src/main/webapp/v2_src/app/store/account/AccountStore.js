Ext.define('Base.store.account.AccountStore', {
    extend: 'Base.store.BaseStore',
    model: 'Base.model.account.AccountModel',
    proxy: {
        type: 'psprest',
        url: "../service/entity/Account:(id,createTime,changeTime,loginName,firstLogin,roleList(id,roleName,roleType)," +
        "person(id,name,email,phoneNumber,company(id,name)))"
    }
});