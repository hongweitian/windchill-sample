package ph.remote;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentServerHelper;
import wt.fc.ReferenceFactory;
import wt.log4j.LogR;
import wt.method.RemoteMethodServer;
import wt.util.WTException;
import wt.util.WTProperties;

public class GetContentHelper {

    private static final Logger LOG = LogR.getLogger(GetContentHelper.class.getName());
    
    public static String downloadFile(ContentHolder contholder, ApplicationData appData, String downloadLocation) throws WTException, IOException {
        if (contholder == null || appData == null) {
            return null;
        }

        if (RemoteMethodServer.ServerFlag) {
            ContentServerHelper.service.writeContentStream(appData, downloadLocation);
        } else {
            String oid = new ReferenceFactory().getReferenceString(contholder);
            String cioids = appData.toString();
            String role = appData.getRole().toString();
            
            String hostname = WTProperties.getLocalProperties().getProperty("wt.rmi.server.hostname");
            String username = "wcadmin";
            String password = "wcadmin";
            
            ClientGetContent getter = new ClientGetContent(hostname, username, password);

            FileOutputStream outStream = new FileOutputStream(new File(downloadLocation));
            getter.saveContent(oid, cioids, role, outStream);
        }

        return downloadLocation;
    }
}
