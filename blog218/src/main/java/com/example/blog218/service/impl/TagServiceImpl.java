package com.example.blog218.service.impl;

import com.example.blog218.dao.mapper.TagMapper;
import com.example.blog218.dao.pojo.Tag;
import com.example.blog218.service.TagService;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        List<Tag> tags = tagMapper.findTagsByArticleId(id);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit)
    {
        /**
         * 1.标签所拥有的文章数量最多就是最热标签
         * 2.查询 根据tag_id 分组计数 从大到小排列 取前limit个
         */
        List<Long> tagIds = tagMapper.findHotTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        //最终需求的是 tagId和tagName tag对象
        //select * from tag where id in (1,2,3,4,5,6,7,8,9))
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }
}
