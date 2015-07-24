/*
 RecentFiles API v0.1
 -------------------------------------
 a Recent Files API for java.
 -------------------------------------
 Developed By : deepak pk
 Email : deepakpk009@yahoo.in
 -------------------------------------
 This Project is Licensed under LGPL
 -------------------------------------
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.deepak.recentfilesapi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * RecentFilesManager provides methods to enable support for recent files in
 * your gui based programs.
 *
 * @author deepak
 */
public class RecentFilesManager {

    /**
     * the recent file handler reference object
     */
    private RecentFileHandler recentFileHandler = null;
    /**
     * the menu component onto which the recent files JMenuItems are to be added
     */
    private JMenu jmenu = null;
    /**
     * the recent file config file object
     */
    private File configFile = null;
    /**
     * the maximum no of records to be stored
     */
    private int maxRecords = 0;
    /**
     * the file icon types array object
     */
    private FileIconTypes[] fileIconTypesArray = null;
    /**
     * the recent files list
     */
    private ArrayList<RecentFile> recentFilesList = null;
    /**
     * the java.util.Properties object
     */
    private Properties properties = null;

    /**
     * Creates a new RecentFileManager object
     *
     * @param recentFileHandler the recent file handler object reference
     *
     * @param jMenu the menu component object reference onto which the recent
     * files JMenuItems are to be added
     *
     * @param configFileName the recent files config save file name. the file
     * will be created in the same folder as that of the project
     *
     * @param maxRecords the maximum no of recent files to be stored
     *
     * @param fileIconTypes the file icon type array. specifies what icon to be
     * displayed for different files. the file type is recognized by their
     * extension.
     */
    public RecentFilesManager(
            RecentFileHandler recentFileHandler,
            JMenu jMenu,
            String configFileName,
            int maxRecords,
            FileIconTypes[] fileIconTypes) throws FileNotFoundException, IOException {

        // set the recent file handler object
        this.recentFileHandler = recentFileHandler;

        // set the jmenu reference
        this.jmenu = jMenu;

        // create the config file from the config file name
        this.configFile = new File(configFileName);

        // if file doesnt exists the ncreate one
        if (!configFile.exists()) {
            this.configFile.createNewFile();
        }

        // set the maximun no of records
        this.maxRecords = maxRecords;

        // set the file icon types reference
        this.fileIconTypesArray = fileIconTypes;

        // create a new recent files list
        recentFilesList = new ArrayList<>();

        // create a properties object
        this.properties = new Properties();

        // load the properties from the config file
        this.properties.load(new FileInputStream(configFile));

        // the recent file name string
        String fileName;

        // the recent file attribute string
        String attribute;

        // the recent file attribute list
        ArrayList<String> attributeList;

        // for the maximun no of recent records
        for (int i = 0; i < maxRecords; i++) {

            // the value for key 'x0' gives the file name 
            fileName = properties.getProperty(String.valueOf(i) + 0);

            // if file name is not equal to null then
            if (fileName != null) {

                // create a new recent file object
                RecentFile recentFile = new RecentFile();

                // create a file object based on the recent file name
                // and set it to the recent file object
                recentFile.setFile(new File(fileName));

                // create the attribute list
                attributeList = new ArrayList<String>();

                // the value of j starts with 1 as the value 0 is already used for the file name
                // we dont know how many attributes are present so we will try to search for 
                // all the possibilities till the integer max value
                for (int j = 1; j < Integer.MAX_VALUE; j++) {

                    // get the attribute 
                    attribute = properties.getProperty(String.valueOf(i) + String.valueOf(j));

                    // if the returned attribute is not null then
                    if (attribute != null) {
                        // add the attribute to the attribute list
                        attributeList.add(attribute);
                    } else {
                        // else if the attribute is null then it means 
                        // the attribute for the file specified by the value 'i' is over
                        // so then break the search for the attribute for the file
                        break;
                    }
                }

                // if the attribute size is greater than zero then
                if (attributeList.size() > 0) {

                    // convert the attributes list into attributes array
                    String[] attributesArray = (String[]) attributeList.toArray(new String[0]);

                    // set the attributes array to the recent file
                    recentFile.setAttributes(attributesArray);
                } else {
                    // set the attribute array as null
                    recentFile.setAttributes(null);
                }

                // add the recent file to the recent files list
                recentFilesList.add(recentFile);
            }
        }
        // load the jmenu with the recent files JMenuItems
        loadRecentFilesMenuItems();
    }

    /**
     * method to add a recent file with its associated attributes
     *
     * @param file the recent file object
     *
     * @param attributes the string attributes array
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void add(File file, String[] attributes) throws FileNotFoundException, IOException {
        // process only if the file is a valid file
        if (file != null) {
            // create a recent file object based on the input parameter
            RecentFile recentFile = new RecentFile(file, attributes);

            // now if the recent file list doesnt contian the recent file then
            if (!recentFilesList.contains(recentFile)) {

                // check if the recent file list size is less than the max record size
                if (recentFilesList.size() < maxRecords) {

                    // if yes then add the recent file to the recent files list
                    recentFilesList.add(recentFile);

                    // rotate the list one time to make the last added (that is the current object added)
                    // element to be the first element in the list
                    Collections.rotate(recentFilesList, 1);
                } else {

                    // else if the max record size have been reached then
                    // rotate to make the last element in the list to be at the first position
                    Collections.rotate(recentFilesList, 1);

                    // replace the first position with the new recent file object
                    recentFilesList.set(0, recentFile);
                }
            } // else if the the recent file list does contain the recent file then
            else {

                // removes the recent file with the same file object
                recentFilesList.remove(recentFile);

                // adds the recent file with new attributes
                recentFilesList.add(recentFile);
            }

            // save the recent files list to the config file
            save();

            // load the recent files menu items
            loadRecentFilesMenuItems();
        }
    }

    /**
     * method to load the recent files as JmenuItem to the JMenu reference
     * object
     */
    private void loadRecentFilesMenuItems() {

        // first clear all previous recent files JMenuItems
        jmenu.removeAll();

        // now for all recent files in the recent files list
        for (final RecentFile recentFile : recentFilesList) {

            // create a JmenuItem with the recent file name
            JMenuItem jMenuItem = new JMenuItem(recentFile.getFile().getName());

            // add action listner to the JMenuItem
            jMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    // on action performed call the recent file handler's onRecentFileSelection method
                    // and get the method result to a refernce
                    boolean result = recentFileHandler.onRecentFileSelection(recentFile.getFile(), recentFile.getAttributes());

                    // if the result is false (ie. the file handling failed)                    
                    if (!result) {

                        // remove the recent file from the recent file list
                        recentFilesList.remove(recentFile);

                        // save the current recent files list to config file
                        try {
                            save();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(RecentFilesManager.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(RecentFilesManager.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // load the recent files JMenuItems to recent files JMenu
                        loadRecentFilesMenuItems();
                    }
                }
            });

            // if the file icon type array is not null then
            if (fileIconTypesArray != null) {
                // for all file icon types in the array
                for (FileIconTypes fileIconTypes : fileIconTypesArray) {

                    // get the recent file extension from file name and check whether it matches
                    // the file icon type's file extension 
                    if (recentFile.getFile().getName().endsWith(fileIconTypes.getFileType())) {
                        // on match set the JMenuItem icon as the file icon types icon
                        jMenuItem.setIcon(fileIconTypes.getIcon());
                        // break the search for the file icon type
                        break;
                    }
                }
            }

            // add the JMenuItem to the JMenu
            jmenu.add(jMenuItem);
        }
    }

    /**
     * method to save the recent file with its attributes to the config file
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void save() throws FileNotFoundException, IOException {

        // the recent file object
        RecentFile recentFile;

        // the index i represents the file index
        int i = 0;
        // the index j represents the attribute index
        int j = 0;

        // clear all properties
        properties.clear();

        // from the recent file list create properties object
        for (i = 0; i < recentFilesList.size(); i++) {
            // get the recent file object
            recentFile = recentFilesList.get(i);
            // reset the j index value for iterations
            j = 0;

            // set the file property - the key for file will be '00'
            // file absolute path is saved as value for the file key
            properties.setProperty(String.valueOf(i) + String.valueOf(j), recentFile.getFile().getAbsolutePath());

            // save the attributres for the file 
            // only if the recent file have any attribute
            if (recentFile.getAttributes() != null) {
                for (String attributes : recentFile.getAttributes()) {
                    // increment the attrbutes index
                    j++;

                    // set the attribute for the file
                    properties.setProperty(String.valueOf(i) + String.valueOf(j), attributes);
                }
            }
        }

        // save the properties object to the config file
        properties.store(new FileOutputStream(configFile), null);
    }

    /**
     * the RecentFile class provides methods for handling recent file object,
     * which are a file and its associated attribute array
     */
    private class RecentFile {

        /**
         * the recent file reference
         */
        private File file = null;
        /**
         * the attribute array reference
         */
        private String[] attributes = null;

        /**
         * creates a recent file object
         */
        public RecentFile() {
        }

        /**
         * creates a recent file object with the specified parameter
         *
         * @param file the recent file object
         * @param attributes the attribute array
         */
        public RecentFile(File file, String[] attributes) {
            // set the file reference
            this.file = file;
            // set the attributes reference
            this.attributes = attributes;
        }

        /**
         * getter for the recent file
         *
         * @return the recent file reference
         */
        public File getFile() {
            return file;
        }

        /**
         * setter for the recent file object
         *
         * @param file the recent file reference object
         */
        public void setFile(File file) {
            this.file = file;
        }

        /**
         * getter for the file attributes
         *
         * @return the attributes String array
         */
        public String[] getAttributes() {
            return attributes;
        }

        /**
         * the setter for the file attributes
         *
         * @param attributes the file attributes String array
         */
        public void setAttributes(String[] attributes) {
            this.attributes = attributes;
        }

        /**
         * the overridden equals method of the object class
         *
         * @param obj the object to be compared with
         * @return true if the files of the two object are the same, else
         * returns false. here we are only concerned with the file object
         * equality as for the same file there will be different attributes
         */
        @Override
        public boolean equals(Object obj) {
            // check whether the passed object is a kind of RecentFile
            if (obj instanceof RecentFile) {
                // cast the passed object into a RecentFile object
                RecentFile rf = (RecentFile) obj;
                // check whether the current objects file matches the passed objects file
                if (file.getAbsolutePath().contentEquals(rf.getFile().getAbsolutePath())) {
                    // if yes then return true
                    return true;
                } else {
                    // else return false
                    return false;
                }
            } else {
                // if compared with any other object kind return false
                return false;
            }
        }

        /**
         * overridden hashCode method of the object class. generates hash code
         * based on the file object only
         *
         * @return the hash code for the current object
         */
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + Objects.hashCode(this.file.getAbsolutePath());
            return hash;
        }
    }
}
