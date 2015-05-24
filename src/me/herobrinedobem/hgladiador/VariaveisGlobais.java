package me.herobrinedobem.hgladiador;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class VariaveisGlobais {

	public boolean isNoite;
    public boolean isMitoEnable;
    public boolean isGladiadorEnable;
    public boolean isChuvendo;
    public boolean isOcorrendo;
    public boolean isAberto;
    public ArrayList<Player> todosParticipantes = new ArrayList<Player>();
    public HashMap<String, Integer> clans = new HashMap<String, Integer>();
    public int quantMensagens;
    public int precoParaParticipar;
    public int premioParaCada;
    public int premioParaLider;
    public int limiteDeMembros;
    public int quantGladiadores;
    public int quantGladiadoresAdicionados;
    
    public void resetVariaveis(){
        isOcorrendo = false;
        isAberto = false;
        todosParticipantes.clear();
        clans.clear();
        premioParaCada = HGladiador.getHGladiador().getConfig().getInt("Gladiador_Premio.Money_Para_Cada_Integrante");
        premioParaLider = HGladiador.getHGladiador().getConfig().getInt("Gladiador_Premio.Money_Para_Cada_Lider");
        isMitoEnable = HGladiador.getHGladiador().getConfig().getBoolean("Mito_Tag.Ativar");
        isGladiadorEnable = HGladiador.getHGladiador().getConfig().getBoolean("Gladiador_Tag.Ativar");
        isNoite = HGladiador.getHGladiador().getConfig().getBoolean("Gladiador.Deixar_De_Noite");
        isChuvendo = HGladiador.getHGladiador().getConfig().getBoolean("Gladiador.Retirar_Chuva");
        quantMensagens = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Anuncio_Quantidade");
        precoParaParticipar = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Preco_Para_Entrar");
        limiteDeMembros = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Limite_De_Membros");
        quantGladiadores = HGladiador.getHGladiador().getConfig().getInt("Gladiador_Tag.Quantidade");
        quantGladiadoresAdicionados = 0;
    }
    
    public void resetVariaveisSemIS(){
        todosParticipantes.clear();
        clans.clear();
        quantMensagens = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Anuncio_Quantidade");
        precoParaParticipar = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Preco_Para_Entrar");
        limiteDeMembros = HGladiador.getHGladiador().getConfig().getInt("Gladiador.Limite_De_Membros");
        quantGladiadores = HGladiador.getHGladiador().getConfig().getInt("Gladiador_Tag.Quantidade");
        quantGladiadoresAdicionados = 0;
    }
    
}
