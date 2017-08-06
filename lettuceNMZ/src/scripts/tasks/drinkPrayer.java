package scripts.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import scripts.api.AntiBan;
import scripts.api.framework.Task;
import scripts.lettuceNMZ;

import static java.lang.Math.toIntExact;

public class drinkPrayer implements Task {

    private String[] _PRAYPOTS = { "Prayer potion(1)", "Prayer potion(2)", "Prayer potion(3)", "Prayer potion(4)" };

    private long _lastClick = 0;

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public boolean validate() {
       return Skills.getCurrentLevel(Skills.SKILLS.PRAYER) <= lettuceNMZ._toDrinkAt
               && Inventory.find(_PRAYPOTS).length > 0;
    }

    @Override
    public void execute() {

        if (Inventory.find(_PRAYPOTS).length > 0) {

            RSItem[] _potions = Inventory.find(_PRAYPOTS);

            if (Clicking.click(_potions[0])) {

                if (_lastClick != 0) {
                    AntiBan.generateTrackers(toIntExact(System.currentTimeMillis() - _lastClick), false);
                }

                _lastClick = System.currentTimeMillis();

                General.sleep(2000,5000);

            }


        }

        lettuceNMZ._toDrinkAt = General.random(5, 44);
        General.println("Drinking prayer pot at: " + lettuceNMZ._toDrinkAt);

    }

    @Override
    public String toString() {
        return "Drinking prayer.";
    }
}