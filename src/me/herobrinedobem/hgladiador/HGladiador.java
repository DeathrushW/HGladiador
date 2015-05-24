package me.herobrinedobem.hgladiador;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import net.milkbowl.vault.economy.Economy;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;

public class HGladiador extends JavaPlugin{

    public Scoreboard scoreboard = null;
    public SimpleClans core;
    public VariaveisGlobais vg = new VariaveisGlobais();
    public Economy economy = null;
    
    @Override
    public void onEnable() {
        setupEconomy();
        
        if(!hookSimpleClans()){
            System.out.println("[HGladiador] Plugin Desabilitado - SimpleClans Nao Encontrado");
            getPluginLoader().disablePlugin(this);
        }else{
            System.out.println("[HGladiador] Hook Com SimpleClans Ativado");
        }
        
        if (!new File(getDataFolder(), "config.yml").exists()){
            saveDefaultConfig();
            System.out.println("[HGladiador] Config.yml Criada Com Sucesso");
        }
        
        getServer().getPluginManager().registerEvents(new Eventos(), this);
        System.out.println("[HGladiador] Eventos Registrados Com Sucesso");
        
        if(getConfig().getBoolean("Clan_Tag.Ativar_Tab_Tag")){
            TabTag tt = new TabTag();
            tt.iniciarTabTagAndCustomNameTag();
            if(getConfig().getBoolean("Clan_Tag.Ativar_Custom_Name_Tag")){
                System.out.println("[HGladiador] Tag Do Clan No Custom Name Ativado");
            }
            System.out.println("[HGladiador] Tag Do Clan No TAB Ativado");
        }

        getCommand("gladiador").setExecutor(new ComandosPlayer());
        getCommand("gladiadoradmin").setExecutor(new ComandosAdmin());
        
        vg.isOcorrendo = false;
        vg.isAberto = false;
        vg.todosParticipantes.clear();
        vg.clans.clear();
        vg.quantMensagens = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Anuncio_Quantidade");
        vg.precoParaParticipar = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Preco_Para_Entrar");
        vg.premioParaCada = HGladiador.getHGladiador().getConfig().getInt("Gladiador_Premio.Money_Para_Cada_Integrante");
        vg.premioParaLider = HGladiador.getHGladiador().getConfig().getInt("Gladiador_Premio.Money_Para_Cada_Lider");
        vg.limiteDeMembros = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Limite_De_Membros");
        vg.quantGladiadores = HGladiador.getHGladiador().getConfig().getInt("Gladiador_Tag.Quantidade");
        vg.quantGladiadoresAdicionados = 0;
        
        scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        
         if(getConfig().getBoolean("Automacao.Ativar")){
             iniciarSchedulerDeAutomacao();
         }
         
         if(getDescription().getVersion().equalsIgnoreCase(verificaVersao())){
             System.out.println("[HGladiador] Nenhuma atualizacao disponivel no momento");
         }else{
             getServer().getConsoleSender().sendMessage("§4[HGladiador] Existe uma versao mais recente disponivel");
         }
        
        System.out.println("[HGladiador] Plugin Habilitado - Versao (V " + getDescription().getVersion() + ")");
    }

    @Override
    public void onDisable() {
        System.out.println("[HGladiador] Plugin Desabilitado - Versao (V " + getDescription().getVersion() + ")");
    }
    
    public static HGladiador getHGladiador(){
        return (HGladiador) Bukkit.getServer().getPluginManager().getPlugin("HGladiador");
    }
    
    private boolean hookSimpleClans(){
        try {
            for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
                if (plugin instanceof SimpleClans) {
                    this.core = (SimpleClans) plugin;
                    return true;
                }
            }
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    private boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    public Clan getClanByPlayer(Player p){
        return core.getClanManager().getClanByPlayerName(p.getName());
    }
    
    private Calendar data = Calendar.getInstance();
    
    public void iniciarSchedulerDeAutomacao(){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    int diaConf = getConfig().getInt("Automacao.Dia");
                    int horaConf = getConfig().getInt("Automacao.Hora");
                    int minutoConf = getConfig().getInt("Automacao.Minuto");
                    if(diaConf == getDay()){
                        if(horaConf == getHour()){
                            if(minutoConf == getMinute()){
                                getServer().dispatchCommand(Bukkit.getConsoleSender(), "gladadm iniciar");
                            }
                        }
                    }
                }
            }, 60, 1 * 20L);
    }
    
    public int getDay(){
       return data.get(Calendar.DAY_OF_WEEK);
    }
    
    public int getHour(){
       return data.get(Calendar.HOUR_OF_DAY);
    }
    
    public int getMinute(){
       return data.get(Calendar.MINUTE);
    }
    
    private String verificaVersao(){
        try{  
          URL url = new URL("http://www.firedragonrpg.esy.es/hgladiador/versao");  
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String s;
                while ((s = br.readLine()) != null) {
                    return s;
                } }
      }catch(Exception e){
          e.printStackTrace();
      }
        return null;
    }
    
}
