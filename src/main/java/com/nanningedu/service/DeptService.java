package com.nanningedu.service;

import com.nanningedu.common.Result;
import com.nanningedu.dto.DeptDto;

public interface DeptService {

    //分页查询
    Result findDeptsByPage(Integer pageNum,Integer pageSize);

    //添加部门信息
    Result saveDept(DeptDto deptDto);

}
