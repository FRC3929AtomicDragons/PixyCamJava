package org.usfirst.frc.team3929.robot;

public class Blocks {
	public int[] values = new int[6];
	public Integer getHeight(){
		if(values[5] != 0)
			return height;
		return null;
	}
	public Integer getWidth(){
		if(values[4] != 0)
			return width;
		return null;
	}
	
	//0 = checksum, 1 = sig, 2 = x, 3 = y, 4 = width, 5 = height
	public void set(int increment, int input) {
		values[increment] = input;
	}
	public void reset(){
		for(int i = 0; i<6; i++){
			values[i] = 0;
		}
	}
	public int checkSum(){
		int checksum = 0;
		for(int i=0; i<6; i++){
			checksum = checksum + values[i];
		}
		return checksum;
	}
}
