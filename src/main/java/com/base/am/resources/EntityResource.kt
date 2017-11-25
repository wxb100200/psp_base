package com.base.am.resources

import com.avaje.ebean.*
import com.base.am.model.BaseEntity
import com.base.am.model.RecordStatus
import com.base.am.util.EbeanUtil
import com.base.am.util.IOUtil
import org.apache.log4j.Logger

import javax.persistence.PersistenceException
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.*
import javax.ws.rs.core.*

import java.io.IOException

@Path("/entity/{className}{uriOptions:(:[^/]+?)?}")
@javax.ws.rs.Consumes("application/json", "text/json")
@javax.ws.rs.Produces("application/json;charset=utf-8", "text/json")
class EntityResource {

    private var defaultFindAllOrderBy: String? = null

    @PUT
    fun updateAll(@PathParam("className") className: String): PagableRecordsWrapper {
        return PagableRecordsWrapper.empty()
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("className") className: String, @PathParam("id") id: String): PagableRecordsWrapper {
        val c = findClass(className) ?: return PagableRecordsWrapper.error(501,"className does not exist: $className")

        val entity = com.base.am.util.EbeanUtil.find(c, id)
        entity.changeTime = System.currentTimeMillis()
        entity.recordStatus = RecordStatus.Invalid
        Ebean.update(entity)

        return PagableRecordsWrapper.single(entity)
    }

    @DELETE
    fun deleteAll(@PathParam("className") className: String, @PathParam("id") id: String): PagableRecordsWrapper {
        return PagableRecordsWrapper.empty()
    }

    @POST
    fun create(@PathParam("className") className: String, @Context request: HttpServletRequest): PagableRecordsWrapper {
        val entity = assetObject(className, request) ?: return PagableRecordsWrapper.error(501,"className does not exist: $className")
        entity.createTime = System.currentTimeMillis()
        Ebean.save(entity)
        return PagableRecordsWrapper.single(entity)
    }

    @PUT
    @Path("/{id}")
    fun update(@PathParam("className") className: String, @PathParam("id") id: String, @Context request: HttpServletRequest): PagableRecordsWrapper {
        val entity = assetObject(className, request) ?: return PagableRecordsWrapper.error(501,"className does not exist: $className")
        entity.changeTime = System.currentTimeMillis()
        Ebean.update(entity)
        return PagableRecordsWrapper.single(entity)
    }

    private fun assetObject(className: String, request: HttpServletRequest): BaseEntity? {
        var content = ""
        try {
            content = IOUtil.readAsText(request.inputStream, "UTF-8")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        logger.info("Content: " + content)
        val c = findClass(className) ?: return null
//        val entity = gson.fromJson(content, c) // gson 转换的时候,不能处理循环依赖的问题,比如对象与对象之间有一对多关联关系等。
        val entity = Ebean.createJsonContext().toBean(c,content)
        return entity
    }

    @GET
    @Path("/{id}")
    fun find(@Context ui: UriInfo, @PathParam("className") className: String, @PathParam("uriOptions") uriOptions: String,
             @PathParam("id") id: String, @QueryParam("debug") @DefaultValue("N") debug:String): PagableRecordsWrapper {

        val c = findClass(className) ?: return PagableRecordsWrapper.error(501,"className does not exist: $className")

        val query = EbeanUtil.find(c).setId(id)

        if (!EbeanUtil.applyUriOptions(uriOptions, query)) {
            configDefaultFindAllQuery(query)
        }
        EbeanUtil.configQuery(query, ui)
        if (defaultFindAllOrderBy != null) {
            // see if we should use the default orderBy clause
            val orderBy = query.orderBy()
            if (orderBy.isEmpty) {
                query.orderBy(defaultFindAllOrderBy)
            }
        }

        var rowCount = 0
        try {
            rowCount = query.findRowCount()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            return PagableRecordsWrapper.ok(query.findList(), rowCount)
        } catch (e: Exception) {
            logger.error("find Error.", e)
            return PagableRecordsWrapper.error(506, " find 错误（鉴于安全原因，仅简要列出错误类型，详细错误请查看系统日志）： " + toErrorString(e,debug))
        }
    }

    @GET
    fun findAll(@Context ui: UriInfo,
                @PathParam("className") className: String,
                @PathParam("uriOptions") uriOptions: String,
                @QueryParam("debug") @DefaultValue("N") debug:String): PagableRecordsWrapper {

        val c = findClass(className) ?: return PagableRecordsWrapper.error(501,"className does not exist: $className")

        val query: Query<out Any>

        try {
            query = EbeanUtil.find(c)
        } catch (e: PersistenceException) {
            logger.error("create query error.", e)
            return PagableRecordsWrapper.error(506, toErrorString(e,debug))
        }

        if (!EbeanUtil.applyUriOptions(uriOptions, query)) {
            configDefaultFindAllQuery(query)
        }
        EbeanUtil.configQuery(query, ui)
        if (defaultFindAllOrderBy != null) {
            // see if we should use the default orderBy clause
            val orderBy = query.orderBy()
            if (orderBy.isEmpty) {
                query.orderBy(defaultFindAllOrderBy)
            }
        }

        val rowCount:Int
        try {
            rowCount = query.findRowCount()
        } catch (e: Exception) {
            logger.error("findRowCount Error.", e)
            return PagableRecordsWrapper.error(506, " findRowCount 错误（鉴于安全原因，仅简要列出错误类型，详细错误请查看系统日志）： " + toErrorString(e,debug))
        }

        return PagableRecordsWrapper.ok(query.findList(), rowCount)
    }

    private fun toErrorString(e:Throwable, debug:String):String {
        if(debug=="Y") return IOUtil.getErrorStackTrace(e)
        else return e.javaClass.name
    }

    private fun findClass(className:String):Class<BaseEntity>?{
        var c: Class<*>?
        try {
            c = Class.forName("com.base.am.model." + className)
        } catch (e: ClassNotFoundException) {
            return null
            /*try {
                c = Class.forName("model." + className)
            } catch (e1: ClassNotFoundException) {
                return null
            }*/
        }

        @Suppress("UNCHECKED_CAST")
        return c as Class<BaseEntity>
    }

    @Suppress("UNUSED_PARAMETER")
    private fun configDefaultFindAllQuery(query: Query<out Any>) {
    }

    companion object {

        private val logger = Logger.getLogger(EntityResource::class.java.name)

//        private val gson = GsonBuilder().create()
    }

}

