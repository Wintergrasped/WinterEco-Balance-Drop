/*   1:    */ package net.wmcsn.Winter.WinterEco;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.milkbowl.vault.economy.Economy;
/*   5:    */ import net.milkbowl.vault.permission.Permission;
/*   6:    */ import org.bukkit.ChatColor;
/*   7:    */ import org.bukkit.Server;
/*   8:    */ import org.bukkit.command.Command;
/*   9:    */ import org.bukkit.command.CommandSender;
/*  10:    */ import org.bukkit.configuration.file.FileConfiguration;
/*  11:    */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*  12:    */ import org.bukkit.entity.Player;
/*  13:    */ import org.bukkit.event.Listener;
/*  14:    */ import org.bukkit.plugin.PluginManager;
/*  15:    */ import org.bukkit.plugin.RegisteredServiceProvider;
/*  16:    */ import org.bukkit.plugin.ServicesManager;
/*  17:    */ import org.bukkit.plugin.java.JavaPlugin;
/*  18:    */ 
/*  19:    */ public class main
/*  20:    */   extends JavaPlugin
/*  21:    */ {
/*  22: 29 */   public int LicenseNumber = new Random().nextInt(100) + 1;
/*  23: 30 */   public int RVal2 = new Random().nextInt(10) + 1;
/*  24:    */   public int Value;
/*  25:    */   public int balance;
/*  26:    */   public int Cash;
/*  27:    */   public int debt;
/*  28:    */   public int creditcards;
/*  29:    */   public int creditscore;
/*  30: 37 */   public static Economy economy = null;
/*  31:    */   public Player[] OnlinePeople;
/*  32:    */   public String PeopleInDebt;
/*  33:    */   public boolean Indebt;
/*  34: 41 */   public boolean hasaccount = false;
/*  35: 42 */   public boolean haswallet = false;
/*  36:    */   private int ShopNumber;
/*  37:    */   private Object RegedShopNumber;
/*  38:    */   private Object ReturnNumber;
/*  39:    */   private String World;
/*  40:    */   private static double Bal;
/*  41: 48 */   public int CostForShop = getConfig().getInt("Licenses.Costs.Shop");
/*  42: 49 */   public int CostForFarm = getConfig().getInt("Licenses.Costs.Farm");
/*  43: 50 */   public int CostForCorp = getConfig().getInt("Licenses.Costs.Corp");
/*  44: 51 */   public int EnconomyStatus = getConfig().getInt("Economy.Enabled");
/*  45: 52 */   public static Permission perms = null;
/*  46:    */   
/*  47:    */   public void onEnable()
/*  48:    */   {
/*  49: 56 */     setupPermissions();
/*  50: 57 */     getServer().getPluginManager().registerEvents(new MyDeathListener(), this);
/*  51: 58 */     RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
/*  52: 59 */     RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
/*  53: 60 */     FileConfiguration config = getConfig();
/*  54:    */     
/*  55: 62 */     config.addDefault("Players.USERNAME.Licenses", "Shop");
/*  56: 63 */     config.addDefault("Licenses.Costs.Shop", Integer.valueOf(2000000));
/*  57: 64 */     config.addDefault("Licenses.Costs.Farm", Integer.valueOf(5000000));
/*  58: 65 */     config.addDefault("Licenses.Costs.Corp", Integer.valueOf(10000000));
/*  59: 66 */     config.addDefault("Economy.Enabled", Integer.valueOf(1));
/*  60:    */     
/*  61: 68 */     config.options().copyDefaults(true);
/*  62: 69 */     saveConfig();
/*  63: 70 */     if (economyProvider != null) {
/*  64: 71 */       economy = (Economy)economyProvider.getProvider();
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean setupPermissions()
/*  69:    */   {
/*  70: 78 */     RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
/*  71: 79 */     perms = (Permission)rsp.getProvider();
/*  72: 80 */     return perms != null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*  76:    */   {
/*  77: 83 */     if (this.EnconomyStatus == 1)
/*  78:    */     {
/*  79: 84 */       if (cmd.getName().equalsIgnoreCase("roll"))
/*  80:    */       {
/*  81: 85 */         sender.sendMessage("You Rolled a " + this.LicenseNumber);
/*  82: 86 */         return true;
/*  83:    */       }
/*  84: 88 */       if (cmd.getName().equalsIgnoreCase("currentval"))
/*  85:    */       {
/*  86: 89 */         sender.sendMessage("The Current Value Of Property is" + this.Value);
/*  87: 90 */         return true;
/*  88:    */       }
/*  89: 92 */       if ((cmd.getName().equalsIgnoreCase("regshop")) && 
/*  90: 93 */         (sender.hasPermission("license.buy.shop")))
/*  91:    */       {
/*  92: 94 */         if (!economy.has(sender.getName(), this.CostForShop)) {
/*  93: 95 */           sender.sendMessage(ChatColor.DARK_GREEN + "You Dont Heve $" + this.CostForShop);
/*  94:    */         }
/*  95: 97 */         if (economy.has(sender.getName(), this.CostForShop))
/*  96:    */         {
/*  97: 98 */           economy.withdrawPlayer(sender.getName(), this.CostForShop);
/*  98: 99 */           this.LicenseNumber = this.ShopNumber;
/*  99:100 */           getConfig().set("Players." + sender.getName() + ".Licenses.shop", "Yes");
/* 100:101 */           this.RegedShopNumber = getConfig().get("Players.Shop.LicenseNumbers." + sender.getName());
/* 101:102 */           sender.sendMessage(ChatColor.DARK_GREEN + "You Have Registerd your Shop With License Number" + this.RegedShopNumber + "Please Post This Number Some Where In Your Shop!");
/* 102:103 */           sender.sendMessage(ChatColor.DARK_GREEN + "With This License You Can Now Setup a Legal Chest Shop");
/* 103:104 */           saveConfig();
/* 104:105 */           return true;
/* 105:    */         }
/* 106:    */       }
/* 107:109 */       if ((cmd.getName().equalsIgnoreCase("regproductionfarm")) && 
/* 108:110 */         (sender.hasPermission("license.buy.farm")))
/* 109:    */       {
/* 110:111 */         if (!economy.has(sender.getName(), this.CostForFarm))
/* 111:    */         {
/* 112:112 */           sender.sendMessage(ChatColor.DARK_GREEN + "You Dont Heve $" + this.CostForFarm);
/* 113:113 */           sender.getServer().broadcastMessage(args[0]);
/* 114:    */         }
/* 115:115 */         if (economy.has(sender.getName(), this.CostForFarm))
/* 116:    */         {
/* 117:116 */           economy.withdrawPlayer(sender.getName(), this.CostForFarm);
/* 118:117 */           Boolean FarmOwned = Boolean.valueOf(true);
/* 119:118 */           getConfig().set("Players." + sender.getName() + ".Licenses.farm", "Yes");
/* 120:119 */           this.RegedShopNumber = getConfig().get("Players.Shop.LicenseNumbers." + sender.getName());
/* 121:120 */           sender.sendMessage(ChatColor.DARK_GREEN + "You Have Registerd your Production Farm With License Number" + this.RegedShopNumber + "Please Post This Number Some Where In Your Shop!");
/* 122:121 */           sender.sendMessage(ChatColor.DARK_GREEN + "With This License You Can Legally farm items to be sold to shops or corperations");
/* 123:122 */           saveConfig();
/* 124:123 */           return true;
/* 125:    */         }
/* 126:    */       }
/* 127:127 */       if ((cmd.getName().equalsIgnoreCase("regcorperation")) && 
/* 128:128 */         (sender.hasPermission("license.buy.corp")))
/* 129:    */       {
/* 130:129 */         if (!economy.has(sender.getName(), this.CostForCorp)) {
/* 131:130 */           sender.sendMessage(ChatColor.DARK_GREEN + "You Dont Heve $" + this.CostForCorp);
/* 132:    */         }
/* 133:132 */         if (economy.has(sender.getName(), this.CostForCorp))
/* 134:    */         {
/* 135:133 */           economy.withdrawPlayer(sender.getName(), this.CostForCorp);
/* 136:134 */           getConfig().set(ChatColor.DARK_GREEN + "Players." + sender.getName() + ".Licenses.corp", "Yes");
/* 137:135 */           this.RegedShopNumber = getConfig().get("Players.Shop.LicenseNumbers." + sender.getName());
/* 138:136 */           sender.sendMessage(ChatColor.DARK_GREEN + "You Have Registerd your Corperation With License Number" + this.RegedShopNumber + "Please Post This Number Some Where In Your Shop!");
/* 139:137 */           sender.sendMessage(ChatColor.DARK_GREEN + "With This License You Can Now Legally Farm, Own a Shop, and Craft Items for sale");
/* 140:138 */           saveConfig();
/* 141:139 */           return true;
/* 142:    */         }
/* 143:    */       }
/* 144:145 */       if (cmd.getName().equalsIgnoreCase("checkshopowner"))
/* 145:    */       {
/* 146:146 */         if (getConfig().get("Players." + args[0] + ".Licenses.shop") == "Yes")
/* 147:    */         {
/* 148:147 */           sender.sendMessage(ChatColor.DARK_GREEN + " This Player Owns a Shop License");
/* 149:148 */           saveConfig();
/* 150:    */         }
/* 151:150 */         if (getConfig().get("Players." + args[0] + ".Licenses.shop") == null)
/* 152:    */         {
/* 153:151 */           sender.sendMessage(ChatColor.DARK_RED + "This Player Dosen't Owner a Legal shop license");
/* 154:152 */           saveConfig();
/* 155:    */         }
/* 156:    */       }
/* 157:157 */       if ((cmd.getName().equalsIgnoreCase("checkfarmowner")) && 
/* 158:158 */         (sender.hasPermission("license.check.farm")))
/* 159:    */       {
/* 160:159 */         if (getConfig().get("Players." + args[0] + ".Licenses.farm") == "Yes")
/* 161:    */         {
/* 162:160 */           sender.sendMessage(ChatColor.DARK_GREEN + " This Player Owns a Farm License");
/* 163:161 */           saveConfig();
/* 164:    */         }
/* 165:163 */         if (getConfig().get("Players." + args[0] + ".Licenses.farm") == null)
/* 166:    */         {
/* 167:164 */           sender.sendMessage(ChatColor.DARK_RED + "This Player Dosen't Owner a Legal farm license");
/* 168:165 */           saveConfig();
/* 169:    */         }
/* 170:167 */         saveConfig();
/* 171:168 */         return true;
/* 172:    */       }
/* 173:173 */       if ((cmd.getName().equalsIgnoreCase("checkcorperationowner")) && 
/* 174:174 */         (sender.hasPermission("license.check.corp")))
/* 175:    */       {
/* 176:175 */         if (getConfig().get("Players." + args[0] + ".Licenses.corp") == "Yes")
/* 177:    */         {
/* 178:176 */           sender.sendMessage(ChatColor.DARK_GREEN + " This Player Owns a Corperation License");
/* 179:177 */           saveConfig();
/* 180:    */         }
/* 181:179 */         if (getConfig().get("Players." + args[0] + ".Licenses.corp") == null)
/* 182:    */         {
/* 183:180 */           sender.sendMessage(ChatColor.DARK_RED + "This Player Dosen't Owner a Legal Corperation license");
/* 184:181 */           saveConfig();
/* 185:    */         }
/* 186:183 */         return true;
/* 187:    */       }
/* 188:    */     }
/* 189:187 */     return false;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static void SetBalZero()
/* 193:    */   {
/* 194:190 */     if (!MyDeathListener.player.hasPermission("baldrop.bypass"))
/* 195:    */     {
/* 196:191 */       Bal = economy.getBalance(MyDeathListener.player.getName());
/* 197:192 */       economy.withdrawPlayer(MyDeathListener.player.getName(), Bal);
/* 198:    */     }
/* 199:194 */     if (MyDeathListener.player.hasPermission("baldrop.bypass")) {
/* 200:195 */       MyDeathListener.player.sendMessage("You Bypassed Bal Drop.");
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   static void GiveKillerBalance()
/* 205:    */   {
/* 206:199 */     economy.depositPlayer(MyDeathListener.killerName, Bal);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public class ConfigListener
/* 210:    */     implements Listener
/* 211:    */   {
/* 212:    */     main plugin;
/* 213:    */     
/* 214:    */     public ConfigListener(main instance)
/* 215:    */     {
/* 216:205 */       this.plugin = instance;
/* 217:    */     }
/* 218:    */   }
/* 219:    */ }