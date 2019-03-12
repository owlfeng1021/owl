package com.tensquare.article.service;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;

    public void updateState(String id){

        articleDao.updateState(id);
    }
    public void addThumbup(String id){

        articleDao.addThumbup(id);
    }
    public Article getbyId(String id){
        Article article =(Article) redisTemplate.opsForValue().get("Article_" + id);
        System.out.println(article);
        return articleDao.findById(id).get();
    }

}
