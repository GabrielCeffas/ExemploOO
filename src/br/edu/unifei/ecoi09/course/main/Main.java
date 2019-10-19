package br.edu.unifei.ecoi09.course.main;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    JFrame frame = new JFrame("SIGAA");
    private static List<Course> courseList = new ArrayList<>();
    private static List<Person> people = new ArrayList<>();
    private int cont = 0;
    private JPanel initialMainPanel;
    private JButton addNewCourseButton;
    private JButton addNewStudentButton;
    private JButton addNewTeacherButton;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JLabel screenLabel;
    private JPanel centerPanel;
    private JButton showStudentsButton;
    private JButton showTeachersButton;
    private JComboBox courseListBox;
    private JButton removeCourseButton;
    private JButton showCoordinatorsButton;
    StudentScreen StudentPanel = new StudentScreen();
    TeacherScreen TeacherPanel = new TeacherScreen();
    AddCourseScreen AddCoursePanel = new AddCourseScreen();
    addStudentScreen AddStudentPanel = new addStudentScreen();
    JPanel cards;
    final static String STUDENTPANEL = "Student List Panel";
    final static String TEACHERPANEL = "Teacher List Panel";
    final static String MAINSPANEL = "Main panel";
    final static String ADDCOURSEPANEL = "Add course Panel";
    final static String ADDSTUDENTPANEL = "Add new Student";
    Course empty = new Course.CourseBuilder("Empty", "").totalHours(0).build();

    public Main() {


        StudentPanel.getNextButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cont < people.size() - 1) {
                    cont++;
                    while (!(people.get(cont) instanceof Student) && cont < people.size() - 1) {
                        cont++;
                    }
                    changeStudent();
                    if (!(people.get(cont) instanceof Student)) {
                        cont--;
                    }
                }
            }
        });

        StudentPanel.getPreviousButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cont > 0) {
                    cont--;
                    while (!(people.get(cont) instanceof Student) && cont > 0) {
                        cont--;
                    }

                    changeStudent();
                }
            }
        });
        StudentPanel.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(MAINSPANEL);
                cont = 0;
            }
        });
        StudentPanel.getAddCourseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student s = (Student) people.get(cont);
                if (!(s.getCourseList().equals(courseList)) && !(s.getCourseList().get(0).equals(empty) && courseList.get(0).equals(empty))) {
                    List<Course> courses = new ArrayList<>();
                    for (int i = 0; i < courseList.size(); i++) {
                        courses.add(courseList.get(i));
                    }
                    for (int i = 0; i < s.getCourseList().size(); i++) {
                        courses.remove(s.getCourseList().get(i));
                    }
                    Course c = (Course) JOptionPane.showInputDialog(frame,
                            "Add Course to student",
                            "Add Course",
                            JOptionPane.QUESTION_MESSAGE,
                            null, courses.toArray()
                            , null);
                    if (c != null) {
                        s.addCourse(c);
                        StudentPanel.getCourseListBox().addItem(c.toString());
                        if (s.getCourseList().get(0).equals(empty)) {
                            StudentPanel.getCourseListBox().removeAllItems();
                            s.removeCourse(empty);
                        }
                    }

                }
                changeStudent();
            }
        });


        StudentPanel.getRemoveCourseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = (Student) people.get(cont);
                if (StudentPanel.getCourseListBox().getItemAt(0) != "Empty") {
                    Course c = student.getCourseList().get(StudentPanel.getCourseListBox().getSelectedIndex());
                    student.removeCourse(c);
                    if (student.getCourseList().isEmpty()) {
                        StudentPanel.getCourseListBox().addItem("Empty");
                        student.addCourse(empty);
                    }
                }
                changeStudent();
            }
        });

        TeacherPanel.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(MAINSPANEL);
            }
        });
        AddCoursePanel.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(MAINSPANEL);
            }
        });
        AddStudentPanel.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!AddStudentPanel.getNameTextField().getText().equals("") && //check if the textfields arent empty
                        !AddStudentPanel.getCpfField1().getText().equals("") &&
                        !AddStudentPanel.getIndexTextField().getText().equals("")) {
                    Student s = new Student.StudentBuilder(AddStudentPanel.getCpfField1().getText(),
                            AddStudentPanel.getNameTextField().getText())
                            .birthDate(AddStudentPanel.getDayCombobox().getSelectedItem().toString() + "/" +
                                    AddStudentPanel.getMonthCombobox().getSelectedItem().toString() + "/" +
                                    AddStudentPanel.getYearCombobox().getSelectedItem().toString())
                            .totalIndex(Float.parseFloat(AddStudentPanel.getIndexTextField().getText()))
                            .build();
                    s.addCourse(empty);
                    people.add(s);
                    changeScreen(MAINSPANEL);

                }

            }
        });
        AddStudentPanel.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(MAINSPANEL);
            }
        });
        AddCoursePanel.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!AddCoursePanel.getIdTextField().getText().equals("") && //check if the textfields arent empty
                        !AddCoursePanel.getTotalHoursTextField().getText().equals("") &&
                        !AddCoursePanel.getDescriptionTextField().getText().equals("")) {

                    courseList.add(
                            new Course.CourseBuilder(AddCoursePanel.getIdTextField().getText(),
                                    " - " + AddCoursePanel.getDescriptionTextField().getText())
                                    .totalHours(Integer.parseInt(AddCoursePanel.getTotalHoursTextField().getText()))
                                    .build());

                    if (courseListBox.getItemAt(0).equals("Empty")) {
                        courseListBox.removeAllItems();
                        courseList.remove(0);
                    }
                    courseListBox.addItem(courseList.get(courseList.size() - 1).toString());

                    changeScreen(MAINSPANEL);

                }
            }
        });
        showStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(STUDENTPANEL);
                changeStudent();
            }
        });

        showTeachersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(TEACHERPANEL);
            }
        });

        removeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courseListBox.getItemAt(0) != "Empty") {
                    Course c = courseList.get(courseListBox.getSelectedIndex());
                    courseList.remove(courseListBox.getSelectedIndex());
                    courseListBox.removeItem(courseListBox.getSelectedItem());
                    for (int i = 0; (i < people.size() - 1); i++) {
                        if (people.get(i) instanceof Student) {
                            Student student = (Student) people.get(i);
                            student.removeCourse(c);
                            if (student.getCourseList().isEmpty()) {
                                student.addCourse(empty);
                            }
                        }
                        if (people.get(i) instanceof Teacher) {
                            Teacher teacher = (Teacher) people.get(i);
                            teacher.removeCourse(c);
                        }
                    }
                }
                if (courseListBox.getItemCount() == 0) {
                    courseListBox.addItem("Empty");
                    courseList.add(empty);
                }
            }
        });
        addNewCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(ADDCOURSEPANEL);
                AddCoursePanel.getIdTextField().setText("");
                AddCoursePanel.getTotalHoursTextField().setText("");
                AddCoursePanel.getDescriptionTextField().setText("");

            }
        });
        addNewStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(ADDSTUDENTPANEL);
                AddStudentPanel.getCpfField1().setText("");
                AddStudentPanel.getNameTextField().setText("");
                AddStudentPanel.getIndexTextField().setText("");
                AddStudentPanel.getDayCombobox().setSelectedIndex(0);
                AddStudentPanel.getYearCombobox().setSelectedIndex(0);
                AddStudentPanel.getMonthCombobox().setSelectedIndex(0);

            }
        });
    }

    public void changeScreen(String panel) {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, panel);
    }

    public void changeStudent() {

        if (people.get(cont) instanceof Student) {
            StudentPanel.getCourseListBox().removeAllItems();
            Student student = (Student) people.get(cont);
            StudentPanel.getNameLabel().setText(student.getName());
            StudentPanel.getCpfLabel().setText(student.getCPF());
            StudentPanel.getIndexLabel().setText(Double.toString(student.getTotalIndex()));
            StudentPanel.getBirthdateLabel().setText(student.getBirthDate());
            StudentPanel.getNameLabel1().setText(student.getName());
            for (Course c : student.getCourseList()) {
                StudentPanel.getCourseListBox().addItem(c.toString());
            }
        }
    }

    public void buildDate() {
        for (int i = 2017; i >= 1900; i--) {
            AddStudentPanel.getYearCombobox().addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            AddStudentPanel.getMonthCombobox().addItem(i);
        }
        for (int i = 1; i <= 31; i++) {
            AddStudentPanel.getDayCombobox().addItem(i);
        }
    }

    public static void build() {
        JFrame frame = new JFrame("SIGAA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(740, 350));
        Main gui = new Main();
        gui.addComponentToPane(frame.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        gui.buildDate();
    }

    public void addComponentToPane(Container pane) {
        for (Course c : courseList) {
            courseListBox.addItem(c.toString());
        }
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(initialMainPanel, MAINSPANEL);
        cards.add(StudentPanel.getStudentMainPanel(), STUDENTPANEL);
        cards.add(TeacherPanel.getTeacherMainPanel(), TEACHERPANEL);
        cards.add(AddCoursePanel.getMainPanel(), ADDCOURSEPANEL);
        cards.add(AddStudentPanel.getMainPanel(), ADDSTUDENTPANEL);
        pane.add(cards, BorderLayout.CENTER);
    }


    public static void main(String[] args) {

        courseList.add(
                new Course.CourseBuilder("ECOI09",
                        " - Linguagens de Programação")
                        .totalHours(64)
                        .build());
        courseList.add(
                new Course.CourseBuilder("ECO044.1",
                        " - Programação de Dispositivos Móveis")
                        .totalHours(16)
                        .build());
        courseList.add(
                new Course.CourseBuilder("ECO044.2",
                        " - Programação de Dispositivos Móveis")
                        .totalHours(48)
                        .build());

        Student john_doe = new Student.StudentBuilder("111.111.111-11", "John Doe")
                .birthDate("12/12/2012")
                .totalIndex(10.0)
                .build();

        john_doe.addCourse(courseList.get(0));
        Student jane_doe = new Student.StudentBuilder("222.222.222-22", "Jane Doe")
                .birthDate("11/11/2011")
                .totalIndex(9)
                .build();
        jane_doe.addCourse(courseList.get(1));
        jane_doe.addCourse(courseList.get(2));

        Teacher alan_turing = new Teacher.TeacherBuilder("999.999.999-99", "Alan Turing")
                .salary(1000000)
                .build();
        alan_turing.addCourse(courseList.get(0));
        alan_turing.addCourse(courseList.get(1));
        people.add(john_doe);
        people.add(jane_doe);
        people.add(alan_turing);
        build();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        initialMainPanel = new JPanel();
        initialMainPanel.setLayout(new BorderLayout(0, 0));
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bottomPanel.setBackground(new Color(-6376005));
        bottomPanel.setEnabled(true);
        bottomPanel.setForeground(new Color(-4474968));
        bottomPanel.setInheritsPopupMenu(false);
        bottomPanel.setOpaque(true);
        bottomPanel.setToolTipText("");
        initialMainPanel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Add", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, bottomPanel.getFont()), new Color(-16777216)));
        addNewCourseButton = new JButton();
        addNewCourseButton.setText("Add new Course");
        addNewCourseButton.setMnemonic('A');
        addNewCourseButton.setDisplayedMnemonicIndex(0);
        bottomPanel.add(addNewCourseButton);
        addNewStudentButton = new JButton();
        addNewStudentButton.setText("Add new Student");
        addNewStudentButton.setMnemonic('A');
        addNewStudentButton.setDisplayedMnemonicIndex(0);
        bottomPanel.add(addNewStudentButton);
        addNewTeacherButton = new JButton();
        addNewTeacherButton.setText("Add new Teacher");
        addNewTeacherButton.setMnemonic('A');
        addNewTeacherButton.setDisplayedMnemonicIndex(0);
        bottomPanel.add(addNewTeacherButton);
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        topPanel.setBackground(new Color(-6376005));
        initialMainPanel.add(topPanel, BorderLayout.NORTH);
        screenLabel = new JLabel();
        screenLabel.setText("Main Screen");
        topPanel.add(screenLabel);
        centerPanel = new JPanel();
        centerPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        centerPanel.setBackground(new Color(-8997701));
        initialMainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Display", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, centerPanel.getFont()), new Color(-16777216)));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.setBackground(new Color(-8997701));
        centerPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Course List:");
        panel1.add(label1);
        courseListBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        courseListBox.setModel(defaultComboBoxModel1);
        panel1.add(courseListBox);
        removeCourseButton = new JButton();
        removeCourseButton.setText("Remove Course");
        removeCourseButton.setMnemonic('R');
        removeCourseButton.setDisplayedMnemonicIndex(0);
        panel1.add(removeCourseButton);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-8997701));
        centerPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        showStudentsButton = new JButton();
        showStudentsButton.setHideActionText(false);
        showStudentsButton.setText("Show Students");
        showStudentsButton.setMnemonic('S');
        showStudentsButton.setDisplayedMnemonicIndex(0);
        panel2.add(showStudentsButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showTeachersButton = new JButton();
        showTeachersButton.setText("Show Teachers");
        showTeachersButton.setMnemonic('S');
        showTeachersButton.setDisplayedMnemonicIndex(0);
        panel2.add(showTeachersButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showCoordinatorsButton = new JButton();
        showCoordinatorsButton.setText("Show Coordinators");
        showCoordinatorsButton.setMnemonic('S');
        showCoordinatorsButton.setDisplayedMnemonicIndex(0);
        panel2.add(showCoordinatorsButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return initialMainPanel;
    }
}