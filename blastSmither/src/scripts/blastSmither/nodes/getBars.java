package scripts.blastSmither.nodes;


import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import scripts.blastSmither.api.AntiBan;
import scripts.blastSmither.api.Node;
import scripts.blastSmither.blastSmither;

public class getBars extends Node {

    @Override
    public void execute() {

        blastSmither.status = "Getting bars...";

        RSObject[] bDisp = Objects.find(20, "Bar dispenser");

        Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(100, 250);
                AntiBan.timedActions();
                return blastSmither.lastBlastXP != Skills.SKILLS.SMITHING.getXP();
            }
        }, General.random(10000, 15000));

        if (blastSmither.lastBlastXP != Skills.SKILLS.SMITHING.getXP()) {

            if (Clicking.click("Take", bDisp[0].getModel())) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100, 250);
                        return Interfaces.isInterfaceValid(28);
                    }
                }, General.random(5000, 8000));

                if (Clicking.click(Interfaces.get(28, 111))) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100, 250);
                            return Inventory.isFull();
                        }
                    }, General.random(2000, 3500));

                    if (Clicking.click(Interfaces.get(28, 118))) {

                        General.sleep(200,1000);
                        blastSmither.deppingBars = true;

                    }

                }


            }
        }

    }

    @Override
    public boolean validate() {

        return blastSmither.gettingBars && !Inventory.isFull();

    }

}
