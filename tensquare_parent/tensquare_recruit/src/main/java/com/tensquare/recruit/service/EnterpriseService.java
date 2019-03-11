package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.EnterpirseDao;
import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EnterpriseService {
    @Autowired
    private EnterpirseDao enterpirseDao;
    @Autowired
    private IdWorker idWorker;

    public List<Enterprise> hotList(String ishot){

        return enterpirseDao.findByIshot(ishot);
    }

}
