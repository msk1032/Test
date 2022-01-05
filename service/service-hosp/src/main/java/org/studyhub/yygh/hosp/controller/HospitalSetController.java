package org.studyhub.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.studyhub.yygh.common.result.Result;
import org.studyhub.yygh.hosp.service.HospitalSetService;
import org.studyhub.yygh.model.hosp.HospitalSet;
import org.studyhub.yygh.vo.hosp.HospitalSetQueryVo;


/**
 * @author haoren
 * @create 2022-01-05 15:01
 */
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @GetMapping("findAll")
    public Result findAllHospitalSet() {

        return Result.ok(hospitalSetService.list());
    }

    @DeleteMapping("{id}")
    public Result deleteHospitalById(@PathVariable Long id) {
        if (hospitalSetService.removeById(id)) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @PostMapping("/findHospitalSetPage/{currentPage}/{pageSize}")
    public Result findHositalPage(@PathVariable Long currentPage,
                                  @PathVariable Long pageSize,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>(currentPage, pageSize);
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<HospitalSet>();

        if (!StringUtils.isEmptyOrWhitespaceOnly(hospitalSetQueryVo.getHosname())) {

            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmptyOrWhitespaceOnly(hospitalSetQueryVo.getHoscode())) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }

        return Result.ok(hospitalSetService.page(page, wrapper));

    }

}
