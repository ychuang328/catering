package com.weiwork.common.base;

public interface BaseService<T extends BaseEntity,M extends BaseMapper<T>> extends BaseServiceRemote<T> {
    public M getDefaulteMapper();
}
