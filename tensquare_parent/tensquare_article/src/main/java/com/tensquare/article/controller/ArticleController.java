package com.tensquare.article.controller;

import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;


    @PutMapping("/thumbup/{articleId}")
    public Result addThumbup(@PathVariable String articleId){
        articleService.addThumbup(articleId);
        return new Result(true, StatusCode.OK ,"点赞成功");
    }

    @PutMapping("/examine/{articleId}")
    public Result examine(@PathVariable String articleId){
        articleService.updateState(articleId);
        return new Result(true, StatusCode.OK ,"审核成功");
    }
    @GetMapping("/{articleId}")
    public Result gbyId(@PathVariable String articleId){
        return new Result(true, StatusCode.OK ,"查询成功",articleService.getbyId(articleId));
    }


}
