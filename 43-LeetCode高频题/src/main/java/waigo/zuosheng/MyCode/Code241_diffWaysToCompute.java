package waigo.zuosheng.MyCode;

import java.util.*;

/**
 * 给你一个由数字和运算符组成的字符串 expression ，按不同优先级组合数字和运算符，计算并返回所有可能组合的结果。你可以 按任意顺序 返回答案。
 * 生成的测试用例满足其对应输出值符合 32 位整数范围，不同结果的数量不超过 104 。
 *
 * 示例 1：
 * 输入：expression = "2-1-1"
 * 输出：[0,2]
 * 解释：
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 * 示例 2：
 * 输入：expression = "2*3-4*5"
 * 输出：[-34,-14,-10,-10,10]
 * 解释：
 * (2*(3-(4*5))) = -34
 * ((2*3)-(4*5)) = -14
 * ((2*(3-4))*5) = -10
 * (2*((3-4)*5)) = -10
 * (((2*3)-4)*5) = 10
 *
 * 提示：
 *
 * 1 <= expression.length <= 20
 * expression 由数字和算符 '+'、'-' 和 '*' 组成。
 * 输入表达式中的所有整数值在范围 [0, 99]
 */
public class Code241_diffWaysToCompute {
   public static final int ADD = -1;

   public static final int SUB = -2;

   public static final int MUL = -3;

   public List<Integer> diffWaysToCompute(String expression) {
      //预处理，将表达式变成数据和字符的集合
      ArrayList<Integer> expList = prepareExpression(expression);

      //暴力递归
      //递归的关键是找到最基础的情况，并且保证基本情况推广开来结果仍然正确
      //这道题的关键点在于按照不同的优先级来组合计算式子，因此递归的基础情况为确定当前运算符是最后一个运算的，
      return diffWaysToCompute(0, expList.size() - 1, expList);
   }

   public List<Integer> diffWaysToComputeMemorySearch(String expression) {
      //预处理，将表达式变成数据和字符的集合
      ArrayList<Integer> expList = prepareExpression(expression);

      List[][] cache = new ArrayList[expList.size()][expList.size()];
      return diffWaysToCompute(0, expList.size() - 1, expList, cache);
   }

   public List<Integer> diffWaysToComputeDP(String expression) {
      //预处理，将表达式变成数据和字符的集合
      ArrayList<Integer> expList = prepareExpression(expression);

      List[][] dp = new ArrayList[expList.size()][expList.size()];
      //只有右上角需要填充
      for (int left = 0; left < expList.size(); left++) {
         for (int right = left; right < expList.size(); right++) {
            dp[left][right] = new ArrayList<Integer>();
         }
      }

      //对角线往右上角填值
      for (int offset = 0; offset < expList.size(); offset++) {
         for (int left = 0, right = offset; left < expList.size() && right < expList.size(); left++,right++) {
            if (left == right && isNumber(expList.get(left))) {
               dp[left][right].add(expList.get(left));
            }
            else if (isNumber(expList.get(left)) && isNumber(expList.get(right))) {
               for (int i = left; i < right; i++) {
                  Integer curExp = expList.get(i);
                  if (curExp == ADD || curExp == SUB || curExp == MUL) {
                     List<Integer> leftResults = dp[left][i - 1];
                     List<Integer> rightResults = dp[i + 1][right];

                     for (Integer leftResult : leftResults) {
                        for (Integer rightResult : rightResults) {
                           dp[left][right].add(calculate(leftResult, rightResult, curExp));
                        }
                     }
                  }
               }
            }
         }
      }

      return dp[0][expList.size() - 1];
   }

   public boolean isNumber(Integer exp) {
      return exp != ADD && exp != SUB && exp != MUL;
   }

   private static ArrayList<Integer> prepareExpression(String expression) {
      ArrayList<Integer> expList = new ArrayList<>();
      for (int i = 0; i < expression.length();) {
         if (!Character.isDigit(expression.charAt(i))) {
            if (expression.charAt(i) == '+'){
               expList.add(ADD);
            }
            else if (expression.charAt(i) == '-'){
               expList.add(SUB);
            }
            else if (expression.charAt(i) == '*'){
               expList.add(MUL);
            }
            i++;
         }
         else {
            int num = 0;
            while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
               num = num * 10 + expression.charAt(i++) - '0';
            }
            expList.add(num);
         }
      }
      return expList;
   }

   private List<Integer> diffWaysToCompute(int left, int right, ArrayList<Integer> expList) {
      if (expList.size() == 0) {
         return Collections.emptyList();
      }

      if (left == right) {
         return Collections.singletonList(expList.get(left));
      }

      //遍历每个运算符，递归查找它两边可能出现的结果集合
      List<Integer> ans = new ArrayList<>();
      for (int i = left; i < right; i++) {
         Integer curExp = expList.get(i);
         if (curExp == ADD || curExp == SUB || curExp == MUL) {
            List<Integer> leftResults = diffWaysToCompute(left, i - 1, expList);
            List<Integer> rightResults = diffWaysToCompute(i + 1, right, expList);

            for (Integer leftResult : leftResults) {
               for (Integer rightResult : rightResults) {
                  ans.add(calculate(leftResult, rightResult, curExp));
               }
            }
         }
      }

      return ans;
   }

   
   private List<Integer> diffWaysToCompute(int left, int right, ArrayList<Integer> expList, List<Integer>[][] cache) {
      if (expList.size() == 0) {
         return Collections.emptyList();
      }

      if (cache[left][right] != null) {
         return cache[left][right];
      }

      if (left == right) {
         List<Integer> ans = new ArrayList<>();
         ans.add(expList.get(left));
         cache[left][right] = ans;
         return ans;
      }

      //遍历每个运算符，递归查找它两边可能出现的结果集合
      List<Integer> ans = new ArrayList<>();
      for (int i = left; i < right; i++) {
         Integer curExp = expList.get(i);
         if (curExp == ADD || curExp == SUB || curExp == MUL) {
            List<Integer> leftResults = diffWaysToCompute(left, i - 1, expList, cache);
            List<Integer> rightResults = diffWaysToCompute(i + 1, right, expList, cache);

            for (Integer leftResult : leftResults) {
               for (Integer rightResult : rightResults) {
                  ans.add(calculate(leftResult, rightResult, curExp));
               }
            }
         }
      }

      cache[left][right] = ans;
      return ans;
   }

   public int calculate(int left, int right, int operator) {
      switch (operator) {
         case ADD:
            return left + right;
         case SUB:
            return left - right;
         default:
            return left * right;
      }
   }
}
