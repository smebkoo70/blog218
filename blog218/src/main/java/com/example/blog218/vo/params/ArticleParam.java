package com.example.blog218.vo.params;

import com.example.blog218.vo.CategoryVo;
import com.example.blog218.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}