package com.nanningedu.controller;

import com.nanningedu.common.Result;
import com.nanningedu.dto.DeptDto;
import com.nanningedu.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/getDeptsByPage.do")
    public Result getDeptsByPage(
            Integer pageNum,
            @RequestParam(required = false,defaultValue = "5") Integer pageSize
    ){
        return deptService.findDeptsByPage(pageNum,pageSize);
    }

    @RequestMapping("/addDept.do")
    public Result addDept(@Valid DeptDto deptDto, BindingResult br){
        if(br.hasErrors()){
            return Result.DATA_FORMAT_ERROR;
        }
        return deptService.saveDept(deptDto);
    }

}
