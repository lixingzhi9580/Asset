package com.cn.feeAmt;

public class FeeAmt {
	
	public static void main(String[] args) {
		// 招商银行分期
		int[] zsyhfq = { 0, 2, 3, 6, 10, 12 };
		// 招商银行分期
		double[] zsyhfqfl = { 0, 0.01, 0.009, 0.0075, 0.007, 0.0066 };
		double hknl = 10000;
		// 本金
		double nbj = 60000;

		for (int y = 1; y < 13; y++) {
			int fq = 0;
			double q = 0;
			for (int z = 0; z < zsyhfq.length; z++) {
				// 分期几个月
				fq = zsyhfq[z];
				// 分期手续费
				q = zsyhfqfl[z];

				// 起始年化
				double nnh = 0.06;
				// 递增年化
				double nznh = 0.01;
				// 提前退出手续费率
				double nsxf = 0.002;
				// 总共几个月
				int ny = 12;
				// 第几个月结束后退出
				// int tqq=4;
				// 总收益
				double zlx = 0;

				for (int i = 1; i <= ny; i++) {
					// 月收益
					zlx += nbj * nnh / 12;
					if (i == y && i != ny) {
						// 退出后减去扣率
						zlx -= nsxf * nbj;
						break;
					}
					// 逐月增加年化
					nnh += nznh;
				}

				double zhsy = zlx - nbj * q * fq;
				double mqhk = fq==0?nbj:nbj * q * fq + nbj / fq;
				if(zhsy>0&&mqhk<hknl){
				System.out
						.println(String
								.format("第\t%s\t月后退出\t本金为：\t%s\t当月年化为：\t%s\t提前提取扣费为：\t%s\t最后收益为：\t%s\t分\t%s\t期，手续费为：\t%s\t最后收益为：\t%s\t每期还款：\t%s\t换算成平均月收益为：\t%s",
										y, nbj, nnh, nsxf * nbj, zlx, fq, nbj
												* q * fq, zhsy, mqhk,zhsy/y));
				}
			}
		}
	}
	
	
//	public static void main(String[] args) {
//		// 招商银行分期
//		int[] zsyhfq = { 2 };
//		// 招商银行分期
//		double[] zsyhfqfl = { 0.01 };
//		// 本金
//		double nbj = 10000;
//
//		// 第几个月退出，分期几个月
//		int y = 2;
//		// 分期手续费
//		double q = 0.01;
//		// 起始年化
//		double nnh = 0.085;
//		// 总共几个月
//		int ny = 3;
//		double zlx = 0;
//
//		for (int i = 1; i <= ny; i++) {
//			// 月收益
//			zlx += nbj * nnh / 12;
//		}
//		System.out.println(String.format(
//				"第%s月后退出；本金为：%s；当月年化为：%s；最后收益为：%s；分%s期，手续费为：%s，最后收益为：%s；", y,
//				nbj, nnh, zlx, y, nbj * q * y, zlx - nbj * q * y));
//	}
}
