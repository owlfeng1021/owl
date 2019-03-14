package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import util.IdWorker;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
//    @Autowired
//    private IdWorker idWorker;

    public  void  save(Article article){
            articleDao.save(article);
    }
    public Page<Article> findById(String key, int page, int size){
        Pageable pageable= PageRequest.of(page-1,size);
            return articleDao.findByTitleOrContentLike(key,key, pageable);
    }
}
