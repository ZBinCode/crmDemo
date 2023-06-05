package com.nanningedu.mapper;

import com.nanningedu.domain.User;
import com.nanningedu.dto.SearchDto;
import com.nanningedu.dto.UserDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    // 分页查询
    @Select("select u.*, d.name, d.name from user u inner join dept d on u.dept_id= d.id")
    List<User> selectUserByPgae();

    // 添加客户信息
    int insert(UserDto userDto);

    //添加功能时，要判断是否选择了部门
    @Select("select count(*) from dept where id=#{deptId}")
    int selectDeptsIsExist(UserDto userDto);

    // 添加时，查询数据库表是否已经存在要添加的这条记录
    @Select("select count(*) from user where username = #{username} and birthday = #{birthday} " +
            "and address=#{address} and sex = #{sex} and tel = #{tel}")
    int selectUsersIsExist(UserDto userDto);

    //批量删除
    @Delete("delete from user where id in(${arg0})")
    int deleteManyUser(String id);

    //多条件搜索
    List<User> selectUsersBySearch(SearchDto searchDto);

    //单个删除
    @Delete("delete from user where id = #{arg0}")
    int deleteOneUser(Long id);

    //修改客户信息
    int updateUser(UserDto userDto);

}
