package waigo.zuosheng.MyCode;

/**
 * author waigo
 * create 2024-07-03 0:19
 */
public class Code1217_minCostToMoveChips {
    //暴力解
    //前提，成本是以单独一个筹码的移动来进行计算的
    //存在的变量：
    //1. 统一移动到位置 x
    //2. 当前正在处理数组中位置 Y 的筹码
    // 需要注意点：
    // 1. 需要跳过Y == X的筹码
    // 2. 可以移动的位置不只是数组中的下标，而是任意一个位置
    // 3. 移动到一个初始时不存在筹码的位置肯定不会成本更低
    // 4. Y和X都应该是position中的值，不要和下标搞混
    public int minCostToMoveChips(int[] position) {
        int minCost = Integer.MAX_VALUE;
        for (int targetPosition : position) {
            //当前尝试将position中除了targetPosition位置的所有筹码移动到targetPosition位置的最小成本
            int cost = 0;
            for (int currentChipWillMove : position) {
                //当前尝试将数组中position[y]位置的筹码移动到x位置的最小成本
                //可以一次性移动2的整数倍，这肯定是最优的
                //移动完2的整数倍后只可能距离x位置0或者1
                cost += (Math.abs(currentChipWillMove - targetPosition) & 1) == 0 ? 0 : 1;
            }

            minCost = Math.min(minCost, cost);
        }

        return minCost;
    }

    //只要相隔偶数个位置，那么成本就是0，因此最低成本就是找出奇数位置和偶数位置的筹码个数
    // 1. 奇数位置的筹码多，那么就选择移动到奇数位置成本最低
    // 2. 偶数同理
    public int minCostToMoveChips2(int[] position) {
        int odd = 0;
        int even = 0;

        for (int chipPosition : position) {
            if ((chipPosition & 1) == 0) {
                even++;
            }
            else {
                odd++;
            }
        }

        return Math.min(odd, even);
    }
}
