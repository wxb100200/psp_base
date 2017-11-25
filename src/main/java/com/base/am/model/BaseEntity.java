package com.base.am.model;


import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 */
@MappedSuperclass
public class BaseEntity implements Serializable{
    /**
     * 给对象方法使用的日志
     */
    @Transient
    protected Logger log = Logger.getLogger(this.getClass());

    @Id
    @GeneratedValue
    private  Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="rs")
    private RecordStatus recordStatus=RecordStatus.Valid;

    @Column(name="ref_id",unique = true)
    private String refId;

    /**
     * 创建时间
     */
    private  Long createTime=new Date().getTime();

    /**
     * 修改时间
     */
    private  Long changeTime=new Date().getTime();

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Long changeTime) {
        this.changeTime = changeTime;
    }
}
