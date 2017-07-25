package scripts.orangeSals.nodes;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.orangeSals.api.Node;
import scripts.orangeSals.orangeSals;

public class pickItems extends Node {

    @Override
    public void execute() {
        orangeSals.status = "Sleeping...";

        General.sleep(400, 2000);

        orangeSals.status = "Picking up items...";

        RSGroundItem[] trapItem = GroundItems.findNearest(954);
        if (trapItem.length > 0) {

            int oldLength = Inventory.getAll().length;

            if (!trapItem[0].isOnScreen())
                Camera.turnToTile(trapItem[0]);

            if (Clicking.click("Take", trapItem[0].getModel())) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100, 250);
                        return Inventory.getAll().length != oldLength;
                    }
                }, General.random(5000, 7000));
            }
        }

        RSGroundItem[] trapItem2 = GroundItems.findNearest(303);
        if (trapItem2.length > 0) {

            int oldLength = Inventory.getAll().length;

            if (!trapItem2[0].isOnScreen())
                Camera.turnToTile(trapItem2[0]);

            General.sleep(200,800);

            if (Clicking.click("Take", trapItem2[0].getModel())) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100, 250);
                        return Inventory.getAll().length != oldLength;
                    }
                }, General.random(5000, 7000));
            }
        }
    }

    @Override
    public boolean validate() {
        return GroundItems.find(954, 303).length > 0;
    }

}
