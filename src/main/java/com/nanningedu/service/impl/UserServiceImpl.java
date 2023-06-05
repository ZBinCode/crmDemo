package com.nanningedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanningedu.common.Result;
import com.nanningedu.domain.User;
import com.nanningedu.dto.SearchDto;
import com.nanningedu.dto.UserDto;
import com.nanningedu.mapper.UserMapper;
import com.nanningedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Result findUserByPage(Integer pageNum, Integer pageSize) {
        // 分页的约束条件
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectUserByPgae();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        Result result = new Result();
        result.setData(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public Result saveUser(UserDto userDto) {
        // 查询部门是否存在
        int i = userMapper.selectDeptsIsExist(userDto);
        if (i == 0){
            return new Result(-1,"部门还没有添加");
        }
        //
        int j = userMapper.selectUsersIsExist(userDto);
        if (j ==1){
            return new Result(-1,"要添加的记录已经存在，不能重复添加");
        }
        int k = userMapper.insert(userDto);
        if (k == 0){
            return new Result(-1,"添加失败");
        }
        return new Result();
    }

    @Override
    public Result removeManyUser(String id) {
        /*
        * 如果正则表达式只有[1-9]，那么它表示的是10以下的数字显示为1,2,3，而不是01,02,03
        * 如果正则表达式只有[1-9][0-9]，那么它表示的是9以上的数字
        * [1-9][0-9]*，表示可以匹配多位数字
        * */
        if(id == null || !((id+",").matches("([1-9][0-9]*,)+"))){
            return Result.DATA_FORMAT_ERROR;
        }
        // 删除
        int i = userMapper.deleteManyUser(id);
        if (i == 0){
            return new Result(-1,"删除失败");
        }
        return new Result();
    }

    @Override
    public Result findUsersBySearch(SearchDto searchDto) {
        PageHelper.startPage(searchDto.getPageNum(),searchDto.getPageSize());
        List<User> userList = userMapper.selectUsersBySearch(searchDto);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        Result result = new Result();
        result.setData(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public Result removeOneUser(Long id) {
        if (id <= 0 ){
            return Result.DATA_FORMAT_ERROR;
        }
        int i = userMapper.deleteOneUser(id);
        if (i == 0){
            return new Result(-1,"删除失败");
        }
        return new Result();
    }

    @Override
    public Result modifyUser(UserDto userDto) {
        //查询部门是否存在
        int i = userMapper.selectDeptsIsExist(userDto);
        if (i == 0){
            return new Result(-1, "部门不存在，需要选择部门");
        }
        //判断要修改的数据，数据库里是否已经存在了
        int j = userMapper.selectUsersIsExist(userDto);
        if (j == 1){
            return new Result(-1, "修改的数据，数据库已经存在");
        }
        //修改
        int m = userMapper.updateUser(userDto);
        if (m == 0){
            return new Result(-1,"修改失败！");
        }
        return new Result();
    }
}
