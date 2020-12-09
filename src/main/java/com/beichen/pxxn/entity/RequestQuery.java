package com.beichen.pxxn.entity;

import lombok.Data;

/**
 * @author A51398
 * @version 1.0
 * @description TODO
 * @since 2020/12/9 8:33
 */
@Data
public class RequestQuery {
    private Integer id;
    private Integer surePlay = 1;
    private Boolean isTrySee = true;
}
