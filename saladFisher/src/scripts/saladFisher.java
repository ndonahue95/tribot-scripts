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

import scripts.tasks.bankLobsters;
import scripts.tasks.fishLobsters;
import scripts.tasks.getGear;

import java.awt.*;

@ScriptManifest(authors = {"Lettuce"}, category = "saladWorks", name = "saladFisher")

public class saladFisher extends Script implements Painting {
    boolean run = true;
    TaskSet taskSet = new TaskSet();
    long startTime = Timing.currentTimeMillis();
    saladWorks guii;

    public static int _caught = 0;
    public static int xp_fishing = Skills.getXP(Skills.SKILLS.FISHING);
    public static String status = "initializing...";

    @Override
    public void run() {
        //guii = new saladWorks();
        //guii.setVisible(true);

        //while (!guii.canStart)
        //    General.sleep(1000);


        Mouse.setSpeed(General.random(90, 110));
        General.useAntiBanCompliance(true);
        ThreadSettings.get().setClickingAPIUseDynamic(true);

        taskSet.addAll(new fishLobsters(), new bankLobsters(), new getGear());
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

            // to track caught fish
            // also in the fishLobsters.java
            // inside fishing Antiban

            if (Skills.getXP(Skills.SKILLS.FISHING) - xp_fishing > 0) {
                xp_fishing = Skills.getXP(Skills.SKILLS.FISHING);
                _caught++;
            }
        }
    }

    @Override
    public void onPaint(Graphics g) {
        g.setColor(Color.BLACK);
        long timeRan = System.currentTimeMillis() - startTime;
        // long xpPerHr = XP.getTotalGainedXp() * (3600000 / timeRan);


        //g.setFont(font);
        //g.setColor(Color.WHITE);
        g.drawString("saladFisher v0.2", 279, 360);
        g.drawString("Running for: " + Timing.msToString(timeRan), 279, 375);
        g.drawString("Status : " + status, 279, 390);
        //g.drawString("Xp Gained: " + (Skills.SKILLS.HUNTER.getXP() - startXP), 279, 405);
        g.drawString("Caught: " + _caught, 279, 420);
    }
}