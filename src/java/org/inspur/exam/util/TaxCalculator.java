package org.inspur.exam.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;

public class TaxCalculator {

	private static DecimalFormat df = new DecimalFormat("0.00");//放到doubleFormat()里的话效率低
	public static void main(String[] args) {
		try {
			File inFile = new File("3.in");
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			String tempLine = null;
			// File outFile = new File("3.out");
			// writer = new BufferedWriter(new FileWriter(outFile));
			// 拆分前年终奖
			double bonus = 0.00;
			// 月工资
			double pre_salary = 0.00;
			// 拆分数组分别是：当月发放年终奖、第一个月拆分额度、第二个月拆分额度、最终拿到的，最小税额，避税
			double[] result = new double[6];
			
			long start = System.currentTimeMillis();
			while ((tempLine = reader.readLine()) != null) {
				String[] numArr = tempLine.split(" ");
				bonus = Double.parseDouble(numArr[0]);
				pre_salary = Double.parseDouble(numArr[1]);
				result=getOptimalSolution(pre_salary, bonus,true);//true:新题目要求：避税小于1块钱就不拆分了   false:旧题目要求：不考虑避税小于1块钱就不拆分的情况
				for(double value:result){
					System.out.print(value+" ");
				}
				System.out.println();
			}
			System.out.println(System.currentTimeMillis() - start + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double doubleFormat(double value) {
		//return Double.parseDouble(String.format("%.2f", value));//效率低		
		return Double.parseDouble(df.format(value));//效率高
	}

	public static double[]  getOptimalSolution(double pre_salary, double bonus,boolean is_escape_below_1RMB) {
		double  bonus1 = bonus,//用于计算当月发的年终奖				
				bonus3 = bonus,//最优解中当月实际发的年终奖
				bonus4 = bonus;//用于计算当月发的年终奖
		int month=2;
		int seed[] = {0, 1500, 4500, 9000, 35000, 55000, 80000 };

		double tax_income = pre_salary - 3500;
		/**********1.不拆分直接发所有年终奖**********/
		double tax = doubleFormat(computeBonusTax(pre_salary, bonus));
		
		/**********2.凑月薪临界点**********/
		double tax1=Double.MAX_VALUE;
		double tax2=Double.MAX_VALUE;
		if (tax_income <0) {// 税率是0.03
			for (int i = 0; i < 7; i++) {
				bonus1 = bonus - (seed[i] - tax_income) * 2;
				if (bonus1 >= 0)
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary))
						* 2);
				bonus4=bonus-(seed[i] - tax_income);
				if (bonus4 >= 0)
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus4) + (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary)));
				double mintax=tax1<=tax2?tax1:tax2;
				if (tax > mintax) {
					tax = mintax;
					bonus3 = tax1<=tax2?bonus4:bonus1;
					month=tax1 <= tax2 ? 1 : 2;
				}
			}

		}else if (tax_income <= 1500) {// 税率是0.03
			for (int i = 1; i < 7; i++) {
				bonus1 = bonus - (seed[i] - tax_income) * 2;
				if (bonus1 >= 0)
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary))
						* 2);
				bonus4=bonus-(seed[i] - tax_income);
				if (bonus4 >= 0)
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus4) + (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary)));
				double mintax=tax1<=tax2?tax1:tax2;
				if (tax > mintax) {
					tax = mintax;
					bonus3 = tax1<=tax2?bonus4:bonus1;
					month=tax1 <= tax2 ? 1 : 2;
				}
			}

		} else if (tax_income <= 4500) {// 税率是0.1
			for (int i = 2; i < 7; i++) {
				bonus1 = bonus - (seed[i] - tax_income) * 2;
				if (bonus1 >= 0)
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary))
						* 2);
				bonus4=bonus-(seed[i] - tax_income);
				if (bonus4 >= 0)
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus4) + (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary)));
				double mintax=tax1<=tax2?tax1:tax2;
				if (tax > mintax) {
					tax = mintax;
					bonus3 = tax1<=tax2?bonus4:bonus1;
					month=tax1 <= tax2 ? 1 : 2;
				}
			}

		} else if (tax_income <= 9000) {// 税率是0.2
			for (int i = 3; i < 7; i++) {
				bonus1 = bonus - (seed[i] - tax_income) * 2;
				if (bonus1 >= 0)
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary))
						* 2);
				bonus4=bonus-(seed[i] - tax_income);
				if (bonus4 >= 0)
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus4) + (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary)));
				double mintax=tax1<=tax2?tax1:tax2;
				if (tax > mintax) {
					tax = mintax;
					bonus3 = tax1<=tax2?bonus4:bonus1;
					month=tax1 <= tax2 ? 1 : 2;
				}
			}

		} else if (tax_income <= 35000) {// 税率是0.25
			for (int i = 4; i < 7; i++) {
				bonus1 = bonus - (seed[i] - tax_income) * 2;
				if (bonus1 >= 0)
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary))
						* 2);
				bonus4=bonus-(seed[i] - tax_income);
				if (bonus4 >= 0)
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus4) + (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary)));
				double mintax=tax1<=tax2?tax1:tax2;
				if (tax > mintax) {
					tax = mintax;
					bonus3 = tax1<=tax2?bonus4:bonus1;
					month=tax1 <= tax2 ? 1 : 2;
				}
			}

		} else if (tax_income <= 55000) {// 税率是0.3
			for (int i = 5; i < 7; i++) {
				bonus1 = bonus - (seed[i] - tax_income) * 2;
				if (bonus1 >= 0)
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary))
						* 2);
				bonus4=bonus-(seed[i] - tax_income);
				if (bonus4 >= 0)
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus4) + (getTax(pre_salary + seed[i] - tax_income) - getTax(pre_salary)));
				double mintax=tax1<=tax2?tax1:tax2;
				if (tax > mintax) {
					tax = mintax;
					bonus3 = tax1<=tax2?bonus4:bonus1;
					month=tax1 <= tax2 ? 1 : 2;
				}
			}

		} else if (tax_income <= 80000) {// 超过5.5万时0.35
			bonus1 = bonus - (80000 - tax_income) * 2;
			if (bonus1 > 0){
				if (bonus1 >= 0)
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + 80000 - tax_income) - getTax(pre_salary))
						* 2);
				bonus4=bonus-(80000 - tax_income);
				if (bonus4 >= 0)
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus4) + (getTax(pre_salary + 80000 - tax_income) - getTax(pre_salary)));
				double mintax=tax1<=tax2?tax1:tax2;
				if (tax > mintax) {
					tax = mintax;
					bonus3 = tax1<=tax2?bonus4:bonus1;
					month=tax1 <= tax2 ? 1 : 2;
				}
			}			

		} else if (tax_income > 80000) {// 税率是0.45
			//这种情况肯定不是最优解，不用算
		}
		if(bonus3==bonus)month=0;
		
		
		/**********3.凑年终奖临界点**********/
		double bonus2 = 0;//拆分到月的年终奖
		int seed2[] = { 18000, 54000, 108000, 420000, 660000 };//最多拆660000，不会有960000
		if (bonus > 660000) {
			if (pre_salary >= 3500) {
				for (int i = 4; i >= 0; i--) {
					bonus1 = seed2[i];
					bonus2 = bonus - bonus1;
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ getTax(pre_salary + bonus2) - getTax(pre_salary));
					bonus2 = (bonus - bonus1) / 2;
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
							* 2);
					double mintax = tax1 <= tax2 ? tax1 : tax2;
					
					if (tax > mintax) {
						tax = mintax;
						bonus3 = bonus1;month=tax1 <= tax2 ? 1 : 2;
					}
				}

			} else {
				for (int i = 4; i >= 0; i--) {
					bonus1 = seed2[i] + 3500 - pre_salary;
					bonus2 = bonus - bonus1;
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ getTax(pre_salary + bonus2) - getTax(pre_salary));
					bonus2 = (bonus - bonus1) / 2;
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
							* 2);
					double mintax = tax1 <= tax2 ? tax1 : tax2;
					
					if (tax > mintax) {
						tax = mintax;
						bonus3 = bonus1;month=tax1 <=tax2 ? 1 : 2;
					}
				}
			}

		} else if (bonus > 420000) {
			if (pre_salary >= 3500) {
				for (int i = 3; i >= 0; i--) {
					bonus1 = seed2[i];
					bonus2 = bonus - bonus1;
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ getTax(pre_salary + bonus2) - getTax(pre_salary));
					bonus2 = (bonus - bonus1) / 2;
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
							* 2);
					double mintax = tax1 <= tax2 ? tax1 : tax2;
					
					if (tax > mintax) {
						tax = mintax;
						bonus3 = bonus1;month=tax1 <= tax2 ? 1 : 2;
					}
				}

			} else {
				for (int i = 3; i >= 0; i--) {
					bonus1 = seed2[i] + 3500 - pre_salary;
					bonus2 = bonus - bonus1;
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ getTax(pre_salary + bonus2) - getTax(pre_salary));
					bonus2 = (bonus - bonus1) / 2;
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
							* 2);
					double mintax = tax1 <= tax2 ? tax1 : tax2;
					
					if (tax > mintax) {
						tax = mintax;
						bonus3 = bonus1;month=tax1 <= tax2 ? 1 : 2;
					}
				}
			}
		} else if (bonus > 108000) {
			if (pre_salary >= 3500) {
				for (int i = 2; i >= 0; i--) {
					bonus1 = seed2[i];
					bonus2 = bonus - bonus1;
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ getTax(pre_salary + bonus2) - getTax(pre_salary));
					bonus2 = (bonus - bonus1) / 2;
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
							* 2);
					double mintax = tax1 <= tax2 ? tax1 : tax2;
					
					if (tax > mintax) {
						tax = mintax;
						bonus3 = bonus1;month=tax1 <= tax2 ? 1 : 2;
					}
				}

			} else {
				for (int i = 2; i >= 0; i--) {
					bonus1 = seed2[i] + 3500 - pre_salary;
					bonus2 = bonus - bonus1;
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ getTax(pre_salary + bonus2) - getTax(pre_salary));
					bonus2 = (bonus - bonus1) / 2;
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
							* 2);
					double mintax = tax1 <= tax2 ? tax1 : tax2;
				
					if (tax > mintax) {
						tax = mintax;
						bonus3 = bonus1;	month=tax1 <= tax2 ? 1 : 2;
					}
				}
			}
		} else if (bonus > 54000) {
			if (pre_salary >= 3500) {
				for (int i = 1; i >= 0; i--) {
					bonus1 = seed2[i];
					bonus2 = bonus - bonus1;
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ getTax(pre_salary + bonus2) - getTax(pre_salary));
					bonus2 = (bonus - bonus1) / 2;
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
							* 2);
					double mintax = tax1 <= tax2 ? tax1 : tax2;
				
					if (tax > mintax) {
						tax = mintax;
						bonus3 = bonus1;	month=tax1 <= tax2 ? 1 : 2;
					}
				}

			} else {
				for (int i = 1; i >= 0; i--) {
					bonus1 = seed2[i] + 3500 - pre_salary;
					bonus2 = bonus - bonus1;
					 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ getTax(pre_salary + bonus2) - getTax(pre_salary));
					bonus2 = (bonus - bonus1) / 2;
					 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
							+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
							* 2);
					double mintax = tax1 <= tax2 ? tax1 : tax2;
					
					if (tax > mintax) {
						tax = mintax;
						bonus3 = bonus1;month=tax1 <= tax2 ? 1 : 2;
					}
				}
			}
		} else if (bonus > 18000) {
			if (pre_salary >= 3500) {

				bonus1 = seed2[0];
				bonus2 = bonus - bonus1;
				 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ getTax(pre_salary + bonus2) - getTax(pre_salary));
				bonus2 = (bonus - bonus1) / 2;
				 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
						* 2);
				double mintax = tax1 <= tax2 ? tax1 : tax2;
				
				if (tax > mintax) {
					tax = mintax;
					bonus3 = bonus1;month=tax1 <= tax2 ? 1 : 2;
				}

			} else {

				bonus1 = seed2[0] + 3500 - pre_salary;
				bonus2 = bonus - bonus1;
				 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ getTax(pre_salary + bonus2) - getTax(pre_salary));
				bonus2 = (bonus - bonus1) / 2;
				 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
						* 2);
				double mintax = tax1 <= tax2 ? tax1 : tax2;
				
				if (tax > mintax) {
					tax = mintax;
					bonus3 = bonus1;month=tax1 <= tax2 ? 1 : 2;
				}

			}
		} else if (bonus <= 18000) {
			if (pre_salary >= 3500) {

				bonus1 = 0;
				bonus2 = bonus - bonus1;
				 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ getTax(pre_salary + bonus2) - getTax(pre_salary));
				bonus2 = (bonus - bonus1) / 2;
				 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
						* 2);
				double mintax = tax1 <= tax2 ? tax1 : tax2;
				
				if (tax > mintax) {
					tax = mintax;
					bonus3 = bonus1;month=tax1 <= tax2 ? 1 : 2;
				}

			} else {

				bonus1 = 3500 - pre_salary;
				bonus2 = bonus - bonus1;
				 tax1 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ getTax(pre_salary + bonus2) - getTax(pre_salary));
				bonus2 = (bonus - bonus1) / 2;
				 tax2 = doubleFormat(computeBonusTax(pre_salary, bonus1)
						+ (getTax(pre_salary + bonus2) - getTax(pre_salary))
						* 2);
				double mintax = tax1 <= tax2 ? tax1 : tax2;
			
				if (tax > mintax) {
					tax = mintax;
					bonus3 = bonus1;	month=tax1 <= tax2 ? 1 : 2;
				}

			}
		}
		//System.out.println(month);
		double[] result=new double[6];//年终奖，第一月，第二月，最终拿到的，税，避税
		
		double escapetax=doubleFormat(computeBonusTax(pre_salary, bonus)-tax);
		if (escapetax<=0)escapetax=0;//有可能是-0.00000000001,四舍五入就是-0.00
		if(is_escape_below_1RMB){
			if(escapetax<=1.00){//根据新的题目要求，避税小于1块钱就不拆分了
				tax=doubleFormat(computeBonusTax(pre_salary, bonus));
				result[0]=doubleFormat(bonus);
				result[1]=0;
				result[2]=0;
				result[3]=doubleFormat(bonus-tax);
				result[4]=tax;
				result[5]=0;
			}else {
				if(month==1){
					result[0]=doubleFormat(bonus3);
					result[1]=doubleFormat(bonus-bonus3);
					result[2]=0;
					result[3]=doubleFormat(bonus - tax);
					result[4]=doubleFormat(tax);
					result[5]=escapetax;
				}else if(month==2){
					result[0]=doubleFormat(bonus3);
					result[1]=doubleFormat((bonus-bonus3)/2);
					result[2]=result[1];
					result[3]=doubleFormat(bonus - tax);
					result[4]=doubleFormat(tax);
					result[5]=escapetax;
				}
			}		
		}else{//不考虑避税小于1块钱就不拆分的情况
			if(month==0){
				result[0]=doubleFormat(bonus3);
				result[1]=0;
				result[2]=0;
				result[3]=doubleFormat(bonus - tax);
				result[4]=doubleFormat(tax);
				result[5]=escapetax;
			}else if(month==1){
				result[0]=doubleFormat(bonus3);
				result[1]=doubleFormat(bonus-bonus3);
				result[2]=0;
				result[3]=doubleFormat(bonus - tax);
				result[4]=doubleFormat(tax);
				result[5]=escapetax;
			}else if(month==2){
				result[0]=doubleFormat(bonus3);
				result[1]=doubleFormat((bonus-bonus3)/2);
				result[2]=result[1];
				result[3]=doubleFormat(bonus - tax);
				result[4]=doubleFormat(tax);
				result[5]=escapetax;
			}
		}
		
		return result;
	}

	/**
	 * 根据工资计算税额
	 * 
	 * @param pre_salary
	 *            税前工资
	 * @return 应该交纳的税
	 */
	public static double getTax(double pre_salary) {
		double tax = 0.00;
		if (pre_salary <= 3500)
			return tax;
		// 需要计算税额的钱
		double tax_income = pre_salary - 3500;
		if (tax_income <= 1500) {// 税率是0.03
			tax = tax_income * 0.03 - 0;
		} else if (tax_income <= 4500) {// 税率是0.1
			tax = tax_income * 0.1 - 105;
		} else if (tax_income <= 9000) {// 税率是0.2
			tax = tax_income * 0.2 - 555;
		} else if (tax_income <= 35000) {// 税率是0.25
			tax = tax_income * 0.25 - 1005;
		} else if (tax_income <= 55000) {// 税率是0.3
			tax = tax_income * 0.3 - 2755;
		} else if (tax_income <= 80000) {// 超过5.5万时0.35
			tax = tax_income * 0.35 - 5505;
		} else if (tax_income > 80000) {// 税率是0.45
			tax = tax_income * 0.45 - 13505;
		}
		return tax;
	}

	/**
	 * 计算年度额需要交纳的税金
	 * 
	 * @param salary
	 * @param bonus
	 * @return
	 */
	public static double computeBonusTax(double salary, double bonus) {
		double tax = 0.00;
		// 用于计算税率的额度
		double tax_income = 0.00;
		double reduce_income = 0.00;
		if (bonus <= 0)
			return tax;
		if (salary < 3500) {// 月工资低于3500
			if ((bonus - (3500 - salary)) <= 0) {
				return tax;
			} else {
				reduce_income = bonus - (3500 - salary);
				tax_income = reduce_income / 12;
			}
		} else {// 月工资高于3500
			reduce_income = bonus;
			tax_income = bonus / 12;
		}
		if (tax_income <= 1500) {// 税率是0.03
			tax = reduce_income * 0.03 - 0;
		} else if (tax_income <= 4500) {// 税率是0.1
			tax = reduce_income * 0.1 - 105;
		} else if (tax_income <= 9000) {// 税率是0.2
			tax = reduce_income * 0.2 - 555;
		} else if (tax_income <= 35000) {// 税率是0.25
			tax = reduce_income * 0.25 - 1005;
		} else if (tax_income <= 55000) {// 税率是0.3
			tax = reduce_income * 0.3 - 2755;
		} else if (tax_income <= 80000) {// 超过5.5万时0.35
			tax = reduce_income * 0.35 - 5505;
		} else if (tax_income > 80000) {// 税率是0.45
			tax = reduce_income * 0.45 - 13505;
		}
		return tax;
	}
}
