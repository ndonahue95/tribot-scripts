package scripts.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.api.AntiBan;
import scripts.api.framework.Task;
import scripts.saladFisher;
import scripts.webwalker_logic.WebWalker;

public class fishLobsters implements Task {

    public final RSTile FISHING_TILE = new RSTile(2840, 3433, 0);
    public final int FISHING_ID = 1519;
    public final RSArea fishSpot1 = new RSArea(new RSTile(2833, 3436), new RSTile(2848, 3428));
    public final RSArea fishSpot2 = new RSArea(new RSTile(2849, 3431), new RSTile(2862, 3420));

    public long lastABTime = 0;

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public boolean validate() {
        boolean isFull = Inventory.isFull();
        boolean hasHarpoon = false;

        if (Inventory.getCount("Lobster pot") >= 1)
            hasHarpoon = true;

        return (hasHarpoon && !isFull && (Player.getRSPlayer().getInteractingCharacter() == null
                || Player.getRSPlayer().getInteractingCharacter() != null && Player.getAnimation() == -1));
    }

    @Override
    public void execute() {
        if (Player.getAnimation() == -1) {
            if (Banking.isInBank()) {
                if (WebWalker.walkTo(fishSpot1.getRandomTile())) {

                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100, 250);
                            saladFisher.status = "Walking from bank.";
                            return NPCs.findNearest(FISHING_ID).length > 0;
                        }
                    }, General.random(15000, 22000));

                }
            }

            RSNPC[] fishSpots = NPCs.find(FISHING_ID);

            if (fishSpots.length > 0) {
                RSNPC toFishAt = AntiBan.selectNextTarget(fishSpots);

                if (toFishAt.isClickable()) {

                    if (lastABTime != 0) {
                        AntiBan.sleepReactionTime();
                    }

                    if (Clicking.click("Cage", toFishAt)) {

                        lastABTime = Timing.currentTimeMillis();

                        General.sleep(2000, 3000);

                        Timing.waitCondition(new Condition() {
                            @Override
                            public boolean active() {
                                General.sleep(100, 250);
                                saladFisher.status = "Fishing antiban.";
                                AntiBan.timedActions();
                                return (Player.getRSPlayer().getInteractingCharacter() == null
                                        || Player.getRSPlayer().getInteractingCharacter() != null && Player.getAnimation() == -1);
                            }
                        }, General.random(100000, 200000));

                    }

                } else {
                    Camera.turnToTile(toFishAt.getPosition());

                    if (WebWalker.walkTo(new RSTile(toFishAt.getPosition().getX(),toFishAt.getPosition().getY() + 1))) {

                        Timing.waitCondition(new Condition() {
                            @Override
                            public boolean active() {
                                General.sleep(100, 250);
                                saladFisher.status = "walking to spot.";
                                return toFishAt.isClickable();
                            }
                        }, General.random(5000, 8000));

                        if (lastABTime != 0) {
                            AntiBan.sleepReactionTime();
                        }

                        if (Clicking.click("Cage", toFishAt)) {

                            lastABTime = Timing.currentTimeMillis();

                            General.sleep(2000, 3000);

                            Timing.waitCondition(new Condition() {
                                @Override
                                public boolean active() {
                                    General.sleep(100, 250);
                                    saladFisher.status = "Fishing antiban.";
                                    AntiBan.timedActions();
                                    return (Player.getRSPlayer().getInteractingCharacter() == null
                                            || Player.getRSPlayer().getInteractingCharacter() != null && Player.getAnimation() == -1);
                                }
                            }, General.random(100000, 200000));

                            AntiBan.generateTrackers((int) (Timing.currentTimeMillis() - lastABTime), false);
                        }
                    }
                }


            } else {

                if (fishSpot1.contains(Player.getPosition())) {
                    WebWalking.walkTo(fishSpot2.getRandomTile());

                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100, 250);
                            return fishSpot2.contains(Player.getPosition());
                        }
                    }, General.random(15000, 22000));
                } else {
                    WebWalking.walkTo(fishSpot1.getRandomTile());

                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100, 250);
                            return fishSpot1.contains(Player.getPosition());
                        }
                    }, General.random(15000, 22000));
                }
            }

        }

    }

    @Override
    public String toString() {
        return "Fishing lobsters.";
    }
}