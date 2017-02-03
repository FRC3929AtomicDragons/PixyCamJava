package org.usfirst.frc.team3929.robot;
import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	
	I2C PixyBus;
	int x1, y1, z1, x2, y2, z2;
	int syncWord, syncWordBack;
	int w, lastw;
	int g_skipstart, PIXY_ARRAYSIZE;
	Blocks[] g_blocks;
	//buffer to store data, count to determine how nuch data to get at once
	byte[] buffer, bufferBack;
	int count, countBack;
	
	boolean returnI, secondObject;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		
		PixyBus = new I2C(I2C.Port.kOnboard, 0x54);
		w = 0;
		lastw = 0;
		syncWord = 0xaa55;
		syncWordBack = 0x55aa;
		PIXY_ARRAYSIZE = 100;
		
		
		//for getting the frames
		buffer = new byte[2];
		count = 2;
		
		bufferBack = new byte[1];
		count = 1;
		
		g_blocks = new Blocks[2];
		
		System.out.println(PixyBus.addressOnly());
		
		secondObject = false;
		returnI = false;
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		
	}
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		int i = 0;
		boolean previous = false;
		getStart();
		if(previous && returnI) {
			System.out.println("Frame #: " + i++);
		}
		previous = returnI;
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
void getStart(){
	PixyBus.readOnly(buffer,count);
	w = getWord();
	if (w == 0 && lastw ==0){
		returnI = false;
	} else if(w == syncWord && lastw == syncWord){
		System.out.println("New Frame");
		returnI = true;
	} else if (w == syncWordBack) {
		PixyBus.readOnly(bufferBack,countBack);
	} else{
		returnI = false;
	}lastw = w;
}	
int getWord() {
	w = ((((int)buffer[1] | buffer[0])));
	return w;
	}
int mane(){
	int i=0 ;
	boolean curr, prev = false;
	
	while(true){
		getStart();
		curr = returnI;
		if (prev & curr)
			System.out.printf("%d", i++);
		prev = curr;
	}
}
void getBlocks(){
	if(returnI){
		for(int increment = 0; increment<13; increment++){
			g_blocks[0].set(increment, getWord());
			if(increment == 6) {
				if(getWord() == syncWord){
					secondObject = true;
				}
			} else if ((increment > 6) && secondObject){
				g_blocks[1].set(increment - 7, getWord());
			}
		}
		if(g_blocks[0].checkSum() == g_blocks[0].values[0]){
			
		} else{
			//put something here if checksum is necessarry
		}
	}
	
}
}
