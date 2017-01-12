package ph.remote;

import java.io.Serializable;

import wt.method.RemoteInterface;
import wt.util.WTException;
import wt.workflow.engine.WfVariable;

@RemoteInterface
public interface RemoteService extends Serializable {

    public abstract Object getVariableValue(WfVariable variable) throws WTException;
    
	public abstract Object executeStaticMethod(String objectName, String targetMethod, Class argTypes[], Object argObjects[]) 
            throws WTException;
	
	public abstract Object executeObjectMethod(String objectName, String targetMethod, Class argTypes[], Object argObjects[])
            throws WTException;
	
	public abstract Object executeServerMethod(String serviceName, String targetMethod, Class argTypes[], Object argObjects[])
            throws WTException;

}
