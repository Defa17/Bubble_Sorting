package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import com.company.Utils.SwingUtils;
import com.company.Utils.JTableUtils;
import com.company.Utils.ArrayUtils;

public class FrameMain extends JFrame {
    private JTable tableInput;
    private JButton buttonFromFile;
    private JButton buttonResult;
    private JTable tableOutput;

    private JPanel panelMain;
    private JButton buttonSaveToFile;
    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;

    public FrameMain() {
        this.setTitle("Task 2");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 40, true, true, true, true);
        tableInput.setRowHeight(25);

        JTableUtils.initJTableForArray(tableOutput, 40, false, false, false, false);
        tableOutput.setRowHeight(25);

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        buttonFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent_1) {
                try {
                    if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[][] mas = ArrayUtils.readIntArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                        JTableUtils.writeArrayToJTable(tableInput, mas);
                    }
                } catch (Exception error) {
                    SwingUtils.showErrorMessageBox(error);
                }
            }
        });

        buttonResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                try {
                    int[] arr = JTableUtils.readIntArrayFromJTable(tableInput);
                    Sort sorted = new Sort();

                    int list_size, i;

                    // start with empty linked list
                    Sort.Node start = null;

                    // Create linked list from the array arr[].
                    //Created linked list will be 1->11->2->56->12
                    for (i = 0; i < arr.length; i++) {
                        start = Sort.append(start, arr[i]);
                    }

                    start = Sort.bubbleSort(start);
                    ArrayList<Integer> list = Sort.printList(start);

                    Object[] result = list.toArray();
                    JTableUtils.writeArrayToJTable(tableOutput, result);
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "Некорректные данные", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonSaveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                try {
                    if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[] arr = JTableUtils.readIntArrayFromJTable(tableOutput);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeArrayToFile(file, arr);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }
}
