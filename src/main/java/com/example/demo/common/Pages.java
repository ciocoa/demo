package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pages<T> {

    private Integer pageNo;

    private Integer pageSize;

    private List<T> list;

    private Long total;

}
