package com.luxcampus.Blog.web;

import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.service.TagServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class TagController {

    Logger logger = LoggerFactory.getLogger(getClass());
    private final TagServiceInterface tagServiceInterface;

    @PostMapping(path = "/{postId}/tags")
    public void addTagToPost(@RequestBody Tag tag, @PathVariable Long postId){
        tagServiceInterface.addTagToPost(tag, postId);
    }


}
