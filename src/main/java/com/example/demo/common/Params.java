package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Params {

    private Integer pageNo;

    private Integer pageSize;

    private String createAtBegin;

    private String createAtEnd;

    private String orderField;

    private Boolean orderAsc;

}
