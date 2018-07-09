package com.zekaoyunlari;

/**
 * User: Olcay
 * Date: Sep 8, 2007
 * Time: 1:54:33 PM
 */
public class KaplarCozum {

    private int istenenAgirlik;
    private int[] kaplar;

    public KaplarCozum(int _kap1, int _kap2, int _kap3, int _istenenAgirlik){
        kaplar = new int[3];
        kaplar[0] = _kap1;
        kaplar[1] = _kap2;
        kaplar[2] = _kap3;
        istenenAgirlik = _istenenAgirlik;
    }

    public boolean listedeVar(List l, KaplarDurum durum){
        ListNode node;
        node = l.firstNode;
        while (node != null){
            if (durum.ayniDurum((KaplarDurum) node.get())){
                return true;
            }
            node = node.next();
        }
        return false;
    }

    public KaplarDurum coz(){
        List yapilacaklar;
        List yapilanlar;
        KaplarDurum durum, baslangic, yeniDurum, eniyi = null;
        int i, j, uzunluk, enIyiUzunluk = 1000;
        int[] agirlik;
        agirlik = new int[3];
        agirlik[0] = kaplar[0];
        yapilacaklar = new List();
        yapilanlar = new List();
        baslangic = new KaplarDurum(agirlik, null);
        yapilacaklar.insertBack(baslangic);
        while (!yapilacaklar.isEmpty()){
            durum = (KaplarDurum) yapilacaklar.removeFront();
            for (i = 0; i < 3; i++){
                for (j = 0; j < 3; j++){
                    if (i != j){
                        yeniDurum = durum.aktar(i, j, kaplar);
                        if (yeniDurum != null){
                            if (!listedeVar(yapilanlar, yeniDurum)){
                                if (yeniDurum.istenenSonuc(istenenAgirlik)){
                                    uzunluk = yeniDurum.cozumUzunlugu();
                                    if (uzunluk < enIyiUzunluk){
                                        enIyiUzunluk = uzunluk;
                                        eniyi = yeniDurum;
                                    }
                                }
                                yapilacaklar.insertBack(yeniDurum);
                            }
                        }
                    }
                }
            }
            yapilanlar.insertBack(durum);
        }
        return eniyi;
    }
}
