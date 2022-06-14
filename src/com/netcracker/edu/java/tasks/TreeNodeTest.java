package com.netcracker.edu.java.tasks;

/**
 * Created by eschy_000 on 23.08.2015.
 */
public class TreeNodeTest {
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNodeImpl();
        treeNode.setData("root");
        treeNode.addChild(new TreeNodeImpl());
        treeNode = treeNode.getChildrenIterator().next();
        treeNode.setData("halo");
        treeNode.addChild(new TreeNodeImpl());
        treeNode = treeNode.getChildrenIterator().next();
        treeNode.setData(null);
        System.out.println((treeNode.getRoot().findChild(null)));

    }
}
