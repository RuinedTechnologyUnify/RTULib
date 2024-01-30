package com.github.ipecter.rtu.lib.util.common;

import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

public class FileUtil {

    public static File copyResource(Plugin plugin, String sourceFile) {
        File targetFolder = getResourceFolder(plugin.getDataFolder());
        File resultFile = new File(targetFolder, sourceFile);
        if (!resultFile.exists()) {
            try {
                resultFile.createNewFile();
                InputStream in = plugin.getResource(sourceFile);
                OutputStream out = new FileOutputStream(resultFile);
                ByteStreams.copy(in, out);
                out.close();
                in.close();
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "Error copying content " + sourceFile, e);
            }
        }
        return resultFile;
    }

    public static File copyResource(Plugin plugin, String sourceFolder, String sourceFile) {
        File targetFolder = getResourceFolder(plugin.getDataFolder() + "/" + sourceFolder);
        File resultFile = new File(targetFolder, sourceFile);
        if (!resultFile.exists()) {
            try {
                resultFile.createNewFile();
                InputStream in = plugin.getResource(sourceFolder + "/" + sourceFile);
                OutputStream out = new FileOutputStream(resultFile);
                ByteStreams.copy(in, out);
                out.close();
                in.close();
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "Error copying file " + sourceFolder + "/" + sourceFile, e);
            }
        }
        return resultFile;
    }

    public static File getResourceWithoutNew(String folder, String file) {
        File resource = new File(folder, file);
        if (!resource.exists()) {
            return null;
        } else {
            return resource;
        }
    }

    public static File getResource(String folder, String file) {
        File resourceFolder = new File(folder);
        if (!resourceFolder.exists()) {
            try {
                resourceFolder.mkdirs();

            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "Error creating folder " + folder, e);
            }
        }
        File resourceFile = new File(folder, file);
        if (!resourceFile.exists()) {
            try {
                resourceFile.createNewFile();

            } catch (Exception e) {
//                Bukkit.getLogger().severe("Error creating file " + file);
//                e.printStackTrace();
                Bukkit.getLogger().log(Level.WARNING, "Error creating file " + file, e);
            }
        }
        return resourceFile;
    }

    public static File getResourceFolder(String folder) {
        File resourceFolder = new File(folder);
        if (!resourceFolder.exists()) {
            try {
                resourceFolder.mkdirs();

            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "Error creating folder " + folder, e);
            }
        }
        return resourceFolder;
    }

    public static File getResourceFolder(File folder) {
        if (!folder.exists()) {
            try {
                folder.mkdirs();

            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "Error creating folder " + folder, e);
            }
        }
        return folder;
    }

    public static File getResource(File folder, String file) {
        if (!folder.exists()) {
            try {
                folder.mkdirs();

            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "Error creating folder " + folder, e);
            }
        }
        File resourceFile = new File(folder, file);
        if (!resourceFile.exists()) {
            try {
                resourceFile.createNewFile();

            } catch (Exception e) {
                Bukkit.getLogger().log(Level.WARNING, "Error creating file " + file, e);
            }
        }
        return resourceFile;
    }

}
