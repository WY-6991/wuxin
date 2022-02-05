package com.wuxin.blog.mode;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @Author: wuxin001
 * @Date: 2022/01/10/20:00
 * @Description: github 图床操作实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Github implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String url;

    @NotNull
    private String sha;

    @NotNull
    private String message;
}
