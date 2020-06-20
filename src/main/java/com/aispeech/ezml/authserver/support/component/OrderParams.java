package com.aispeech.ezml.authserver.support.component;

import lombok.Data;

import java.util.List;

/**
 * 排序参数
 *
 * @author ZhangXi
 */
@Data
public class OrderParams {

    /**
     * 排序对
     */
    private List<OrderItem> orderPairs;


}
