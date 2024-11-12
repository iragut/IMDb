package org.example.entities;

import lombok.*;
import org.example.interfaces.InterfaceMethodes;
import org.example.user.User;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

@AllArgsConstructor
public class Actor implements Comparable<Object>, SortedInterface {
    @Setter
    private String name;
    @Getter @Setter
    private List<Performance> performances;
    @Getter @Setter
    private String biography;
    private JButton addFavorite, removeFavorite;

    public Actor() {
        List<Performance> performances = new ArrayList<>();
        this.setPerformances(performances);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return this.name.compareTo(((Actor) o).name);
    }

    @SuppressWarnings("unchecked")
    public void displayInfoUI(User<?> user) {
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(700, 500, "Actor");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel title = interfaceMethodes.createLabel(this.getName(), 20, 5, 500, 50, "#E1DE07");
        title.setFont(new Font("Impact Regular", Font.BOLD, 25));

        if (this.getBiography() != null) {
            JTextArea biography = new JTextArea(this.getBiography());
            biography.setBounds(20, 50, 650, 70);
            biography.setFont(new Font("Impact Regular", Font.BOLD, 13));
            biography.setLineWrap(true);
            biography.setEditable(false);
            biography.setWrapStyleWord(true);
            biography.setBackground(Color.decode("#000011"));
            biography.setForeground(Color.WHITE);

            frame.add(biography);
        }
        JLabel performances = interfaceMethodes.createLabel("Performances: ", 20, 120, 550, 20, "#E1DE07");
        performances.setFont(new Font("Impact Regular", Font.BOLD, 15));

        int y = 130;
        for (Performance performance : this.getPerformances()) {
            JLabel performanceLabel = interfaceMethodes.createLabel(performance.getTitle() + " - " + performance.getType(), 20, y, 500, 50, "#ffffff");
            performanceLabel.setFont(new Font("Impact Regular", Font.BOLD, 15));
            y += 20;
            frame.add(performanceLabel);
        }

        removeFavorite = interfaceMethodes.createButton("Rm from favorites", 500, 400, 150, 30, "#2F4F4F", "#F53F20", null);
        removeFavorite.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        addFavorite = interfaceMethodes.createButton("Add to favorites", 500, 400, 150, 30, "#2F4F4F", "#E1DE07", null);
        addFavorite.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        removeFavorite.addActionListener(e -> {
            SortedSet<Object> favorites = (SortedSet<Object>) user.getFavorites();
            favorites.remove(this);
            JOptionPane.showMessageDialog(null, "Actor removed from favorites!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.remove(removeFavorite);
            frame.add(addFavorite);
            frame.repaint();
        });

        addFavorite.addActionListener(e -> {
            SortedSet<Object> favorites = (SortedSet<Object>) user.getFavorites();
            favorites.add(this);
            JOptionPane.showMessageDialog(null, "Actor added to favorites!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.remove(addFavorite);
            frame.add(removeFavorite);
            frame.repaint();
        });

        if (user.getFavorites().contains(this)){
            frame.add(removeFavorite);
        } else {
            frame.add(addFavorite);
        }

        frame.add(title);
        frame.add(performances);
        frame.setVisible(true);
    }


    public void updateActorUI(){
        InterfaceMethodes interfaceMethodes = new InterfaceMethodes();
        JFrame frame = interfaceMethodes.createFrame(700, 500, "Update actor");
        frame.getContentPane().setBackground(Color.decode("#000011"));

        JLabel nameLabel = interfaceMethodes.createLabel("Name:", 100, 30, 100, 50, "#E1DE07");
        nameLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JLabel biographyLabel = interfaceMethodes.createLabel("Biography:", 100, 250, 200, 50, "#E1DE07");
        biographyLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JLabel performancesLabel = interfaceMethodes.createLabel("Performances:", 400, 30, 200, 50, "#E1DE07");
        performancesLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

        JTextField name = new JTextField(this.getName());
        name.setBounds(100, 80, 200, 30);
        name.setFont(new Font("Impact Regular", Font.BOLD, 15));
        name.setBackground(Color.DARK_GRAY);
        name.setForeground(Color.WHITE);
        name.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        JTextArea biography = new JTextArea(this.getBiography());
        biography.setBounds(100, 300, 475, 100);
        biography.setLineWrap(true);
        biography.setWrapStyleWord(true);
        biography.setBackground(Color.DARK_GRAY);
        biography.setForeground(Color.WHITE);
        biography.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        biography.setFont(new Font("Impact Regular", Font.BOLD, 13));

        DefaultListModel<String> performanceList = new DefaultListModel<>();
        for (Performance performance : this.getPerformances()){
            performanceList.addElement(performance.getTitle() + " - " + performance.getType());
        }
        JList<String> performanceJList = new JList<>(performanceList);
        performanceJList.setBounds(375, 80, 200, 200);
        performanceJList.setFont(new Font("Impact Regular", Font.BOLD, 14));
        performanceJList.setBackground(Color.DARK_GRAY);
        performanceJList.setForeground(Color.WHITE);
        performanceJList.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

        JScrollPane scrollPane = new JScrollPane(performanceJList);
        scrollPane.setBounds(375, 80, 200, 200);
        scrollPane.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Color.DARK_GRAY);
        scrollPane.setForeground(Color.WHITE);

        performanceJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && performanceJList.getSelectedIndex() != -1) {
                JFrame frame1 = interfaceMethodes.createFrame(300, 400, "Update performance");
                frame1.getContentPane().setBackground(Color.decode("#000011"));
                Performance performance = new Performance();

                JLabel titleLabel = interfaceMethodes.createLabel("Title:", 20, 30, 100, 50, "#E1DE07");
                titleLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

                JLabel typeLabel = interfaceMethodes.createLabel("Type:", 20, 130, 100, 50, "#E1DE07");
                typeLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

                JTextField title = new JTextField(performanceJList.getSelectedValue().split(" - ")[0]);
                title.setBounds(20, 80, 250, 30);
                title.setFont(new Font("Impact Regular", Font.BOLD, 15));
                title.setBackground(Color.DARK_GRAY);
                title.setForeground(Color.WHITE);
                title.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

                JTextField type = new JTextField(performanceJList.getSelectedValue().split(" - ")[1]);
                type.setBounds(20, 180, 250, 30);
                type.setFont(new Font("Impact Regular", Font.BOLD, 15));
                type.setBackground(Color.DARK_GRAY);
                type.setForeground(Color.WHITE);
                type.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

                JButton update = interfaceMethodes.createButton("Update", 20, 230, 100, 30, "#2F4F4F", "#E1DE07", null);
                update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                update.addActionListener(e1 -> {
                    if (title.getText().isEmpty() || type.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        performance.setTitle(title.getText());
                        performance.setType(type.getText());
                        performanceList.setElementAt(performance.getTitle() + " - " + performance.getType(), performanceJList.getSelectedIndex());
                        frame.repaint();
                        frame1.dispose();
                    }
                });
                JButton delete = interfaceMethodes.createButton("Delete", 150, 230, 100, 30, "#2F4F4F", "#E1DE07", null);
                delete.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
                delete.addActionListener(e1 -> {
                    performanceList.remove(performanceJList.getSelectedIndex());
                    frame.repaint();
                    frame1.dispose();
                });

                frame1.add(titleLabel);
                frame1.add(typeLabel);
                frame1.add(title);
                frame1.add(type);
                frame1.add(update);
                frame1.add(delete);
                frame1.setVisible(true);
            }
        });

        // Add performance
        JButton addPerformance = interfaceMethodes.createButton("+", 580, 80, 30, 30, "#2F4F4F", "#E1DE07", null);
        addPerformance.setBorder(new LineBorder(Color.decode("#48E5FB"), 1));
        addPerformance.addActionListener(e -> {
            JFrame frame1 = interfaceMethodes.createFrame(300, 400, "Update performance");
            frame1.getContentPane().setBackground(Color.decode("#000011"));
            Performance performance = new Performance();

            JLabel titleLabel = interfaceMethodes.createLabel("Title:", 20, 30, 100, 50, "#E1DE07");
            titleLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

            JLabel typeLabel = interfaceMethodes.createLabel("Type:", 20, 130, 100, 50, "#E1DE07");
            typeLabel.setFont(new Font("Impact Regular", Font.BOLD, 20));

            JTextField title = new JTextField();
            title.setBounds(20, 80, 250, 30);
            title.setFont(new Font("Impact Regular", Font.BOLD, 15));
            title.setBackground(Color.DARK_GRAY);
            title.setForeground(Color.WHITE);
            title.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            JTextField type = new JTextField();
            type.setBounds(20, 180, 250, 30);
            type.setFont(new Font("Impact Regular", Font.BOLD, 15));
            type.setBackground(Color.DARK_GRAY);
            type.setForeground(Color.WHITE);
            type.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));

            JButton update = interfaceMethodes.createButton("Add", 100, 230, 100, 30, "#2F4F4F", "#E1DE07", null);
            update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
            update.addActionListener(e1 -> {
                if (title.getText().isEmpty() || type.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    performance.setTitle(title.getText());
                    performance.setType(type.getText());
                    performanceList.addElement(performance.getTitle() + " - " + performance.getType());
                    frame.repaint();
                    frame1.dispose();
                }
            });
            frame1.add(titleLabel);
            frame1.add(typeLabel);
            frame1.add(title);
            frame1.add(type);
            frame1.add(update);
            frame1.setVisible(true);

        });

        JButton update = interfaceMethodes.createButton("Update", 100, 420, 100, 30, "#2F4F4F", "#E1DE07", null);
        update.setBorder(new LineBorder(Color.decode("#E1DE07"), 1));
        update.addActionListener(e -> {
            if (name.getText().isEmpty() || biography.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                this.setName(name.getText());
                this.setBiography(biography.getText());
                if (!performanceList.isEmpty()){
                    List<Performance> performances = new ArrayList<>();
                    for (int i = 0; i < performanceList.size(); i++){
                        String[] performanceInfo = performanceList.get(i).split(" - ");
                        Performance performance = new Performance();
                        performance.setTitle(performanceInfo[0]);
                        performance.setType(performanceInfo[1]);
                        performances.add(performance);
                    }
                    this.setPerformances(performances);
                } else {
                    this.setPerformances(new ArrayList<>());
                }
                frame.dispose();
            }
        });

        frame.add(nameLabel);
        frame.add(addPerformance);
        frame.add(performancesLabel);
        frame.add(scrollPane);
        frame.add(name);
        frame.add(addPerformance);
        frame.add(biographyLabel);
        frame.add(biography);
        frame.add(update);
        frame.setVisible(true);
}

    public void displayInfo() {
        System.out.println("Name: " + this.getName());
        System.out.println("Biography: " + this.getBiography());
        System.out.println("Performances: ");
        for (Performance performance : this.getPerformances()){
            System.out.println("Movie name: " + performance.getTitle());
            System.out.println("Type: " + performance.getType());
        }
        System.out.print("-".repeat(64));
        System.out.println();
    }

}
