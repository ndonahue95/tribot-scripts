package scripts.blastSmither.nodes;


import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import scripts.blastSmither.api.Node;
import scripts.blastSmither.blastSmither;

public class depBars extends Node {

    @Override
    public void execute() {

        blastSmither.status = "Depositing bars...";

        Banking.openBank();
        Banking.deposit(0, "Mithril bar");

        blastSmither.coalTrip = true;
        blastSmither.gettingBars = false;
        blastSmither.deppingBars = false;

    }

    @Override
    public boolean validate() {

        return blastSmither.gettingBars && Inventory.isFull() && blastSmither.deppingBars;

    }

}
