package com.luxcampus.Blog.web;

import com.luxcampus.Blog.entity.Tag;
import com.luxcampus.Blog.entity.dto.TagWithoutPostDto;
import com.luxcampus.Blog.service.TagServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    Logger logger = LoggerFactory.getLogger(getClass());
    private final TagServiceInterface tagService;

    @PostMapping(path = "/{postId}")
    public ResponseEntity<TagWithoutPostDto> addTagToPost(@RequestBody Tag tag, @PathVariable Long postId){
        tagService.addTagToPost(tag, postId);
        TagWithoutPostDto tagWithoutPostDto = getTagWithOutPostDto(tag);
        return ResponseEntity.status(HttpStatus.OK).body(tagWithoutPostDto);
    }

    @DeleteMapping (path = "/{id}")
    public void deleteTagFromPost(@PathVariable Long id){
        tagService.deleteTagFromPost(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllTags(){
       List<Tag> tags = tagService.getAllTags();
       List<TagWithoutPostDto> tagWithoutPostDtos = new ArrayList<>(tags.size());
        for (Tag tag : tags) {
            tagWithoutPostDtos.add(getTagWithOutPostDto(tag));
        }
        return ResponseEntity.status(HttpStatus.OK).body(tagWithoutPostDtos);
    }

    private TagWithoutPostDto getTagWithOutPostDto(Tag tag) {
        TagWithoutPostDto tagWithoutPostDto = TagWithoutPostDto.builder()
                .name(tag.getName())
                .id(tag.getId())
                .build();
        return tagWithoutPostDto;
    }


}
