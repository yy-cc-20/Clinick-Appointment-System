package boundary;

import database.DatabaseConnection;
import entity.Receptionist;
import entity.User;

import java.sql.SQLException;

public class testingForMakeApt {
    public static void main(String[] args) {
        User user = new Receptionist();
        MakeAppointmentUI makeAppointmentInterface = new MakeAppointmentUI(user);

        int choiceNo; // the action that user wants to perform
        final int beginChoiceNo = 1;
        final int endChoiceNo = 3;

        while (true) {
            ConsoleUI.displaySystemName("Make Appointment");
            ConsoleUI.displayMenuForReceptionist(); // need to change the menu
            choiceNo = ConsoleInput.askChoice(beginChoiceNo, endChoiceNo, "Your choice");

            switch (choiceNo) {
                case 1 -> {
                    makeAppointmentInterface.viewAppointment();
                }
                case 2 -> // Modify Account Info
                    makeAppointmentInterface.searchAppointment();
                case 3 ->  // logout and exit the program
                    makeAppointmentInterface.makeAppointment();
            }
            ConsoleUI.clearScreen();
        }
    }
}
