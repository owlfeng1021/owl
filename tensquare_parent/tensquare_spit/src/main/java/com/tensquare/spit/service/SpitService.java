package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private  MongoTemplate mongoTemplate;


    public List<Spit> findByAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    public void save(Spit spit) {
        spit.set_id(new IdWorker().nextId() + "");
        spitDao.save(spit);
    }

    public void update(Spit spit) {

        spitDao.save(spit);
    }

    public void delete(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }

    public void addThumbup(String id) {
//        Spit byId = spitDao.findById(id).get();
//        byId.setThumbup(byId.getThumbup() == null ? 0:byId.getThumbup()+1);
//        spitDao.save(byId);
//        使用原生mongo命令 减少数据库查询次数 db.spit.update({"_id:"?""},{$inc:{thumbup:NumberInt(1)}})
//        先是  Query
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query ,update,"spit");
    }


}
