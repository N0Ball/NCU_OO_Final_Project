package umleditor;

import java.awt.Color;

import umleditor.interactor.*;

public class Presenter {

    private static final SidebarInteractor SIDEBAR_INTERACTOR = new SidebarInteractor();

    public Presenter(){}

    public class Base{
        public Base(){}
    }

    public class Sidebar extends Base{

        public int getBtnNumbers() { return SIDEBAR_INTERACTOR.getAllBtn().size(); }

        public String getBtnName(int btnID){
            return SIDEBAR_INTERACTOR.getBtn(btnID).getName();
        }

        public String getBtnIPath(int btnID){
            return SIDEBAR_INTERACTOR.getBtn(btnID).getIconPath();
        }

        public Color getBtnColor(int btnID){

            if (SIDEBAR_INTERACTOR.getBtn(btnID).getSelect()) return Color.DARK_GRAY;

            return Color.WHITE;
        }

        public void onPressed(int btnID){
            SIDEBAR_INTERACTOR.pressedBtn(btnID);
        }

    }
    
}
