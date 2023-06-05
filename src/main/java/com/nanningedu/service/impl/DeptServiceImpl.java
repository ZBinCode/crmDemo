package com.nanningedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanningedu.common.Result;
import com.nanningedu.domain.Dept;
import com.nanningedu.dto.DeptDto;
import com.nanningedu.mapper.DeptMapper;
import com.nanningedu.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public Result findDeptsByPage(Integer pageNum, Integer pageSize) {
        // 分页的约束条件
        PageHelper.startPage(pageNum, pageSize);
        List<Dept> deptList = deptMapper.selectDeptsByPage();
        PageInfo<Dept> pageInfo = new PageInfo<>(deptList);
        Result result = new Result();
        result.setData(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    // 添加功能
    @Transactional
    @Override
    public Result saveDept(DeptDto deptDto) {
        //要添加的部门数据库表里面是否已经存在了
        int i = deptMapper.selectDeptNameIsExist(deptDto.getName());
        if (i == 1){
            return new Result(-1,"该部门已经存在了，不能重复添加");
        }
        // 添加
        int j = deptMapper.insertDept(deptDto);
        if (j == 0){
            return new Result(-1,"添加失败");
        }
        return new Result();
    }
}
