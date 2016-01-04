/*  1:   */ package net.wmcsn.Winter.WinterEco;
/*  2:   */ 
/*  3:   */ import org.bukkit.entity.Player;
/*  4:   */ import org.bukkit.event.EventHandler;
/*  5:   */ import org.bukkit.event.Listener;
/*  6:   */ import org.bukkit.event.entity.PlayerDeathEvent;
/*  7:   */ 
/*  8:   */ public class MyDeathListener
/*  9:   */   implements Listener
/* 10:   */ {
/* 11:   */   public static Player player;
/* 12:   */   public static String killerName;
/* 13:   */   
/* 14:   */   @EventHandler
/* 15:   */   public void onEntityDeath(PlayerDeathEvent e)
/* 16:   */   {
/* 17:23 */     player = e.getEntity();
/* 18:24 */     if (!player.hasPermission("baldrop.bypass"))
/* 19:   */     {
/* 20:25 */       main.SetBalZero();
/* 21:26 */       killerName = player.getKiller().getName();
/* 22:27 */       main.GiveKillerBalance();
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }
