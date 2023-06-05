package com.nanningedu.controller;

import com.nanningedu.common.Result;
import com.nanningedu.dto.SearchDto;
import com.nanningedu.dto.UserDto;
import com.nanningedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //required = false 意为如果没有给pageSize设置值
    //defaultValue = "10" 意为那么就给pageSize设置一个值为10的默认值
    @RequestMapping("/getUsersByPage.do")
    public Result getUsersByPage(
            Integer pageNum,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize
    ){
        return userService.findUserByPage(pageNum,pageSize);
    }

    @RequestMapping("/addUser.do")
    public Result addUser(@Valid UserDto userDto, BindingResult br){
        if(br.hasErrors()){
            return Result.DATA_FORMAT_ERROR;
        }
        return userService.saveUser(userDto);
    }

    @RequestMapping("/cutManyUser.do")
    public Result cutManyUser(String id){
        return userService.removeManyUser(id);
    }

    @RequestMapping("/getUserBySearch.do")
    public Result getUserBySearch(@Valid SearchDto searchDto,BindingResult br){
        if(br.hasErrors()){
            return Result.DATA_FORMAT_ERROR;
        }
        return userService.findUsersBySearch(searchDto);
    }

    @RequestMapping("/cutOneUser.do")
    public Result cutOneUser(Long id){
        return userService.removeOneUser(id);
    }

    @RequestMapping("/editUser.do")
    public Result editUser(@Valid UserDto userDto,BindingResult br){
        if(br.hasErrors()){
            return Result.DATA_FORMAT_ERROR;
        }
        return userService.modifyUser(userDto);
    }

}
