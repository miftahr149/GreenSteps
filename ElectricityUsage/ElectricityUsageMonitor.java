package ElectricityUsage;

import database.*;
import java.util.Scanner;
import java.util.ArrayList;

public class ElectricityUsageMonitor {

    private RecordManager<Room> roomManager;
    private Scanner input;

    public ElectricityUsageMonitor() {
        this.input = new Scanner(System.in);
        roomManager = RecordManager.get("room");
    }


    public void addRoom() {
        System.out.print("Enter room name: ");
        String name = input.nextLine();
        Room r1 = roomManager.create();
        r1.setName(name);
        roomManager.save();

    }

    public void deleteRoom() {
        RecordQuery<Room> roomQuery = (Room recordQuery) -> {return true;};
        ArrayList<Room> rooms = roomManager.query(roomQuery);
        System.out.println("Room List:");
        for (Room room : rooms) {
                System.out.println(room.getName());
        }
        System.out.print("Enter room name to delete: ");
        String name = input.nextLine();
        ArrayList<Room> roomToRemove = roomManager.query(r -> r.getName().equals(name));
        if (!roomToRemove.isEmpty()) {
            roomToRemove.get(0).delete();
            System.out.println("Room deleted.");
        } else {
            System.out.println("Room not found.");
        }
        roomManager.save();
    }

    public void displayRoomsByCategory() {
        System.out.print("Enter room category: ");
        String type = input.nextLine();

    }

    public void limitRoom(Room r1) {
        System.out.print("Current Limit: ");
        r1.getLimit();
        System.out.print("New Limit: ");
        double limit = input.nextDouble();
        r1.setLimit(limit);
        roomManager.save();
    }
}
