package com.example.common.utils;


import com.example.biz.entity.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建Tree树
 */
public class TreeUtil {

    public static <T> List<Tree<T>> build(List<Tree<T>> nodes) {
        if (nodes == null) {
            return new ArrayList<>();
        }

        List<Tree<T>> tree = new ArrayList<>();
        nodes.forEach(node -> {
            Long pId = node.getPId();
            if (pId == null || pId.equals(0L)) {
                //父节点
                tree.add(node);
                return;
            }
            for (Tree<T> c : nodes) {
                Long id = c.getId();
                if (id != null && id.equals(pId)) {
                    c.getChildren().add(node);
                    return;
                }
            }
        });
        return tree;
    }
}
