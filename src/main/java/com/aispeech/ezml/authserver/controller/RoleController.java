package com.aispeech.ezml.authserver.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色API
 *
 * @author ZhangXi
 */
@Tag(name = "/role", description = "角色接口")
@RestController
@RequestMapping(path = "/role")
public class RoleController {
}
