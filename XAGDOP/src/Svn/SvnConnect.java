package src.Svn;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SvnConnect {
	protected String _url = "svn://marine.edu.ups-tlse.fr/users/iupisi/m1isb4/svn/XAGDOP";
	protected String _name = "XAGDOP";
	protected String _password = "blabla";
	
	
	public SvnConnect(String url, String name, String password){
		_url = url;
		_name = name;
		_password = password;
		setupLibrary();
	}
	public SvnConnect(){
		setupLibrary();
	}
	
	public SVNRepository connect(){
		SVNRepository repository = null;
		try {
			/*
			 * Creates an instance of SVNRepository to work with the repository.
			 * All user's requests to the repository are relative to the
			 * repository location used to create this SVNRepository.
			 * SVNURL is a wrapper for URL strings that refer to repository locations.
			 */
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(_url));
		} catch (SVNException svne) {
			/*
			 * Perhaps a malformed URL is the cause of this exception
			 */
			System.err
			.println("error while creating an SVNRepository for location '"
					+ _url + "': " + svne.getMessage());
			System.exit(1);
		}
		
		/*
		 * User's authentication information is provided via an ISVNAuthenticationManager
		 * instance. SVNWCUtil creates a default usre's authentication manager given user's
		 * name and password.
		 */
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(_name, _password);
		
		/*
		 * Sets the manager of the user's authentication information that will 
		 * be used to authenticate the user to the server (if needed) during 
		 * operations handled by the SVNRepository.
		 */
		repository.setAuthenticationManager(authManager);
		try {
			/*
			 * Checks up if the specified path/to/repository part of the URL
			 * really corresponds to a directory. If doesn't the program exits.
			 * SVNNodeKind is that one who says what is located at a path in a
			 * revision. -1 means the latest revision.
			 */
			SVNNodeKind nodeKind = repository.checkPath("", -1);
			if (nodeKind == SVNNodeKind.NONE) {
				System.err.println("There is no entry at '" + _url + "'.");
				//System.exit(1);
			} else if (nodeKind == SVNNodeKind.FILE) {
				System.err.println("The entry at '" + _url + "' is a file while a directory was expected.");
				//System.exit(1);
			}
			/*
			 * getRepositoryRoot returns the actual root directory where the
			 * repository was created
			 */
		/*	System.out.println("Repository Root: "
					+ repository.getRepositoryRoot());
			/*
			 * getRepositoryUUID returns Universal Unique IDentifier (UUID) - an
			 * identifier of the repository
			 */
			/*System.out.println("Repository UUID: "
					+ repository.getRepositoryUUID());
			System.out.println("");
			
			/*
			 * Displays the repository tree at the current path - "" (what means
			 * the path/to/repository directory)
			 */
		} catch (SVNException svne) {
			System.err.println("error while listing entries: "
					+ svne.getMessage());
			//System.exit(1);
		}
		
		return repository;
	}
	
	
	/*
	 * Initializes the library to work with a repository either via svn:// 
	 * (and svn+ssh://) or via http:// (and https://)
	 */
	public  void setupLibrary() {
		
		/*
		 * for SVN (over svn and svn+ssh)
		 */
		SVNRepositoryFactoryImpl.setup();
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		this._password = password;
	}
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		this._url = url;
	}
	
	
}
