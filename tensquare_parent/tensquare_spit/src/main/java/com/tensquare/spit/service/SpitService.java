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

import java.util.Date;
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
        spit.setPublishtime(new Date());
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态码cc
        //如果添加的吐槽有父节点 那么父节点的回复数要加一
        if(spit.getParentid()!=null&&!"".equals(spit.getParentid())){
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));//找到父节点的id
            Update update=new Update();
            update.inc("comment",1);//然后加一 操作 inc
            mongoTemplate.updateFirst(query,update,"spit");

        }

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
