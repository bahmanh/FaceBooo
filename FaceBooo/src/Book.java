/**
 * @author Bahman Hayat
 */
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;


public class Book {

	protected Shell shell;
	private Text user;
	private String name, gender, locale, username;
	private String imageUrl;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Book window = new Book();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("FaceBooo");
		
		user = new Text(shell, SWT.BORDER);
		user.setBounds(10, 31, 77, 19);
		
		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setBounds(10, 11, 77, 14);
		lblUsername.setText("Username");
		
		final Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		nameLabel.setBounds(150, 10, 169, 14);
		nameLabel.setText("Name: ");
		
		final Label genderLabel = new Label(shell, SWT.NONE);
		genderLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		genderLabel.setBounds(150, 30, 130, 14);
		genderLabel.setText("Gender: ");
		
		final Label localeLabel = new Label(shell, SWT.NONE);
		localeLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		localeLabel.setBounds(150, 50, 130, 14);
		localeLabel.setText("Locale: ");
		
		final Label usernameLabel = new Label(shell, SWT.NONE);
		usernameLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		usernameLabel.setBounds(150, 70, 130, 14);
		usernameLabel.setText("Username: ");
		
		Button btnEnter = new Button(shell, SWT.NONE);
		btnEnter.addSelectionListener(new SelectionAdapter() {
	
			String userN;
			public void widgetSelected(SelectionEvent e) {
				userN = user.getText();
				httpReq(userN, false);
				imageDisplay(userN);
				nameLabel.setText("Name: "+name);
				genderLabel.setText("Gender: "+gender);
				localeLabel.setText("Locale: "+locale);
				usernameLabel.setText("Username: "+username);
			}
		});
		btnEnter.setBounds(10, 56, 77, 28);
		btnEnter.setText("Enter");	

	}
	
	private void httpReq(String user, boolean isPhoto){
		String urlx;
		if(isPhoto)
			 urlx = "http://graph.facebook.com/" + user + "/picture?redirect=0&height=200&type=normal&width=200";
		else 
			 urlx = "http://graph.facebook.com/"+user;
		String s = " ";
		try {
			URL url = new URL(urlx);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				s = strTemp;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String[] spl = s.split("\\,");
		if(!isPhoto){
			if(s.contains("link")){
				name = spl[6].split("\"")[3];
				gender = spl[2].split("\"")[3];
				locale = spl[5].split("\"")[3];
				username = spl[7].split("\"")[3];
			}
			else{
				name = spl[5].split("\"")[3];
				gender = spl[2].split("\"")[3];
				locale = spl[4].split("\"")[3];
				username = spl[6].split("\"")[3];
			}
		}
		
		String[] spli = s.split("\\,");
		imageUrl = spli[2].split("\"")[3];
		imageUrl = imageUrl.replace("\\", "");
		
	}
	
	private void imageDisplay(String user){
		httpReq(user, true);
		Image image = null;
        try {
            URL urla = new URL(imageUrl);
            image = ImageIO.read(urla);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);

	}
}
