package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

public class HW3 {

	public static void main(String[] args) throws IOException {
		//Build the UI
		JFrame frame = new MainClass();
		frame.setTitle("Yelp Review Search");
		frame.setVisible(true);
		frame.setSize(1500, 1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
	}
}

@SuppressWarnings("serial")
//MainClass is being inherited from JFrame
class MainClass extends JFrame {
	String SUBHEADINGCOLOR = "#99b3ff";
	JList<String> mainCategoryList;
	JList<String> subCategoryList;
	JList<String> resultList;
	JList<String> userresultList;

	String connectionString;
	String query;

	JButton executeQueryButton;
	JButton userQueryButton;
	JButton closeAppButton;

	JTable tableResults;
	JTable usertableResults;
	JTable reviewJTable;
	JTable userreviewJTable;

	JSplitPane resultPane;
	JSplitPane userresultPane;

	JPanel queryPanel;
	JPanel userPanel;

	DefaultTableModel resultTable;
	DefaultTableModel userresultTable;
	DefaultTableModel reviewTable;
	DefaultTableModel userreviewTable;

	JComboBox <String> fromdayComboBox;
	JComboBox <String> ANDOR;
	JComboBox <String> cheats;
	JComboBox <String> toComboBox;
	JComboBox <String> totimeComboBox;
	JComboBox <String> numCheckinComboBox;
	JComboBox <String> startsComboBox;
	JComboBox <String> votesComboBox;
	JComboBox <String> reviewCountLabelComboBox;
	JComboBox <String> friendsCountLabelComboBox;
	JComboBox <String> andOrLabelComboBox;
	JComboBox <String> avgStarComboBox;

	JTextField valueTextField;
	JTextField fromdateTextField;
	JTextField todateTextField;
	JTextField userValueTextField;
	JTextField votesValueTextField;
	JTextField countValueTextField;
	JTextField frndsCountValueTextField;
	JTextField memberSinceTextField;
	JTextField avgStarTextField;

	JSpinner.DateEditor memberSincede;
	JSpinner memberSincespinner;

	JTextArea queryField;

	DefaultListModel<String> addMainCategoryList = new DefaultListModel<String>();
	DefaultListModel<String> addSubCategoryList = new DefaultListModel<String>();
	DefaultListModel<String> addAttributeList = new DefaultListModel<String>();
	DefaultListModel<String> addResultList = new DefaultListModel<String>();
	DefaultListModel<String> addUserResultList = new DefaultListModel<String>();

	//It provides us dynamic arrays in Java. It is helpful in programs where lots
	//of manipulation in the array is needed.
	ArrayList<String> selectedMainCategoryList = new ArrayList<String>();
	ArrayList<String> selectedSubCategoryList = new ArrayList<String>();
	ArrayList<String> selectedAttributeList = new ArrayList<String>();
	ArrayList<String> selectedResultList = new ArrayList<String>();

	//To initialize the connection object to null.
	static Connection con = null;

	//Variable to define prepare statement for main category
	PreparedStatement preparedStatement = null;

	HashMap<Integer,String> bIDMap;
	HashMap<Integer,String> userMap;

	//Class to establish data connection
	public static void connect()
	{
		try {
			//To register the oracle driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			//Use the connection object, since its already created during method creation.
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:orcl", "system", "1234567890");
			if(con != null)
			{
				System.out.println("Connected to Database Successfully!");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	//Class to populate Main Category list

	//Default Constructor to provides the default
	//values to the object like 0, null etc. depending on the type.
	//Class to build UI
	MainClass() throws IOException {

		bIDMap = new HashMap<Integer,String>();
		userMap = new HashMap<Integer,String>();
		//Pane generation for top pane
		//Pane generation for Main Category List
		mainCategoryList = new JList<String>();
		JLabel mainCategoryLabel = new JLabel("Main Categories");
		Font f = mainCategoryLabel.getFont();
		mainCategoryLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel mainCategoryTitle = new JPanel();
		mainCategoryTitle.setBackground(Color.decode(SUBHEADINGCOLOR));
		mainCategoryTitle.add(mainCategoryLabel);
		JScrollPane mainCategoryContent = new JScrollPane();
		JSplitPane mainCategoryPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainCategoryTitle, mainCategoryContent);
		mainCategoryPane.setEnabled(false);
		mainCategoryContent.setViewportView(mainCategoryList);

		//Pane Generation for Sub Category List
		subCategoryList = new JList<>();
		JLabel subCategoryLabel = new JLabel("Sub-Categories");
		subCategoryLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel subCategoryTitle = new JPanel();
		subCategoryTitle.setBackground(Color.decode(SUBHEADINGCOLOR));
		subCategoryTitle.add(subCategoryLabel);
		JScrollPane subCategoryContents = new JScrollPane();
		JSplitPane subCategoryPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, subCategoryTitle, subCategoryContents);
		subCategoryPane.setEnabled(false);
		subCategoryContents.setViewportView(subCategoryList);

		// Pane generation for Check-in
		JPanel CheckinPanel = new JPanel();
		CheckinPanel.setLayout(new BoxLayout(CheckinPanel, BoxLayout.Y_AXIS));
		JLabel CheckinLabel = new JLabel("Checkin");
		CheckinLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel CheckinTitle = new JPanel();
		CheckinTitle.setBackground(Color.decode(SUBHEADINGCOLOR));
		CheckinTitle.add(CheckinLabel);
		JScrollPane CheckinContents = new JScrollPane();
		JSplitPane CheckinPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, CheckinTitle, CheckinContents);
		CheckinPane.setEnabled(false);
		CheckinContents.setViewportView(CheckinPanel);

		JLabel fromLabel = new JLabel("From Day");
		CheckinPanel.add(fromLabel);
		fromdayComboBox = new JComboBox<>();
		fromdayComboBox.addItem("");
		fromdayComboBox.addItem("Monday");
		fromdayComboBox.addItem("Tuesday");
		fromdayComboBox.addItem("Wednesday");
		fromdayComboBox.addItem("Thursday");
		fromdayComboBox.addItem("Friday");
		fromdayComboBox.addItem("Saturday");
		fromdayComboBox.addItem("Sunday");
		CheckinPanel.add(fromdayComboBox);
		fromdayComboBox.setSelectedIndex(0);

		JLabel fromTimeLabel = new JLabel("From Time");
		CheckinPanel.add(fromTimeLabel);
		cheats = new JComboBox<>();
		cheats.addItem("");
		for(int i = 0;i<24;i++) {
			cheats.addItem((Integer.toString(i)));
		}
		CheckinPanel.add(cheats);
		cheats.setSelectedItem(0);

		JLabel toLabel = new JLabel("To Day");
		CheckinPanel.add(toLabel);
		toComboBox = new JComboBox<>();
		toComboBox.addItem("");
		toComboBox.addItem("Monday");
		toComboBox.addItem("Tuesday");
		toComboBox.addItem("Wednesday");
		toComboBox.addItem("Thursday");
		toComboBox.addItem("Friday");
		toComboBox.addItem("Saturday");
		toComboBox.addItem("Sunday");
		CheckinPanel.add(toComboBox);
		toComboBox.setSelectedIndex(0);

		JLabel toTimeLabel = new JLabel("To Time");
		CheckinPanel.add(toTimeLabel);
		totimeComboBox = new JComboBox<>();
		totimeComboBox.addItem("");
		for(int i =0;i<24;i++) {
			totimeComboBox.addItem((Integer.toString(i)));
		}
		CheckinPanel.add(totimeComboBox);
		totimeComboBox.setSelectedItem(0);
		/*

     //To get only time from Jspinner
     Date toTimedate = new Date();
     SpinnerDateModel toTimesm = new SpinnerDateModel(toTimedate, null, null, Calendar.HOUR_OF_DAY);
     JSpinner toTimespinner = new JSpinner(toTimesm);
     JSpinner.DateEditor toTimede = new JSpinner.DateEditor(toTimespinner, "hh:mm");
     toTimespinner.setEditor(toTimede);
     CheckinPanel.add(toTimespinner);
		 */
		JLabel numCheckinLabel = new JLabel("Num. of Checkins:");
		CheckinPanel.add(numCheckinLabel);
		numCheckinComboBox = new JComboBox<>();
		numCheckinComboBox.addItem("=");
		numCheckinComboBox.addItem(">");
		numCheckinComboBox.addItem("<");
		CheckinPanel.add(numCheckinComboBox);
		numCheckinComboBox.setSelectedItem(0);

		JLabel valueLabel = new JLabel("Value:");
		CheckinPanel.add(valueLabel);
		valueTextField = new JTextField();
		CheckinPanel.add(valueTextField);

		//Creating a pane for AND/OR box
		//JPanel andORDropDownPane = new JPanel();
		ANDOR = new JComboBox<>();
		ANDOR.addItem("OR");
		ANDOR.addItem("AND");
		CheckinPanel.add(new JLabel("Search Between Attribute Values: "));
		CheckinPanel.add(ANDOR);
		ANDOR.setSelectedIndex(1);


		// Pane generation for Review
		JPanel reviewPanel = new JPanel();
		reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
		JLabel reviewLabel = new JLabel("Review");
		reviewLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel reviewTitle = new JPanel();
		reviewTitle.setBackground(Color.decode(SUBHEADINGCOLOR));
		reviewTitle.add(reviewLabel);
		JScrollPane reviewContents = new JScrollPane();
		JSplitPane reviewPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, reviewTitle, reviewContents);
		reviewPane.setEnabled(false);
		reviewContents.setViewportView(reviewPanel);

		JLabel fromdateLabel = new JLabel("From Date");
		reviewPanel.add(fromdateLabel);

		//To get only date form JSpinner.
		Date fromdatedate = new Date();
		SpinnerDateModel fromdatesm = new SpinnerDateModel(fromdatedate, null, null, Calendar.HOUR_OF_DAY);
		JSpinner fromdatespinner = new JSpinner(fromdatesm);
		JSpinner.DateEditor fromdatede = new JSpinner.DateEditor(fromdatespinner, "MM-dd-yyyy");
		fromdatespinner.setEditor(fromdatede);
		reviewPanel.add(fromdatespinner);

		JLabel todateLabel = new JLabel("To Date");
		//fromLabel.setBounds(18,20,61,16);
		reviewPanel.add(todateLabel);

		Date todatedate = new Date();
		SpinnerDateModel todatesm = new SpinnerDateModel(todatedate, null, null, Calendar.HOUR_OF_DAY);
		//JSpinner todatespin = new JSpinner(todatesm);
		JSpinner todatespinner = new JSpinner(todatesm);
		JSpinner.DateEditor todatede = new JSpinner.DateEditor(todatespinner, "MM-dd-yyyy");
		todatespinner.setEditor(todatede);
		reviewPanel.add(todatespinner);
		//SpinnerDateModel spinMod = new SpinnerDateModel();

		//todateTextField = new JTextField();
		//reviewPanel.add(todateTextField);
		//reviewPanel.add(todatespin);

		JLabel starsLabel = new JLabel("Stars:");
		reviewPanel.add(starsLabel);

		startsComboBox = new JComboBox<>();
		startsComboBox.addItem("=");
		startsComboBox.addItem(">");
		startsComboBox.addItem("<");

		reviewPanel.add(startsComboBox);
		startsComboBox.setSelectedItem(0);

		JLabel startValueLabel = new JLabel("Value");
		//fromLabel.setBounds(18,20,61,16);
		reviewPanel.add(startValueLabel);

		userValueTextField = new JTextField();
		reviewPanel.add(userValueTextField);

		JLabel votesLabel = new JLabel("Votes:");
		reviewPanel.add(votesLabel);

		votesComboBox = new JComboBox<>();
		votesComboBox.addItem("=");
		votesComboBox.addItem(">");
		votesComboBox.addItem("<");
		reviewPanel.add(votesComboBox);

		JLabel votesValueLabel = new JLabel("Value");
		//fromLabel.setBounds(18,20,61,16);
		reviewPanel.add(votesValueLabel);

		votesValueTextField = new JTextField();
		reviewPanel.add(votesValueTextField);

		// Pane Generation for Business Results
		resultTable = new DefaultTableModel();
		tableResults = new JTable();
		tableResults.setModel(resultTable);
		resultTable.addColumn("Business Name");
		resultTable.addColumn("City");
		resultTable.addColumn("State");
		resultTable.addColumn("Stars");

		JLabel resultlabel = new JLabel("Business Results");
		resultlabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel resultTitle = new JPanel();
		resultTitle.setBackground(Color.decode(SUBHEADINGCOLOR));
		resultTitle.add(resultlabel);
		JScrollPane result = new JScrollPane(tableResults);
		resultPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, resultTitle, result);
		//resultPane.setDividerLocation(40);
		//resultPane.setDividerSize(2);
		resultPane.setEnabled(false);


		//Pane generation for bottom pane
		//Pane Generation for user List
		userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		JLabel userLabel = new JLabel("Users");
		userLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel userTitle = new JPanel();
		userTitle.setBackground(Color.decode(SUBHEADINGCOLOR));
		userTitle.add(userLabel);
		JScrollPane userContents = new JScrollPane();
		JSplitPane userPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, userTitle, userContents);
		//userPane.setDividerLocation(40);
		//userPane.setDividerSize(2);
		userPane.setEnabled(false);
		userContents.setViewportView(userPanel);

		JLabel memberSinceLabel = new JLabel("Member Since:");
		//fromLabel.setBounds(18,20,61,16);
		userPanel.add(memberSinceLabel);
		/*
     Date memberSincedate = new Date();
     SpinnerDateModel memberSincesm = new SpinnerDateModel(memberSincedate, null, null, Calendar.HOUR_OF_DAY);
     memberSincespinner = new JSpinner(memberSincesm);
     memberSincede = new JSpinner.DateEditor(memberSincespinner, "yyyy-MM");
     memberSincespinner.setEditor(memberSincede);
     userPanel.add(memberSincespinner);

		 */
		memberSinceTextField = new JTextField();
		userPanel.add(memberSinceTextField);

		JLabel reviewCountLabel = new JLabel("Review Count:");
		userPanel.add(reviewCountLabel);

		reviewCountLabelComboBox = new JComboBox<>();
		reviewCountLabelComboBox.addItem("=");
		reviewCountLabelComboBox.addItem(">");
		reviewCountLabelComboBox.addItem("<");
		userPanel.add(reviewCountLabelComboBox);

		JLabel countValueLabel = new JLabel("Value");
		userPanel.add(countValueLabel);

		countValueTextField = new JTextField();
		userPanel.add(countValueTextField);

		JLabel freindsCountLabel = new JLabel("Num of Friends:");
		userPanel.add(freindsCountLabel);

		friendsCountLabelComboBox = new JComboBox<>();
		friendsCountLabelComboBox.addItem("=");
		friendsCountLabelComboBox.addItem(">");
		friendsCountLabelComboBox.addItem("<");
		userPanel.add(friendsCountLabelComboBox);

		JLabel freindsValueLabel = new JLabel("Value");
		//fromLabel.setBounds(18,20,61,16);
		userPanel.add(freindsValueLabel);

		frndsCountValueTextField = new JTextField();
		userPanel.add(frndsCountValueTextField);

		JLabel avgStarsLabel = new JLabel("Avg. Stars:");
		userPanel.add(avgStarsLabel);

		avgStarComboBox = new JComboBox<>();
		avgStarComboBox.addItem("=");
		avgStarComboBox.addItem(">");
		avgStarComboBox.addItem("<");
		userPanel.add(avgStarComboBox);

		JLabel avgStarLabel = new JLabel("Value");
		//fromLabel.setBounds(18,20,61,16);
		userPanel.add(avgStarLabel);

		avgStarTextField = new JTextField();
		userPanel.add(avgStarTextField);

		JLabel andOrLabel = new JLabel("Select:");
		userPanel.add(andOrLabel);

		andOrLabelComboBox = new JComboBox<>();
		andOrLabelComboBox.addItem("AND");
		andOrLabelComboBox.addItem("OR");
		userPanel.add(andOrLabelComboBox);

		// Pane for Execute Query Box
		queryField = new JTextArea();

		JLabel queryLabel = new JLabel("Query");
		queryLabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel queryTitle = new JPanel();
		queryTitle.setBackground(Color.decode(SUBHEADINGCOLOR));
		queryTitle.add(queryLabel);
		JScrollPane queryContents = new JScrollPane();
		JSplitPane queryPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, queryTitle, queryContents);
		//queryPane.setDividerLocation(40);
		//queryPane.setDividerSize(2);
		queryPane.setEnabled(false);
		queryContents.setViewportView(queryField);

		//Pane for "execute query" button
		JPanel executePane = new JPanel();
		executeQueryButton = new JButton("Business Query");
		executeQueryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				executeQuerryButtonActionPerformed(evt);
			}
		});
		executePane.add(executeQueryButton);

		userQueryButton = new JButton("User Query");
		userQueryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				userQuerryButtonActionPerformed(evt);
			}
		});
		executePane.add(userQueryButton);

		//Pane for "Exit" button
		//JPanel closePane = new JPanel();
		closeAppButton = new JButton("Exit");
		closeAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		executePane.add(closeAppButton);

		// Pane Generation for User Results
		userresultTable = new DefaultTableModel();
		usertableResults = new JTable();
		usertableResults.setModel(userresultTable);
		userresultTable.addColumn("Name");
		userresultTable.addColumn("Yelping Since");
		userresultTable.addColumn("Stars");

		JLabel userresultlabel = new JLabel("User Results");
		userresultlabel.setFont(new Font(f.getFontName(), Font.PLAIN, 20));
		JPanel userresultTitle = new JPanel();
		userresultTitle.setBackground(Color.decode(SUBHEADINGCOLOR));
		userresultTitle.add(userresultlabel);
		JScrollPane userresult = new JScrollPane(usertableResults);
		userresultPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, userresultTitle, userresult);
		//resultPane.setDividerLocation(40);
		//resultPane.setDividerSize(2);
		userresultPane.setEnabled(false);

		// Creating Top Panes
		// Creating a Pane for Main and Sub Categories
		JSplitPane firstColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainCategoryPane, subCategoryPane);
		firstColumnPane.setDividerLocation(200);
		firstColumnPane.setEnabled(false);

		JSplitPane secondColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, firstColumnPane, CheckinPane);
		secondColumnPane.setDividerLocation(400);
		secondColumnPane.setEnabled(false);

		JSplitPane thirdColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, secondColumnPane, reviewPane);
		thirdColumnPane.setDividerLocation(600);
		thirdColumnPane.setEnabled(false);

		JSplitPane mainTopPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, thirdColumnPane, resultPane );
		mainTopPane.setDividerLocation(800);
		mainTopPane.setEnabled(false);

		// Creating Bottom Panes
		// Creating a Pane for Main and Sub Categories
		JSplitPane buttonSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, queryPane, executePane);
		buttonSplitPane.setDividerLocation(400);;
		buttonSplitPane.setEnabled(false);

		JSplitPane bottomFirstColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userPane, buttonSplitPane);
		bottomFirstColumnPane.setDividerLocation(300);
		bottomFirstColumnPane.setEnabled(false);

		JSplitPane bottomSecondColumnPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bottomFirstColumnPane, userresultPane);
		bottomSecondColumnPane.setDividerLocation(300);

		JSplitPane mainBottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,bottomFirstColumnPane, bottomSecondColumnPane);
		mainBottomPane.setDividerLocation(600);
		mainBottomPane.setEnabled(false);




		JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainTopPane, mainBottomPane);
		mainPane.setDividerLocation(400);
		mainPane.setEnabled(false);
		connectionString = "INTERSECT";
		//connectionString = "AND"

		//FInally adding all the panes to the main pane
		getContentPane().add(mainPane);

		//Establish Connection to database
		connect();

		//Update AND or
		updateUnionIntersect();

		// Populate Main Category List
		populateMainCategoryList();

		//Populate data in Results Column based on SubCategory
		populateResults();
		openReviewFrame();
		//populateUserResult();
		openUserFrame();
	}

	private void executeQuerryButtonActionPerformed(ActionEvent evt) {

		//System.out.println(tableResults.getRowCount());
		if (tableResults.getRowCount() > 0) {
			for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
				resultTable.removeRow(i);
			}
		}

		//System.out.println(cheats.getSelectedIndex() != 0);

		if(selectedSubCategoryList.isEmpty()) {
			if(selectedMainCategoryList.size()!=0){
				System.out.println("First IF");
				//reviewCountLabelComboBox
				String FinalSubQuery = "SELECT BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID\r\n" +
						"FROM B_MAIN_CATEGORY B, BUSINESS BT\r\n" + "WHERE B.BID = BT.BID AND B.C_NAME";

				System.out.println("Second Query");
				for(int i=0;i<selectedMainCategoryList.size();i++) {
					FinalSubQuery += " LIKE '"+selectedMainCategoryList.get(i)+"' "+ connectionString +" SELECT BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID\r\n" +
							"FROM B_MAIN_CATEGORY B, BUSINESS BT\r\n" +
							"WHERE B.BID = BT.BID AND B.C_NAME";
				}

				if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
					FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-131);
				}
				if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
					FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-136);
				}
				System.out.println(FinalSubQuery);

				queryField.setText(FinalSubQuery);


				String[] rowObj = new String[4];
				int i =0;
				try {
					ResultSet rs13 = null;
					preparedStatement=con.prepareStatement(FinalSubQuery);
					rs13 = preparedStatement.executeQuery(FinalSubQuery);

					while(rs13.next())
					{

						rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
						//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
						resultTable.addRow(rowObj);
						bIDMap.put(i++, rs13.getString("BID"));

					}
					preparedStatement.close();
					rs13.close();
				} catch(Exception ex) {
					System.out.println(ex);
				}
			}
		}

		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty()) {
			addResultList.clear();
			String FinalSubQuery = "";
			System.out.println("3rd Query");
			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT DISTINCT BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND BM.C_NAME LIKE '"+selectedMainCategoryList.get(i)+"'";

					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");
			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next())
				{

					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}


		String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
				"Saturday", "Sunday" };
		int startIndexDays = fromdayComboBox.getSelectedIndex();
		int endIndexDays = toComboBox.getSelectedIndex();

		// Fetching the start time and end time starts
		int startIndexTime = cheats.getSelectedIndex();
		int endIndexTime = totimeComboBox.getSelectedIndex();

		ArrayList<String> allDays = new ArrayList<>();
		String daysToString="";
		if (startIndexDays>0) {
			for (int day = startIndexDays-1; day < 100; day++) {
				if (days[day % 7] == days[endIndexDays-1]) {
					allDays.add(days[day % 7]);
					break;
				}
				allDays.add(days[day % 7]);
			}


			for(int h=1;h<allDays.size()-1;h++) {
				daysToString+="'"+allDays.get(h)+"',";
			}
			daysToString = daysToString.replaceAll(",$", "");

		}
		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				fromdayComboBox.getSelectedIndex() != 0 && toComboBox.getSelectedIndex() != 0 &&
				totimeComboBox.getSelectedIndex() != 0 && cheats.getSelectedIndex() != 0){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			System.out.println("Fourth IF");
			queryField.setText(" ");

			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}

			String FinalSubQuery = "";
			System.out.println("4th Query");
			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					if (startIndexDays == endIndexDays) {
						FinalSubQuery += "SELECT DISTINCT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
								"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, B_HOURS BH, CHECK_IN CI, REVIEWS RE \r\n" +
								"WHERE BT.BID = BH.BID AND BT.BID = BS.BID AND BM.BID = BT.BID AND RE.BID = BT.BID AND BH.D_O_W = '"+
								days[startIndexDays - 1] + "' AND BH.FROM_H <= "+ (startIndexTime - 1) +" AND BH.TO_H >= "
								+ (endIndexTime - 1) +"  AND BM.C_NAME LIKE '" +selectedMainCategoryList.get(i)+"'";
						FinalSubQuery += " AND "
								+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
						FinalSubQuery += "\n"+connectionString+"\n";
					} else {
						//int i = startIndexDays - 1, j = endIndexDays - 1;
						int m = startIndexDays - 1, n = endIndexDays - 1;
						System.out.println("5th Query");

						//System.out.println("Fourth IF");
						FinalSubQuery += "SELECT DISTINCT BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
								"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, B_HOURS BH, CHECK_IN CI, REVIEWS RE \r\n" +
								"WHERE BT.BID = BH.BID AND BT.BID = BS.BID AND BM.BID = BT.BID AND RE.BID = BT.BID AND BH.D_O_W = '"+
								days[startIndexDays - 1] + "' AND BH.FROM_H >= "+ (startIndexTime - 1) + "\n"
								+ "UNION \n" +
								"SELECT DISTINCT BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
								"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, B_HOURS BH, CHECK_IN CI, REVIEWS RE \r\n" +
								"WHERE BT.BID = BH.BID AND BT.BID = BS.BID AND BM.BID = BT.BID AND RE.BID = BT.BID AND BH.D_O_W IN "
								+ "("+daysToString+")"+ "\n"
								+ "UNION \n" +
								"SELECT DISTINCT BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
								"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, B_HOURS BH, CHECK_IN CI, REVIEWS RE \r\n" +
								"WHERE BT.BID = BH.BID AND BT.BID = BS.BID AND BM.BID = BT.BID AND RE.BID = BT.BID AND BH.D_O_W = '"+
								days[endIndexDays - 1] + "' AND BH.TO_H <= "+ (endIndexTime - 1);

						FinalSubQuery += " AND BM.C_NAME LIKE '" +selectedMainCategoryList.get(i)+"' AND "
								+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
						FinalSubQuery += "\n"+connectionString+"\n";
					}
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}


		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				fromdayComboBox.getSelectedIndex() == 0 && toComboBox.getSelectedIndex() == 0 &&
				totimeComboBox.getSelectedIndex() != 0 && cheats.getSelectedIndex() != 0){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			//System.out.println("Fourth IF");
			queryField.setText(" ");

			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";
			System.out.println("6th Query");

			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, CHECK_IN CI\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND CI.BID = BT.BID AND CI.CI_HOUR BETWEEN "+
							(startIndexTime - 1) +" AND "+(endIndexTime - 1)+ " AND BM.C_NAME LIKE '" +selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				fromdayComboBox.getSelectedIndex() != 0 && toComboBox.getSelectedIndex() != 0 &&
				totimeComboBox.getSelectedIndex() == 0 && cheats.getSelectedIndex() == 0){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			//System.out.println("Fourth IF");
			System.out.println("7th Query");
			queryField.setText(" ");

			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";

			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, CHECK_IN CI\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND CI.BID = BT.BID AND CI.CI_DAY IN "+
							"(" + daysToString + ")  AND BM.C_NAME LIKE '"+selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				fromdayComboBox.getSelectedIndex() == 0 && toComboBox.getSelectedIndex() == 0 &&
				totimeComboBox.getSelectedIndex() != 0 && cheats.getSelectedIndex() != 0){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			System.out.println("8th IF");
			queryField.setText(" ");

			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";

			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, CHECK_IN CI\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND CI.BID = BT.BID AND CI.CI_HOUR BETWEEN "+
							(startIndexTime - 1) +" AND "+(endIndexTime - 1)+" AND BM.C_NAME LIKE '" + selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				fromdayComboBox.getSelectedIndex() != 0 && toComboBox.getSelectedIndex() != 0 &&
				numCheckinComboBox.getSelectedIndex() != 0){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			System.out.println("9th IF");
			queryField.setText(" ");

			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";
			int checkinCount = Integer.parseInt((String) valueTextField.getText());
			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, CHECK_IN CI\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND CI.BID = BT.BID AND CI.CI_COUNT "+
							numCheckinComboBox.getSelectedItem()+" " +checkinCount + "AND CI.CI_DAY IN "+
							"(" + daysToString + ")  AND BM.C_NAME LIKE '"+selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				fromdayComboBox.getSelectedIndex() == 0 && toComboBox.getSelectedIndex() == 0 &&
				numCheckinComboBox.getSelectedIndex() != 0){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			System.out.println("10th IF");
			queryField.setText(" ");

			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";
			int checkinCount = Integer.parseInt((String) valueTextField.getText());
			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, CHECK_IN CI\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND CI.BID = BT.BID AND CI.CI_COUNT "+
							numCheckinComboBox.getSelectedItem()+" " +checkinCount+" AND BM.C_NAME LIKE '"+selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		int starsCount = 0;
		starsCount = Integer.parseInt((String) userValueTextField.getText());

		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				fromdayComboBox.getSelectedIndex() == 0 && toComboBox.getSelectedIndex() == 0 &&
						startsComboBox.getSelectedIndex() != 0){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			System.out.println("12th IF");
			queryField.setText(" ");
			//int starsCount = Integer.parseInt((String) userValueTextField.getText());
			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";
			//int checkinCount = Integer.parseInt((String) valueTextField.getText());
			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND BT.BID IN "+
							"(SELECT BID FROM REVIEWS RE WHERE STARS " +
							startsComboBox.getSelectedItem() + " " + starsCount + ") AND BM.C_NAME LIKE '" +selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				fromdayComboBox.getSelectedIndex() != 0 && toComboBox.getSelectedIndex() != 0 &&
				startsComboBox.getSelectedIndex() != 0){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			System.out.println("11th IF");
			queryField.setText(" ");
			//int starsCount = Integer.parseInt((String) userValueTextField.getText());
			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";
			//int checkinCount = Integer.parseInt((String) valueTextField.getText());
			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, CHECK_IN CI, REVIEWS RE\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND CI.BID = BT.BID AND CI.CI_DAY IN "+
							"(" + daysToString + ")" + " AND RE.STARS " +
							startsComboBox.getSelectedItem() + starsCount + " AND BM.C_NAME LIKE '" +selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}


		int valueCount = 0;
		String voteBox = votesValueTextField.getText();
		if (! voteBox.equals("")) {
			valueCount = Integer.parseInt(voteBox);
		}
		else {
			valueCount = -1;
		}


		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				starsCount != 0 && valueCount != -1){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			System.out.println("13th IF");
			queryField.setText(" ");
			//int starsCount = Integer.parseInt((String) userValueTextField.getText());
			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";
			//int checkinCount = Integer.parseInt((String) valueTextField.getText());
			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS, REVIEWS RE\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND RE.BID = BT.BID" + " AND RE.STARS " +
							startsComboBox.getSelectedItem() + starsCount + " AND (RE.FUNNY_VOTE + RE.USEFUL_VOTE + RE.COOL_VOTE) " +
							votesComboBox.getSelectedItem() + valueCount +"  AND BM.C_NAME LIKE '" +selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		if(!selectedMainCategoryList.isEmpty() && !selectedSubCategoryList.isEmpty() &&
				valueCount != -1){
			//if(!selectedMainCategoryList.isEmpty() && && (Integer.parseInt((String) valueTextField.getSelectedText()) !=0) && && !selectedSubCategoryList.isEmpty() && fromdayComboBox.getSelectedIndex() != 0 || totimeComboBox.getSelectedIndex() != 0 || cheats.getSelectedIndex() != 0){
			System.out.println("14th IF");
			queryField.setText(" ");
			//int starsCount = Integer.parseInt((String) userValueTextField.getText());
			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}


			String FinalSubQuery = "";
			//int checkinCount = Integer.parseInt((String) valueTextField.getText());
			for(int i=0; i<selectedMainCategoryList.size();i++) {
				for(int j=0;j<selectedSubCategoryList.size();j++) {
					FinalSubQuery += "SELECT  BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
							"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS\r\n" +
							"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND BT.BID IN (SELECT BID FROM REVIEWS RE WHERE " +
							"(RE.FUNNY_VOTE + RE.USEFUL_VOTE + RE.COOL_VOTE)" +
							votesComboBox.getSelectedItem() + valueCount +") AND BM.C_NAME LIKE '" +selectedMainCategoryList.get(i)+"'";
					FinalSubQuery += " AND "
							+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
					FinalSubQuery += "\n"+connectionString+"\n";
				}
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
			}
			if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
				FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
			}
			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i =0;
				String[] rowObj = new String[4];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					resultTable.addRow(rowObj);
					bIDMap.put(i++, rs13.getString("BID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}





	}

	private void populateMainCategoryList() {
		//updateUnionIntersect();
		System.out.println("Inside 1st Query");
		String getMainCategories = "SELECT DISTINCT C_NAME FROM B_MAIN_CATEGORY ORDER BY C_NAME\n";
		//queryField.setText(getMainCategories);
		try {
			ResultSet rs11 = null;
			preparedStatement=con.prepareStatement(getMainCategories);
			rs11 = preparedStatement.executeQuery(getMainCategories);
			int i = 0;
			while(rs11.next())
			{
				if(!addMainCategoryList.contains(rs11.getString("C_NAME")))
				{
					addMainCategoryList.addElement(rs11.getString("C_NAME"));
				}
			}
			preparedStatement.close();
			rs11.close();
		} catch(Exception ex) {
			System.out.println(ex);
		}
		mainCategoryList.setModel(addMainCategoryList);


		MouseListener mainCategoryMouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{

				if (e.getClickCount() == 1)
				{
					//queryField.setText(" ");
					if (tableResults.getRowCount() > 0) {
						for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
							resultTable.removeRow(i);
						}
					}
					selectedMainCategoryList = (ArrayList<String>) mainCategoryList.getSelectedValuesList();
					//System.out.println(selectedGenreList);
					if(selectedMainCategoryList.size()!=0)
					{
						addSubCategoryList.clear();
						addAttributeList.clear();
						addResultList.clear();
						System.out.println("Inside 2nd Query");
						String FinalSubQuery = "SELECT DISTINCT BT.C_NAME\r\n" +
								"FROM B_MAIN_CATEGORY B, B_SUB_CATEGORY BT\r\n" +
								"WHERE B.BID = BT.BID AND B.C_NAME";
						for(int i=0;i<selectedMainCategoryList.size();i++) {
							FinalSubQuery += " LIKE '"+selectedMainCategoryList.get(i)+"' "+ connectionString +" SELECT DISTINCT BT.C_NAME "
									+ "FROM B_MAIN_CATEGORY B, B_SUB_CATEGORY BT WHERE B.BID = BT.BID AND B.C_NAME";

						}
						//  System.out.println(connectionString);
						if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
							FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-107);
						}
						if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
							FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-112);
						}

						try {
							ResultSet rs12 = null;
							preparedStatement=con.prepareStatement(FinalSubQuery);
							rs12 = preparedStatement.executeQuery(FinalSubQuery);

							while(rs12.next())
							{
								if(!addSubCategoryList.contains(rs12.getString("C_NAME")))
								{
									addSubCategoryList.addElement(rs12.getString("C_NAME"));
								}
							}
							preparedStatement.close();
							rs12.close();
						} catch(Exception ex) {
							System.out.println(ex);
						}
						subCategoryList.setModel(addSubCategoryList);
					}
				}
			}
		};
		mainCategoryList.addMouseListener(mainCategoryMouseListener);
	}
	private void populateResults() {
		MouseListener attributeMouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 1)
				{
					if (tableResults.getRowCount() > 0) {
						for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
							resultTable.removeRow(i);
						}
					}

					selectedSubCategoryList = (ArrayList<String>) subCategoryList.getSelectedValuesList();
					//if(selectedSubCategoryList.size()!=0) {
					//if(selectedSubCategoryList.size()==0) {
					//  addResultList.clear();
					String FinalSubQuery = "";
					System.out.println("Inside 3rd Query");
					for(int i=0; i<selectedMainCategoryList.size();i++) {
						for(int j=0;j<selectedSubCategoryList.size();j++) {
							FinalSubQuery += "SELECT DISTINCT BT.B_NAME, BT.CITY, BT.STATE, BT.STARS, BT.BID \r\n" +
									"FROM B_MAIN_CATEGORY BM, BUSINESS BT, B_SUB_CATEGORY BS\r\n" +
									"WHERE BT.BID = BS.BID AND BM.BID = BT.BID AND BM.C_NAME LIKE '"+selectedMainCategoryList.get(i)+"'";

							FinalSubQuery += " AND "
									+ "BS.C_NAME ='"+selectedSubCategoryList.get(j)+"' ";
							FinalSubQuery += "\n"+connectionString+"\n";
						}
					}
					if(selectedMainCategoryList.size()>0 && (connectionString.equals("UNION"))){
						FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-7);
					}
					if(selectedMainCategoryList.size()>0 && (connectionString.equals("INTERSECT"))){
						FinalSubQuery = FinalSubQuery.substring(0, FinalSubQuery.length()-11);
					}
					queryField.setText(FinalSubQuery);
					//System.out.println(connectionString);
					System.out.println("--------");
					System.out.println(FinalSubQuery);
					System.out.println("--------");
					try {
						ResultSet rs13 = null;
						preparedStatement=con.prepareStatement(FinalSubQuery);
						rs13 = preparedStatement.executeQuery(FinalSubQuery);

						String[] rowObj = new String[4];
						int i=0;
						while(rs13.next())
						{
							rowObj = new String[] {rs13.getString("B_NAME"), rs13.getString("CITY"), rs13.getString("STATE"), rs13.getString("STARS")};
							//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
							resultTable.addRow(rowObj);
							System.out.println(rs13.getString("BID"));
							bIDMap.put(i++, rs13.getString("BID"));
						}
						preparedStatement.close();
						rs13.close();
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			//}
		};
		subCategoryList.addMouseListener(attributeMouseListener);
	}

	private void openReviewFrame() {
		tableResults.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JFrame frame = new JFrame("JFrame Example");

					JPanel panel = new JPanel();
					panel.setLayout(new FlowLayout());
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					System.out.println("Inside Query");
					String querry = "SELECT R.R_DATE, R.STARS, R.R_TEXT, Y.USER_NAME, R.USEFUL_VOTE, R.FUNNY_VOTE, R.COOL_VOTE\r\n" +
							"FROM REVIEWS R, BUSINESS B, YELP_USER Y\r\n" +
							"WHERE R.USER_ID = Y.USER_ID AND B.BID = R.BID AND B.BID = '"+bIDMap.get(row)+"'";
					reviewJTable = new JTable();
					reviewJTable.setBounds(300,400,1000,1000);

					reviewTable = new DefaultTableModel();
					reviewJTable.setModel(reviewTable);
					reviewTable.addColumn("Review Date");
					reviewTable.addColumn("Stars");
					reviewTable.addColumn("Review");
					reviewTable.addColumn("User Name");
					reviewTable.addColumn("Useful Vote");
					reviewTable.addColumn("Funny Vote");
					reviewTable.addColumn("Cool Vote");

					JScrollPane reviewResultPane = new JScrollPane(reviewJTable);
					//  frame1.add(reviewResultPane);

					panel.add(reviewResultPane);
					//  panel.add(button);
					String[] rowObj = new String[7];
					try {
						ResultSet rs13 = null;
						preparedStatement=con.prepareStatement(querry);
						rs13 = preparedStatement.executeQuery(querry);

						while(rs13.next())
						{

							rowObj = new String[] {rs13.getString("R_DATE"), rs13.getString("STARS"), rs13.getString("R_TEXT"), rs13.getString("USER_NAME"), rs13.getString("USEFUL_VOTE"), rs13.getString("FUNNY_VOTE"), rs13.getString("COOL_VOTE")};
							//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
							reviewTable.addRow(rowObj);


						}
						preparedStatement.close();
						rs13.close();
					} catch(Exception ex) {
						System.out.println(ex);
					}
					frame.add(panel);
					frame.setSize(1800, 1200);
					frame.setLocationRelativeTo(null);
					//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			}
		});

	}

	private void populateUserResult() {
		MouseListener userMouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{

				//reviewCountLabelComboBox.addMouseListener(new MouseAdapter() {
				//   public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					if (tableResults.getRowCount() > 0) {
						for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
							resultTable.removeRow(i);
						}
					}
				}

				System.out.println("value of user field:" + countValueTextField.getText());
				if((Integer.parseInt((String) countValueTextField.getText())) !=0) {
					queryField.setText(" ");
					if (tableResults.getRowCount() > 0) {
						for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
							resultTable.removeRow(i);
						}
					}

					String FinalSubQuery = "";
					//String day = (String) fromdayComboBox.getSelectedItem();
					//int startTime = Integer.parseInt((String) cheats.getSelectedItem());
					//int endTime = Integer.parseInt((String) totimeComboBox.getSelectedItem());
					//System.out.println(valueTextField.getText());
					int reviewCount = Integer.parseInt((String) countValueTextField.getText());
					//int friendsCountLabelComboBox = Integer.parseInt((String) frndsCountValueTextField.getText());

					System.out.println("Inside 4th Query");
					FinalSubQuery += "SELECT DISTINCT YU.USER_NAME, YU.YELPING_SINCE, YU.AVERAGE_STARS \r\n" +
							"FROM YELP_USER YU \r\n" +
							"WHERE YU.REVIEW_COUNT " + reviewCountLabelComboBox.getSelectedItem() +
							" " + reviewCount;

					//System.out.println(connectionString);
					System.out.println("--------");
					System.out.println(FinalSubQuery);
					System.out.println("--------");

					queryField.setText(FinalSubQuery);

					try {
						ResultSet rs13 = null;
						preparedStatement=con.prepareStatement(FinalSubQuery);
						rs13 = preparedStatement.executeQuery(FinalSubQuery);
						int i =0;
						String[] rowObj = new String[4];

						while(rs13.next()){
							rowObj = new String[] {rs13.getString("USER_NAME"), rs13.getString("YELPING_SINCE"), rs13.getString("AVERAGE_STARS")};
							//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
							resultTable.addRow(rowObj);
							bIDMap.put(i++, rs13.getString("BID"));
						}
						preparedStatement.close();
						rs13.close();
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		};
		userPanel.addMouseListener(userMouseListener);
	}

	private void userQuerryButtonActionPerformed(ActionEvent evt) {

		System.out.println(tableResults.getRowCount());
		if (tableResults.getRowCount() > 0) {
			for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
				resultTable.removeRow(i);
			}
		}

		if((Integer.parseInt((String) countValueTextField.getText())) !=0) {
			queryField.setText(" ");
			if (tableResults.getRowCount() > 0) {
				for (int i = tableResults.getRowCount() - 1; i > -1; i--) {
					resultTable.removeRow(i);
				}
			}

			String FinalSubQuery = "";
			//String day = (String) fromdayComboBox.getSelectedItem();
			//int startTime = Integer.parseInt((String) cheats.getSelectedItem());
			//int endTime = Integer.parseInt((String) totimeComboBox.getSelectedItem());
			//System.out.println(valueTextField.getText());
			int reviewCount = Integer.parseInt((String) countValueTextField.getText());
			int friendCount = Integer.parseInt((String) frndsCountValueTextField.getText());
			int avgCount = Integer.parseInt((String) avgStarTextField.getText());
			//int friendsCountLabelComboBox = Integer.parseInt((String) frndsCountValueTextField.getText());

			System.out.println("Inside 4th Query");
/*
			FinalSubQuery += "SELECT DISTINCT YU.USER_ID AS USERID, YU.USER_NAME AS USER_NAME, YU.YELPING_SINCE AS YELPING_SINCE" +
					", (SELECT AVG(STARS) FROM REVIEWS RX WHERE RX.USER_ID = YU.USER_ID) AS STARS\r\n" +
					"FROM YELP_USER YU\r\n" +
					"WHERE YU.YELPING_SINCE LIKE '" + memberSinceTextField.getText() + "' " +
					andOrLabelComboBox.getSelectedItem() + " YU.REVIEW_COUNT " + reviewCountLabelComboBox.getSelectedItem() +
					" " + reviewCount + " " +
					andOrLabelComboBox.getSelectedItem() + " (SELECT COUNT(F.USER_ID) FROM " +
					" FRIENDS F WHERE F.USER_ID = YU.USER_ID) " + friendsCountLabelComboBox.getSelectedItem() +
					friendCount + " " +
					andOrLabelComboBox.getSelectedItem() + " " + "(SELECT AVG(STARS) FROM REVIEWS RE WHERE " +
					"RE.USER_ID = YU.USER_ID ) " + avgStarComboBox.getSelectedItem() +
					" " + avgCount;
			*/
			FinalSubQuery += "YU.USER_NAME, YU.YELPING_SINCE, YU.AVERAGE_STARS\r\n" +
					"FROM YELP_USER YU\r\n" +
					"WHERE YU.YELPING_SINCE LIKE '" + memberSinceTextField.getText() + "' " +
					andOrLabelComboBox.getSelectedItem() + " YU.REVIEW_COUNT " + reviewCountLabelComboBox.getSelectedItem() +
					" " + reviewCount + " " +
					andOrLabelComboBox.getSelectedItem() + " (SELECT COUNT(F.USER_ID) FROM " +
					" FRIENDS F WHERE F.USER_ID = YU.USER_ID) " + friendsCountLabelComboBox.getSelectedItem() +
					friendCount + " " +
					andOrLabelComboBox.getSelectedItem() + " " + " YU.REVIEW_COUNT " + avgStarComboBox.getSelectedItem() +
					" " + avgCount;

			//System.out.println(connectionString);
			System.out.println("--------");
			System.out.println(FinalSubQuery);
			System.out.println("--------");

			queryField.setText(FinalSubQuery);

			try {
				ResultSet rs13 = null;
				preparedStatement=con.prepareStatement(FinalSubQuery);
				rs13 = preparedStatement.executeQuery(FinalSubQuery);
				int i = 0;
				String[] rowObj = new String[3];

				while(rs13.next()){
					rowObj = new String[] {rs13.getString("USER_NAME"), rs13.getString("YELPING_SINCE"), rs13.getString("AVERAGE_STARS")};
					//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
					userresultTable.addRow(rowObj);
					userMap.put(i++, rs13.getString("USER_ID"));
				}
				preparedStatement.close();
				rs13.close();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void openUserFrame() {
		usertableResults.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JFrame userframe = new JFrame("User JFrame");

					JPanel userpanel = new JPanel();
					userpanel.setLayout(new FlowLayout());
					JTable usertarget = (JTable)e.getSource();
					int row = usertarget.getSelectedRow();
					System.out.println("Inside Query");
					System.out.println(userMap.get(row));

					String userquerry = "SELECT R.R_DATE, R.STARS, R.R_TEXT, Y.USER_NAME, R.USEFUL_VOTE, R.FUNNY_VOTE, R.COOL_VOTE\r\n" +
							"FROM REVIEWS R, YELP_USER Y\r\n" +
							"WHERE R.USER_ID = Y.USER_ID AND Y.USER_NAME LIKE '" + userMap.get(row)+"'";

					userreviewJTable = new JTable();
					userreviewJTable.setBounds(300,400,1000,1000);

					userreviewTable = new DefaultTableModel();
					userreviewJTable.setModel(userreviewTable);
					userreviewTable.addColumn("Review Date");
					userreviewTable.addColumn("Stars");
					userreviewTable.addColumn("Review");
					userreviewTable.addColumn("User Name");
					userreviewTable.addColumn("Useful Vote");
					userreviewTable.addColumn("Funny Vote");
					userreviewTable.addColumn("Cool Vote");

					JScrollPane userreviewResultPane = new JScrollPane(userreviewJTable);
					//  frame1.add(reviewResultPane);

					System.out.println("--------");
					System.out.println(userquerry);
					System.out.println("--------");

					userpanel.add(userreviewResultPane);
					//  panel.add(button);
					String[] rowObj = new String[7];

					try {
						ResultSet rs13 = null;
						preparedStatement=con.prepareStatement(userquerry);
						rs13 = preparedStatement.executeQuery(userquerry);

						while(rs13.next())
						{

							rowObj = new String[] {rs13.getString("R_DATE"), rs13.getString("STARS"), rs13.getString("R_TEXT"), rs13.getString("USER_NAME"), rs13.getString("USEFUL_VOTE"), rs13.getString("FUNNY_VOTE"), rs13.getString("COOL_VOTE")};
							//String data[][] = (String[][]) appendValue(myTwoDimensionalStringArray, rowObj);
							userreviewTable.addRow(rowObj);

						}
						preparedStatement.close();
						rs13.close();
					} catch(Exception ex) {
						System.out.println(ex);
					}
					userframe.add(userpanel);
					userframe.setSize(1800, 1200);
					userframe.setLocationRelativeTo(null);
					//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					userframe.setVisible(true);
				}
			}
		});

	}

	private void updateUnionIntersect() {
		ANDOR.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				populateMainCategoryList();
				addSubCategoryList.clear();
				//addAttributeList.clear();
				addResultList.clear();
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				String selectedBook = (String) combo.getSelectedItem();

				if (selectedBook.equals("AND")) {
					connectionString = "INTERSECT";
				} else if (selectedBook.equals("OR")) {
					connectionString = "UNION";
				}
			}
		});

	}
}
