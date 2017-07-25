package scripts.blastSmither.nodes;


import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.blastSmither.api.Node;
import scripts.blastSmither.blastSmither;

public class plopCoal extends Node {

    @Override
    public void execute() {

        blastSmither.status = "Placing coal...";

        RSObject[] cBelt = Objects.find(50, 9100);
        RSItem[] coalBag = Inventory.find("Coal bag");

        if (Clicking.click("Put-ore-on", cBelt[0])) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100, 250);
                    return !Inventory.isFull();
                }
            }, General.random(6000, 8000));


            if (Clicking.click("Empty", coalBag[0])) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100, 250);
                        return Inventory.isFull();
                    }
                }, General.random(1000, 2000));

                if (Clicking.click("Put-ore-on", cBelt[0])) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100, 250);
                            return !Inventory.isFull();
                        }
                    }, General.random(2500, 3500));

                    blastSmither.coalTrip = false;
                }

            }

         }

    }

    @Override
    public boolean validate() {

        return Inventory.isFull() && blastSmither.coalTrip && !blastSmither.gettingBars;

    }

}
