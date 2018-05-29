package com.dream.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huxingnan
 * @date 2018/5/12 16:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAIL_CODE = 500;

    private Integer code;
    private Boolean success;
    private T result;
}
