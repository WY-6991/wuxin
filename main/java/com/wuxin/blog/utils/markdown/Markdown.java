package com.wuxin.blog.utils.markdown;


import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * @Author: wuxin001
 * @Date: 2021/12/17/16:27
 * @Description:
 */
public class Markdown {

    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node parse = parser.parse(markdown);
        HtmlRenderer build = HtmlRenderer.builder().build();
        return build.render(parse);
    }

}
