package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
      @Autowired
      private SpitService spitService;
      @Autowired
      private RedisTemplate redisTemplate;

      @GetMapping("")
      public Result findAll(){
          return new Result(true, StatusCode.OK, "查询成功", spitService.findByAll());
      }
      @GetMapping("/{spitId}")
      public Result findById(@PathVariable String spitId){

            return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
      }
      @PostMapping("")
      public Result save(@RequestBody Spit spit){
            spitService.save(spit);
            return new Result(true, StatusCode.OK, "保存成功");
      }
      @PutMapping("/{spitId}")
      public Result update(@RequestBody Spit spit,@PathVariable String spitId){
            spit.set_id(spitId);
            spitService.update(spit);
            return new Result(true, StatusCode.OK, "修改成功");
      }

      @DeleteMapping("/{spitId}")
      public Result save(@PathVariable String spitId){
            spitService.delete(spitId);
            return new Result(true, StatusCode.OK, "删除成功");
      }
//      /spit/comment/{parentid}/{page}/{size}
      @GetMapping("/comment/{parentid}/{page}/{size}")
      public PageResult  comment(@PathVariable String parentid,@PathVariable int page,@PathVariable int size ){
            Page<Spit>  pageData = spitService.findByParentId(parentid, page, size);
            return new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent());
      }
      @PutMapping("/thumbup/{spitId}")
      public Result addThumbup(@PathVariable String spitId){
            String userid="123";
            if(redisTemplate.opsForValue().get("addThumbup_"+userid)!=null){
                  return new Result(true, StatusCode.OK, "点赞成功");
            }

            spitService.addThumbup(spitId);
            redisTemplate.opsForValue().set("addThumbup_"+userid,"1");
            return new Result(true, StatusCode.OK, "点赞成功");
      }
}
