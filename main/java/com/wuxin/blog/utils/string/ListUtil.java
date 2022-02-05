package com.wuxin.blog.utils.string;

import java.util.List;

/**
 * @Author: wuxin001
 * @Date: 2022/02/03/11:45
 * @Description:
 */
public class ListUtil {

    public static boolean listEqual(List<Long> ls1, List<Long> ls2) {
        return ls1.containsAll(ls2) && ls2.containsAll(ls1);
    }
}
