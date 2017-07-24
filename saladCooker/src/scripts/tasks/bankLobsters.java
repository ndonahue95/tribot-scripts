package scripts.tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import scripts.api.framework.Task;
import scripts.webwalker_logic.WebWalker;

public class bankLobsters implements Task {

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean validate() {
        boolean isFull = Inventory.isFull();
        boolean hasHarpoon = false;

        if (Inventory.getCount("Lobster pot") >= 1)
            hasHarpoon = true;

        return (hasHarpoon && isFull);
    }

    @Override
    public void execute() {
        if (!Banking.isInBank()) {
            if (WebWalker.walkToBank()) {

                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100, 250);
                        return Banking.isInBank();
                    }
                }, General.random(15000, 22000));
            }
        }

        Banking.openBank();
        Banking.depositAllExcept("Lobster pot");
        Banking.close();
    }

    @Override
    public String toString() {
        return "Banking lobsters.";
    }
}