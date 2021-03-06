package me.herobrinedobem.hgladiador;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

public class TeamManager {

    private HGladiador hg = HGladiador.getHGladiador();
    
    public void criar(String jogador) {
      if (hg.scoreboard.getTeam(jogador.toUpperCase() + "_") != null) {
        return;
      }
      Team t = hg.scoreboard.registerNewTeam(jogador.toUpperCase() + "_");
      t.addPlayer(Bukkit.getOfflinePlayer(jogador));
    }

    public void deletar(String jogador) {
      if (hg.scoreboard.getTeams().contains(jogador.toUpperCase() + "_")){
        hg.scoreboard.getTeam(jogador.toUpperCase() + "_").unregister();
      }
    }

    public void addPrefixo(String jogador, String prefixo) {
      criar(jogador);
      if(prefixo.length() >= 16){
          prefixo = prefixo.substring(0,14);
      }
      Team t = (Team) hg.scoreboard.getTeam(jogador.toUpperCase() + "_");
      t.setPrefix(prefixo.replaceAll("&", "�"));
    }

    public void addSufixo(String jogador, String prefixo) {
      criar(jogador);
      Team t = hg.scoreboard.getTeam(jogador.toUpperCase() + "_");
      if(prefixo.length() >= 16){
        prefixo = prefixo.substring(0,14);
      }
      t.setSuffix(prefixo.replaceAll("&", "�"));
    }

}