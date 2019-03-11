package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpirseDao extends JpaRepository<Enterprise,String>, JpaSpecificationExecutor<Enterprise> {
    public List<Enterprise> findByIshot(String ishot); //where ishot = ?

}
