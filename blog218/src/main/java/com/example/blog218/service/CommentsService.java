package com.example.blog218.service;

import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.CommentParam;


public interface CommentsService {


    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
