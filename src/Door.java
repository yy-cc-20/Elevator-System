
public class Door {
	DoorState doorState = DoorState.CLOSED;
	
	public void open() {
		doorState = DoorState.OPEN;
		System.out.println("Door opened.");
	}
	
	public void close() {
		doorState = DoorState.CLOSED;
		System.out.println("Door closed.");
	}
	
	public DoorState getState() {
		return doorState;
	}
}
