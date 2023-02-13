package com.example.demo.util;

import com.example.demo.common.Params;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Base64;
import java.util.UUID;

public class ToolUtil {

    /**
     * 获取分页信息
     *
     * @param params 分页条件
     * @return 分页信息
     */
    public static Pageable getPageable(Params params) {
        Pageable pageable = PageRequest.of(params.getPageNo() - 1, params.getPageSize());
        if (params.getOrderAsc() != null && params.getOrderField() != null) {
            Sort.Direction direction = params.getOrderAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sort = Sort.by(direction, params.getOrderField());
            pageable = PageRequest.of(params.getPageNo() - 1, params.getPageSize(), sort);
        }
        return pageable;
    }

    /**
     * 生成 UUID
     *
     * @return uuid
     */
    public static String generalUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * MIME 型 解密Base64
     *
     * @param string 需解密的 key
     * @return byte[]
     */
    public static byte[] getBase64(String string) {
        return Base64.getMimeDecoder().decode(string);
    }

}
