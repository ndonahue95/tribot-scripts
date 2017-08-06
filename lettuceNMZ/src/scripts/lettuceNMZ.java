package scripts;

//import scripts.saladWorks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Skills;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.api.AntiBan;
import scripts.api.framework.Task;
import scripts.api.framework.TaskSet;
import scripts.tasks.drinkOverload;
import scripts.tasks.drinkPrayer;

import java.awt.*;

@ScriptManifest(authors = {"Lettuce"}, category = "saladWorks", name = "lettuceNMZ")

public class lettuceNMZ extends Script implements Painting {
    boolean run = true;
    TaskSet taskSet = new TaskSet();
    long startTime = Timing.currentTimeMillis();

    public static String status = "initializing...";
    public static int _toDrinkAt = General.random(5, 44);
    public static long _nextOverload = 0;

    @Override
    public void run() {
        Mouse.setSpeed(General.random(90, 110));
        General.useAntiBanCompliance(true);
        ThreadSettings.get().setClickingAPIUseDynamic(true);

        General.println("Drinking prayer pot at: " + _toDrinkAt);

        taskSet.addAll(new drinkOverload(), new drinkPrayer());
        while (run) {
            sleep(60);
            Task task = taskSet.getValidTask();
            if (task != null) {
                status = task.toString();
                task.execute();
            } else {
                status = "AntiBan...";
                AntiBan.timedActions();
            }

            // end after runtime!
            if (Timing.currentTimeMillis() - startTime >= 9000000)
                run = false;

        }
    }

    @Override
    public void onPaint(Graphics g) {
        g.setColor(Color.WHITE);
        long timeRan = System.currentTimeMillis() - startTime;
        // long xpPerHr = XP.getTotalGainedXp() * (3600000 / timeRan);


        //g.setFont(font);
        //g.setColor(Color.WHITE);
        g.drawString("lettuceNMZ v0.1", 279, 360);
        g.drawString("Running for: " + Timing.msToString(timeRan), 279, 375);
        g.drawString("Status : " + status, 279, 390);
        //g.drawString("Xp Gained: " + (Skills.SKILLS.HUNTER.getXP() - startXP), 279, 405);
        //g.drawString("Caught: " + _caught, 279, 420);
    }
}