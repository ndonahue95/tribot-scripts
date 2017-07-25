package scripts.tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSGroundItem;
import scripts.api.framework.Task;
import scripts.webwalker_logic.WebWalker;

public class getGear implements Task {

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public boolean validate() {
        boolean hasHarpoon = false;

        if (Inventory.getCount("Lobster pot") >= 1)
            hasHarpoon = true;

        return (!hasHarpoon);
    }

    @Override
    public void execute() {
        RSGroundItem[] detectGear = GroundItems.find("Lobster pot");

        if (detectGear.length == 0) {

            if (!Banking.isInBank()) {
                WebWalker.walkToBank();

                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100, 250);
                        return Banking.isInBank();
                    }
                }, General.random(15000, 22000));
            }

            Banking.openBank();
            Banking.withdraw(1, "Lobster pot");
            Banking.close();

        } else {
            // there is gear somewhere on ground
        }
    }

    @Override
    public String toString() {
        return "Getting gear.";
    }
}