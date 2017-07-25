package scripts.orangeSals.nodes;


import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import scripts.orangeSals.api.Node;
import scripts.orangeSals.api.ShiftDrop;
import scripts.orangeSals.orangeSals;

public class dropSals extends Node {

    int dropThresh = General.random(1, 4);

    @Override
    public void execute() {

        orangeSals.status = "Dropping...";

        ShiftDrop.shiftDrop(10146);

        dropThresh = General.random(1, 4);

    }

    @Override
    public boolean validate() {
        return Inventory.getCount(10146) >= dropThresh;
    }

}
