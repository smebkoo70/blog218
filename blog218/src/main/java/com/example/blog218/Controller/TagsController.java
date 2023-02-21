package com.example.blog218.Controller;


import com.example.blog218.service.TagService;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagsService;

    /**
     * /tags/hot
     * @return
     */
    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        /**
         * 1.标签所拥有的文章数量最多就是最热标签
         * 2.查询 根据tag_id 分组计数 从大到小排列 取前limit个
         */
        return tagsService.hots(limit);
        //List<TagVo> tagVoList = tagsService.hots(limit);
        //return Result.success(tagVoList);
    }

}