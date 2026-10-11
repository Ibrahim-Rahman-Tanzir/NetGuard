package com.netguard.server;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * NetGuard - Intelligent Network Security Monitoring System
 *
 * Modern Java Swing Dashboard
 * Modern Java Swing Dashboard
 *
 * No external images.
 * No external icons.
 * No external GUI dependency.
 *
 * Works with the existing NetGuard backend callbacks:
 *
 * addActivity(...)
 * addClient(...)
 * heartbeatReceived(...)
 * securityAlert(...)
 * clientOffline(...)
 */
public class ServerGUI extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(7, 12, 24);

    private static final Color SIDEBAR =
            new Color(10, 18, 34);

    private static final Color PANEL =
            new Color(13, 23, 42);

    private static final Color PANEL_LIGHT =
            new Color(17, 30, 53);

    private static final Color BORDER =
            new Color(35, 63, 95);

    private static final Color BLUE =
            new Color(45, 156, 255);

    private static final Color CYAN =
            new Color(58, 205, 255);

    private static final Color GREEN =
            new Color(40, 220, 130);

    private static final Color RED =
            new Color(255, 75, 90);

    private static final Color YELLOW =
            new Color(255, 190, 60);

    private static final Color PURPLE =
            new Color(180, 100, 255);

    private static final Color WHITE =
            new Color(240, 245, 255);

    private static final Color LIGHT =
            new Color(165, 183, 208);

    private static final Color MUTED =
            new Color(105, 125, 150);


    // =========================================================
    // GUI COMPONENTS
    // =========================================================

    private JLabel totalClientsLabel;
    private JLabel onlineClientsLabel;
    private JLabel securityAlertsLabel;
    private JLabel heartbeatLabel;

    private JLabel serverStatusLabel;
    private JLabel uptimeLabel;
    private JLabel clockLabel;

    private JTable clientsTable;
    private DefaultTableModel tableModel;

    private JTextArea alertArea;
    private JTextArea activityArea;

    private JLabel systemMessageLabel;


    // =========================================================
    // COUNTERS
    // =========================================================

    private int totalClients = 0;
    private int onlineClients = 0;
    private int securityAlerts = 0;
    private int heartbeatCount = 0;

    private long startTime;

    private boolean serverRunning = false;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ServerGUI() {

        setTitle(
                "NetGuard"
        );

        setSize(
                1400,
                850
        );


        setMinimumSize(
                new Dimension(
                        1150,
                        700
                )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        startTime =
                System.currentTimeMillis();

        createInterface();

        startClock();

        startUptimeTimer();

        setVisible(true);

        addActivity(
                "SYSTEM",
                "NetGuard dashboard initialized successfully."
        );
    }


    // =========================================================
    // MAIN INTERFACE
    // =========================================================

    private void createInterface() {

        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(
                BACKGROUND
        );

        root.add(
                createSidebar(),
                BorderLayout.WEST
        );

        root.add(
                createMainArea(),
                BorderLayout.CENTER
        );

        root.add(
                createFooter(),
                BorderLayout.SOUTH
        );

        setContentPane(root);
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel();

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setPreferredSize(
                new Dimension(
                        245,
                        0
                )
        );

        sidebar.setBackground(
                SIDEBAR
        );

        sidebar.setBorder(
                new CompoundBorder(
                        new MatteBorder(
                                0,
                                0,
                                0,
                                1,
                                BORDER
                        ),
                        new EmptyBorder(
                                25,
                                20,
                                20,
                                20
                        )
                )
        );


        // -----------------------------------------------------
        // LOGO
        // -----------------------------------------------------

        ShieldIcon logo =
                new ShieldIcon(
                        CYAN,
                        78
                );

        JPanel logoPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                0
                        )
                );

        logoPanel.setOpaque(false);

        logoPanel.add(logo);

        sidebar.add(
                logoPanel
        );

        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );


        // -----------------------------------------------------
        // BRAND
        // -----------------------------------------------------

        JLabel brand =
                new JLabel(
                        "NETGUARD"
                );

        brand.setForeground(
                WHITE
        );

        brand.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        brand.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                brand
        );


        JLabel subtitle =
                new JLabel(
                        "SECURITY MONITOR"
                );

        subtitle.setForeground(
                CYAN
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                subtitle
        );


        sidebar.add(
                Box.createVerticalStrut(
                        10
                )
        );


        JLabel developer =
                new JLabel(
                        "Intelligent Network Security Monitoring System"
                );

        developer.setForeground(
                LIGHT
        );

        developer.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        developer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                developer
        );


        sidebar.add(
                Box.createVerticalStrut(
                        30
                )
        );


        // -----------------------------------------------------
        // MENU
        // -----------------------------------------------------

        sidebar.add(
                createMenuButton(
                        "Dashboard",
                        new HomeIcon(CYAN, 23),
                        true
                )
        );

        sidebar.add(
                Box.createVerticalStrut(8)
        );

        sidebar.add(
                createMenuButton(
                        "Clients",
                        new UsersIcon(LIGHT, 23),
                        false
                )
        );

        sidebar.add(
                Box.createVerticalStrut(8)
        );

        sidebar.add(
                createMenuButton(
                        "Security Alerts",
                        new AlertIcon(YELLOW, 23),
                        false
                )
        );

        sidebar.add(
                Box.createVerticalStrut(8)
        );

        sidebar.add(
                createMenuButton(
                        "Activity Logs",
                        new LogIcon(CYAN, 23),
                        false
                )
        );

        sidebar.add(
                Box.createVerticalStrut(8)
        );

        sidebar.add(
                createMenuButton(
                        "Server Settings",
                        new SettingsIcon(LIGHT, 23),
                        false
                )
        );


        sidebar.add(
                Box.createVerticalGlue()
        );


        // -----------------------------------------------------
        // SYSTEM STATUS
        // -----------------------------------------------------

        sidebar.add(
                createSystemStatus()
        );


        return sidebar;
    }


    // =========================================================
    // MENU BUTTON
    // =========================================================

    private JPanel createMenuButton(
            String text,
            JComponent icon,
            boolean selected
    ) {

        JPanel button =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        button.setBorder(
                new EmptyBorder(
                        8,
                        12,
                        8,
                        12
                )
        );

        button.setBackground(
                selected
                        ? new Color(
                        22,
                        69,
                        120
                )
                        : SIDEBAR
        );


        button.add(
                icon,
                BorderLayout.WEST
        );


        JLabel label =
                new JLabel(
                        text
                );

        label.setForeground(
                selected
                        ? WHITE
                        : LIGHT
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        selected
                                ? Font.BOLD
                                : Font.PLAIN,
                        13
                )
        );


        button.add(
                label,
                BorderLayout.CENTER
        );


        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        if (!selected) {

                            button.setBackground(
                                    PANEL_LIGHT
                            );
                        }
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        if (!selected) {

                            button.setBackground(
                                    SIDEBAR
                            );
                        }
                    }
                }
        );


        return button;
    }


    // =========================================================
    // SYSTEM STATUS
    // =========================================================

    private JPanel createSystemStatus() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                PANEL
        );

        panel.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );


        JLabel title =
                new JLabel(
                        "SYSTEM STATUS"
                );

        title.setForeground(
                CYAN
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        panel.add(
                title
        );


        panel.add(
                Box.createVerticalStrut(
                        12
                )
        );


        serverStatusLabel =
                new JLabel(
                        "\u25CF  SERVER OFFLINE"
                );

        serverStatusLabel.setForeground(
                RED
        );

        serverStatusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        panel.add(
                serverStatusLabel
        );


        panel.add(
                Box.createVerticalStrut(
                        18
                )
        );


        panel.add(
                smallLabel(
                        "SERVER IP"
                )
        );

        panel.add(
                valueLabel(
                        "127.0.0.1"
                )
        );


        panel.add(
                Box.createVerticalStrut(
                        10
                )
        );


        panel.add(
                smallLabel(
                        "PORT"
                )
        );

        panel.add(
                valueLabel(
                        "5000"
                )
        );


        panel.add(
                Box.createVerticalStrut(
                        10
                )
        );


        panel.add(
                smallLabel(
                        "UPTIME"
                )
        );

        uptimeLabel =
                valueLabel(
                        "00:00:00"
                );

        panel.add(
                uptimeLabel
        );


        return panel;
    }


    // =========================================================
    // MAIN AREA
    // =========================================================

    private JPanel createMainArea() {

        JPanel main =
                new JPanel(
                        new BorderLayout(
                                0,
                                16
                        )
                );

        main.setBackground(
                BACKGROUND
        );

        main.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        10,
                        20
                )
        );


        main.add(
                createTopHeader(),
                BorderLayout.NORTH
        );


        JPanel content =
                new JPanel(
                        new BorderLayout(
                                0,
                                16
                        )
                );

        content.setOpaque(false);


        content.add(
                createStatistics(),
                BorderLayout.NORTH
        );


        JPanel middle =
                new JPanel(
                        new BorderLayout(
                                16,
                                0
                        )
                );

        middle.setOpaque(false);


        middle.add(
                createClientsPanel(),
                BorderLayout.CENTER
        );


        middle.add(
                createNetworkPanel(),
                BorderLayout.EAST
        );


        content.add(
                middle,
                BorderLayout.CENTER
        );


        content.add(
                createBottomArea(),
                BorderLayout.SOUTH
        );


        main.add(
                content,
                BorderLayout.CENTER
        );


        return main;
    }


    // =========================================================
    // TOP HEADER
    // =========================================================

    private JPanel createTopHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);


        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "Security Operations Center"
                );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Real-time Network Monitoring & Threat Detection"
                );

        subtitle.setForeground(
                LIGHT
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );


        titlePanel.add(
                title
        );

        titlePanel.add(
                Box.createVerticalStrut(
                        3
                )
        );

        titlePanel.add(
                subtitle
        );


        header.add(
                titlePanel,
                BorderLayout.WEST
        );


        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                15,
                                4
                        )
                );

        right.setOpaque(false);


        JLabel live =
                new JLabel(
                        "\u25CF  LIVE MONITORING"
                );

        live.setForeground(
                GREEN
        );

        live.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        JSeparator separator =
                new JSeparator(
                        SwingConstants.VERTICAL
                );

        separator.setPreferredSize(
                new Dimension(
                        1,
                        30
                )
        );

        separator.setForeground(
                BORDER
        );


        clockLabel =
                new JLabel(
                        "00:00:00"
                );

        clockLabel.setForeground(
                LIGHT
        );

        clockLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        right.add(
                live
        );

        right.add(
                separator
        );

        right.add(
                clockLabel
        );


        header.add(
                right,
                BorderLayout.EAST
        );


        return header;
    }


    // =========================================================
    // STATISTICS
    // =========================================================

    private JPanel createStatistics() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                14,
                                0
                        )
                );

        panel.setOpaque(false);


        totalClientsLabel =
                new JLabel(
                        "0"
                );

        onlineClientsLabel =
                new JLabel(
                        "0"
                );

        securityAlertsLabel =
                new JLabel(
                        "0"
                );

        heartbeatLabel =
                new JLabel(
                        "0"
                );


        panel.add(
                createStatCard(
                        "TOTAL CLIENTS",
                        totalClientsLabel,
                        BLUE,
                        "Connected devices",
                        new UsersIcon(
                                BLUE,
                                34
                        )
                )
        );


        panel.add(
                createStatCard(
                        "ONLINE CLIENTS",
                        onlineClientsLabel,
                        GREEN,
                        "Currently active",
                        new WifiIcon(
                                GREEN,
                                34
                        )
                )
        );


        panel.add(
                createStatCard(
                        "SECURITY ALERTS",
                        securityAlertsLabel,
                        YELLOW,
                        "Threats detected",
                        new AlertIcon(
                                YELLOW,
                                34
                        )
                )
        );


        panel.add(
                createStatCard(
                        "HEARTBEAT RECEIVED",
                        heartbeatLabel,
                        PURPLE,
                        "Total heartbeats",
                        new HeartbeatIcon(
                                PURPLE,
                                34
                        )
                )
        );


        return panel;
    }


    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            JLabel value,
            Color accent,
            String description,
            JComponent icon
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        card.setBackground(
                PANEL
        );

        card.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );


        JPanel iconHolder =
                new JPanel(
                        new GridBagLayout()
                );

        iconHolder.setOpaque(false);

        iconHolder.add(
                icon
        );


        card.add(
                iconHolder,
                BorderLayout.WEST
        );


        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel heading =
                new JLabel(
                        title
                );

        heading.setForeground(
                LIGHT
        );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );


        value.setForeground(
                accent
        );

        value.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );


        JLabel desc =
                new JLabel(
                        description
                );

        desc.setForeground(
                MUTED
        );

        desc.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );


        text.add(
                heading
        );

        text.add(
                Box.createVerticalStrut(
                        2
                )
        );

        text.add(
                value
        );

        text.add(
                desc
        );


        card.add(
                text,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // CLIENT PANEL
    // =========================================================

    private JPanel createClientsPanel() {

        JPanel panel =
                createCard();


        JPanel heading =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        heading.setOpaque(false);


        heading.add(
                new UsersIcon(
                        CYAN,
                        24
                )
        );


        JLabel title =
                new JLabel(
                        "  CONNECTED CLIENTS"
                );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        heading.add(
                title
        );


        panel.add(
                heading,
                BorderLayout.NORTH
        );


        String[] columns = {

                "#",
                "CLIENT NAME",
                "IP ADDRESS",
                "CONNECTION TIME",
                "STATUS",
                "LAST HEARTBEAT"

        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        clientsTable =
                new JTable(
                        tableModel
                );


        clientsTable.setBackground(
                PANEL
        );

        clientsTable.setForeground(
                WHITE
        );

        clientsTable.setGridColor(
                new Color(
                        25,
                        45,
                        70
                )
        );

        clientsTable.setRowHeight(
                36
        );

        clientsTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        clientsTable.setSelectionBackground(
                new Color(
                        25,
                        70,
                        110
                )
        );

        clientsTable.setSelectionForeground(
                WHITE
        );


        clientsTable.getTableHeader()
                .setBackground(
                        new Color(
                                9,
                                20,
                                37
                        )
                );

        clientsTable.getTableHeader()
                .setForeground(
                        LIGHT
                );

        clientsTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                11
                        )
                );

        clientsTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                38
                        )
                );


        clientsTable
                .getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new StatusRenderer()
                );


        JScrollPane scroll =
                new JScrollPane(
                        clientsTable
                );

        scroll.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );

        scroll.getViewport()
                .setBackground(
                        PANEL
                );


        panel.add(
                scroll,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // NETWORK ACTIVITY PANEL
    // =========================================================

    private JPanel createNetworkPanel() {

        JPanel panel =
                createCard();

        panel.setPreferredSize(
                new Dimension(
                        315,
                        0
                )
        );


        JPanel heading =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        heading.setOpaque(false);


        heading.add(
                new NetworkIcon(
                        CYAN,
                        24
                )
        );


        JLabel title =
                new JLabel(
                        "  NETWORK ACTIVITY"
                );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        heading.add(
                title
        );


        panel.add(
                heading,
                BorderLayout.NORTH
        );


        JPanel graph =
                new NetworkGraph();

        panel.add(
                graph,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // BOTTOM AREA
    // =========================================================

    private JPanel createBottomArea() {

        JPanel bottom =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                14,
                                0
                        )
                );

        bottom.setPreferredSize(
                new Dimension(
                        0,
                        210
                )
        );

        bottom.setOpaque(false);


        bottom.add(
                createAlertPanel()
        );


        bottom.add(
                createActivityPanel()
        );


        bottom.add(
                createControlPanel()
        );


        return bottom;
    }


    // =========================================================
    // SECURITY ALERT PANEL
    // =========================================================

    private JPanel createAlertPanel() {

        JPanel panel =
                createCard();


        JPanel heading =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        heading.setOpaque(false);


        heading.add(
                new AlertIcon(
                        YELLOW,
                        23
                )
        );


        JLabel title =
                new JLabel(
                        "  SECURITY ALERTS"
                );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        heading.add(
                title
        );


        panel.add(
                heading,
                BorderLayout.NORTH
        );


        alertArea =
                new JTextArea();

        alertArea.setEditable(
                false
        );

        alertArea.setLineWrap(
                true
        );

        alertArea.setWrapStyleWord(
                true
        );

        alertArea.setBackground(
                new Color(
                        10,
                        20,
                        35
                )
        );

        alertArea.setForeground(
                GREEN
        );

        alertArea.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        alertArea.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                new Color(
                                        35,
                                        100,
                                        75
                                ),
                                1,
                                true
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );


        alertArea.setText(
                "SYSTEM NORMAL\n\n"
                        + "No security threats detected.\n\n"
                        + "NetGuard is ready to monitor network activity."
        );


        panel.add(
                alertArea,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // ACTIVITY LOG PANEL
    // =========================================================

    private JPanel createActivityPanel() {

        JPanel panel =
                createCard();


        JPanel heading =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        heading.setOpaque(false);


        heading.add(
                new LogIcon(
                        CYAN,
                        23
                )
        );


        JLabel title =
                new JLabel(
                        "  LIVE ACTIVITY LOG"
                );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        heading.add(
                title
        );


        panel.add(
                heading,
                BorderLayout.NORTH
        );


        activityArea =
                new JTextArea();

        activityArea.setEditable(
                false
        );

        activityArea.setLineWrap(
                true
        );

        activityArea.setWrapStyleWord(
                true
        );

        activityArea.setBackground(
                new Color(
                        9,
                        18,
                        32
                )
        );

        activityArea.setForeground(
                LIGHT
        );

        activityArea.setFont(
                new Font(
                        "Consolas",
                        Font.PLAIN,
                        11
                )
        );

        activityArea.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        JScrollPane scroll =
                new JScrollPane(
                        activityArea
                );

        scroll.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );


        panel.add(
                scroll,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // SERVER CONTROL PANEL
    // =========================================================

    private JPanel createControlPanel() {

        JPanel panel =
                createCard();


        JPanel heading =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        heading.setOpaque(false);


        heading.add(
                new SettingsIcon(
                        CYAN,
                        23
                )
        );


        JLabel title =
                new JLabel(
                        "  SERVER CONTROLS"
                );

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        heading.add(
                title
        );


        panel.add(
                heading,
                BorderLayout.NORTH
        );


        JPanel buttons =
                new JPanel();

        buttons.setOpaque(false);

        buttons.setLayout(
                new GridLayout(
                        3,
                        1,
                        0,
                        8
                )
        );


        JButton startButton =
                createControlButton(
                        "START SERVER",
                        GREEN
                );


        JButton stopButton =
                createControlButton(
                        "STOP SERVER",
                        RED
                );


        JButton clearButton =
                createControlButton(
                        "CLEAR LOGS",
                        BLUE
                );


        // -----------------------------------------------------
        // START BUTTON
        // -----------------------------------------------------

        startButton.addActionListener(
                e -> {

                    setServerRunning(
                            true
                    );

                    addActivity(
                            "SYSTEM",
                            "Server monitoring status set to ONLINE."
                    );
                }
        );


        // -----------------------------------------------------
        // STOP BUTTON
        // -----------------------------------------------------

        stopButton.addActionListener(
                e -> {

                    setServerRunning(
                            false
                    );

                    addActivity(
                            "SYSTEM",
                            "Server monitoring status set to OFFLINE."
                    );
                }
        );


        // -----------------------------------------------------
        // CLEAR BUTTON
        // -----------------------------------------------------

        clearButton.addActionListener(
                e -> {

                    if (activityArea != null) {

                        activityArea.setText(
                                ""
                        );
                    }

                    if (alertArea != null) {

                        alertArea.setText(
                                "SYSTEM NORMAL\n\n"
                                        + "Activity log cleared."
                        );

                        alertArea.setForeground(
                                GREEN
                        );
                    }
                }
        );


        buttons.add(
                startButton
        );

        buttons.add(
                stopButton
        );

        buttons.add(
                clearButton
        );


        panel.add(
                buttons,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // CONTROL BUTTON
    // =========================================================

    private JButton createControlButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(
                        text
                );


        button.setForeground(
                color
        );

        button.setBackground(
                new Color(
                        10,
                        22,
                        39
                )
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                color,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                9,
                                10,
                                9,
                                10
                        )
                )
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }


    // =========================================================
    // CARD
    // =========================================================

    private JPanel createCard() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                10
                        )
                );

        panel.setBackground(
                PANEL
        );

        panel.setBorder(
                new CompoundBorder(
                        new LineBorder(
                                BORDER,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                13,
                                14,
                                13,
                                14
                        )
                )
        );


        return panel;
    }


    // =========================================================
    // FOOTER
    // =========================================================

    private JPanel createFooter() {

        JPanel footer =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        footer.setBackground(
                new Color(
                        5,
                        10,
                        20
                )
        );


        JLabel text =
                new JLabel(
                        "\u00A9 2026 NetGuard Security Monitor"
                                + "   \u2022   "
                                + "Developed by Ibrahim Rahman & Ziham Mahmud"
                );

        text.setForeground(
                MUTED
        );

        text.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );


        footer.add(
                text
        );


        return footer;
    }


    // =========================================================
    // SMALL LABEL
    // =========================================================

    private JLabel smallLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setForeground(
                MUTED
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        return label;
    }


    // =========================================================
    // VALUE LABEL
    // =========================================================

    private JLabel valueLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setForeground(
                CYAN
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        return label;
    }


    // =========================================================
    // SERVER STATUS
    // =========================================================

    public synchronized void setServerRunning(
            boolean running
    ) {

        serverRunning =
                running;


        if (serverStatusLabel == null) {

            return;
        }


        if (running) {

            serverStatusLabel.setText(
                    "\u25CF  SERVER ONLINE"
            );

            serverStatusLabel.setForeground(
                    GREEN
            );

        } else {

            serverStatusLabel.setText(
                    "\u25CF  SERVER OFFLINE"
            );

            serverStatusLabel.setForeground(
                    RED
            );
        }
    }


    // =========================================================
    // ADD ACTIVITY
    // =========================================================

    public void addActivity(
            String type,
            String message
    ) {

        if (activityArea == null) {

            return;
        }


        String time =
                new SimpleDateFormat(
                        "HH:mm:ss"
                ).format(
                        new Date()
                );


        SwingUtilities.invokeLater(
                () -> {

                    activityArea.append(
                            time
                                    + "  ["
                                    + type
                                    + "]  "
                                    + message
                                    + "\n"
                    );


                    activityArea.setCaretPosition(
                            activityArea.getDocument()
                                    .getLength()
                    );
                }
        );
    }


    // =========================================================
    // ADD CLIENT
    // =========================================================

    public synchronized void addClient(
            String name,
            String ip,
            String connectionTime,
            String status,
            String heartbeat
    ) {

        if (tableModel == null) {

            return;
        }


        totalClients++;


        if (
                "ONLINE".equalsIgnoreCase(
                        status
                )
        ) {

            onlineClients++;
        }


        int rowNumber =
                totalClients;


        SwingUtilities.invokeLater(
                () -> {

                    tableModel.addRow(
                            new Object[]{
                                    rowNumber,
                                    name,
                                    ip,
                                    connectionTime,
                                    status,
                                    heartbeat
                            }
                    );


                    totalClientsLabel.setText(
                            String.valueOf(
                                    totalClients
                            )
                    );


                    onlineClientsLabel.setText(
                            String.valueOf(
                                    onlineClients
                            )
                    );


                    addActivity(
                            "CONNECTED",
                            "Client "
                                    + name
                                    + " connected from "
                                    + ip
                    );
                }
        );
    }
    // =========================================================
// HEARTBEAT
// =========================================================

    public synchronized void heartbeatReceived(
            String clientName,
            String heartbeatTime
    ) {

        heartbeatCount++;

        SwingUtilities.invokeLater(
                () -> {

                    heartbeatLabel.setText(
                            String.valueOf(
                                    heartbeatCount
                            )
                    );


                    // Update Last Heartbeat in Connected Clients table
                    if (tableModel != null) {

                        for (
                                int row = tableModel.getRowCount() - 1;
                                row >= 0;
                                row--
                        ) {

                            Object clientNameValue =
                                    tableModel.getValueAt(
                                            row,
                                            1
                                    );

                            Object statusValue =
                                    tableModel.getValueAt(
                                            row,
                                            4
                                    );


                            if (
                                    clientNameValue != null
                                            &&
                                            clientName.equalsIgnoreCase(
                                                    clientNameValue.toString()
                                            )
                                            &&
                                            statusValue != null
                                            &&
                                            "ONLINE".equalsIgnoreCase(
                                                    statusValue.toString()
                                            )
                            ) {

                                tableModel.setValueAt(
                                        heartbeatTime,
                                        row,
                                        5
                                );

                                break;
                            }
                        }
                    }


                    addActivity(
                            "HEARTBEAT",
                            "Heartbeat received from "
                                    + clientName
                                    + " | Last Heartbeat: "
                                    + heartbeatTime
                    );
                }
        );
    }




    // =========================================================
    // SIMPLE HEARTBEAT
    // =========================================================

    public synchronized void heartbeatReceived() {

        heartbeatCount++;


        SwingUtilities.invokeLater(
                () -> {

                    if (heartbeatLabel != null) {

                        heartbeatLabel.setText(
                                String.valueOf(
                                        heartbeatCount
                                )
                        );
                    }
                }
        );
    }


    // =========================================================
    // SECURITY ALERT
    // =========================================================

    public synchronized void securityAlert(
            String message
    ) {

        securityAlerts++;


        SwingUtilities.invokeLater(
                () -> {

                    securityAlertsLabel.setText(
                            String.valueOf(
                                    securityAlerts
                            )
                    );


                    alertArea.setForeground(
                            RED
                    );


                    alertArea.setText(
                            "\u26A0 SECURITY ALERT\n\n"
                                    + message
                                    + "\n\n"
                                    + "Suspicious network behavior detected."
                    );


                    addActivity(
                            "SECURITY ALERT",
                            message
                    );
                }
        );
    }


    // =========================================================
    // CLIENT OFFLINE
    // =========================================================

    public synchronized void clientOffline(
            String clientName
    ) {

        if (onlineClients > 0) {

            onlineClients--;
        }


        SwingUtilities.invokeLater(
                () -> {

                    onlineClientsLabel.setText(
                            String.valueOf(
                                    onlineClients
                            )
                    );


                    // =================================================
                    // UPDATE CLIENT STATUS IN TABLE
                    // =================================================

                    if (tableModel != null) {

                        for (
                                int row = tableModel.getRowCount() - 1;
                                row >= 0;
                                row--
                        ) {

                            Object nameValue =
                                    tableModel.getValueAt(
                                            row,
                                            1
                                    );

                            Object statusValue =
                                    tableModel.getValueAt(
                                            row,
                                            4
                                    );


                            if (
                                    nameValue != null
                                            &&
                                            clientName.equalsIgnoreCase(
                                                    nameValue.toString()
                                            )
                                            &&
                                            statusValue != null
                                            &&
                                            "ONLINE".equalsIgnoreCase(
                                                    statusValue.toString()
                                            )
                            ) {

                                // Change ONLINE → OFFLINE
                                tableModel.setValueAt(
                                        "OFFLINE",
                                        row,
                                        4
                                );

                                break;
                            }
                        }
                    }


                    // =================================================
                    // ACTIVITY LOG
                    // =================================================

                    addActivity(
                            "OFFLINE",
                            "Client "
                                    + clientName
                                    + " disconnected."
                    );
                }
        );
    }


    // =========================================================
    // CLOCK
    // =========================================================

    private void startClock() {

        Timer timer =
                new Timer(
                        1000,
                        e -> {

                            if (clockLabel != null) {

                                clockLabel.setText(
                                        new SimpleDateFormat(
                                                "HH:mm:ss   dd MMM yyyy"
                                        ).format(
                                                new Date()
                                        )
                                );
                            }
                        }
                );


        timer.setInitialDelay(
                0
        );

        timer.start();
    }


    // =========================================================
    // UPTIME
    // =========================================================

    private void startUptimeTimer() {

        Timer timer =
                new Timer(
                        1000,
                        e -> {

                            long seconds =
                                    (
                                            System.currentTimeMillis()
                                                    - startTime
                                    ) / 1000;


                            long hours =
                                    seconds / 3600;


                            long minutes =
                                    (
                                            seconds % 3600
                                    ) / 60;


                            long secs =
                                    seconds % 60;


                            if (uptimeLabel != null) {

                                uptimeLabel.setText(
                                        String.format(
                                                "%02d:%02d:%02d",
                                                hours,
                                                minutes,
                                                secs
                                        )
                                );
                            }
                        }
                );


        timer.start();
    }


    // =========================================================
    // STATUS TABLE RENDERER
    // =========================================================

    private static class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column
        ) {

            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            selected,
                            focused,
                            row,
                            column
                    );


            if (
                    value != null
                            &&
                            "ONLINE".equalsIgnoreCase(
                                    value.toString()
                            )
            ) {

                component.setForeground(
                        GREEN
                );

            } else {

                component.setForeground(
                        RED
                );
            }


            if (selected) {

                component.setBackground(
                        new Color(
                                25,
                                70,
                                110
                        )
                );

            } else {

                component.setBackground(
                        PANEL
                );
            }


            return component;
        }
    }


    // =========================================================
    // NETWORK GRAPH
    // =========================================================

    private static class NetworkGraph
            extends JPanel {

        private int animation =
                0;


        public NetworkGraph() {

            setOpaque(
                    false
            );


            Timer timer =
                    new Timer(
                            80,
                            e -> {

                                animation++;

                                repaint();
                            }
                    );


            timer.start();
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(
                    g
            );


            Graphics2D g2 =
                    (Graphics2D) g.create();


            int width =
                    getWidth();

            int height =
                    getHeight();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            // -------------------------------------------------
            // GRID
            // -------------------------------------------------

            g2.setColor(
                    new Color(
                            25,
                            45,
                            70
                    )
            );


            for (
                    int y = 25;
                    y < height - 20;
                    y += 35
            ) {

                g2.drawLine(
                        15,
                        y,
                        width - 15,
                        y
                );
            }


            // -------------------------------------------------
            // ZERO ACTIVITY LINE
            // -------------------------------------------------

            int baseY =
                    height - 35;


            g2.setColor(
                    CYAN
            );


            g2.setStroke(
                    new BasicStroke(
                            2f
                    )
            );


            int previousX =
                    15;

            int previousY =
                    baseY;


            for (
                    int x = 15;
                    x < width - 15;
                    x += 18
            ) {

                int wave =
                        (int)
                                (
                                        Math.sin(
                                                (
                                                        x
                                                                + animation
                                                ) * 0.08
                                        ) * 8
                                );


                int y =
                        baseY
                                - wave;


                g2.drawLine(
                        previousX,
                        previousY,
                        x,
                        y
                );


                previousX =
                        x;

                previousY =
                        y;
            }


            g2.setColor(
                    CYAN
            );

            g2.fillOval(
                    previousX - 4,
                    previousY - 4,
                    8,
                    8
            );


            g2.setColor(
                    LIGHT
            );

            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            10
                    )
            );


            g2.drawString(
                    "LIVE",
                    15,
                    18
            );


            g2.drawString(
                    "Connections",
                    15,
                    height - 8
            );


            g2.dispose();
        }
    }


    // =========================================================
    // BASE ICON
    // =========================================================

    private static abstract class BaseIcon
            extends JComponent {

        protected Color color;

        protected int size;


        public BaseIcon(
                Color color,
                int size
        ) {

            this.color =
                    color;

            this.size =
                    size;


            setPreferredSize(
                    new Dimension(
                            size,
                            size
                    )
            );

            setMinimumSize(
                    new Dimension(
                            size,
                            size
                    )
            );

            setMaximumSize(
                    new Dimension(
                            size,
                            size
                    )
            );
        }


        protected Graphics2D prepare(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            g2.setColor(
                    color
            );


            g2.setStroke(
                    new BasicStroke(
                            2.2f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );


            return g2;
        }
    }


    // =========================================================
    // SHIELD ICON
    // =========================================================

    private static class ShieldIcon
            extends BaseIcon {

        public ShieldIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            Polygon shield =
                    new Polygon();


            shield.addPoint(
                    s / 2,
                    5
            );

            shield.addPoint(
                    s - 10,
                    15
            );

            shield.addPoint(
                    s - 13,
                    s - 25
            );

            shield.addPoint(
                    s / 2,
                    s - 5
            );

            shield.addPoint(
                    13,
                    s - 25
            );

            shield.addPoint(
                    10,
                    15
            );


            g2.drawPolygon(
                    shield
            );


            g2.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            s / 3
                    )
            );


            FontMetrics fm =
                    g2.getFontMetrics();


            String n =
                    "N";


            int x =
                    (s - fm.stringWidth(n))
                            / 2;


            int y =
                    (s + fm.getAscent())
                            / 2
                            - 2;


            g2.drawString(
                    n,
                    x,
                    y
            );


            g2.dispose();
        }
    }


    // =========================================================
    // HOME ICON
    // =========================================================

    private static class HomeIcon
            extends BaseIcon {

        public HomeIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            Polygon roof =
                    new Polygon();


            roof.addPoint(
                    s / 2,
                    3
            );

            roof.addPoint(
                    s - 3,
                    11
            );

            roof.addPoint(
                    s - 5,
                    14
            );

            roof.addPoint(
                    s / 2,
                    7
            );

            roof.addPoint(
                    5,
                    14
            );

            roof.addPoint(
                    3,
                    11
            );


            g2.drawPolygon(
                    roof
            );


            g2.drawRect(
                    6,
                    12,
                    s - 12,
                    s - 15
            );


            g2.drawRect(
                    s / 2 - 3,
                    s - 9,
                    6,
                    6
            );


            g2.dispose();
        }
    }


    // =========================================================
    // USERS ICON
    // =========================================================

    private static class UsersIcon
            extends BaseIcon {

        public UsersIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            g2.drawOval(
                    4,
                    4,
                    8,
                    8
            );


            g2.drawOval(
                    s - 12,
                    6,
                    7,
                    7
            );


            g2.drawArc(
                    1,
                    12,
                    15,
                    10,
                    200,
                    140
            );


            g2.drawArc(
                    s - 15,
                    13,
                    13,
                    9,
                    200,
                    140
            );


            g2.dispose();
        }
    }


    // =========================================================
    // WIFI ICON
    // =========================================================

    private static class WifiIcon
            extends BaseIcon {

        public WifiIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            g2.drawArc(
                    4,
                    3,
                    s - 8,
                    s - 8,
                    45,
                    90
            );


            g2.drawArc(
                    8,
                    8,
                    s - 16,
                    s - 16,
                    45,
                    90
            );


            g2.fillOval(
                    s / 2 - 2,
                    s - 8,
                    5,
                    5
            );


            g2.dispose();
        }
    }


    // =========================================================
    // ALERT ICON
    // =========================================================

    private static class AlertIcon
            extends BaseIcon {

        public AlertIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            Polygon triangle =
                    new Polygon();


            triangle.addPoint(
                    s / 2,
                    3
            );

            triangle.addPoint(
                    s - 3,
                    s - 4
            );

            triangle.addPoint(
                    3,
                    s - 4
            );


            g2.drawPolygon(
                    triangle
            );


            g2.drawLine(
                    s / 2,
                    9,
                    s / 2,
                    s - 10
            );


            g2.fillOval(
                    s / 2 - 2,
                    s - 8,
                    4,
                    4
            );


            g2.dispose();
        }
    }


    // =========================================================
    // LOG ICON
    // =========================================================

    private static class LogIcon
            extends BaseIcon {

        public LogIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            g2.drawRect(
                    4,
                    3,
                    s - 8,
                    s - 6
            );


            g2.drawLine(
                    8,
                    9,
                    s - 8,
                    9
            );


            g2.drawLine(
                    8,
                    14,
                    s - 8,
                    14
            );


            g2.drawLine(
                    8,
                    19,
                    s - 8,
                    19
            );


            g2.dispose();
        }
    }


    // =========================================================
    // SETTINGS ICON
    // =========================================================

    private static class SettingsIcon
            extends BaseIcon {

        public SettingsIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            g2.drawOval(
                    5,
                    5,
                    s - 10,
                    s - 10
            );


            g2.drawOval(
                    s / 2 - 3,
                    s / 2 - 3,
                    6,
                    6
            );


            g2.drawLine(
                    s / 2,
                    1,
                    s / 2,
                    5
            );


            g2.drawLine(
                    s / 2,
                    s - 5,
                    s / 2,
                    s - 1
            );


            g2.drawLine(
                    1,
                    s / 2,
                    5,
                    s / 2
            );


            g2.drawLine(
                    s - 5,
                    s / 2,
                    s - 1,
                    s / 2
            );


            g2.dispose();
        }
    }


    // =========================================================
    // HEARTBEAT ICON
    // =========================================================

    private static class HeartbeatIcon
            extends BaseIcon {

        public HeartbeatIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            int mid =
                    s / 2;


            Polygon pulse =
                    new Polygon();


            pulse.addPoint(
                    2,
                    mid
            );

            pulse.addPoint(
                    7,
                    mid
            );

            pulse.addPoint(
                    10,
                    8
            );

            pulse.addPoint(
                    14,
                    s - 6
            );

            pulse.addPoint(
                    18,
                    mid
            );

            pulse.addPoint(
                    s - 2,
                    mid
            );


            g2.drawPolyline(
                    pulse.xpoints,
                    pulse.ypoints,
                    pulse.npoints
            );


            g2.dispose();
        }
    }


    // =========================================================
    // NETWORK ICON
    // =========================================================

    private static class NetworkIcon
            extends BaseIcon {

        public NetworkIcon(
                Color color,
                int size
        ) {

            super(
                    color,
                    size
            );
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    prepare(g);


            int s =
                    size;


            g2.drawLine(
                    5,
                    s / 2,
                    s - 5,
                    s / 2
            );


            g2.drawLine(
                    s / 2,
                    5,
                    s / 2,
                    s - 5
            );


            g2.fillOval(
                    2,
                    s / 2 - 4,
                    8,
                    8
            );


            g2.fillOval(
                    s - 10,
                    s / 2 - 4,
                    8,
                    8
            );


            g2.fillOval(
                    s / 2 - 4,
                    2,
                    8,
                    8
            );


            g2.fillOval(
                    s / 2 - 4,
                    s - 10,
                    8,
                    8
            );


            g2.dispose();
        }
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    // =========================================
                    // CREATE GUI
                    // =========================================

                    ServerGUI gui =
                            new ServerGUI();

                    // =========================================
                    // START SERVER
                    // =========================================

                    Thread serverThread =
                            new Thread(
                                    () -> {

                                        Server server =
                                                new Server(gui);

                                        server.startServer();

                                    },
                                    "NetGuard-Server"
                            );

                    serverThread.start();
                }
        );
    }
}