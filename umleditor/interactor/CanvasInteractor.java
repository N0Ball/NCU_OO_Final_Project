package umleditor.interactor;

import java.awt.Point;
import java.util.ArrayList;

import umleditor.Statics;
import umleditor.entity.UMLObject;
import umleditor.interactor.modes.*;

public class CanvasInteractor {

    private Mode currentMode;
    private UMLObject UMLOBJECT = new UMLObject();
    private static final ArrayList<UMLObject.Base> objects = new ArrayList<>();
    
    public CanvasInteractor(){}

    private UMLObject.Base getObject(int ObjectId){
        return UMLOBJECT.getObject(ObjectId);
    }

    public ArrayList<UMLObject.Base> getAllObjects(){
        return objects;
    }

    public void setMode(int btnId){

        switch (btnId) {

            // case Statics.BUTTON.SELECT:
            //     setMode(Statics.MODE.SELECT);
            //     break;

            case Statics.BUTTON.UML_CLASS:
                System.out.println("Log:\t Set Mode to <OBJECT>, Target to <CLASS>");
                currentMode = new ObjectMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.CLASS);
                break;

            case Statics.BUTTON.USE_CASE:
                System.out.println("Log:\t Set Mode to <OBJECT>, Target to <USE_CASE>");
                currentMode = new ObjectMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.USE_CASE);
                break;

            // case Statics.BUTTON.ASSOCIATION_LINE:
            //     setMode(Statics.MODE.LINE);
            //     currentMode.setTarget(Statics.UMLOBJECT.ASSOCIATION_LINE);
            //     break;
        
            // case Statics.BUTTON.COMPOSITION_LINE:
            //     setMode(Statics.MODE.LINE);
            //     currentMode.setTarget(Statics.UMLOBJECT.COMPOSITION_LINE);
            //     break;

            // case Statics.BUTTON.GENERALIZATION_LINE:
            //     setMode(Statics.MODE.LINE);
            //     currentMode.setTarget(Statics.UMLOBJECT.GENERALIZATION_LINE);
            //     break;

            default:
                System.out.println("Warning: Selected a unsupported Mode at Canvasinteractor.setMode !");
                break;
        }

    }

    public UMLObject.Base createObject(){
        UMLObject.Base object = getObject(currentMode.getTarget());
        objects.add(object);
        return object;
    }

    public void onPressed(Point pt){
        currentMode.onPressed(pt);
    }

    public void onDragged(Point pt){
        currentMode.onDragged(pt);
    }

    public void onReleased(Point pt){
        currentMode.onReleased(pt);
    }
    
}
