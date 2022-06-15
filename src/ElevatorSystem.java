
public class ElevatorSystem {
	// Fixed number of elevators
	public static final int NUMBER_OF_ELEVATORS = 2;
	
	// Floor level: 5, 4, 3, 2, 1, G, B1, B2
	public static final int TOP_FLOOR = 5;
	public static final int BOTTOM_FLOOR = -2;
	
	// Composite relationship
	private final Elevator elevatorList[];
	private WallButton[] wallButtonList = new WallButton[TOP_FLOOR - BOTTOM_FLOOR + 1];
	
	public ElevatorSystem(Elevator[] elevatorList) {
		this.elevatorList = elevatorList;
		
		if (this.elevatorList == null || this.elevatorList.length == 0)
			throw new IllegalArgumentException("Elevator not exists.");
		
		for (int floor = BOTTOM_FLOOR, i = 0; floor <= TOP_FLOOR; ++floor, ++i)
			wallButtonList[i] = new WallButton(floor);
	}
	
	private int calculateFloorDistance(Elevator elevator, int targetFloor) {
			return Math.abs(elevator.getCurrentFloor() - targetFloor);
	}
	
	// Find the nearest elevator to minimize the waiting time
	private Elevator assignElevator(int requiredFloor, Direction direction) {
		int nearestFloorDistance = Integer.MAX_VALUE;
		int tempDistance;
		int nearestElevator = -1;
		
		for (int i = 0; i < elevatorList.length; ++i) {
			
			if (elevatorList[i].floorLevelInService(requiredFloor) && elevatorList[i].canStop(requiredFloor, direction)) {
				tempDistance = calculateFloorDistance(elevatorList[i], requiredFloor);
				
				if (tempDistance < nearestFloorDistance) {
					nearestFloorDistance = tempDistance;
					nearestElevator = i;
				}
			}
		}
		
		// Could not find elevator
		if (nearestElevator == -1) 
			for (Elevator elevator : elevatorList)
				if (elevator.floorLevelInService(requiredFloor))
					return elevator;
		
		return elevatorList[nearestElevator];
	}
	
	public void start() {
		WallButton wallButton = null;
		Elevator anElevator = null;
		
		while (true) {
			// Outside elevator
			for (int floor = BOTTOM_FLOOR; floor <= TOP_FLOOR; ++floor) {
				wallButton = wallButtonList[floor];
				if (wallButton.promptButtonPressed()) {
					anElevator = assignElevator(floor, wallButton.getDirection());
					anElevator.addDestination(floor);
				}
			}
			
			// Inside elevator
			for (Elevator elevator : elevatorList) 
				anElevator.addDestination(
						elevator.getCabinButton().promptDestination());
			
			// Elevators move
			for (Elevator elevator : elevatorList) {
				if (elevator != null && anElevator.hasDestination()) {
					elevator.move();
					elevator.openDoor();
					elevator.clearDestination();
					elevator.closeDoor();
				}
			}
		}
	}
	
	public void main(String[] args) {
		Elevator[] elevatorList = new Elevator[NUMBER_OF_ELEVATORS];
		elevatorList[0] = new Elevator(1, TOP_FLOOR, 1, 1); // Floor level: 5, 4, 3, 2, 1
		elevatorList[1] = new Elevator(2, 1, BOTTOM_FLOOR, 1); // Floor level: 1, G, B1, B2
		elevatorList[2] = new Elevator(3, TOP_FLOOR, BOTTOM_FLOOR, 0); // Floor level: 5, 4, 3, 2, 1, G, B1, B2
		
		ElevatorSystem elevatorSystem = new ElevatorSystem(elevatorList);
		elevatorSystem.start();
	}
}
