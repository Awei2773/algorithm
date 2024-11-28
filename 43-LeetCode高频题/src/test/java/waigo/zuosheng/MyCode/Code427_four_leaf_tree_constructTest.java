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
        int[][] grid = {{0, 1}, {1, 0}};
        Code427_four_leaf_tree_construct.Node treeHead = solution.construct(grid);
        Assert.assertEquals("[[0,1],[1,0],[1,1],[1,1],[1,0]]", treeHead.toBfsString());
        Assert.assertEquals("[[0,1],[1,0],[1,1],[1,1],[1,0]]", solution.construct1(grid).toBfsString());

        int[][] grid1 = {{1, 1, 1, 1, 0, 0, 0, 0}, {1, 1, 1, 1, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 0, 0, 0, 0}, {1, 1, 1, 1, 0, 0, 0, 0}, {1, 1, 1, 1, 0, 0, 0, 0}, {1, 1, 1, 1, 0, 0, 0, 0}};
        Assert.assertEquals(solution.construct1(grid1).toBfsString(),
                solution.construct(grid1).toBfsString());
    }
}