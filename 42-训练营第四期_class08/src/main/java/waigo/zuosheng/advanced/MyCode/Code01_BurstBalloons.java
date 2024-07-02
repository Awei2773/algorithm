package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-07-09 21:35
 */

/**
 * 给定一个数组 arr，代表一排有分数的气球。每打爆一个气球都能获得分数，假设打爆气 球 的分数为 X，获得分数的规则如下
 * : 1)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L;如果被打爆气球的右边
 * 有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 R。 获得分数为 L*X*R。 2)如果被打爆气球的左边
 * 有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L;如果被打爆气球的右边所有气球都已经被打爆。
 * 获得分数为 L*X。 3)如果被打爆气球的左边所有的气球都已经被打爆;如果被打爆气球的右边有没被打爆的 气球，
 * 找到离被打爆气球最近的气球，假设分数为 R;如果被打爆气球的右边所有气球都 已经 被打爆。获得分数为 X*R。
 * 4)如果被打爆气球的左边和右边所有的气球都已经被打爆。获得分数为 X。
 * 目标是打爆所有气球，获得每次打爆的分数。通过选择打爆气球的顺序，可以得到不同的总分，请返回能获得的
 * 最大分数。
 * 【举例】
 * arr = {3,2,5} 如果先打爆3，获得3*2;再打爆2，获得2*5;最后打爆5，获得5;最后总分21 如果先打爆3，
 * 获得3*2;再打爆5，获得2*5;最后打爆2，获得2;最后总分18 如果先打爆2，获得3*2*5;再打爆3，获得3*5;
 * 最后打爆5，获得5;最后总分50 如果先打爆2，获得3*2*5;再打爆5，获得3*5;最后打爆3，获得3;最后总分48
 * 如果先打爆5，获得2*5;再打爆3，获得3*2;最后打爆2，获得2;最后总分18 如果先打爆5，获得2*5;再打爆2，
 * 获得3*2;最后打爆3，获得3;最后总分19 返回能获得的最大分数为50
 */
public class Code01_BurstBalloons {
}
