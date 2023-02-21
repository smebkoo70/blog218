package com.example.blog218.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog218.dao.mapper.ArticleMapper;
import com.example.blog218.dao.pojo.Article;
import com.example.blog218.service.ArticleService;
import com.example.blog218.service.SysUserService;
import com.example.blog218.service.TagService;
import com.example.blog218.vo.ArticleBodyVo;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 1、分页查询article数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPagesize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //是否置顶进行排序,        //时间倒序进行排列相当于order by create_data desc
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        //分页查询用法 https://blog.csdn.net/weixin_41010294/article/details/105726879
        List<Article> records = articlePage.getRecords();
        // 要返回我们定义的vo数据，就是对应的前端数据，不应该只返回现在的数据需要进一步进行处理
        List<ArticleBodyVo> articleVoList =copyList(records,true,true);
        return Result.success(articleVoList);
    }

    private List<ArticleBodyVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleBodyVo> articleBodyVoList = new ArrayList<>();
        for (Article article : records)
        {
            ArticleBodyVo articleBodyVo = copy(article,isTag,isAuthor);
            articleBodyVoList.add(articleBodyVo);
        }
        return articleBodyVoList;
    }

    private ArticleBodyVo copy(Article article, boolean isTag,boolean isAuthor)
    {
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        BeanUtils.copyProperties(article, articleBodyVo);

        articleBodyVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口都需要标签和作者信息。
        if(isTag)
        {
            Long articleId = article.getId();
            articleBodyVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor)
        {
            Long authorId = article.getAuthorId();
            articleBodyVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }

        return articleBodyVo;
    }

}
