package com.wuxin.blog.controller.admin;


import com.wuxin.blog.pojo.vo.PageVo;
import com.wuxin.blog.service.UploadPictureService;
import com.wuxin.blog.util.result.R;
import com.wuxin.blog.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/upload/picture")
@RestController
public class UploadPictureController {

    @Autowired
    private UploadPictureService uploadPictureService;


    @PostMapping("/list")
    public Result findUploadPictureList(@RequestBody PageVo pageVo){
        return R.ok(uploadPictureService.findUploadPictureList(pageVo.getCurrent(), pageVo.getLimit()));
    }


    @GetMapping("/del/{id}")
    public Result delUploadPicture(@PathVariable Long id){
        uploadPictureService.deleteUploadPicture(id);
        return R.ok("删除成功！");
    }

}
