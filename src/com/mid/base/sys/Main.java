package com.mid.base.sys;

import com.mid.base.core.Game;
import com.mid.base.sys.asset.SoundManager;
import com.mid.base.sys.mouse.Mouse;
import com.mid.base.sys.mouse.MouseListener;
import com.mid.base.sys.view.IFrameSize;
import com.mid.base.sys.view.IPause;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Main extends JPanel implements IFrameSize, IPause {
    JFrame frame = new JFrame("god.......");

    private long lastTime;

    private boolean pause = false;

    public Game game = new Game();

    private Console console = new Console();
    public Console getConsole() {
        return console;
    }

    private final ViewMetrics viewMetrics;

    public Main() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        frame.setPreferredSize((new Dimension(1280,720)));
        setFocusable(true);

        viewMetrics = new ViewMetrics(this);
        MouseListener mouseListener = new MouseListener();

        frame.add(this);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.pack();

        setBackground(Color.BLACK);

        viewMetrics.calculateViewMetrics();

        this.addMouseListener(mouseListener);
        this.addMouseWheelListener(mouseListener);

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                viewMetrics.updateVirtualMouse(e.getX(),e.getY());
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                viewMetrics.calculateViewMetrics();
            }
        });



        startGameLoop();
    }

    private void startGameLoop() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        lastTime = System.nanoTime();

        executor.scheduleAtFixedRate(() -> {
            try {
                long now = System.nanoTime();
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                update(deltaTime);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            SwingUtilities.invokeLater(this::repaint);

        }, 0, 16, TimeUnit.MILLISECONDS);
    }

    private void update(double deltaTime) {
        double dt = deltaTime / (16.0 / 1000.0);
        SoundManager.update();
        game.update(dt);
    }

    @Override public boolean isPause() {return pause;}
    @Override public void setPause(boolean b) {pause = b;}

    @Override public int getComponentWidth() { return this.getWidth(); }
    @Override public int getComponentHeight() { return this.getHeight(); }
    public int getMouseX() {return viewMetrics.getVirtualMouseX();}
    public int getMouseY() {return viewMetrics.getVirtualMouseY();}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d2 = (Graphics2D) g;

        d2.translate(viewMetrics.getCurrentXOffset(), viewMetrics.getCurrentYOffset());
        d2.scale(viewMetrics.getCurrentScale(), viewMetrics.getCurrentScale());

        game.render(g);

        console.render(g);
    }

    public static void main(String[] args) {
        Main m = new Main();
        Mouse.wakeUp(m);
    }
}