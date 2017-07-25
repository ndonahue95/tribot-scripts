package scripts.blastSmither;

// paint imports

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Skills;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.blastSmither.api.AntiBan;
import scripts.blastSmither.api.Node;
import scripts.blastSmither.nodes.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest (authors = {"Lettuce"}, category = "saladWorks", name = "blastSmither")

public class blastSmither extends Script implements Painting {

    public static ArrayList<Node> nodes = new ArrayList<>();

    public static ABCUtil abc = new ABCUtil();
    public static String status = "Initializing...";
    //public static PersistentABCUtil abc2 = new PersistentABCUtil();
    public static int eatAt = abc.generateEatAtHP();
    long startTime = System.currentTimeMillis();
    int startXP = Skills.SKILLS.HUNTER.getXP();

    public static boolean coalTrip = true;
    public static boolean gettingBars = false;
    public static boolean deppingBars = false;
    public static int lastBlastXP = 0;

    @Override
    public void run() {
        Mouse.setSpeed(General.random(90, 110));
        General.useAntiBanCompliance(true);
        ThreadSettings.get().setClickingAPIUseDynamic(true);


        Collections.addAll(nodes, new grabCoal(), new plopCoal(), new grabMith(), new plopMith(), new getBars(), new depBars());
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
        g.drawString("blastSmither v0.1", 279, 360);
        g.drawString("Running for: " + Timing.msToString(timeRan), 279, 375);
        g.drawString("Status : " + status, 279, 390);
        //g.drawString("Xp Gained: " + (Skills.SKILLS.HUNTER.getXP() - startXP), 279, 405);
        //g.drawString("Profit: " + profit + " (" + profitPerHr + "/hr)", 279, 420);
    }

}
