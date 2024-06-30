package com.cyy.finance.admin.api.controller;


import com.cyy.commom.dto.ApiResponse;
import com.cyy.finance.biz.dto.form.CreateMenuForm;
import com.cyy.finance.biz.dto.form.DelMenuForm;
import com.cyy.finance.biz.dto.form.UpdateMenuForm;
import com.cyy.finance.biz.dto.vo.GetMenuByIdVo;
import com.cyy.finance.biz.dto.vo.ListTreeMenuVo;
import com.cyy.finance.biz.dto.vo.ListTreeSelectMenuVo;
import com.cyy.finance.biz.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "系统菜单管理")
@RestController
@RequestMapping(value = "/sysMenu")
@RequiredArgsConstructor
@Slf4j
@Validated
public class SysMenuController {
    final SysMenuService sysMenuService;

    @ApiOperation(value = "树形菜单列表")
    @GetMapping(value = "/listTreeMenu")
    public ApiResponse<List<ListTreeMenuVo>> listTreeMenu(@RequestParam(required = false) String title) {
        return ApiResponse.success(sysMenuService.listTreeMenu(title));
    }

    @ApiOperation(value = "树形选择菜单列表")
    @GetMapping(value = "/listTreeSelectMenu")
    public ApiResponse<List<ListTreeSelectMenuVo>> listTreeSelectMenu() {
        return ApiResponse.success(sysMenuService.listTreeSelectMenu());
    }

    @ApiOperation(value = "获取菜单明细")
    @GetMapping(value = "/getMenuById")
    public ApiResponse<GetMenuByIdVo> getMenuById(@Validated @RequestParam @NotNull Integer id) {
        return ApiResponse.success(sysMenuService.getMenuById(id));
    }

    @ApiOperation(value = "创建菜单")
    @PostMapping(value = "/create")
    public ApiResponse<Boolean> create(@Validated @RequestBody CreateMenuForm form) {
        return ApiResponse.success(sysMenuService.create(form));
    }

    @ApiOperation(value = "修改菜单")
    @PostMapping(value = "/update")
    public ApiResponse<Boolean> update(@Validated @RequestBody UpdateMenuForm form) {
        return ApiResponse.success(sysMenuService.update(form));
    }

    @ApiOperation(value = "删除菜单")
    @PostMapping(value = "/del")
    public ApiResponse<Boolean> del(@Validated @RequestBody DelMenuForm form) {
        return ApiResponse.success(sysMenuService.del(form));
    }
}
