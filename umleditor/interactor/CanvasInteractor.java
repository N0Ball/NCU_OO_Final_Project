package umleditor.interactor;

import java.awt.Point;
import java.util.ArrayList;

import umleditor.Statics;
import umleditor.entity.UMLObject;
import umleditor.interactor.modes.*;

public class CanvasInteractor {

    private Mode currentMode;
    private UMLObject UMLOBJECT = new UMLObject();
    
    public CanvasInteractor(){}

    public UMLObject.Base getObject(int ObjectId){
        return UMLOBJECT.getObject(ObjectId);
    }

    public UMLObject.Base getObject(Object target){
        return UMLOBJECT.getObject(target);
    }

    public ArrayList<UMLObject.Base> getAllObjects(){
        return UMLOBJECT.getAllObjects();
    }

    public UMLObject.Base getCollideObject(Point pt){

        ArrayList<UMLObject.Base> objects = UMLOBJECT.getAllObjects();
        
        for (int i = objects.size() - 1 ; i >= 0; i--){
            if(isCollide(objects.get(i), pt)){
                return objects.get(i);
            }
        }
        return null;
    }

    public int getCollidePort(UMLObject.Base target, Point pt){

        Point normPt = new Point(
            pt.x - target.getLocation().x - target.getSize().x/2, 
            pt.y - target.getLocation().y - target.getSize().y/2
        );

        if( normPt.x + normPt.y > 0){
            if ( normPt.x - normPt.y > 0){
                return Statics.PORT.RIGHT;
            }
            return Statics.PORT.BOTTOM;
        }else{
            if ( normPt.x - normPt.y > 0){
                return Statics.PORT.TOP;
            }
            return Statics.PORT.LEFT;
        }

    }

    public ArrayList<UMLObject.Base> getCollideArea(Point topCorner, Point bottomCorner){
        ArrayList<UMLObject.Base> targets = new ArrayList<>();

        for (UMLObject.Base object: getAllObjects()){
            if (object.getLocation().x > topCorner.x && object.getLocation().x + object.getSize().x < bottomCorner.x){
                if (object.getLocation().y > topCorner.y && object.getLocation().y + object.getSize().y < bottomCorner.y){
                    targets.add(object);
                }
            }
        }

        return targets;
    }

    public void setMode(int btnId){

        switch (btnId) {

            case Statics.BUTTON.SELECT:
                System.out.println("Log:\t Set Mode to <SELECT>");
                currentMode = new SelectMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.SELECT_SQUARE);
                break;

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

            case Statics.BUTTON.ASSOCIATION_LINE:
                System.out.println("Log:\t Set Mode to <LINE>, Target to <ASSOCIATION_LINE>");
                currentMode = new LineMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.ASSOCIATION_LINE);
                break;
                
            case Statics.BUTTON.GENERALIZATION_LINE:
                System.out.println("Log:\t Set Mode to <LINE>, Target to <GENERALIZATION_LINE>");
                currentMode = new LineMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.GENERALIZATION_LINE);
                break;
                
            case Statics.BUTTON.COMPOSITION_LINE:
                System.out.println("Log:\t Set Mode to <LINE>, Target to <COMPOSITION_LINE>");
                currentMode = new LineMode(this);
                currentMode.setTarget(Statics.UMLOBJECT.COMPOSITION_LINE);
                break;

            default:
                System.out.println("Warning:\t Selected a unsupported Mode at Canvasinteractor.setMode !");
                break;
        }

    }

    public void select(Object target){
        for (UMLObject.Base object: getAllObjects()){
            if (object == target){
                object.select();
            }
        }
    }

    public void deselectAll(){
        getAllObjects().forEach(e -> {e.deselect();});
    }

    public UMLObject.Base createObject(){
        UMLObject.Base object = UMLOBJECT.createObject(currentMode.getTarget());
        return object;
    }

    public void deleteObject(Object target){
        try {
            UMLOBJECT.deleteObject(target);
        } catch (Exception e) {
            System.out.println("Error:\t at CanvasInteractor.deleteObject " + e);
        }
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
    
    private boolean isCollide(UMLObject.Base target, Point pt){

        if (target.getType() == Statics.UMLOBJECT_TYPE.LINE){
            return false;
        }

        int X = target.getLocation().x;
        int Y = target.getLocation().y;
        int width = target.getSize().x;
        int height = target.getSize().y;

        if (pt.x > X && pt.x < X + width){
            if (pt.y > Y && pt.y < Y + height){
                return true;
            }
        }

        return false;
        
    }

}
