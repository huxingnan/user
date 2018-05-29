package com.dream.domain.dto;

import lombok.Data;

/**
 * @author huxingnan
 * @date 2018/5/13 11:21
 */
@Data
public class PageInfo<T> {
    private Integer size;
    private Integer pageNum;
    private  T param;

    public Integer getSize() {
        return (size == null || size <= 0)?10:size;
    }

    public Integer getPageNum() {
        return (pageNum == null|| pageNum < 0)?0:pageNum;
    }

}
