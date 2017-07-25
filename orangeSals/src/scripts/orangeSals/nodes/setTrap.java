package scripts.orangeSals.nodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.orangeSals.api.AntiBan;
import scripts.orangeSals.api.Node;
import scripts.orangeSals.orangeSals;

public class setTrap extends Node {
    private final Positionable[] TRAP_TILES = {new RSTile(3412,3075,0),
                                               new RSTile(3414,3078,0),
                                               new RSTile(3415,3073,0)};

    private boolean firstRun = true;

    @Override
    public void execute() {

        if (!firstRun) {
            orangeSals.status = "[abc2] Sleeping...";
            AntiBan.sleepReactionTime();
        }
        firstRun = false;
        long startTime = Timing.currentTimeMillis();

        //orangeSals.status = "Sleeping...";
        //General.sleep(800, 4500);

        orangeSals.status = "Setting traps...";

        int i = 0;
        RSObject[] trapList = new RSObject[3];

        //General.println(Objects.getAt(TRAP_TILES[0])[0]);

        if (Objects.isAt(TRAP_TILES[0], 8732)) {
            trapList[i] = Objects.getAt(TRAP_TILES[0])[0];
            i++;
        }
        if (Objects.isAt(TRAP_TILES[1], 8732)) {
            trapList[i] = Objects.getAt(TRAP_TILES[1])[0];
            i++;
        }
        if (Objects.isAt(TRAP_TILES[2], 8732)) {
            trapList[i] = Objects.getAt(TRAP_TILES[2])[0];
        }

        int random = (int)(Math.random() * i + 0);
        RSObject chosenTrap = trapList[random];

        if (chosenTrap != null && Player.getAnimation() == -1) {

            if (!chosenTrap.isOnScreen())
                Camera.turnToTile(chosenTrap);

            if (Clicking.click("Set-trap", chosenTrap)) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100, 250);
                        return Player.getAnimation() == 5215;
                    }
                }, General.random(2500, 3250));

                AntiBan.generateTrackers( (int)(Timing.currentTimeMillis() - startTime),false);
            }
        }
    }

    @Override
    public boolean validate() {
        if (Inventory.getCount(303) > 0 && Inventory.getCount(954) > 0) {
            return (Objects.isAt(TRAP_TILES[0], 8732) || Objects.isAt(TRAP_TILES[1], 8732) || Objects.isAt(TRAP_TILES[2], 8732));
        } else {
            return false;
        }
    }

}