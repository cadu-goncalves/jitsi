/*
 * SIP Communicator, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 *
 * Resources.java
 *
 * Created on 22 May, 2007, 8:53 AM
 *
 * SSH Suport in SIP Communicator - GSoC' 07 Project
 *
 */
package net.java.sip.communicator.plugin.sshaccregwizz;

import java.io.*;

import net.java.sip.communicator.service.resources.*;
import net.java.sip.communicator.util.*;

import org.osgi.framework.*;

/**
 * The <tt>Resources</tt> class manages the access to the internationalization
 * properties files and the image resources used in this plugin.
 * 
 * @author Shobhit Jindal
 */
public class Resources
{

    private static Logger log = Logger.getLogger(Resources.class);

    private static ResourceManagementService resourcesService;

    public static ImageID SSH_LOGO = new ImageID("protocolIconSsh");
    
    public static ImageID PAGE_IMAGE = new ImageID("pageImageSsh");

    /**
     * Returns an internationalized string corresponding to the given key.
     * @param key The key of the string.
     * @return An internationalized string corresponding to the given key.
     */
    public static String getString(String key)
    {
        return getResources().getI18NString(key);
    }

    /**
     * Loads an image from a given image identifier.
     * 
     * @param imageID The identifier of the image.
     * @return The image for the given identifier.
     */
    public static byte[] getImage(ImageID imageID)
    {
        InputStream in = 
            getResources().getImageInputStream(imageID.getId());
        
        if(in == null)
            return null;
        
        byte[] image = null;

        try
        {
            image = new byte[in.available()];
            in.read(image);
        }
        catch (IOException e)
        {
            log.error("Failed to load image:" + imageID, e);
        }

        return image;
    }

    public static ResourceManagementService getResources()
    {
        if (resourcesService == null)
        {
            ServiceReference serviceReference = SSHAccRegWizzActivator.bundleContext
                .getServiceReference(ResourceManagementService.class.getName());

            if(serviceReference == null)
                return null;
            
            resourcesService = 
                (ResourceManagementService)SSHAccRegWizzActivator.bundleContext
                    .getService(serviceReference);
        }

        return resourcesService;
    }
}
