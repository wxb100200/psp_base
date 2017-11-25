Ext.define('Base.proxy.Rest', {
    extend: 'Ext.data.proxy.Rest',
    alias : 'proxy.psprest',
    noCache:true,
    reader: {
        root: 'records',
        successProperty: 'success'
    },

    // 覆盖，以将filter里面的operator传到后台
    encodeFilters: function(filters) {
        var min = [],
            length = filters.length,
            i = 0;

        for (; i < length; i++) {
            var f = filters[i];
            if(! f.property){
                // console.log(f);
                continue; // 跳过所有函数的，因为函数的Filter只能是本地的
            }

            // 特殊的WHERE操作,value 中为PSP.W的对象
            if(f.operator=='WHERE'){
                min[i] = f.value;
            }else{
                min[i] = {
                    property: f.property,
                    value   : f.value,
    /*
                    anyMatch:f.anyMatch,
                    caseSensitive:f.caseSensitive,
    */
                    operator:f.operator || '='

                };
            }
        }
        return this.applyEncoding(min);
    },

    // 用于子类覆盖， 参见 PSP.store.supplierInfo.StatisticalTableInstrument 的实现
    // 可以在Store加载数据的时候，修改URL。但是与POST/PUT/DELETE无关。因此保存数据的url不受影响
    createGetUrl:function(url,request){
        return url ;
    },

    getUrl:function(request){
        if(this.getMethod(request)==='GET'){
            return this.createGetUrl(this.url,request);
        }else{
            return this.callParent(arguments);
        }
    }
});