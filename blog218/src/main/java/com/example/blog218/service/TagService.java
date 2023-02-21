package com.example.blog218.service;

import com.example.blog218.vo.Result;
import com.example.blog218.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
}
