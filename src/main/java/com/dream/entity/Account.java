package com.dream.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author huxingnan
 * @date 2018/5/12 12:09
 */
@Data
@Entity
@Table(name = "tbl_account")
//避免 json 转换 失败
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Column(name = "pass_word")
    private String passWord;

    @NotNull
    private String age;

    @Column(name = "create_time")
    private Date createTime;

    private String name;

}
