/*
 This file is part of RecentFiles API v0.1

 RecentFiles API is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 RecentFiles API is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with RecentFiles API.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.deepak.recentfilesapi;

import javax.swing.ImageIcon;

/**
 * this class provides methods for specifying icons for file types which will be
 * used by the RecentFileManger for setting the JMenuItem icon for a particular
 * file type
 *
 * @author deepak
 */
public class FileIconTypes {

    /**
     * the file type extension string object
     */
    private String fileType = null;
    /**
     * the image icon for the file with the file extension
     */
    private ImageIcon icon = null;

    /**
     * creates a FileIconType
     *
     * @param fileType the file extension. set empty string ("") for setting
     * default icon
     * @param icon the icon associated with the specified file extension
     */
    public FileIconTypes(String fileType, ImageIcon icon) {
        // set the file type and icon
        this.fileType = fileType;
        this.icon = icon;
    }

    /**
     * getter to get the file type
     *
     * @return the file extension String
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * setter for file type extension
     *
     * @param fileType the file extension type. set empty string ("") for
     * setting default icon
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * getter for the icon
     *
     * @return the icon associated with the file extension
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * setter for the icon
     *
     * @param icon the icon associated with the file extension
     */
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
