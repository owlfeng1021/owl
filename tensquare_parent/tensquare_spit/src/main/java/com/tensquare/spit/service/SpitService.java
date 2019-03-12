package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

@Service
@Transactional
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    public List<Spit> findByAll(){
        return  spitDao.findAll();
    }

    public Spit findById(String id){
        return  spitDao.findById(id).get();
    }
    public  void save(Spit spit){
        spit.set_id(new IdWorker().nextId()+"");
        spitDao.save(spit);
    }
    public  void update(Spit spit){

        spitDao.save(spit);
    }
    public  void delete(String id){
        spitDao.deleteById(id);
    }
    public Page<Spit> findByParentId(String parentId,int page ,int size ){
        Pageable pageable =  PageRequest.of(page-1,size);
        return  spitDao.findByParentid(parentId,pageable);
    }
}
