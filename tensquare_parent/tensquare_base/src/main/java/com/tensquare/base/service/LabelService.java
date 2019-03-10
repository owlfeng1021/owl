package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;
    public List<Label> findAll(){
        return labelDao.findAll();
    }
    public Label  findById(String id){
        return labelDao.findById(id).get();
        //Optional 是一个jdk 1.8的新特性

    }
    //增加
    public void save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label);
    }
    public void delete(String id) {
        labelDao.deleteById(id);
    }
    public List<Label> findSearch(Label label){
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象 也就是把条件封装到那个对象中 where 类名=label。getid
             * @param query 封装的都是查询关键字 比如 group by' order by
             * @param cb 用来封装条件对象 如果直接返回null 表示 不需要任何的条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list=new ArrayList<>();
                if (!StringUtils.isEmpty(label.getLabelname())){
                    Predicate predicate =cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%"); // where labelname like %labelname%
                    list.add(predicate);
                }
                if(!StringUtils.isEmpty(label.getState())){
                    Predicate predicate =cb.equal(root.get("state").as(String.class),label.getState()); // and state = 1
                    list.add(predicate);
                }
                Predicate[] parr =new Predicate[list.size()];
                parr=list.toArray(parr);
                return cb.and(parr);
            }
        });
    }

    public Page<Label> findQuery(Label label, int page, int size) {
        //封装分页对象
        Pageable pageable=PageRequest.of(page-1,size);
        return  labelDao.findAll(new Specification<Label>() {
                /**
                 *
                 * @param root 根对象 也就是把条件封装到那个对象中 where 类名=label。getid
                 * @param query 封装的都是查询关键字 比如 group by' order by
                 * @param cb 用来封装条件对象 如果直接返回null 表示 不需要任何的条件
                 * @return
                 */
                @Override
                public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list=new ArrayList<>();
                    if (!StringUtils.isEmpty(label.getLabelname())){
                        Predicate predicate =cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%"); // where labelname like %labelname%
                        list.add(predicate);
                    }
                    if(!StringUtils.isEmpty(label.getState())){
                        Predicate predicate =cb.equal(root.get("state").as(String.class),label.getState()); // and state = 1
                        list.add(predicate);
                    }
                    Predicate[] parr =new Predicate[list.size()];
                    parr=list.toArray(parr);
                    return cb.and(parr);
                }
            },pageable);
    }
}
