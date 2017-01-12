package ph.remote;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.services.ServiceFactory;

public class RemoteHelper {

	private static final Logger LOG = LogR.getLogger(RemoteHelper.class.getName());
	
	public static final RemoteService service = ServiceFactory.getService(RemoteService.class);

    public static String uniquify() {
        return uniquify("");
    }
    
	public static String uniquify(String pre) {
        return uniquify(pre, "");
    }
	
    public static String uniquify(String pre, String suf) {
        return pre + System.currentTimeMillis() + suf;
    }

}
