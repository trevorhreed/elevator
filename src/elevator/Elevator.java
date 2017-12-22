package elevator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;


public class Elevator implements ActionListener {
    
    public enum DoorState { OPEN, CLOSED }
    public enum Direction { UP, DOWN, NONE }
    public enum Command { MOVE_UP, MOVE_DOWN, OPEN_DOORS, CLOSE_DOORS, NONE }
        
    private Comparator byFloor;
    private Comparator byFloorReversed;
    private Comparator byDirection;
    private Comparator byDirectionReversed;
    
    private int floorCount;
    
    private int currentFloor;
    private Direction currentDirection;
    private DoorState doorState;
    private boolean isMoving;
    
    private List<Request> requests;
    private List<Command> commands;
    
    private List<ElevatorListener> listeners;
    
    private Timer timer;
    
    public Elevator(int pFloorCount, int delay) {
        if(pFloorCount < 1) {
            throw new IllegalArgumentException("Floor count must be positive integer.");
        }
        if(pFloorCount > 10) {
            throw new IllegalArgumentException("Too many floors. Building codes permit at most 10 floors.");
        }
        
        byFloor = new SortByFloor();
        byFloorReversed = Collections.reverseOrder(byFloor);
        byDirection = new SortByDirection();
        byDirectionReversed = Collections.reverseOrder(byDirection);
        
        this.floorCount = pFloorCount;
        this.currentFloor = 1;
        this.currentDirection = Direction.UP;
        this.doorState = DoorState.OPEN;
        this.isMoving = false;
        this.requests = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.timer = new Timer(delay, this);
        this.timer.start();
    }
    
    public int getFloor(){
        return this.currentFloor;
    }
    
    public Direction getDirection(){
        return this.currentDirection;
    }
    
    public DoorState getDoorState(){
        return this.doorState;
    }
    
    public Map<Integer, List<Direction>> getRequests() {
        Map<Integer, List<Direction>> floors = new HashMap<>();
        for(int i=1; i <= floorCount; i++){
            floors.put(i, new ArrayList<>());
        }
        requests.forEach((req) -> {
            int key = req.getFloor();
            floors.get(key).add(req.getDirection());
        });
        return floors;
    }
    
    public void requestALift(int floor, Direction direction){
        if(floor < 1 || floor > floorCount){
            throw new IllegalArgumentException("Invalid floor. Must be betweeen 1 - " + floorCount + ".");
        }
        
        Request newRequest = new Request(floor, direction);
        if(!requests.contains(newRequest)){
            requests.add(newRequest);
        }
    }
    
    public void selectFloor(int floor){
        if(floor < 1 || floor > floorCount){
            throw new IllegalArgumentException("Invalid floor. Must be betweeen 1 - " + floorCount + ".");
        }
        
        Request newRequest = new Request(floor, Direction.NONE);
        if(!requests.contains(newRequest)){
            requests.add(newRequest);
        }
    }
    
    public void openDoors(){
        Command cmd = Command.NONE;
        if(!commands.isEmpty()) cmd = commands.get(0);
        if(isMoving || doorState == DoorState.OPEN || cmd == Command.OPEN_DOORS) return;
        commands.add(0, Command.OPEN_DOORS);
    }
    
    public void closeDoors(){
        Command cmd = Command.NONE;
        if(!commands.isEmpty()) cmd = commands.get(0);
        if(doorState != DoorState.OPEN || cmd == Command.CLOSE_DOORS) return;
        commands.add(0, Command.CLOSE_DOORS);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(requests.size() > 0 || commands.size() > 0){
            process();
        }
    }
    
    public void addListener(ElevatorListener listener){
        listeners.add(listener);
    }
    
    public void triggerListeners(Command cmd){
        listeners.forEach((listener) -> {
            listener.onElevatorUpdate(cmd);
        });
    }
    
    private void process(){
        Command pending = null;
        if(!commands.isEmpty()){
            pending = commands.remove(0);
            switch(pending){
                case MOVE_UP:
                    currentDirection = Direction.UP;
                    if(currentFloor < floorCount) currentFloor++;
                    break;
                case MOVE_DOWN:
                    currentDirection = Direction.DOWN;
                    if(currentFloor > 1) currentFloor--;
                    break;
                case OPEN_DOORS:
                    doorState = DoorState.OPEN;
                    break;
                case CLOSE_DOORS:
                    doorState = DoorState.CLOSED;
                    break;
                default:
                    break;
            }
        }
        
        boolean isRequestForCurrentFloor = false;
        Request requestForCurrentFloor = null;
        for(Request req : requests){
            if(req.getFloor() == currentFloor && 
                (
                    req.getDirection() == Direction.NONE ||
                    req.getDirection() == currentDirection ||
                    isLastRequestInCurrentDirection(req)
                )
                ){
                requestForCurrentFloor = req;
                break;
            }else if(req.getAcknowledged()){
                req.setAcknowledged(false);
            }
        }
        if(requestForCurrentFloor != null){
            isRequestForCurrentFloor = true;
            requests.remove(requestForCurrentFloor);
        }
        
        if(isRequestForCurrentFloor){
            commands.add(0, Command.OPEN_DOORS);
        }
        
        if(!requests.isEmpty()){
            Command nextMove = null;
            for(Command cmd : commands){
                if(cmd == Command.MOVE_UP || cmd == Command.MOVE_DOWN){
                    nextMove = cmd;
                    break;
                }
            }
            if(nextMove == null){
                if(currentDirection == Direction.DOWN){
                    Collections.sort(requests, byDirectionReversed);
                }
                else{
                    Collections.sort(requests, byDirection);
                }
                Request req = requests.get(0);
                currentDirection = getDirectionOfFloor(req.getFloor());
            }
        }
        
        for(Request req : requests){
            if(req.getFloor() == 2){
                boolean isLast = isLastRequestInCurrentDirection(req);    
            }
            if(req.getAcknowledged() || 
                (   req.getDirection() != Direction.NONE &&
                    req.getDirection() != currentDirection &&
                    !isLastRequestInCurrentDirection(req)
                ) ||
                (currentDirection == Direction.UP && req.getFloor() <= currentFloor) ||
                (currentDirection == Direction.DOWN && req.getFloor() >= currentFloor)
            ) continue;
            req.acknowledge();
            int distance = currentFloor - req.getFloor();
            if(distance == 0){
                commands.add(0, Command.OPEN_DOORS);
            }else{
                Command relevant = distance < 0 ? Command.MOVE_UP : Command.MOVE_DOWN;
                int moveCommands = 0;
                for(Command cmd : commands){
                    if(cmd == relevant) moveCommands++;
                }
                int neededMoveCommands = Math.abs(distance) - moveCommands;
                for(int i=0; i < neededMoveCommands; i++){
                    commands.add(relevant);
                }
            }
            
        }
        
        if(commands.isEmpty()){
            isMoving = false;   
        }else{
            Command next = commands.get(0);
            isMoving = (next == Command.MOVE_UP || next == Command.MOVE_DOWN);
            if(isMoving && doorState == DoorState.OPEN){
                isMoving = false;
                commands.add(0, Command.CLOSE_DOORS);
            }
        }
        
        triggerListeners(pending);
    }
    
    private Direction getDirectionOfFloor(int floor){
        if(currentFloor == floor) return Direction.NONE;
        return (currentFloor - floor) > 0 ? Direction.DOWN : Direction.UP;
    }
    
    private boolean isLastRequestInCurrentDirection(Request a){
        for(Request i : requests){
            if(isACloserThanB(a.getFloor(), i.getFloor())) return false;
        }
        return true;
    }
    private boolean isACloserThanB(int a, int b){
        if(currentDirection == Direction.NONE){
            throw new IllegalArgumentException("Cannot compare distance with a direction of 'NONE'.");
        }
        return currentDirection == Direction.UP ? a < b : a > b;
    }
    
    public class Request {

        private boolean acknowledged;
        private final int floor;
        private final Direction direction;
        
        public Request(int floor, Direction direction){
            this.floor = floor;
            this.direction = direction;
            this.acknowledged = false;
        }
        
        public boolean getAcknowledged(){
            return this.acknowledged;
        }
        
        public void setAcknowledged(boolean acknowledged){
            this.acknowledged = acknowledged;
        }
        
        public void acknowledge(){
            this.acknowledged = true;
        }
        
        public int getFloor(){
            return this.floor;
        }
        
        public Direction getDirection(){
            return this.direction;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Request other = (Request) obj;
            if (this.floor != other.floor) {
                return false;
            }
            if (this.direction != other.direction) {
                return false;
            }
            return true;
        }
    }
    
    class SortByFloor implements Comparator<Request> {
        @Override
        public int compare(Request a, Request b){
            return Integer.compare(a.getFloor(), b.getFloor());
        }
    }
    
    class SortByDirection implements Comparator<Request> {
        @Override
        public int compare(Request a, Request b){
            if(a.getDirection() == b.getDirection()) return 0;
            else if(a.getDirection() == Direction.UP) return 1;
            else return -1;
        }
    }
    
    public interface ElevatorListener {
        public void onElevatorUpdate(Command cmd);
    }
}
