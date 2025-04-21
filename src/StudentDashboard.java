import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.IOException;

class StudentDashboard extends JFrame {
    private Connection con;
    private int studentId;
    private int rollNo;
    private String studentName;
    private JPanel mainPanel;
    private JTable quizTable;
    private DefaultTableModel tableModel;

    public StudentDashboard(int studentId, int rollNo, String studentName) {
        this.studentId = studentId;
        this.rollNo = rollNo;
        this.studentName = studentName;
        this.con = Db.dbconnect();

        setTitle("Student Dashboard");
        setLocation(280, 120);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(new Color(255, 255, 224));

        // Create main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel("Welcome, " + studentName);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        headerPanel.add(welcomeLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create quiz table
        String[] columnNames = {"Quiz ID", "Quiz Name", "Time Limit", "Total Marks", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the Action column is editable
            }
        };
        quizTable = new JTable(tableModel);
        quizTable.setRowHeight(30);
        quizTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // Quiz ID
        quizTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Quiz Name
        quizTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Time Limit
        quizTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Total Marks
        quizTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Action

        // Add scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(quizTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add logout button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            try {
                Welcome_page1 welcomePage = new Welcome_page1();
                welcomePage.setSize(800, 600);
                welcomePage.setVisible(true);
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during logout: " + ex.getMessage());
            }
        });
        buttonPanel.add(logoutButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        loadAvailableQuizzes();

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadAvailableQuizzes() {
        try {
            // Clear existing data
            tableModel.setRowCount(0);

            // Query to get available quizzes
            String sql = "SELECT q.quiz_id, q.quiz_name, q.time_limit, q.total_marks " +
                        "FROM quizzes q " +
                        "WHERE NOT EXISTS (SELECT 1 FROM student_quiz_results sqr " +
                        "WHERE sqr.quiz_id = q.quiz_id AND sqr.student_id = ?) " +
                        "ORDER BY q.quiz_name";
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String quizId = String.valueOf(rs.getInt("quiz_id"));
                String quizName = rs.getString("quiz_name");
                String timeLimit = rs.getInt("time_limit") + " minutes";
                String totalMarks = String.valueOf(rs.getInt("total_marks"));
                String action = "Take Quiz";
                
                tableModel.addRow(new Object[]{quizId, quizName, timeLimit, totalMarks, action});
            }

            // Add action listener for the Take Quiz button
            quizTable.getColumnModel().getColumn(4).setCellRenderer(new QuizButtonRenderer());
            quizTable.getColumnModel().getColumn(4).setCellEditor(new QuizButtonEditor(new JCheckBox(), quizTable, studentId, rollNo, studentName));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading quizzes: " + e.getMessage());
        }
    }
}

class QuizButtonRenderer extends JButton implements TableCellRenderer {
    public QuizButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class QuizButtonEditor extends DefaultCellEditor {
    protected JButton button;
    protected String label;
    protected boolean isPushed;
    protected JTable table;
    protected int studentId;
    protected int rollNo;
    protected String studentName;

    public QuizButtonEditor(JCheckBox checkBox, JTable table, int studentId, int rollNo, String studentName) {
        super(checkBox);
        this.table = table;
        this.studentId = studentId;
        this.rollNo = rollNo;
        this.studentName = studentName;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.getSelectedRow();
            int quizId = Integer.parseInt(table.getValueAt(row, 0).toString());
            
            // Show instructions before starting the quiz
            int confirm = JOptionPane.showConfirmDialog(button,
                "Do you want to start this quiz? You will have " + table.getValueAt(row, 2) + " to complete it.",
                "Start Quiz",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Close the dashboard
                ((JFrame) SwingUtilities.getWindowAncestor(table)).dispose();
                
                // Show instructions page first
                student_instruction_page1 instructions = new student_instruction_page1(studentId, rollNo, studentName, quizId);
                instructions.setSize(1000, 600);
                instructions.setVisible(true);
            }
        }
        isPushed = false;
        return label;
    }
} 