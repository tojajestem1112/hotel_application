package sample.module;

import sample.module.entities.Employee;

public class SavedData
{
    public static Employee loggedEmployee = null;
    public static int endedLogging = 0;
    public static void clear()
    {
        loggedEmployee = null;
    }
    private static final int PRICE = 35;
    public static Employee getLoggedEmployee() {
        return loggedEmployee;
    }
    public static int getPrice(){return PRICE;}
    public static void setLoggedEmployee(Employee loggedEmployee) {
        SavedData.loggedEmployee = loggedEmployee;
    }
}
