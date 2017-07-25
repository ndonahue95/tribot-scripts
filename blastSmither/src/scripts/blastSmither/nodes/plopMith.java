package scripts.blastSmither.nodes;


import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.blastSmither.api.Node;
import scripts.blastSmither.blastSmither;

public class plopMith extends Node {

    @Override
    public void execute() {

        blastSmither.status = "Placing mithril...";

        RSObject[] cBelt = Objects.find(20, 9100);

        if (Clicking.click("Put-ore-on", cBelt[0])) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return !Inventory.isFull();
                }
            }, General.random(6000, 8000));


            blastSmither.lastBlastXP = Skills.SKILLS.SMITHING.getXP();
            blastSmither.gettingBars = true;
         }

    }

    @Override
    public boolean validate() {

        return Inventory.isFull() && !blastSmither.coalTrip && !blastSmither.gettingBars;

    }

}
