package application;

import java.util.Random;

public class Model {
	
	public static String tracker;

	public static Integer roll(int num, int i, int mod, String mode, int rerollThreshold) {
		Integer result = 0;
		Integer[] roll;
		tracker = "Roll<"+mode+">(" + num + "d" + i + ")+" + mod + ":\n";
		
		for(int j = 0; j < num; j++){
			switch(mode){
				case "triadv":
					roll = triadv(i, rerollThreshold);
					tracker += "{"+roll[1].intValue()+","+roll[2].intValue()+","+roll[3].intValue()+"}:";
					break;
				case "adv":
					roll = radv(i, rerollThreshold);
					tracker += "{"+roll[1].intValue()+","+roll[2].intValue()+"}:";
					break;
				case "dis":
					roll = rdis(i, rerollThreshold);
					tracker += "{"+roll[1].intValue()+","+roll[2].intValue()+"}:";
					break;
				default:
					roll = rnorm(i, rerollThreshold);
					break; }
			result =  roll[0].intValue() + result;
			tracker += roll[0].intValue();
			if(j < num - 1)
				tracker += ",";
			else{
				result += mod;
				if(result <= 0)
					result = 1;
				tracker += ",+" + mod + "\nTotal: " + result;
			}
		}
		System.out.print(tracker);
		return result;
	}
	
	public static Integer[] triadv(int i, int rerollThreshold) {
		Random rand = new Random();
		Integer temp1 = rand.nextInt(i) + 1;
		if(temp1 <= rerollThreshold){
			tracker += "[rr" + temp1 + "~";
			temp1 = rand.nextInt(i) + 1;
			tracker += temp1 + "]";
		}
		Integer temp2 = rand.nextInt(i) + 1;
		if(temp2 <= rerollThreshold){
			tracker += "[rr" + temp2 + "~";
			temp2 = rand.nextInt(i) + 1;
			tracker += temp2 + "]";
		}
		Integer temp3 = rand.nextInt(i) + 1;
		if(temp3 <= rerollThreshold){
			tracker += "[rr" + temp3 + "~";
			temp3 = rand.nextInt(i) + 1;
			tracker += temp3 + "]";
		}
		Integer[] ret = { Math.max(temp1, Math.max(temp2, temp3)), temp1, temp2, temp3 };
		return ret;
	}
	public static Integer[] radv(int i, int rerollThreshold) {
		Random rand = new Random();
		Integer temp1 = rand.nextInt(i) + 1;
		if(temp1 <= rerollThreshold){
			tracker += "[rr" + temp1 + "~";
			temp1 = rand.nextInt(i) + 1;
			tracker += temp1 + "]";
		}
		Integer temp2 = rand.nextInt(i) + 1;
		if(temp2 <= rerollThreshold){
			tracker += "[rr" + temp2 + "~";
			temp2 = rand.nextInt(i) + 1;
			tracker += temp2 + "]";
		}
		Integer[] ret = { Math.max(temp1, temp2), temp1, temp2 };
		return ret;
	}
	public static Integer[] rdis(int i, int rerollThreshold) {
		Random rand = new Random();
		Integer temp1 = rand.nextInt(i) + 1;
		if(temp1 <= rerollThreshold){
			tracker += "[rr" + temp1 + "~";
			temp1 = rand.nextInt(i) + 1;
			tracker += temp1 + "]";
		}
		Integer temp2 = rand.nextInt(i) + 1;
		if(temp2 <= rerollThreshold){
			tracker += "[rr" + temp2 + "~";
			temp2 = rand.nextInt(i) + 1;
			tracker += temp2 + "]";
		}
		Integer[] ret = { Math.min(temp1, temp2), temp1, temp2 };
		return ret;
	}
	public static Integer[] rnorm(int i, int rerollThreshold) {
		Random rand = new Random();
		Integer temp1 = rand.nextInt(i) + 1;
		if(temp1 <= rerollThreshold){
			tracker += "[rr" + temp1 + "~";
			temp1 = rand.nextInt(i) + 1;
			tracker += temp1 + "]";
		}
		Integer[] ret = { temp1 };
		return ret;
	}

}
