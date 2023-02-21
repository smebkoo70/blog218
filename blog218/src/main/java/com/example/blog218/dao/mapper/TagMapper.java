package com.example.blog218.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog218.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章ID查询标签猎豹
     *
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签 前N条
     * @param limit
     * @return
     */
    List<Long> findHotTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
