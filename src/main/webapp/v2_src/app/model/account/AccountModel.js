/**
 * 新的无线站点的Model, 站点由PMS同步而来
 */
Ext.define('Base.model.account.AccountModel', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id', type: 'int' },
        { name: 'createTime', type: 'date',dateFormat:'time' },
        { name: 'changeTime', type: 'date',dateFormat:'time' },
        { name: 'loginName', type: 'string' },
        { name: 'firstLogin', type: 'string' },

        { name: 'roleList', type: 'auto' },
        { name: 'roleList.id', mapping:'roleList.id' },
        { name: 'roleList.roleName', mapping:'roleList.roleName' },
        { name: 'roleList.roleType', mapping:'roleList.roleType' },

        { name: 'person', type: 'auto'},
        { name: 'person.id', mapping:'person.id'},
        { name: 'person.name', mapping:'person.name'},
        { name: 'person.email', mapping:'person.email'},
        { name: 'person.phoneNumber', mapping:'person.phoneNumber'},

        { name: 'person.company', type: 'auto'},
        { name: 'person.company.id', mapping:'person.company.id'},
        { name: 'person.company.name', mapping:'person.company.name'}
    ]
});
