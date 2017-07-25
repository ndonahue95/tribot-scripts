package scripts.orangeSals;

// paint imports
import java.awt.Graphics; //paint

import org.tribot.api.Timing; //to calculate time things
import org.tribot.api2007.Skills; //to get XP/levels
import org.tribot.script.interfaces.Painting; //for onPaint()

import java.util.ArrayList;
import java.util.Collections;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

import scripts.orangeSals.api.Node;
import scripts.orangeSals.api.AntiBan;
import scripts.orangeSals.nodes.dropSals;
import scripts.orangeSals.nodes.getSal;
import scripts.orangeSals.nodes.pickItems;
import scripts.orangeSals.nodes.setTrap;

@ScriptManifest (authors = {"Lettuce"}, category = "saladWorks", name = "orangeSals")

public class orangeSals extends Script implements Painting {

    public static ArrayList<Node> nodes = new ArrayList<>();

    public static ABCUtil abc = new ABCUtil();
    public static String status = "Initializing...";
    //public static PersistentABCUtil abc2 = new PersistentABCUtil();
    public static int eatAt = abc.generateEatAtHP();
    long startTime = System.currentTimeMillis();
    int startXP = Skills.SKILLS.HUNTER.getXP();

    @Override
    public void run() {
        Mouse.setSpeed(General.random(90, 110));
        General.useAntiBanCompliance(true);
        ThreadSettings.get().setClickingAPIUseDynamic(true);


        Collections.addAll(nodes, new pickItems(), new setTrap(), new dropSals(), new getSal());
        loop(20, 40);
    }

    private void loop(int min, int max) {
        while (true) {
            for (final Node node : nodes) {
                if (node.validate()) {
                    node.execute();
                    General.sleep(General.random(min, max));	//time in between executing nodes
                } else {
                    status = "AntiBan...";
                    AntiBan.timedActions();
                }
            }
        }
    }

    @Override
    public void onPaint(Graphics g) {
        long timeRan = System.currentTimeMillis() - startTime;
       // long xpPerHr = XP.getTotalGainedXp() * (3600000 / timeRan);


        //g.setFont(font);
        //g.setColor(Color.WHITE);
        g.drawString("orangeSals v0.5", 279, 360);
        g.drawString("Running for: " + Timing.msToString(timeRan), 279, 375);
        g.drawString("Status : " + status, 279, 390);
        g.drawString("Xp Gained: " + (Skills.SKILLS.HUNTER.getXP() - startXP), 279, 405);
        //g.drawString("Profit: " + profit + " (" + profitPerHr + "/hr)", 279, 420);
    }

}
