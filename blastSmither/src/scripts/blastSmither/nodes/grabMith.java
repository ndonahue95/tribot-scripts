package scripts.blastSmither.nodes;


import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.blastSmither.api.Node;
import scripts.blastSmither.blastSmither;

public class grabMith extends Node {

    @Override
    public void execute() {

        blastSmither.status = "Getting mithril...";

        Banking.openBank();
        Banking.withdraw(0, "Mithril ore");
        Banking.close();


    }

    @Override
    public boolean validate() {

        return !Inventory.isFull() && !blastSmither.coalTrip && !blastSmither.gettingBars;

    }

}
