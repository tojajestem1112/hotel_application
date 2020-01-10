package sample.view;

public class ViewDetails
{
    private static String showedCentralPanel = "";
    private static String showedRightPanel="";

    public static String getShowedCentralPanel() {
        return showedCentralPanel;
    }

    public static void setShowedCentralPanel(String showedCentralPanel) {
        ViewDetails.showedCentralPanel = showedCentralPanel;
    }

    public static String getShowedRightPanel() {
        return showedRightPanel;
    }

    public static void setShowedRightPanel(String showedRightPanel) {
        ViewDetails.showedRightPanel = showedRightPanel;
    }
}
