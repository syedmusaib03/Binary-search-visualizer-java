import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class BinarySearchVisualizer extends JPanel {
    private int[] numbers;
    private int target, low = 0, high, mid = -1;
    private boolean found = false, started = false;

    public BinarySearchVisualizer(int[] numbers, int target) {
        this.numbers = numbers;
        this.target = target;
        this.high = numbers.length - 1;

        new Thread(() -> {
            started = true;
            while (low <= high) {
                mid = (low + high) / 2;
                repaint();
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                }
                if (numbers[mid] == target) {
                    found = true;
                    repaint();
                    return;
                } else if (numbers[mid] < target) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            repaint();
        }).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth(), h = getHeight(), bw = w / numbers.length;

        for (int i = 0; i < numbers.length; i++) {
            int bh = numbers[i] * 4;
            if (i == mid && started) g.setColor(Color.YELLOW);
            else if (i == low && started) g.setColor(Color.RED);
            else if (i == high && started) g.setColor(Color.GREEN);
            else g.setColor(Color.LIGHT_GRAY);
            g.fillRect(i * bw, h - bh, bw - 2, bh);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(numbers[i]), i * bw + 5, h - bh - 5);
        }

        g.setColor(Color.BLACK);
        if (found)
            g.drawString("Found " + target + " at index " + mid, 20, 20);
        else if (started && low > high)
            g.drawString("Target " + target + " not found", 20, 20);
    }

    public static void main(String[] args) {
        try {
            String input = JOptionPane.showInputDialog(null, "Enter sorted array:", "Array Input", JOptionPane.QUESTION_MESSAGE);
            if (input == null || input.trim().isEmpty()) return;
            String[] parts = input.split(",");
            int[] arr = new int[parts.length];
            for (int i = 0; i < parts.length; i++) arr[i] = Integer.parseInt(parts[i].trim());
            Arrays.sort(arr);

            String t = JOptionPane.showInputDialog(null, "Enter target:", "Target Input", JOptionPane.QUESTION_MESSAGE);
            if (t == null || t.trim().isEmpty()) return;
            int target = Integer.parseInt(t.trim());

            JFrame f = new JFrame("Binary Search Visualizer");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(800, 400);
            f.add(new BinarySearchVisualizer(arr, target));
            f.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input.");
        }
    }
}
