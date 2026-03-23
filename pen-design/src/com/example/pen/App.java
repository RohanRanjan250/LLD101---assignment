package com.example.pen;

/**
 * Demo application showcasing the three pen types,
 * their writing behaviour, and refill mechanics.
 */
public class App {
    public static void main(String[] args) {

        // --- Ball-Point Pen ---
        System.out.println("=== BallPoint Pen ===");
        Refill blueCart = new Refill(InkColor.BLUE, 20);
        Pen ballPen = new BallPointPen(blueCart);

        ballPen.write("test"); // pen is closed — should warn
        ballPen.start();
        ballPen.write("Hello World"); // 11 ink consumed
        ballPen.write("More text"); // 9 ink → depletes
        ballPen.write("nothing"); // empty — should warn

        // swap in a new cartridge
        ballPen.refill(new Refill(InkColor.BLACK, 20));
        ballPen.write("Back in action");
        ballPen.close();

        // --- Fountain Pen ---
        System.out.println("\n=== Fountain Pen ===");
        Refill blackRes = new Refill(InkColor.BLACK, 30);
        Pen fountainPen = new FountainPen(blackRes);

        fountainPen.start();
        fountainPen.write("Elegant"); // 7 × 2 = 14 ink
        fountainPen.write("Writing"); // 7 × 2 = 14 ink
        fountainPen.write("More"); // 4 × 2 = 8 needed, only 2 left

        // top up from ink bottle
        fountainPen.refill(null);
        fountainPen.write("Refilled");
        fountainPen.close();

        // --- Marker Pen ---
        System.out.println("\n=== Marker Pen ===");
        Refill redInk = new Refill(InkColor.RED, 30);
        Pen markerPen = new MarkerPen(redInk);

        markerPen.start();
        markerPen.write("BOLD"); // 4 × 3 = 12 ink
        markerPen.write("HEADING"); // 7 × 3 = 21 needed, only 18 left

        // marker refill should fail
        try {
            markerPen.refill(new Refill(InkColor.RED, 30));
        } catch (UnsupportedOperationException ex) {
            System.out.println("[Marker] Refill failed: " + ex.getMessage());
        }

        markerPen.close();
    }
}
