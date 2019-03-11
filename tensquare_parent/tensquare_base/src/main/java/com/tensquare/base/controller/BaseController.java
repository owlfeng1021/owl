package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class BaseController {
    @Autowired
    private LabelService labelService;

    @GetMapping("")
    public Result findAll(){

        return new Result(true,StatusCode.OK,"查询成功",labelService.findAll());
    }
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(labelId));
    }
    @PostMapping()
    public Result insert(@RequestBody Label label){
        labelService.save(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @PutMapping("/{labelId}")
    public Result update(@PathVariable("labelId") String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"更新成功");
    }
    @DeleteMapping("/{labelId}")
    public Result delete(@PathVariable("labelId") String labelId){
        labelService.delete(labelId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    @PostMapping("/search")
    public Result findSearch(@RequestBody Label label){

        return new Result(true,StatusCode.OK,"查询成功",labelService.findSearch(label));
    }
      @PostMapping("/search/{page}/{size}")
      public Result findPage(@RequestBody Label label ,@PathVariable int page ,@PathVariable int size){
          Page<Label> labelPage= labelService.findQuery(label,page,size);
          return new Result(true,StatusCode.OK,"查询成功", new PageResult<Label>(labelPage.getTotalElements(),labelPage.getContent()));
      }





}
