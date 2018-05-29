package com.dream.repository;

import com.dream.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


/**
 * @author huxingnan
 * @date 2018/5/12 12:13
 */
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query(" select t from Account t where t.name = :name ")
    Page<Account> findAccountByName( @Param("name")String name,Pageable pageable);


    @Query("select t from Account t where t.createTime >= :startDate and t.createTime <= :endDate")
    Page<Account> findAccountByTimeRang(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Pageable pageable);
}
