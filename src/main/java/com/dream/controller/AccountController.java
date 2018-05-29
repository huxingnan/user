package com.dream.controller;

import com.dream.domain.Result;
import com.dream.domain.dto.AccountDTO;
import com.dream.domain.dto.PageInfo;
import com.dream.entity.Account;
import com.dream.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


/**
 * @author huxingnan
 * @date 2018/5/12 12:16
 */
@RestController
@RequestMapping(value = "/dream/account/",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Resource
    private DataSource dataSource;


    @PostMapping("/getAccount")
    @ResponseBody
    public Account getAccount(@RequestBody Account account){
        Integer id = account.getId();
        System.out.println(account);
        Account one = accountRepository.getOne(id);
        Class<? extends Account> aClass = one.getClass();
        System.out.println(aClass.getName());
        return one;
    }

    @PostMapping("/saveAccount")
    @ResponseBody
    public Boolean saveAccount(@RequestBody Account account){
        System.out.println(account);
        Account save = accountRepository.save(account);
        return save.getId() != null;
    }

    @PostMapping("/findAccountAll")
    @ResponseBody
    public Result<List<Account>> findAccountAll(){

        List<Account> save = accountRepository.findAll();

        return new Result<>(Result.SUCCESS_CODE,true,save);
    }
    @PostMapping("/findAccount")
    @ResponseBody
    public Result<List<Account>> findAccount(@RequestBody Account account){

        List<Account> save = accountRepository.findAll(Example.of(account, ExampleMatcher.matching().withIgnoreNullValues()));
        return new Result<>(Result.SUCCESS_CODE,true,save);
    }


//    @PostMapping("/findAccountPageable")
//    @ResponseBody
//    public Result<Page<Account>> findAccountPageable(@RequestBody PageInfo<Account> pageInfo){
//        PageRequest pageRequest = PageRequest.of(pageInfo.getPageNum(), pageInfo.getSize(), Sort.Direction.ASC,"id");
//        Page<Account> all = accountRepository.findAll(Example.of(pageInfo.getParam(), ExampleMatcher.matching().withIgnoreNullValues()), pageRequest);
//        return new Result<>(Result.SUCCESS_CODE,true,all);
//    }

    @PostMapping("/findAccountTimeRange")
    @ResponseBody
    public Result<Page<Account>> findAccountTimeRange(@RequestBody PageInfo<AccountDTO> pageInfo){
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            String sql = "select * from tbl_account t where t.age >= 4 and t.create_time <= 6";
            ResultSet resultSet = statement.executeQuery(sql);
            connection.close();
            return new Result<>(Result.SUCCESS_CODE,true,null);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new Result<>(Result.SUCCESS_CODE,true,null);
    }




}
