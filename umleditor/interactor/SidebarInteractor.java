package umleditor.interactor;

import umleditor.entity.Button;

import java.util.ArrayList;

public class SidebarInteractor {

    private static final Button BUTTON = new Button();
    
    public SidebarInteractor(){}

    public Button.Base getBtn(int btnID){ return BUTTON.getBtn(btnID); }

    public ArrayList<Button.Base> getAllBtn(){ return BUTTON.getAll(); }

    public void pressedBtn(int BtnID){

        for (Button.Base btn: getAllBtn()){
            
            if (btn.getId() == BtnID){
                btn.select();
            }else{
                btn.deselect();
            }
        }
    }
}
