import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Elevator {
	private int id;
	
	// Composite relationship
	private final CabinButton cabinButton = new CabinButton(this);
	private final Door door = new Door();
	
	private int topFloor;
	private int bottomFloor;
	
	private int currentFloor;
	private Direction movingDirection = Direction.STOPPED;
	private SortedSet<Integer> destinationList = new TreeSet<>();
	
	private void setCurrentFloor(int currentFloor) {
		if (floorLevelInService(currentFloor))
			this.currentFloor = currentFloor;
		else
			throw new IllegalArgumentException("Invalid current floor.");
	}
	
	public Elevator(int id, int currentFloor, int topFloor, int bottomFloor) {
		this.id = id;
		setCurrentFloor(currentFloor);	
		
		if (topFloor < bottomFloor)
			throw new IllegalArgumentException("Top floor must be higher than bottom floor.");
		
		this.topFloor = topFloor;
		this.bottomFloor = bottomFloor;
	}
	
	public boolean hasDestination() {
		return destinationList.size() > 0;
	}
	
	public void addDestination(SortedSet<Integer> destination) {
		if (destination != null)
			destinationList.addAll(destinationList);
	}
	
	public void addDestination(Integer destination) {
		if (destination != null)
			destinationList.add(destination);
	}
	
	public void clearDestination() {
		destinationList.remove(Integer.valueOf(currentFloor));
	}

	public boolean floorLevelInService(int floorLevel) {
		return floorLevel <= topFloor && floorLevel >= bottomFloor;
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	public CabinButton getCabinButton() {
		return cabinButton;
	}

	public void openDoor() {
		door.open();
	}
	
	public void closeDoor() {
		door.close();
	}
	
	/** @return true if the elevator can stop at the target floor */
	public boolean canStop(int targetFloor, Direction direction) {
		return this.movingDirection == direction || this.movingDirection == Direction.STOPPED;
	}

	private void changeDirection() {
		if (movingDirection == Direction.UP)
			movingDirection = Direction.DOWN;
		else if (movingDirection == Direction.DOWN)
			movingDirection = Direction.UP;
		else {
			// TODO determine the new direction
		}
	}
	
	private void moveUp() {
		if (currentFloor < topFloor) {
			movingDirection = Direction.UP;
			currentFloor++;
			System.out.println("E" + id + " Current floor: " + currentFloor);
		} else {
			System.out.println("Reaching top floor. Cannot go higher.");
			return;
		}
	}
	
	private void moveDown() {
		if (currentFloor > bottomFloor) {
			movingDirection = Direction.DOWN;
			currentFloor--;
			System.out.println("E" + id + " Current floor: " + currentFloor);
		} else {
			System.out.println("Reaching bottom floor. Cannot go lower.");
			return;
		}
	}

	// Find requests in the same direction
	/** @return null if not found */
	private Integer findNearestDestinationWithSameDirection() {
		if (movingDirection == Direction.UP)
			
		else if (movingDirection == Direction.DOWN)
			
		else
			throw new IllegalArgumentException("Direction cannot be 'STOPPED'.");
			
	}
	
	// Move to the nearest destination
	public void move() {
		if (door.getState() != DoorState.CLOSED) {
			System.out.println("The elevator cannot move because the door is not closed.");
			System.exit(1);
		} else if (destinationList.size() == 0) {
			System.out.println("The elevator cannot move because of no request was made.");
			System.exit(1);
		}
		
		if (movingDirection == Direction.STOPPED)
			changeDirection();
		
		Integer destination = findNearestDestinationWithSameDirection();
		
		// Change the direction if no requests in the same direction, but has some requests in the opposite direction
		if (destination == null) { 
			changeDirection();
			destination = findNearestDestinationWithSameDirection();
		} 
		
		if (movingDirection == Direction.UP) {
			while (currentFloor != destination.intValue()) {
				moveUp();
			}
		} else if (movingDirection == Direction.DOWN) {
			while (currentFloor != destination.intValue()) {
				moveDown();
			}
		}
	}
}
