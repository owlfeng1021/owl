package com.tensquare.qa.dao;

import antlr.collections.impl.LList;
import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemDao extends JpaRepository<Problem,String> , JpaSpecificationExecutor<Problem> {
    @Query(name = "SELECT * FROM tb_problem ,tb_pl WHERE id=tb_pl.problemid AND labelid = ? ORDER BY replytime DESC", nativeQuery = true)
    public Page<Problem> newList(String labelid, Pageable pageable); //按照回复时间排序
    @Query(name ="SELECT * FROM tb_problem ,tb_pl WHERE id=tb_pl.problemid AND labelid = ? ORDER BY reply DESC", nativeQuery = true)
    public Page<Problem> hotList(String labelid, Pageable pageable); //按照回复数量查询
    @Query(name ="SELECT * FROM tb_problem ,tb_pl WHERE id=tb_pl.problemid AND labelid = ? AND reply = 0 ORDER BY createtime DESC", nativeQuery = true)
    public Page<Problem> waitList(String labelid, Pageable pageable);
}
