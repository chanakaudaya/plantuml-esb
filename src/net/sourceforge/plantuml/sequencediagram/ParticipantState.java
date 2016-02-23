package net.sourceforge.plantuml.sequencediagram;

/**
 * Maintains the last participant that was activated
 */
public class ParticipantState {

    private static String lastParticipant = null;

    public static String getLastParticipant() {
        return lastParticipant;
    }

    public static void setLastParticipant(String lastp){
        lastParticipant = lastp;
    }
}
