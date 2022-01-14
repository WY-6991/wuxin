package com.wuxin.blog.pojo.blog;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String author;
    private String title;
    private String content;
    private String lyric;
    private Integer musicId;
}
