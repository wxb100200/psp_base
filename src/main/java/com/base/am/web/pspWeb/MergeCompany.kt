import java.io.FileInputStream
const val oldCompanyId=5721
const val newCompanyId=22
fun main(args:Array<String>){
    FileInputStream("d://ora-create.sql").reader(Charsets.UTF_8).readLines().filter { it.contains("references am_company (id)") }.forEach {
        //println(it)
        println(it.toUpdateSql())
    }
}
private fun String.toUpdateSql():String{
    val arr=this.split(' ')
    val tableName=arr[2]
    if(tableName.contains("am_company_basic")
            ||tableName.contains("am_company_extra_info")
            ||tableName.contains("am_company_status")
            ||tableName.contains("am_notice_details")
            ||tableName.contains("am_person")
            ||tableName.contains("am_qual_draft")
            ||tableName.contains("am_sup_han_detail")
            ||tableName.contains("am_supplier_department")
            ||tableName.contains("am_company_draft")
            ||tableName.contains("am_company_manager")
    ){
        return ""
    }
    val columnName=arr[8].realColumnName()
//    return  "select * from $tableName where $columnName=$oldCompanyId;"
    return  "update $tableName set $columnName=$newCompanyId where $columnName=$oldCompanyId;"
}
private fun String.realColumnName():String{
    return this.substring(1,this.length-1)
}


