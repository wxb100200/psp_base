package com.base.am.web.pspWeb

import com.base.am.util.FileUtil

const val OldPersonId=59823      //9168 //59823
const val NewPersonId=4021
fun main(args:Array<String>){
    FileUtil.filterRecord("d://ora-create.sql","references am_person (id)")!!.forEach {
        println(it.toUpdateSql())
    }
}
private fun String.toUpdateSql():String{
    val arr=this.split(' ')
    val tableName=arr[2]
    if(tableName.contains("as_record")
            ||tableName.contains("am_auth_accounts")
            ||tableName.contains("am_sys_up_log_detail")
            ||tableName.contains("am_person_projects")
            ||tableName.contains("am_notify")
            ||tableName.contains("am_commission_problem")
    ){
        return ""
    }
    val columnName=arr[8].realColumnName()
    return  "select * from $tableName where $columnName=$OldPersonId;"
//    return  "update $tableName set $columnName=${NewPersonId} where $columnName=${OldPersonId};"
}
private fun String.realColumnName():String{
    return this.substring(1,this.length-1)
}
