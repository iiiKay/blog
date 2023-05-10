package com.kay.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private Integer code;
    private String msg;
    private Map<String, Object> item;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, Map<String, Object> item) {
        this.code = code;
        this.item = item;
    }
}
