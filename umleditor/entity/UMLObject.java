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
            case Statics.UMLOBJECT.ASSOCIATION_LINE:
                return new AssociationLine();
            case Statics.UMLOBJECT.GENERALIZATION_LINE:
                return new GeneralizationLine();
            case Statics.UMLOBJECT.COMPOSITION_LINE:
                return new CompositionLine();
            case Statics.UMLOBJECT.COMPOSITE:
                return new Composite();
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
        protected final ArrayList<Base> PORTS = new ArrayList<>();
        protected final int PORT_SIZE = 10;
        protected ArrayList<Base> compositions;
        protected SelectBehavior selectBehavior = new NormalSelect();

        public int getType() { return type; }
        public Point getSize() { return new Point(width, height); }
        public String getName() { return name; }
        public Point getLocation(){ return location; }
        public Base getPort(int portId) { return PORTS.get(portId); }
        public IDraw getDrawMethod() {
            return (Graphics2D g) -> draw(g);
        }
        public ArrayList<Base> getCompositions() { return compositions; }
        public int getId() { return id; }
        public boolean getSelected() { return selected; }
        
        public void setLocation(int x,int y){
            location.x = x;
            location.y = y;
        }
        public void setSize(int x, int y){
            width = x;
            height = y;
        }
        public void setGroupSelectBehavior() {selectBehavior = new GroupSelect();}
        public void setNormSelectBehavior() {selectBehavior = new NormalSelect();}
        public void setCompositions(ArrayList<Base> comps) { compositions = comps; }
        public void setName(String newName) { name = newName; }

        public void Move(int dx, int dy){
            location.x += dx;
            location.y += dy;
        }

        public void select() { selectBehavior.select(); }
        public void deselect() { selected = false; }
        
        protected void addPorts(){
            PORTS.add(new Port(this, Statics.PORT.TOP));
            PORTS.add(new Port(this, Statics.PORT.LEFT));
            PORTS.add(new Port(this, Statics.PORT.RIGHT));
            PORTS.add(new Port(this, Statics.PORT.BOTTOM));
        }

        abstract public void draw(Graphics2D g);

        
        abstract private class SelectBehavior{
            abstract public void select();
        }
    
        protected class NormalSelect extends SelectBehavior{
            @Override
            public void select() {
                selected = true;
            }
        }

        protected class GroupSelect extends SelectBehavior{
            @Override
            public void select() {}
        }
    }

    abstract public class Shape extends Base{

        Shape(){
            this.height = 25;
            this.width = 100;
            this.type = Statics.UMLOBJECT_TYPE.SHAPE;
        }

        protected final int TEXT_PADDING = 7;

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void draw(Graphics2D g){

            g.setColor(Color.BLACK);

            PORTS.forEach( e-> {
                e.draw(g);
            });
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
            addPorts();
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
            addPorts();
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

    private class Composite extends Shape{

        public Composite(){
            this.id = Statics.UMLOBJECT.COMPOSITE;
        }

        @Override
        public void Move(int dx, int dy) {

            compositions.forEach( e -> { e.Move(dx, dy);});

            super.Move(dx, dy);
        }

        @Override
        public void draw(Graphics2D g) {
            
            if (selected){
                g.setColor(Color.RED);
            }else{
                g.setColor(Color.LIGHT_GRAY);
            }

            g.drawRect(location.x, location.y, getSize().x, getSize().y);
        }
    }

    private class Port extends Shape{

        private final int AREA;
        private final UMLObject.Base CONNECTOR;

        public Port(UMLObject.Base connector, int areaId){
            this.id = areaId;
            this.name = "Port";
            this.width = PORT_SIZE;
            this.height = PORT_SIZE;
            this.AREA = areaId;
            this.CONNECTOR = connector;
        }

        private void updateLocation(){

            switch (AREA) {
                case Statics.PORT.TOP:
                    this.location.x = CONNECTOR.getLocation().x + CONNECTOR.getSize().x/2 - PORT_SIZE/2;
                    this.location.y = CONNECTOR.getLocation().y - PORT_SIZE;
                    break;

                case Statics.PORT.LEFT:
                    this.location.x = CONNECTOR.getLocation().x - PORT_SIZE;
                    this.location.y = CONNECTOR.getLocation().y + CONNECTOR.getSize().y / 2 - PORT_SIZE / 2;
                    break;

                case Statics.PORT.RIGHT:
                    this.location.x = CONNECTOR.getLocation().x + CONNECTOR.getSize().x;
                    this.location.y = CONNECTOR.getLocation().y + CONNECTOR.getSize().y / 2 - PORT_SIZE / 2;
                    break;

                case Statics.PORT.BOTTOM:
                    this.location.x = CONNECTOR.getLocation().x + CONNECTOR.getSize().x/2 - PORT_SIZE/2;
                    this.location.y = CONNECTOR.getLocation().y + CONNECTOR.getSize().y;
                    break;

                default:
                    System.out.println("Warning:\t Get unsupported port at UMLObject.updateLocation");
                    break;
            }

        }

        @Override
        public Point getLocation() {

            switch (AREA) {

                case Statics.PORT.TOP:

                    return new Point(
                        CONNECTOR.getLocation().x + CONNECTOR.getSize().x/2,
                        CONNECTOR.getLocation().y
                    );

                case Statics.PORT.LEFT:

                    return new Point(
                        CONNECTOR.getLocation().x,
                        CONNECTOR.getLocation().y + CONNECTOR.getSize().y / 2
                    );

                case Statics.PORT.RIGHT:

                    return new Point(
                        CONNECTOR.getLocation().x + CONNECTOR.getSize().x,
                        CONNECTOR.getLocation().y + CONNECTOR.getSize().y / 2
                    );

                case Statics.PORT.BOTTOM:

                    return new Point(
                        CONNECTOR.getLocation().x + CONNECTOR.getSize().x/2,
                        CONNECTOR.getLocation().y + CONNECTOR.getSize().y
                    );

                default:
                    System.out.println("Warning:\t Get unsupported port at UMLObject.getLocation");
                    return new Point(0, 0);
            }

        }

        @Override
        public void draw(Graphics2D g) {

            updateLocation();
            
            g.setColor(Color.BLACK);
            if (CONNECTOR.getSelected()){
                g.fillRect(this.location.x, this.location.y, this.width, this.height);
            }

        }

    }

    abstract public class Line extends Base{

        protected static final int ARROW_LENGTH = 20;
        protected Base startPort;
        protected Base endPort;
        protected Point start;
        protected Point end;
        private boolean isSet = false;
        protected int dx;
        protected int dy;
        protected double D;
        protected double sqrt2;
        protected double cos;
        protected double sin;

        public Line(){
            this.height = 0;
            this.width = 0;
            this.type = Statics.UMLOBJECT_TYPE.LINE;
            this.name = "Line";
        }

        public void setConnection(Base start, Base end){
            this.startPort = start;
            this.endPort = end;
            setLocation(startPort.getLocation(), endPort.getLocation());
        }

        public void setLocation(Point start, Point end){

            this.start = start;
            this.end = end;

        }

        public void set() { isSet = true; }

        @Override
        public void draw(Graphics2D g) {

            dx = this.end.x - this.start.x;
            dy = this.end.y - this.start.y;


            D = Math.sqrt(dx * dx + dy * dy);
            sqrt2 = 1 / Math.sqrt(2);
            cos = dx / D;
            sin = dy / D;

            if (isSet) {
                setLocation(
                    startPort.getLocation(),
                    endPort.getLocation()
                );
            }
            
            g.setColor(Color.BLACK);
            g.drawLine(
                start.x,
                start.y,
                end.x - (int)(ARROW_LENGTH * cos * sqrt2),
                end.y - (int)(ARROW_LENGTH * sin * sqrt2)
            );

        }
    }

    private class AssociationLine extends Line{

        @Override
        public void draw(Graphics2D g) {

            super.draw(g);

            int lineX = (int)(ARROW_LENGTH * cos);
            int lineY = (int)(ARROW_LENGTH * sin);
            g.drawLine(this.end.x - lineX, this.end.y - lineY, this.end.x, this.end.y);

            int leftArrowX = (int)(ARROW_LENGTH * (- sqrt2 * cos - sqrt2 * sin));
            int leftArrowY = (int)(ARROW_LENGTH * (- sqrt2 * sin + sqrt2 * cos));
            g.drawLine(this.end.x, this.end.y, this.end.x + leftArrowX, this.end.y + leftArrowY);

            int rightArrowX = (int)(ARROW_LENGTH * (- sqrt2 * cos + sqrt2 * sin));
            int rightArrowY = (int)(ARROW_LENGTH * (- sqrt2 * sin - sqrt2 * cos));
            g.drawLine(this.end.x, this.end.y, this.end.x + rightArrowX, this.end.y + rightArrowY);
        
        }
    }

    private class GeneralizationLine extends Line{

        @Override
        public void draw(Graphics2D g) {

            super.draw(g);

            int leftArrowX = (int)(ARROW_LENGTH * (- sqrt2 * cos - sqrt2 * sin));
            int leftArrowY = (int)(ARROW_LENGTH * (- sqrt2 * sin + sqrt2 * cos));
            g.drawLine(this.end.x, this.end.y, this.end.x + leftArrowX, this.end.y + leftArrowY);

            int rightArrowX = (int)(ARROW_LENGTH * (- sqrt2 * cos + sqrt2 * sin));
            int rightArrowY = (int)(ARROW_LENGTH * (- sqrt2 * sin - sqrt2 * cos));
            g.drawLine(this.end.x, this.end.y, this.end.x + rightArrowX, this.end.y + rightArrowY);

            g.drawLine(this.end.x + leftArrowX, this.end.y + leftArrowY,  this.end.x + rightArrowX, this.end.y + rightArrowY);
        
        }
    }

    private class CompositionLine extends Line{

        @Override
        public void draw(Graphics2D g) {

            super.draw(g);

            int leftTopArrowX = (int)(ARROW_LENGTH / 2 * (- sqrt2 * cos - sqrt2 * sin));
            int leftTopArrowY = (int)(ARROW_LENGTH / 2 * (- sqrt2 * sin + sqrt2 * cos));
            g.drawLine(this.end.x, this.end.y, this.end.x + leftTopArrowX, this.end.y + leftTopArrowY);

            int rightTopArrowX = (int)(ARROW_LENGTH / 2 * (- sqrt2 * cos + sqrt2 * sin));
            int rightTopArrowY = (int)(ARROW_LENGTH / 2 * (- sqrt2 * sin - sqrt2 * cos));
            g.drawLine(this.end.x, this.end.y, this.end.x + rightTopArrowX, this.end.y + rightTopArrowY);

            g.drawLine(this.end.x + leftTopArrowX, this.end.y + leftTopArrowY, this.end.x + leftTopArrowX + rightTopArrowX, this.end.y + leftTopArrowY + rightTopArrowY);
            g.drawLine(this.end.x + rightTopArrowX, this.end.y + rightTopArrowY, this.end.x + leftTopArrowX + rightTopArrowX, this.end.y + leftTopArrowY + rightTopArrowY);
        }
    }
}
