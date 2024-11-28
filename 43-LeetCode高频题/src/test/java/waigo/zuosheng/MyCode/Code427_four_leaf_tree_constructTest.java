package waigo.zuosheng.MyCode;

import org.junit.Assert;
import org.junit.Test;

/**
 * author waigo
 * create 2024-07-23 22:58
 */
public class Code427_four_leaf_tree_constructTest {
    Code427_four_leaf_tree_construct solution = new Code427_four_leaf_tree_construct();

    @Test
    public void construct() {
        Code427_four_leaf_tree_construct.Node treeHead = solution.construct(new int[][]{{0, 1}, {1, 0}});
        Assert.assertEquals("[[0,1],[1,0],[1,1],[1,1],[1,0]]", treeHead.toBfsString());

        Assert.assertEquals("[[0,1],[1,1],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]",
                solution.construct(new int[][]{{1,1,1,1,0,0,0,0}, {1,1,1,1,0,0,0,0}, {1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}, {1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0}}).toBfsString());
    }
}