Ext.define('Base.view.BaseGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.basegrid',
    //emptyText: G['default']['gridEmptyText'],
    overflowX: 'auto',
    columnLines: true,
    /**
     *  可以取消掉checkbox:
     *  selType:'rowmodel',
     */
    selType: 'checkboxmodel',
    allowDeselect: true,
    sortableColumns: false,
    enableColumnMove: false,
    border:false,
    viewConfig: {
        stripeRows: true,
        enableTextSelection: true
    },
    showCellTip: true, // 是否要显示cell里面的内容作为tip。
    constructor: function (config) {
        var me = this;
        config = config || {};
        me.callParent([config]);
        if (me.showCellTip) me.on('render', function () {
            Ext.defer(function () {
                if (me.recordTip) return;
                var view = me.getView();
                try {
                    var tip = Ext.create('Ext.tip.ToolTip', {
                        // The overall target element.
                        target: view.el,
                        // Each grid row causes its own separate show and hide.
                        delegate: 'td.x-grid-cell', //view.itemSelector, // tr.x-grid-row
                        // Moving within the row should not hide the tip.
                        trackMouse: true,
                        // Render immediately so that tip.body can be referenced prior to the first show.
                        renderTo: Ext.getBody(),
                        listeners: {
                            // Change content dynamically depending on which element triggered the show.
                            beforeshow: function (tip) {
                                if (!tip || !tip.triggerEvent || !tip.triggerEvent.target || !tip.triggerEvent.target.textContent) {
                                    return false;
                                }
                                var txt = "" + tip.triggerEvent.target.textContent;
                                txt = (txt || "").trim();

                                if (!txt) {
                                    return false; // 不显示空白tip
                                }
                                tip.update(tip.triggerEvent.target.innerHTML);
                            }
                        }
                    });
                    me.recordtip = tip;
                }catch(e){}
            }, 100)
        });
    },

    /**
     * 取得当前选择的数据
     * @returns {*|Selection}
     */
    getSelection:function(){
        return this.getSelectionModel().getSelection();
    },

    /**
     * 取消选择
     */
    deselectAll:function(){
        this.getSelectionModel().deselectAll();
    }

});

