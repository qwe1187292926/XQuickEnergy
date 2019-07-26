package pansong291.xposed.quickenergy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Config
{
 public enum ShowMode
 {
  dialog, toast
 }
 
 private static final String TAG = Config.class.getCanonicalName();
 public boolean defInit;
 public boolean helpFriend;
 public ShowMode showMode;
 public boolean recordLog;
 public List<String> dontCollectList;
 public List<String> dontHelpList;
 
 private static Config config;
 private static Map idMap;
 private static boolean hasIdMapChanged = false;
 private static String selfId;
 
 public static boolean helpFriend()
 {
  return getConfig().helpFriend;
 }
 
 public static ShowMode showMode()
 {
  return getConfig().showMode;
 }
 
 public static boolean recordLog()
 {
  return getConfig().recordLog;
 }
 
 public static boolean dontCollect(String id)
 {
  return getConfig().dontCollectList.contains(id);
 }
 
 public static boolean dontHelp(String id)
 {
  return getConfig().dontHelpList.contains(id);
 }
 
 private static Config getConfig()
 {
  if(config == null)
   config = FileUtils.getSavedConfig();
  return config;
 }
  
 public static void putIdMap(String key, String value)
 {
  if(getIdMap().containsKey(key))
  {
   if(!getIdMap().get(key).equals(value))
   {
    getIdMap().replace(key, value);
    hasIdMapChanged = true;
   }
  }else
  {
   getIdMap().put(key, value);
   hasIdMapChanged = true;
  }
 }

 public static boolean saveIdMap()
 {
  if(hasIdMapChanged)
   hasIdMapChanged = !FileUtils.saveFriendIdMapFile(getIdMap());
  return hasIdMapChanged;
 }
 
 public static String getSelfId()
 {
  if(selfId == null)
  {
   Set idSet = getIdMap().entrySet();
   for(Map.Entry entry: idSet)
    if(!entry.getValue().toString().contains("*"))
    {
     selfId = entry.getKey().toString();
     break;
    }
  }
  return selfId;
 }
 
 public static String getNameById(String id)
 {
  if(getIdMap().containsKey(id))
   return getIdMap().get(id).toString();
  return id;
 }
 
 private static Map getIdMap()
 {
  if(idMap == null)
  {
   idMap = FileUtils.getSavedFriendIdMap();
  }
  return idMap;
 }
 
 public static Config defInit()
 {
  Config c = new Config();
  c.defInit = true;
  c.helpFriend = true;
  c.showMode = ShowMode.dialog;
  c.recordLog = false;
  c.dontCollectList = new ArrayList<>();
  c.dontHelpList = new ArrayList<>();
  return c;
 }
 
}