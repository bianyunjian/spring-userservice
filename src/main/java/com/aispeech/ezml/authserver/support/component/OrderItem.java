package com.aispeech.ezml.authserver.support.component;

import lombok.Data;

import javax.swing.*;

/**
 * 复杂查询中的排序参数项
 *
 * @author ZhangXi
 */
@Data
public class OrderItem {

    private String name;

    private SortOrder order;

}
