package com.zekaoyunlari;

/**
 * User: Olcay
 * Date: Sep 8, 2007
 * Time: 2:09:27 PM
 */
public class KaplarDurum {

    private int[] agirlik;
    private KaplarDurum gelinenDurum;
    private int hangikaptan, hangikaba;

    public KaplarDurum(int[] _agirlik, KaplarDurum _gelinenDurum){
        int i;
        agirlik = new int[3];
        for (i = 0; i < 3; i++){
            agirlik[i] = _agirlik[i];
        }
        gelinenDurum = _gelinenDurum;
        hangikaptan = hangikaba = -1;
    }

    public KaplarDurum(int[] _agirlik, KaplarDurum _gelinenDurum, int _hangikaptan, int _hangikaba){
        int i;
        agirlik = new int[3];
        for (i = 0; i < 3; i++){
            agirlik[i] = _agirlik[i];
        }
        gelinenDurum = _gelinenDurum;
        hangikaptan = _hangikaptan;
        hangikaba = _hangikaba;
    }

    public KaplarDurum gelinen(){
        return gelinenDurum;
    }

    public int[] agirlik(){
        return agirlik;
    }

    public int hangikaptan(){
        return hangikaptan;
    }

    public int hangikaba(){
        return hangikaba;
    }

    public KaplarDurum aktar(int hangikaptan, int hangikaba, int[] kaplar){
        int eklememiktari;
        KaplarDurum yenidurum;
        if (agirlik[hangikaptan] > 0 && agirlik[hangikaba] < kaplar[hangikaba]){
            if (agirlik[hangikaptan] < kaplar[hangikaba] - agirlik[hangikaba]){
                eklememiktari = agirlik[hangikaptan];
            }
            else{
                eklememiktari = kaplar[hangikaba] - agirlik[hangikaba];
            }
            agirlik[hangikaptan] -= eklememiktari;
            agirlik[hangikaba] += eklememiktari;
            yenidurum = new KaplarDurum(agirlik, this, hangikaptan, hangikaba);
            agirlik[hangikaptan] += eklememiktari;
            agirlik[hangikaba] -= eklememiktari;            
            return yenidurum;
        }
        else{
            return null;
        }
    }

    public boolean ayniDurum(KaplarDurum d){
        int i;
        for (i = 0; i < 3; i++){
            if (d.agirlik[i] != agirlik[i]){
                return false;
            }
        }
        return true;
    }

    public boolean istenenSonuc(int istenenAgirlik){
        int i;
        for (i = 0; i < 3; i++){
            if (agirlik[i] == istenenAgirlik){
                return true;
            }
        }
        return false;
    }

    public int cozumUzunlugu(){
        int uzunluk = 0;
        KaplarDurum durum = this;
        while (durum != null){
            uzunluk++;
            durum = durum.gelinenDurum;
        }
        return uzunluk;
    }

}
