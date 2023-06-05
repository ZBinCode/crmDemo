package com.nanningedu.mapper;

import com.nanningedu.domain.Dept;
import com.nanningedu.dto.DeptDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptMapper {

    //分页查询
    @Select("select * from dept")
    List<Dept> selectDeptsByPage();

    //添加部门信息
    @Insert("insert into dept values(null,#{name},#{loc})")
    int insertDept(DeptDto deptDto);

    //判断要添加的部门，数据库表里面是否已经存在
    @Select("select count(*) from dept where name=#{arg0}")
    int selectDeptNameIsExist(String name);
}
