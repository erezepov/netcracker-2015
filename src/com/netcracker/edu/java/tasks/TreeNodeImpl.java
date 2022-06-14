package com.netcracker.edu.java.tasks;

import java.util.Iterator;
import java.util.LinkedList;

public class TreeNodeImpl implements TreeNode {

    TreeNode parent;
    LinkedList<TreeNode> children;
    Object userData;
    boolean expanded;

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getRoot() {
        return parent == null ? null : parent.getParent() == null ? parent : parent.getRoot();
    }

    public boolean isLeaf() {
        return children == null || children.size() == 0;
    }

    public int getChildCount() {
        return children.size();
    }

    public Iterator<TreeNode> getChildrenIterator() {
        return children.iterator();
    }

    public void addChild(TreeNode child) {
        if (children == null) {
            children = new LinkedList<>();
        }
        child.setParent(this);
        children.addLast(child);
    }

    public boolean removeChild(TreeNode child) {
        child.setParent(null);
        return children.remove(child);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        if (!isLeaf()) {
            for (TreeNode it: children) {
                it.setExpanded(expanded);
            }
        }
    }

    public Object getData() {
        return userData;
    }

    public void setData(Object data) {
        userData = data;
    }

    public String getTreePath() {
        return getParent() == null ? getData() == null ? "empty" : getData().toString() :
                getData() == null ? getParent().getTreePath() + "->empty" :
                        getParent().getTreePath() + "->" + getData().toString();
    }

    public TreeNode findParent(Object data) {
        return getData() == null ?
                getParent() == null ?
                        data == null ?
                                this : null :
                        data == null ?
                                this : getParent().findParent(data) :
                getParent() == null ?
                        getData().equals(data) ?
                                this : null :
                        getData().equals(data) ?
                                this : getParent().findParent(data);
    }

    public TreeNode findChild(Object data) {
        TreeNode lostChild = null;
        if (children != null && children.size() > 0) {
            for (TreeNode it : children) {
                if (it.getData() == null) {
                    if (data == null) {
                        lostChild = it;
                        break;
                    }
                } else if (it.getData().equals(data)) {
                    lostChild = it;
                    break;
                }
                if ((lostChild = it.findChild(data)) != null) {
                    break;
                }
            }
        }
        return lostChild;
    }
}