package com.tensquare.search.Controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping()
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"增加成功");
    }
    @GetMapping( "/{key}/{page}/{size}")
    public Result find(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        Page<Article> pageData=articleService.findById(key,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }
}
