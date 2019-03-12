package com.tensquare.article.dao;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

public  interface  ArticleDao  extends JpaRepository<Article,String> , JpaSpecificationExecutor<Article> {
    @Modifying
    @Query(value = "UPDATE tb_article SET state = 1 WHERE id = ?" ,nativeQuery = true) //这里 ? 右边 可以加上数字代表？是参数的位置
    public void updateState(String id);

    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup=thumbup+1 WHERE id = ?" ,nativeQuery = true) //这里 ? 右边 可以加上数字代表？是参数的位置
    public void addThumbup(String id);
}
