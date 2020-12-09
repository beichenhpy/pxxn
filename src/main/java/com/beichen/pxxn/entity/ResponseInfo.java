package com.beichen.pxxn.entity;

import lombok.Data;

/**
 * @author A51398
 * @version 1.0
 * @description TODO
 * @since 2020/12/9 9:02
 */
@Data
public class ResponseInfo {
    private Integer resultCode;
    private ResponseData data;
}
