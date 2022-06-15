import java.util.Scanner;

public class WallButton {
	Direction wallButtonPressed;
	int waitingFloor;
	
	public WallButton (int floorLevel) {
		waitingFloor = floorLevel;
	}
	
	// the top floor only has down button
	// the bottom floor only has up button
	
	public boolean promptButtonPressed() {
		// TODO console input
		Scanner scanner = new Scanner(System.in);
		System.out.print("Current floor: ");
		
		System.out.print("Button: ");
		String buttonInput = scanner.nextLine();
		Direction wallbutton = Direction.toButton(buttonInput);
	}
	
	// get waiting floor, validation
	
	public Direction getDirection() {
		return wallButtonPressed;
	}
	
	public Integer getWaitingFloor() {
		return Integer.valueOf(waitingFloor);
	}
}
