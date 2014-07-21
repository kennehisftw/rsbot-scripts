package org.kenneh.scripts.fighter;

import org.kenneh.core.context.Context;
import org.kenneh.scripts.fighter.actions.*;
import org.kenneh.util.Calculations;
import org.powerbot.script.Filter;
import org.powerbot.script.rt6.Npc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kenneth on 7/20/2014.
 */
public class FighterGUI extends JFrame {

    private final Context ctx;

    private DefaultListModel<String> allMonsterListModel = new DefaultListModel<>();
    private JList<String> allMonsterList = new JList<>(allMonsterListModel);

    private DefaultListModel<String> selectedMonsterListModel = new DefaultListModel<>();
    private JList<String> selectedMonsterList = new JList<>(selectedMonsterListModel);

    private final JButton startButton = new JButton("Start");
    private final JButton refreshButton = new JButton("Refresh Npcs");

    public FighterGUI(Context context) {
        this.ctx = context;
        setTitle(ctx.controller.script().getName());

        allMonsterList.setPreferredSize(new Dimension(200, 300));
        selectedMonsterList.setPreferredSize(new Dimension(200, 300));

        final JPanel listPanel = new JPanel();
        listPanel.setLayout(new FlowLayout());
        listPanel.add(allMonsterList);
        listPanel.add(selectedMonsterList);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(listPanel, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(refreshButton);
        bottomPanel.add(startButton);

        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        allMonsterList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                final JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    final int index = list.locationToIndex(evt.getPoint());
                    final String npcInfo = allMonsterListModel.get(index);
                    if(!selectedMonsterListModel.contains(npcInfo)) {
                        selectedMonsterListModel.addElement(npcInfo);
                    }
                }
            }
        });

        selectedMonsterList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JList list = (JList) e.getSource();
                if(e.getClickCount() == 2) {
                    final int index = list.locationToIndex(e.getPoint());
                    selectedMonsterListModel.removeElementAt(index);
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Main script instance
                final AutoFighter fighter = (AutoFighter) ctx.controller.script();

                // Convert monster list to ids for fighting
                final List<Integer> list = new ArrayList<>();
                for(int i = 0; i < selectedMonsterListModel.size(); i++) {
                    final String npcInfo = selectedMonsterListModel.getElementAt(i);
                    list.add(Integer.parseInt(npcInfo.split("-")[1].trim()));
                }
                fighter.setMonsterIds(Calculations.toArray(list));
                fighter.setLootNames(Constants.RARE_DROP_TABLE);
                fighter.add(new Fight(ctx), new Loot(ctx), new Eat(ctx), new Teleport(ctx), new ActionBar(ctx));

                dispose();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateMonsterList();
            }
        });

        populateMonsterList();
        pack();
    }

    private void populateMonsterList() {
        allMonsterList.removeAll();
        allMonsterListModel.clear();
        for(Npc npc : ctx.npcs.select().select(DEFAULT_FILTER).within(15)) {
            if(npc.valid()) {
                final String npcInfo = npc.name() + "(" + npc.combatLevel() + ") - " + npc.id();
                if(!allMonsterListModel.contains(npcInfo)) {
                    allMonsterListModel.addElement(npcInfo);
                }
            }
        }
    }

    private final Filter<Npc> DEFAULT_FILTER = new Filter<Npc>() {
        @Override
        public boolean accept(Npc npc) {
            return npc.combatLevel() != 0  && Arrays.asList(npc.actions()).contains("Attack");
        }
    };

}
