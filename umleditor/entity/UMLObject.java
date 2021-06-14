package umleditor.entity;

import java.awt.Point;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import umleditor.Statics;
import umleditor.utils.IDraw;

public class UMLObject {

    private static final ArrayList<Base> objects = new ArrayList<>();
    
    public UMLObject.Base createObject(int ObjectId){
        UMLObject.Base new_object = getObject(ObjectId);
        objects.add(new_object);
        return new_object;
    }

    public void deleteObject(Object target){
        objects.remove(target);
    }

    public ArrayList<Base> getAllObjects() { return objects; }

    private Base getObject(int ObjectId){

        switch (ObjectId) {
            case Statics.UMLOBJECT.CLASS:
                return new Class();
            case Statics.UMLOBJECT.USE_CASE:
                return new UseCase();
            case Statics.UMLOBJECT.SELECT_SQUARE:
                return new SelectSquare();
            // case Statics.UMLOBJECT.COMPOSITION_LINE:
            //     return new Class();
            // case Statics.UMLOBJECT.GENERALIZATION_LINE:
            //     return new Class();
            // case Statics.UMLOBJECT.COMPOSITION_LINE:
            //     return new Class();
            default:
                System.out.println("Warning: get null return at UMLObject.getObject");
                return null;
        }
    }

    public Base getObject(Object obj){
        for (UMLObject.Base object: objects){
            if (object == obj){
                return object;
            }
        }

        System.out.println("Warning:\t Get an mull object from UMLObject.getObject(Object obj).");
        return null;
    }

    abstract public class Base{

        protected Point location = new Point();
        protected int height;
        protected int width;
        protected int id;
        protected int type;
        protected String name;
        protected boolean selected = false;

        public int getType() { return type; }
        public Point getSize() { return new Point(width, height); }
        public String getName() { return name; }
        public Point getLocation(){ return location; }
        public IDraw getDrawMethod() {
            return (Graphics2D g) -> draw(g);
        }
        public boolean getSelected() { return selected; }
        
        public void setLocation(int x,int y){
            location.x = x;
            location.y = y;
        }
        public void setSize(int x, int y){
            width = x;
            height = y;
        }
        public void Move(int dx, int dy){
            location.x += dx;
            location.y += dy;
        }

        public void select() { selected = true; }
        public void deselect() { selected = false; }

        abstract public void draw(Graphics2D g);
    }

    abstract public class Shape extends Base{

        Shape(){
            this.height = 25;
            this.width = 100;
            this.type = Statics.UMLOBJECT_TYPE.SHAPE;
        }

        protected final int TEXT_PADDING = 7;
        protected final int PORT_SIZE = 10;

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void draw(Graphics2D g){

            g.setColor(Color.BLACK);

            if (selected){
                g.fillRect(this.location.x + this.width / 2, this.location.y - PORT_SIZE, PORT_SIZE, PORT_SIZE);
                g.fillRect(this.location.x - PORT_SIZE, this.location.y + this.height / 2 - PORT_SIZE / 2, PORT_SIZE, PORT_SIZE);
                g.fillRect(this.location.x + this.width, this.location.y + this.height / 2 - PORT_SIZE / 2, PORT_SIZE, PORT_SIZE);
                g.fillRect(this.location.x + this.width / 2, this.location.y + this.height, PORT_SIZE, PORT_SIZE);
            }
        }

    }

    private class Class extends Shape{

        private final int TITLE_LENGTH = 1;
        private final int FIELD_LENGTH = 1;
        private final int METHOD_LENGTH = 1;
        private final int TOTLE_LENGTH = TITLE_LENGTH + FIELD_LENGTH + METHOD_LENGTH;
        
        public Class(){
            this.id = Statics.UMLOBJECT.CLASS;
            this.name = "New Class";
            this.height = TOTLE_LENGTH * this.height;
        }

        @Override
        public void draw(Graphics2D g) {

            super.draw(g);
            
            g.drawRect(this.location.x, this.location.y, this.width, this.height);
            g.drawLine(this.location.x, this.location.y + TITLE_LENGTH*this.height/TOTLE_LENGTH, this.location.x + this.width, this.location.y + TITLE_LENGTH*this.height/TOTLE_LENGTH);
            g.drawLine(this.location.x, this.location.y + (TITLE_LENGTH + FIELD_LENGTH)*this.height/TOTLE_LENGTH, this.location.x + this.width, this.location.y + (TITLE_LENGTH + FIELD_LENGTH)*this.height/TOTLE_LENGTH);

            g.setFont(new Font("default", Font.BOLD, 16));
            g.drawString(this.name, this.location.x + TEXT_PADDING, this.location.y + this.height / TOTLE_LENGTH / 2 + TEXT_PADDING);
        }
    }

    private class UseCase extends Shape{
        public UseCase(){
            this.id = Statics.UMLOBJECT.USE_CASE;
            this.name = "New Use Case";
            this.width *= 1.5;
            this.height *= 2;
        }

        @Override
        public void draw(Graphics2D g) {
            
            super.draw(g);

            g.drawArc(this.location.x, this.location.y, this.width, this.height, 0, 360);

            g.setFont(new Font("default", Font.BOLD, 16));
            g.drawString(this.name, this.location.x + TEXT_PADDING, this.location.y + this.height / 2 + TEXT_PADDING);
        }
    }

    private class SelectSquare extends Shape{
        public SelectSquare(){
            this.id = Statics.UMLOBJECT.SELECT_SQUARE;
            this.name = "Select Square";
            this.width = 0;
            this.height = 0;
        }

        @Override
        public void draw(Graphics2D g) {
            
            g.setColor(new Color(200, 200, 200, 30));
            g.fillRect(this.location.x, this.location.y, this.width, this.height);
            g.setColor(Color.BLACK);
            g.drawRect(this.location.x, this.location.y, this.width, this.height);

        }
    }

    abstract public class Line extends Base{

        public Line(){
            this.height = 0;
            this.width = 0;
            this.type = Statics.UMLOBJECT_TYPE.LINE;
            this.name = "Line";
        }

        protected Shape startShape;
        protected int startPort;
        protected Shape endShape;
        protected int endPort;
    }
}
