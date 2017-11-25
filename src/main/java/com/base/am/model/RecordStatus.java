package com.base.am.model;

import com.avaje.ebean.annotation.EnumValue;

/**
 * 记录数据是否有效
 */
public enum RecordStatus {
    /** record has been deleted! */
    @EnumValue("Invalid")
    Invalid,

    @EnumValue("Valid")
    Valid
}
