package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    public static final Integer SUCCESS = 200;

    public static final Integer FAILURE = 400;

    public static final Integer NO_ACCESS = 403;

    public static final Integer ERROR = 500;

    public static final String MESSAGE = "成功";

    private Integer code;

    private T data;

    private String message;

}
