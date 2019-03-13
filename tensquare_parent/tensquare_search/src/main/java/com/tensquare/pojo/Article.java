package com.tensquare.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "tensquare_article",type = "article"  )
public class Article implements Serializable {

    @Id
    private String id;

}
