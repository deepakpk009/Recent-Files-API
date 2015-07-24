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

import java.io.File;

/**
 * This interface is to be implemented by any class which requires the recent
 * files support
 *
 * @author deepak
 */
public interface RecentFileHandler {

    /**
     * this function is called by the RecentFilesManager object when the user
     * selects a recent file from the JMenu. The class which implements this
     * interface should provide the methods of handling the file opening
     * operation within this function implementation
     *
     * @param file the recent file that is selected
     *
     * @param attributes the file attributes which the user have saved for the
     * file
     *
     * @return true if the recent file is handled properly else return false. on
     * returning false the RecentFileManager removes the file and its attributes
     * from the recent files list
     */
    public boolean onRecentFileSelection(File file, String[] attributes);
}
