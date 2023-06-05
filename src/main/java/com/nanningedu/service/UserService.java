package com.nanningedu.service;

import com.nanningedu.common.Result;
import com.nanningedu.dto.SearchDto;
import com.nanningedu.dto.UserDto;

public interface UserService {

    //分页查询
    Result findUserByPage(Integer pageNum, Integer pageSize);

    //添加
    Result saveUser(UserDto userDto);

    //批量删除
    Result removeManyUser(String id);

    //搜索查询
    Result findUsersBySearch(SearchDto searchDto);

    //单个删除
    Result removeOneUser(Long id);

    //修改
    Result modifyUser(UserDto userDto);

}
