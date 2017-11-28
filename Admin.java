/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import pages.AdminDashboard;

/**
 *
 * @author Deborah
 */
public class Admin {

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;

    public void connect(Connection con) {
        connection = con;
    }

    public void closeAll() {
        try {
            rs.close();
            statement.close();
            //connection.close();                                         
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public String retrieve(String query) throws SQLException {
        String results = "";
        select(query);
        return makeTable(rsToList(query));//results;
    }

    private String makeTable(ArrayList dataList) throws SQLException {
        StringBuilder b = new StringBuilder();
        String[] row;
        b.append("<table border=\"3\">");
        //display column name to table
        b.append("<tr>");
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            b.append("<th>");
            b.append(rs.getMetaData().getColumnName(i + 1));
            b.append("</th>");
        }
        b.append("</tr>");

        for (Object s : dataList) {
            b.append("<tr>");
            row = (String[]) s;
            for (String row1 : row) {
                b.append("<td>");
                b.append(row1);
                b.append("</td>");
            }
            b.append("</tr>\n");
        } // for
        b.append("</table>");
        return b.toString();
    }//makeHtmlTable

    private ArrayList rsToList(String query) throws SQLException {
        ArrayList aList = new ArrayList();

   statement = connection.createStatement();
      rs = statement.executeQuery(query);
      ResultSetMetaData metaData = rs.getMetaData();
  
        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            String[] s = new String[cols];
            for (int i = 1; i <= cols; i++) {
                s[i - 1] = rs.getString(i);
            }
            aList.add(s);
        } // while   
      return aList;
    } //rsToList

    private void select(String query) {
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {

        }
    }

    public boolean exists(String user) {
        boolean bool = false;
        try {
            select("select username from users where username='" + user + "'");
            if (rs.next()) {
                System.out.println("TRUE");
                bool = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bool;
    }

    public void insert(String[] str) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO Users VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, str[0].trim());
            ps.setString(2, str[1]);
            ps.executeUpdate();

            ps.close();
            System.out.println("1 row added.");
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete(String user) {

        String query = "DELETE FROM Users WHERE \"balance\" = '" + user.trim() + "'";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("way way" + e);
            //results = e.toString();
        }
    }

    public String displayTable(String qry) {
        String msg = "No member";
        try {
            msg = this.retrieve(qry);
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

        return msg;
    }

    public String makeApproveList() {
        StringBuilder b = new StringBuilder();
        try {
            select("SELECT * FROM members WHERE \"balance\"=0 AND \"status\"='APPLIED'");
            while (rs.next()) {
                b.append("<option value=\"" + rs.getString(1) + "\">" + rs.getString(1) + ": " + rs.getString(2) + "</option>");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return b.toString();
    }

    private void updateMembers(String[] str) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("Update members Set \"status\"=? where \"id\"=?", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, str[0].trim());
            ps.setString(2, str[1].trim());
            ps.executeUpdate();

            //reset DOR to upgrade membership date
            ps = connection.prepareStatement("Update members Set \"dor\"=? where \"id\"=?", PreparedStatement.RETURN_GENERATED_KEYS);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calobj = Calendar.getInstance();
            ps.setString(1, df.format(calobj.getTime()));
            ps.setString(2, str[1].trim());
            ps.executeUpdate();
            ps.close();

            System.out.println("1 rows updated.");
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void approveMember(String mem_id) {
        String[] query = new String[2];
        query[0] = "APPROVED";
        query[1] = mem_id;
        updateMembers(query);
    }

    private long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }

    public double getTotalApprovedClaim() {
        select("SELECT SUM(amount) FROM claims WHERE \"status\" = 'APPROVED'");
        double sum = 0;
        try {
            while (rs.next()) {
                sum = rs.getDouble(1);
            }
        } catch (Exception e) {
        }

        return sum;
    }

    public double getAnnualLumpsum() {
        double annualLumpsum = this.getTotalApprovedClaim() / this.getApprovedMemberCount();
        double number = Math.round(annualLumpsum * 100);
        number = number / 100.0;
        return number;
    }

    public int getApprovedMemberCount() {
        select("SELECT COUNT(*) FROM members WHERE \"status\"='APPROVED'");
        int count = 0;
        try {
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return count;
    }

    public void chargeAnnualLumpsum() {
        Double annualLumpsum = this.getAnnualLumpsum();
        PreparedStatement ps = null;

        try {
            //select("SELECT * FROM members WHERE status='APPROVED'");
            HashMap bal = new HashMap();
            
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM members WHERE \"status\"='APPROVED'");

            while (rs.next()) {
                bal.put(rs.getString(1), rs.getDouble(7) + this.getAnnualLumpsum());
            }

            Iterator it = bal.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                ps = connection.prepareStatement("Update members Set \"balance\"=? where \"id\"=?", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, (Double)pair.getValue());
                ps.setString(2, (String)pair.getKey());
                ps.executeUpdate();                
                
                it.remove(); // avoids a ConcurrentModificationException
            }

            //ps.close();
            System.out.println("1 rows updated.");
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String makeSuccessClaimUserList() throws ParseException {
        StringBuilder b = new StringBuilder();
        try {
            select("SELECT * FROM claims WHERE status='SUBMITTED'");
            ResultSet claimsRS = rs;

            while (claimsRS.next()) {
                String id = claimsRS.getString(1);
                String mem_id = claimsRS.getString(2);
                Calendar claimDate = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                claimDate.setTime(sdf.parse(claimsRS.getString(3)));

                select("SELECT * FROM members WHERE \"id\" ='" + mem_id + "'");
                String memStatus = "";
                Calendar dor = Calendar.getInstance();
                while (rs.next()) {
                    memStatus = rs.getString(6);
                    dor = Calendar.getInstance();
                    dor.setTime(sdf.parse(rs.getString(5)));
                }

                //check how many claims a member has made
                select("SELECT COUNT(*) FROM claims WHERE mem_id='" + mem_id + "'");
                int count = 0;
                while (rs.next()) {
                    count = rs.getInt(1);
                }

                if (memStatus.equals("APPROVED")) {
                    if (daysBetween(dor, claimDate) >= 183) {
                        if (count <= 2) {
                            System.out.println("you can claim");
                            b.append("<option value=\"" + id + "\">" + id + ": " + mem_id + "</option>");
                        } else {
                            System.out.println("you kenot claim");
                        }
                    } else {

                        System.out.println("membership less than 6 months");
                    }
                } else {
                    System.out.println(memStatus);
                    System.out.println("you're no member");
                }

            }//while

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return b.toString();
    }

    public String makeFailedClaimUserList() throws ParseException {
        StringBuilder b = new StringBuilder();
        try {
            select("SELECT * FROM claims WHERE status='SUBMITTED'");
            ResultSet claimsRS = rs;

            while (claimsRS.next()) {
                String id = claimsRS.getString(1);
                String mem_id = claimsRS.getString(2);
                Calendar claimDate = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                claimDate.setTime(sdf.parse(claimsRS.getString(3)));

                select("SELECT * FROM members WHERE \"id\" ='" + mem_id + "'");
                String memStatus = "";
                Calendar dor = Calendar.getInstance();
                while (rs.next()) {
                    memStatus = rs.getString(6);
                    dor = Calendar.getInstance();
                    dor.setTime(sdf.parse(rs.getString(5)));
                }

                //check how many claims a member has made
                select("SELECT COUNT(*) FROM claims WHERE mem_id='" + mem_id + "'");
                int count = 0;
                while (rs.next()) {
                    count = rs.getInt(1);
                }

                if (memStatus.equals("APPLIED")) {
                    b.append("<option value=\"" + id + "\">" + id + ": " + mem_id + " is still a provisional member" + "</option>");
                } else if (daysBetween(dor, claimDate) < 183) {
                    b.append("<option value=\"" + id + "\">" + id + ": " + mem_id + "'s membership is not over 6 months" + "</option>");
                } else if (count > 2) {
                    b.append("<option value=\"" + id + "\">" + id + ": " + mem_id + " has already claimed twice this year" + "</option>");
                }

            }//while

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return b.toString();
    }

    private void updateClaim(String str, int id) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("Update claims Set \"status\"=? where \"id\"=?", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, str.trim());
            ps.setInt(2, id);
            ps.executeUpdate();

            ps.close();
            System.out.println("1 rows updated.");
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void processClaim(String claim_id, String decision) {
        String query = decision;
        int id = Integer.parseInt(claim_id);
        updateClaim(query, id);
    }

    public static void main(String[] args) throws SQLException {
        Statement statement = null;
        ResultSet rs = null;
        String str = "SELECT COUNT(*) FROM claims WHERE mem_id='er-yew'";
        String insert = "INSERT INTO `Users` (`username`, `password`) VALUES ('meaydin', 'meaydin')";
        String update = "UPDATE `Users` SET `password`='eaydin' WHERE `username`='eaydin' ";
        String db = "mydb";

        Admin admin = new Admin();
        Connection conn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + db.trim(), "mydb", "mydb");
        } catch (ClassNotFoundException | SQLException e) {

        }
        admin.connect(conn);

        try {
            //System.out.println(admin.makeClaimUserList());
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM members WHERE status='APPROVED'");
            //admin.select("SELECT * FROM claims WHERE status='APPROVED'");
//            while(rs.next()){
//                System.out.println(rs.getString(1));
//            }

//            while(rs.next()){
//                int count = rs.getInt(1);
//                System.out.println(count);
//                //System.out.println(rs.getString(1) + rs.getString(2)+ rs.getString(3) +rs.getString(4) +rs.getString(5)+rs.getString(6));
//                //System.out.println(rs.getMetaData().getColumnCount());
//            }
        System.out.println("*****************");
        //System.out.println(admin.getTotalApprovedClaim());
        System.out.println(admin.makeSuccessClaimUserList());
        System.out.println("---");
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        //admin.chargeAnnualLumpsum();
        
        

        admin.closeAll();
    }

}
