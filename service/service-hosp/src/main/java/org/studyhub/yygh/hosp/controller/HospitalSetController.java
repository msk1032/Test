package org.studyhub.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.studyhub.yygh.common.result.Result;
import org.studyhub.yygh.common.utils.MD5;
import org.studyhub.yygh.hosp.service.HospitalSetService;
import org.studyhub.yygh.model.hosp.HospitalSet;
import org.studyhub.yygh.vo.hosp.HospitalSetQueryVo;
import java.util.List;
import java.util.Random;


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
    @ApiOperation("查找所有医院结果")
    public Result findAllHospitalSet() {

        return Result.ok(hospitalSetService.list());
    }

    @DeleteMapping("{id}")
    @ApiOperation("根据id删除")
    public Result deleteHospitalById(@PathVariable Long id) {
        if (hospitalSetService.removeById(id)) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @PostMapping("/findHospitalSetPage/{currentPage}/{pageSize}")
    @ApiOperation("模糊查询分页")
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

    @ApiOperation("添加hospitalSet")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置为1 表示可用
        hospitalSet.setStatus(1);
        Random random = new Random();

        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));

        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        }else {

            return Result.fail();
        }

    }

    @ApiOperation("根据id获取医院设置" )
    @GetMapping("getHospitalById/{id}")
    public Result getHospitalById(@PathVariable Long id) {

        HospitalSet result = hospitalSetService.getById(id);

        if (result != null){
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation("修改医院设置" )
    @PostMapping("updateHospitalSet/{id}")
    public Result<Object> updateHospitalSet(@PathVariable Long id,
                                            @RequestBody HospitalSet hospitalSet) {
        hospitalSet.setId(id);
        boolean update = hospitalSetService.updateById(hospitalSet);
        if (update) {
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @ApiOperation("批量删除" )
    @PostMapping("removeSets")
    public Result removeHospitalSets(@RequestBody List<Long> idList) {

        System.out.println(idList);

        return Result.ok(hospitalSetService.removeByIds(idList));
    }

    @ApiOperation("医院设置解锁和锁定" )
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result updateHospitalSetStatus(@PathVariable Long id,
                                          @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        return Result.ok(hospitalSetService.updateById(hospitalSet));
    }
    
    @ApiOperation("发送签名私钥" )
    @PostMapping("sendKey/{id}")
    public Result senSecreteKey(@PathVariable Long id) {

        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();

        //TODO
        return Result.ok();

    }

}
