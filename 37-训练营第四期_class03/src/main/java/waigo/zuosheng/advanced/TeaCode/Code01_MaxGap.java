package waigo.zuosheng.advanced.TeaCode;

import java.util.Arrays;

public class Code01_MaxGap {

	public static int maxGap(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		int len = nums.length;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < len; i++) {
			min = Math.min(min, nums[i]);
			max = Math.max(max, nums[i]);
		}
		if (min == max) {
			return 0;
		}
		// 不止一种数，min~max一定有range,  len个数据，准备len+1个桶
		boolean[] hasNum = new boolean[len + 1]; // hasNum[i] i号桶是否进来过数字
		int[] maxs = new int[len + 1];  // maxs[i] i号桶收集的所有数字的最大值
		int[] mins = new int[len + 1];  // mins[i] i号桶收集的所有数字的最小值
		
		int bid = 0; // 桶号
		for (int i = 0; i < len; i++) {
			bid = bucket(nums[i], len, min, max);
			mins[bid] = hasNum[bid] ? Math.min(mins[bid], nums[i]) : nums[i];
			maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], nums[i]) : nums[i];
			hasNum[bid] = true;
		}
		int res = 0;
		int lastMax = maxs[0]; // 上一个非空桶的最大值
		int i = 1;
		for (; i <= len; i++) {
			if (hasNum[i]) {
				res = Math.max(res, mins[i] - lastMax);
				lastMax = maxs[i];
			}
		}
		return res;
	}

	public static int bucket(long num, long len, long min, long max) {
		return (int) ((num - min) * len / (max - min));
	}

	// for test
	public static int comparator(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		Arrays.sort(nums);
		int gap = Integer.MIN_VALUE;
		for (int i = 1; i < nums.length; i++) {
			gap = Math.max(nums[i] - nums[i - 1], gap);
		}
		return gap;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static void main(String[] args) {
//		int testTime = 500000;
//		int maxSize = 100;
//		int maxValue = 100;
//		boolean succeed = true;
//		for (int i = 0; i < testTime; i++) {
//			int[] arr1 = generateRandomArray(maxSize, maxValue);
//			int[] arr2 = copyArray(arr1);
//			if (maxGap(arr1) != comparator(arr2)) {
//				succeed = false;
//				break;
//			}
//		}
//		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
		int[] a = {601408776,63967816,431363697,242509930,15970592,60284088,228037800,147629558,220782926,55455864,456541040,106650540,17290078,52153098,103139530,294196042,16568100,426864152,61916064,657788565,166159446,1741650,101791800,28206276,6223796,524849590,125389882,84399672,153834912,164568204,1866165,283209696,560993994,16266096,219635658,9188983,485969304,782013650,120332636,44659356,444517408,36369045,47370708,18542592,98802990,137690000,124889895,56062800,265421676,309417680,4634176,801661539,510541206,258227892,398938089,47255754,152260962,409663140,102847688,45756553,377936600,269498,375738702,263761134,53797945,329493948,224442208,508336845,189507850,40944620,127879560,119629476,186894520,62409156,693721503,4289916,523899936,28955240,266488028,20356650,40769391,483694272,97988044,84102,67246047,310688630,41288643,58965588,42881432,152159462,94786355,174917835,119224652,525034376,261516,274800528,62643819,23613832,8397240,797832131,855155367,337066320,26341480,61932200,20661075,515542796,390337500,522552030,43538516,150800550,116747540,152989123,488640056,700610304,233604,344277340,21439176,9397864,16365822,73027584,453041413,197374275,157735188,15273822,187081152,379611084,865005504,223099767,80478651,377729400,186738219,34738263,16634072,112791343,99631856,119364960,477106486,583953920,624509809,188442472,294181256,213023715,146645884,149530380,497592753,132170327,72770643,126683010,405141255,590214306,26670714,95582385,162080790,231120099,8946432,204967980,592849110,54120698,375915096,602145859,5346440,226337825,425156369,653591624,578483360,572410800,32290700,381384563,149939976,183225375,155695620,38307636,457513760,97085778,75200576,8068176,221650296,556889418,252495726,895020231,19932465,156334887,191383314,348432526,368701264,14315598,148936587,279419435,237325542,252587218,322929504,26331343,355297676,600420786,652017765,51673622,159015675};
		System.out.println(maxGap(a));
	}

}
