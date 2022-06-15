import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class CabinButton {
	private Elevator elevator; // Bidirectional one-to-one relationship
	
	public CabinButton(Elevator elevator) {
		this.elevator = elevator;
	}
	
	/** @return null if no response */
	// one button for each floor	
	// open and close door button
	public SortedSet<Integer> promptDestination() {
		SortedSet<Integer> destination = new TreeSet<>();
		// TODO console input
		
		if (button == "open") {
			elevator.openDoor();
		} else if (button == "close") {
			elevator.closeDoor();
		}
		
		return destination;
	}
}
