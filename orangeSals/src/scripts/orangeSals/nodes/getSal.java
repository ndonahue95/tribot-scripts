package scripts.orangeSals.nodes;


import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;
import scripts.orangeSals.api.AntiBan;
import scripts.orangeSals.api.Node;
import scripts.orangeSals.api.ShiftDrop;
import scripts.orangeSals.orangeSals;

public class getSal extends Node {

    private boolean firstRun = true;

    @Override
    public void execute() {

        if (!firstRun) {
            orangeSals.status = "[abc2] Sleeping...";
            AntiBan.sleepReactionTime();
        }
        firstRun = false;
        long startTime = Timing.currentTimeMillis();

        orangeSals.status = "Claiming traps...";

        RSObject[] finishedTrap = Objects.findNearest(10, 8734);

        if (finishedTrap.length > 0) {

            int oldLength = Inventory.getAll().length;

            if (!finishedTrap[0].isOnScreen())
                Camera.turnToTile(finishedTrap[0]);

            Clicking.click("Check", finishedTrap);
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return Inventory.getAll().length != oldLength;
                }
            }, General.random(2500, 3250));

            AntiBan.generateTrackers( (int)(Timing.currentTimeMillis() - startTime),false);
        }

    }

    @Override
    public boolean validate() {
        return Objects.findNearest(10, 8734).length > 0;
    }

}
