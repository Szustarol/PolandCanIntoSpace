package space;

import space.view.MainWindow;

public class Space {
    public static void main(String [] args){
        System.out.println("Starting PCIS");
        Translations.init("pl_PL");
        MainWindow mainWindow = new MainWindow("Poland Can Into Space");
        mainWindow.setVisible(true);
    }
}
