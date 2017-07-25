package scripts.blastSmither.nodes;


import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.blastSmither.api.Node;
import scripts.blastSmither.api.ShiftDrop;
import scripts.blastSmither.blastSmither;

public class grabCoal extends Node {

    @Override
    public void execute() {

        blastSmither.status = "Getting coal...";

        if (!Banking.isBankScreenOpen()) {
            Banking.openBank();
        }

        Banking.withdraw(0, "Coal");
        Banking.close();

        RSItem[] coalBag = Inventory.find("Coal bag");

        if (Clicking.click("Fill", coalBag[0])) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return !Inventory.isFull();
                }
            }, General.random(1000, 2000));

            Banking.openBank();
            Banking.withdraw(0, "Coal");
            Banking.close();

            General.sleep(100,500);
        }

    }

    @Override
    public boolean validate() {

        return !Inventory.isFull() && blastSmither.coalTrip && !blastSmither.gettingBars;

    }

}
