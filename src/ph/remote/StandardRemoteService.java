package ph.remote;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import wt.log4j.LogR;
import wt.services.Manager;
import wt.services.ManagerServiceFactory;
import wt.services.StandardManager;
import wt.util.WTException;
import wt.workflow.engine.WfVariable;

public class StandardRemoteService extends StandardManager implements RemoteService {
	
	private static final Logger LOG = LogR.getLogger(StandardRemoteService.class.getName());
	
	private static final String DEFAULT_ARCHIVE_FILE_NAME = "document_contents.zip";
	
	private static final boolean IS_EMPTY_FILE_VALID = true;
	
	private static final String MORE_RESOURCE = "com.ptc.netmarkets.object.objectResource";
	
	private static File saveDirectory = null;

	public static StandardRemoteService newStandardRemoteService() throws WTException {
		StandardRemoteService service = new StandardRemoteService();
		service.initialize();
		return service;
	}
	   
    public Object getVariableValue(WfVariable variable) throws WTException {
        if (variable == null) {
            return null;
        }
        return variable.getValue();
    }
    
	public Object executeStaticMethod(String objectName, String targetMethod, Class argTypes[], Object argObjects[]) 
	        throws WTException {
        Class<?> target_class = null;
        try {
            target_class = Class.forName(objectName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        System.out.println("target_class=" + target_class);
        if (target_class == null) {
            throw new WTException("类未找到=" + objectName);
        }
        
        Object result = null;
        
        try {
        Method method = target_class.getMethod(targetMethod, argTypes);

        result = method.invoke(null, argObjects);
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return result;

	}
	
    public Object executeObjectMethod(String objectName, String targetMethod, Class argTypes[], Object argObjects[])
            throws WTException {
        Class<?> target_class = null;
        try {
            target_class = Class.forName(objectName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("target_class=" + target_class);
        if (target_class == null) {
            throw new WTException("类未找到=" + objectName);
        }

        Object result = null;

        try {
            Method method = target_class.getMethod(targetMethod, argTypes);

            Object obj = target_class.newInstance();

            result = method.invoke(obj, argObjects);

        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
        return result;

    }
	
	public Object executeServerMethod(String serviceName, String targetMethod, Class argTypes[], Object argObjects[])
	        throws WTException {
	    
        Class<?> target_class = null;
        try {
            target_class = Class.forName(serviceName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        System.out.println("target_class=" + target_class);
        if (target_class == null) {
            throw new WTException("类未找到=" + serviceName);
        }
        
        Object result = null;
        
        try {
        Method method = target_class.getMethod(targetMethod, argTypes);

	    Manager service = ManagerServiceFactory.getDefault().getManager(target_class);
	    result = method.invoke(service, argObjects);
	    
        } catch (Exception e) {
            e.printStackTrace();
            throw new WTException(e);
        }
	    return result;
	    
	}
    
}
