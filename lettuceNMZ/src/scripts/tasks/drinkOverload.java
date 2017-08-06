package scripts.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import scripts.api.framework.Task;
import scripts.lettuceNMZ;

public class drinkOverload implements Task {

    private String[] _OVERPOTS = { "Overload (1)", "Overload (2)", "Overload (3)", "Overload (4)" };

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean validate() {
       return Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS) > 50
               && Skills.getCurrentLevel(Skills.SKILLS.ATTACK) <= Skills.getActualLevel(Skills.SKILLS.ATTACK)
               && Inventory.find(_OVERPOTS).length > 0
               && lettuceNMZ._nextOverload <= System.currentTimeMillis();
    }

    @Override
    public void execute() {

        if (Inventory.find(_OVERPOTS).length > 0) {

            RSItem[] _potions = Inventory.find(_OVERPOTS);

            if (Clicking.click(_potions[0])) {

                long randomize = 300000 + General.random(5000, 100000);
                lettuceNMZ._nextOverload = System.currentTimeMillis() + randomize;
                General.println("Took overload. Next overload in: " + randomize / 1000 + " seconds.");
                //General.sleep(5000,10000);

            }


        }

    }

    @Override
    public String toString() {
        return "Drinking overload.";
    }
}