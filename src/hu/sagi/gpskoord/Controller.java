package hu.sagi.gpskoord;

import hu.sagi.gpskoord.Koordinata.Tipus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Controller {

    private final String TEXTLAT1 = "Üres a szélesség koordináta!";
    private final String TEXTLON1 = "Üres a hosszúság koordináta!";
    private final String TEXTIL = "Illegális karakter '";
    private final String TEXTHELY = "Üres hely (space) ";
    private final String TEXTFOKH = "A fok értékhatáron kivül van";
    private final String TEXTMINH = "A perc értékhatáron kivül van";
    private final String TEXTSECH = "A másodperc értékhatáron kivül van";

    private final String TEXTLAT2 = "' a szélesség koordinátában!";
    private final String TEXTLON2 = "' a hosszúság koordinátában!";
    private final String TEXTLATNSZU = "A szélesség koordinátban negatívjel után csak szám lehet!";
    private final String TEXTLONNSZU = "A hosszúság koordinátban negatívjel után csak szám lehet!";
    private final String TEXTLATRF = "Rossz a szélesség koordináta formátuma!";
    private final String TEXTLONRF = "Rossz a hosszúság koordináta formátuma!";
    private final String TEXTLATHOSZ = "Túl hosszú a szélesség koordináta formátuma!";
    private final String TEXTLONHOSZ = "Túl hosszú a hosszúság koordináta formátuma!";

    private Koordinata lat = new Koordinata();
    private Koordinata lon = new Koordinata();

    private String beLat;
    private String beLon;
    private String o_DD_S;
    private String o_DM_S;
    private String o_DMS_S;

//<editor-fold defaultstate="collapsed" desc="FXML annotációk">
    @FXML
    private Button btn;
    @FXML
    private TextField olvasLat;
    @FXML
    private TextField olvasLon;
    @FXML
    private Label o_dd_lat;
    @FXML
    private Label o_dm_lon;
    @FXML
    private Label o_dms_lat;
    @FXML
    private Label o_dms_lon;
    @FXML
    private Label o_dm_lat;
    @FXML
    private Label o_dd_lon;
    @FXML
    private Pane basePane;
    @FXML
    private Pane alertPane;
    @FXML
    private Label alertText;
    @FXML
    private Button alertButton;
//</editor-fold>

    @FXML
    private void hand_foklat(ActionEvent event) {
        olvasLon.requestFocus();
    }

    @FXML
    private void hand_konv(ActionEvent event) {
        beolvasás();
    }

    @FXML
    private void hand_foklon(ActionEvent event) {
        btn.requestFocus();
    }

    @FXML
    private void hand_ok(ActionEvent event) {
        basePane.setDisable(false);
        basePane.setOpacity(1);
        alertPane.setVisible(false);
        alertText.setText("");
        o_dm_lat.setText("0° 0'");
        o_dms_lat.setText("0° 0' 0\"");
        o_dd_lat.setText("0°");
         o_dm_lon.setText("0° 0'");
        o_dms_lon.setText("0° 0' 0\"");
        o_dd_lon.setText("0°");
    }

    private void beolvasás() {
        beLat = olvasLat.getText().trim();
        beLon = olvasLon.getText().trim();

        if (beLat.equals("")) {
            alert(TEXTLAT1);
            return;
        }
        if (beLon.equals("")) {
            alert(TEXTLON1);
            return;
        }
        if (beLat.length() > 16) {
            alert(TEXTLATHOSZ);
            return;
        }
        if (beLon.length() > 16) {
            alert(TEXTLONHOSZ);
            return;
        }
        //System.out.println("1" + lat);
        töröl(lat);
        töröl(lon);
        //System.out.println("2" + lat);
        byte hiba;
        hiba = check1(beLat, lat);
        switch (hiba) {
            case 20:
                alert(TEXTHELY + TEXTLAT2.substring(2));
                return;
            case 21:
                alert(TEXTLATRF + 21);
                return;
            case 90:
                alert(TEXTLATNSZU);
                return;
            case 100:
                System.out.println(lat);
                break;
            default:
                alert(TEXTIL + beLat.charAt(hiba) + TEXTLAT2);
                return;
        }
        hiba = check1(beLon, lon);
        switch (hiba) {
            case 20:
                alert(TEXTHELY + TEXTLON2.substring(2));
                return;
            case 21:
                alert(TEXTLONRF + 21);
                return;
            case 90:
                alert(TEXTLONNSZU);
                return;
            case 100:
                System.out.println(lon);
                break;
            default:
                alert(TEXTIL + beLon.charAt(hiba) + TEXTLON2);
                return;
        }
        hiba = check2(lat);
        switch (hiba) {
            case 1:
                alert(TEXTFOKH + TEXTLAT2.substring(1));
                break;
            case 2:
                alert(TEXTMINH + TEXTLAT2.substring(1));
                break;
            case 3:
                alert(TEXTSECH + TEXTLAT2.substring(1));
                break;
            default:
        }
        hiba = check2(lon);
        switch (hiba) {
            case 1:
                alert(TEXTFOKH + TEXTLON2.substring(1));
                break;
            case 2:
                alert(TEXTMINH + TEXTLON2.substring(1));
                break;
            case 3:
                alert(TEXTSECH + TEXTLON2.substring(1));
                break;
            default:
        }
        out(lat);
        o_dd_lat.setText(o_DD_S);
        o_dm_lat.setText(o_DM_S);
        o_dms_lat.setText(o_DMS_S);
        out(lon);
        o_dd_lon.setText(o_DD_S);
        o_dm_lon.setText(o_DM_S);
        o_dms_lon.setText(o_DMS_S);
    }
    private void out(Koordinata k){
        double fok, perc, mperc, mpt;
        int sep, sep1; //separátor helye
        int hossz = 16;
        StringBuilder sb = new StringBuilder(hossz);
        switch (k.getTipus()) {
            case DD:
                if (k.isNegatív()) {
                    sb.append("-");
                }
                sb.append(k.getFok());
                sb.append(".");
                sb.append(k.getPerc());
                sb.append('°');
                o_DD_S = sb.toString();
                
                sep = sb.indexOf(".");
                sb.delete(sep, hossz);
                sb.append("° ");
                perc = Double.parseDouble("." + lat.getPerc());
                perc = perc * 60;
                System.out.println(perc);
                sb.append((int) perc);
                sb.append(digRound(perc));
                sep1 = sb.length();

                sb.append('\'');
                o_DM_S = sb.toString();
                mperc = perc * 60;
                sep = sb.indexOf(".");
                if (!(sep == -1)) {
                    sb.delete(sep, hossz);
                    sb.append("\'");
                }
                sb.append(" ");
                mperc = mperc % 60;
                sb.append((int) mperc);
                sb.append(digRound(mperc));
                sb.append('"');
                o_DMS_S = sb.toString();
                break;
            case DM:
                if (k.isNegatív()) {
                    sb.append("-");
                }
                sb.append(k.getFok());
                perc = Double.parseDouble(k.getPerc());
                mperc = Double.parseDouble("." + k.getMásodperc());
                System.out.println("perc:"+perc +"  mperc:"+mperc +"   " + (perc+mperc));
                perc = perc+mperc;
                sb.append(digRound(perc/60));
                sb.append('°');
                o_DD_S =sb.toString();
                sep = sb.indexOf(".");
                sb.delete(sep, hossz);
                sb.append('°');
                sb.append(" ");
                sb.append(k.getPerc());
                sb.append(".");
                sb.append(k.getMásodperc());
                sb.append('\'');
                o_DM_S = sb.toString();
                sep = sb.indexOf(".");
                if (!(sep == -1)) {
                    sb.delete(sep, hossz);
                    sb.append('\'');
                }
                sb.append(" ");
                mperc = Double.parseDouble("." + k.getMásodperc());
                mperc = mperc * 60;
                sb.append((int) mperc);
                sb.append(digRound(mperc));
                sb.append('"');
                o_DMS_S = sb.toString();
                break;
            case DMS:
                if (k.isNegatív()) {
                    sb.append("-");
                }
                sb.append(k.getFok());
                mpt = Double.parseDouble("." + k.getMptört());
                mperc = Double.parseDouble(k.getMásodperc());
                perc = Double.parseDouble(k.getPerc());
                fok =(mpt + mperc)/60+perc;
                
                System.out.println(" mp" + fok);
                sb.append(digRound(fok/60));
                sb.append('°');
                o_DD_S =sb.toString();
                sep = sb.indexOf(".");
                sb.delete(sep, hossz);
                sb.append('°');
                sb.append(" ");
                mperc = (mpt + mperc)/60;
                System.out.println("perc " + digRound(mperc+perc) + " mp " + digRound(mperc));
                sb.append((int)perc);
                sb.append(digRound(mperc));
                sb.append('\'');
                o_DM_S = sb.toString(); //DM formátum
                sep = sb.indexOf(".");
                sb.delete(sep, hossz);
                sb.append('\'');
                sb.append("  ");
                sb.append(k.getMásodperc());
                sb.append('.');
                sb.append(k.getMptört());
                sb.append('"');
                o_DMS_S = sb.toString();
                break;
        }
    }
    private byte check1(String szöveg, Koordinata k) {
        String s = "";
        boolean kész = false;   //tizedes beolvasása kezdődik
        boolean tizedes = false;
        byte hossz = (byte) (szöveg.length() - 1);
        byte köv = 0;  //0 fok, 1 perc, 2 másodperc
        for (byte i = 0; i <= hossz; i++) {
            char c = szöveg.charAt(i);
            //<editor-fold defaultstate="collapsed" desc="Az első karakter vizsgálata">
            if (i == 0) {  //első karakter vizsgálata
                if (c == '-') {
                    k.setNegatív(true);
                    if (!(szöveg.charAt(i + 1) >= '0' && szöveg.charAt(i + 1) <= '9')) {
                        return 90;
                    }
                    continue;
                }
                if (c == ',' || c == '.') {
                    tizedes = true;
                    k.setFok("0");
                    köv++;
                    if (!(szöveg.charAt(i + 1) >= '0' && szöveg.charAt(i + 1) <= '9')) {
                        return 70;
                    }
                    k.setTipus(Tipus.DD);
                    continue;
                }  //első karakter vizsgálata vége
//</editor-fold>
            } else {
//<editor-fold defaultstate="collapsed" desc="IF kötőjel">
                if (c == '-') {//IF kötőjel{}
                    if (tizedes) {
                        return 21;
                    }
                    if (!s.equals("")) {
                        switch (köv) {
                            case 0:
                                k.setFok(s);
                                break;
                            case 1:
                                k.setPerc(s);
                                break;
                            case 2:
                                k.setMásodperc(s);
                                break;
                            default:
                                k.setMptört(s);
                                break;
                        }
                        s = "";
                        köv++;
                    }
                    continue;
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc=" IF tizedes vessző és pont">
                if (c == ',' || c == '.') {
                    if (s.equals("")) {
                        return 20;
                    }
                    tizedes = true;
                    if (köv == 0) {
                        k.setFok(s);
                        k.setTipus(Tipus.DD);
                    } else if (köv == 1) {
                        k.setPerc(s);
                        k.setTipus(Tipus.DM);
                    } else if (köv == 2) {
                        k.setMásodperc(s);
                        k.setTipus(Tipus.DMS);
                    }
                    s = "";
                    köv++;
                    continue;
                }
//</editor-fold>
                if (c == '\'') { //IF idéző jel
                    if (köv >= 3) {  // rossz helyen van a perc jel
                        return 21;
                    }
                    k.setTipus(Tipus.DM);
                    if (!s.equals("")) {
                        if (köv == 0) {  //már ezzel kezdődik
                            k.setPerc(s);
                            köv++;
                        } else if (köv == 1) {
                            if (tizedes) {  //ha nem volt megadva fok
                                k.setPerc(k.getFok());
                                k.setFok("0");
                                k.setMásodperc(s);
                            } else {
                                k.setPerc(s);
                            }

                        } else if (köv == 2) {
                            k.setMásodperc(s);
                        }
                        s = "";
                        köv++;
                        if (!(i == hossz) && (köv == 1)) {  // Ha csak perc van tizedes formában
                            return 21;
                        }

                        if (tizedes) {
                            kész = true;//tizedes beolvasása kész
                        }
                    } else {  //másik helyre kell tárolni
                        if (tizedes) {
                            if (köv == 1) {
                                k.setPerc(k.getFok());
                                k.setFok("0");
                                köv++;
                            }
                            if (köv == 2) {
                                k.setMásodperc(k.getPerc());
                                k.setPerc(k.getFok());
                                k.setFok("0");
                            }
                        } else {
                            if (köv == 1) {
                                k.setPerc(k.getFok());
                                k.setFok("0");
                                köv++;
                            }
                        }
                    }
                    continue;

                }
                if (c == '"') { //IF dupla idézőjel
                    k.setTipus(Tipus.DMS);
                    if (!s.equals("")) {
                        if (köv == 0) {
                            k.setMásodperc(s);
                            k.setFok("0");
                            k.setPerc("0");
                        } else if (köv == 1) {
                            if (tizedes) {
                                k.setMásodperc(k.getFok());
                                k.setFok("0");
                                k.setPerc("0");
                                k.setMptört(s);
                            }else{
                               k.setMásodperc(s);
                            }
                        } else if (köv == 2) {
                             if (tizedes) {
                            k.setMptört(s);
                            k.setMásodperc(k.getPerc());
                            k.setPerc("0");
                            }else {
                                k.setMásodperc(s); 
                             }
                             
                        } else if (köv == 3) {
                            k.setMptört(s);
                        }
                        if (!(i == hossz) && (köv == 1)) {  // Ha csak perc van tizedes formában
                            alert(TEXTLATRF + 2);
                        }
                        s = "";
                        köv++;
                    } else {
                        if (köv == 1) {
                            k.setMásodperc(k.getFok());
                            k.setFok("0");
                            k.setPerc("0");
                        }
                        if (köv == 2) {
                            if  (tizedes){
                                k.setMptört(k.getPerc());
                                k.setMásodperc(k.getFok());
                                k.setFok("0");
                                k.setPerc("0");
                            }else{
                                k.setMásodperc(k.getPerc());
                                k.setPerc("0");
                            }
                        }
                        if (köv == 3) {
                            k.setMptört(k.getMásodperc());
                            k.setMásodperc(k.getPerc());
                            k.setPerc("0");
                        }
                        
                    }
                    continue;
                }
                if (c == '°') { //IF fok jel 
                    k.setTipus(Tipus.DD);
                    if (!s.equals("")) {
                        if (köv >= 2) {  // rossz helyen van a fok jel
                            return 21;
                        }
                        if (köv == 0) {
                            k.setFok(s);
                            k.setPerc("0");
                            continue;
                        }
                        if ((tizedes) && (köv == 1)) {
                            k.setPerc(s);
                        } else {
                            return 21;
                        }
                    }
                    continue;
                }
                if (c == ' ') { //IF space
                    if (!s.equals("")) {
                        switch (köv) {
                            case 0:
                                k.setFok(s);
                                break;
                            case 1:
                                k.setPerc(s);
                                break;
                            case 2:
                                k.setMásodperc(s);
                                break;
                            default:
                                k.setMptört(s);
                                break;
                        }
                        if (tizedes) {
                            kész = true;//tizedes beolvasása kész
                        }
                        s = "";
                        köv++;
                    }
                    continue;
                }
            }
            if (c >= '0' && c <= '9') {

                if (kész && s.equals("")) {
                    return 21;
                }
                s = s + c;
                if (i == hossz) {  //ha koordináta vége
                    switch (köv) {
                        case 0:
                            k.setFok(s);
                            k.setTipus(Tipus.DD);
                            break;
                        case 1:
                            k.setPerc(s);
                            if (tizedes) {
                                k.setTipus(Tipus.DD);
                            } else {
                                k.setTipus(Tipus.DM);
                            }
                            break;
                        case 2:
                            k.setMásodperc(s);
                            if (tizedes) {
                                k.setTipus(Tipus.DM);
                            } else {
                                k.setTipus(Tipus.DMS);
                            }
                            break;
                        default:
                            k.setMptört(s);
                            k.setTipus(Tipus.DMS);
                            break;
                    }
                }
                continue;
            }
            return i;
        }//for vége
        
        return 100;
    }

    private byte check2(Koordinata k) {
        int s = 0;
        boolean egy = false;
        if (!("".equals(k.getFok()))) {
            s = Integer.valueOf(k.getFok());
            if (s > 180) {
                return 1;
            }
        }
        switch (k.getTipus()) {
            case DD:
                if (s == 180) {
                    if (!("".equals(k.getPerc()))) {
                        if (!("0".equals(k.getPerc()))) {
                            return 1;
                        }
                    }
                }
                break;
            case DM:
                if (!("".equals(k.getPerc()))) {
                    s = Integer.valueOf(k.getPerc());
                    if (s > 60) {
                        return 2;
                    }
                    if (s == 60) {
                        if (!("".equals(k.getMásodperc()))) {
                            if (!("0".equals(k.getMásodperc()))) {
                                return 2;
                            }
                        }
                    }
                }
                break;
            default:
                if (!("".equals(k.getMásodperc()))) {
                    s = Integer.valueOf(k.getMásodperc());
                    if (s > 60) {
                        return 3;
                    }
                    if (s == 60) {
                        if (!("".equals(k.getMptört()))) {
                            if (!("0".equals(k.getMptört()))) {
                                return 3;
                            }
                        }
                    }
                }
        }

        return 0;
    }

    private void alert(String szöveg) {
        basePane.setDisable(true);
        basePane.setOpacity(0.3);
        alertPane.setVisible(true);
        alertText.setText(szöveg);
    }

    private void töröl(Koordinata o) {
        o.setNegatív(false);
        o.setFok("0");
        o.setPerc("0");
        o.setMásodperc("0");
        o.setMptört("0");
    }

    private String digRound(double d) {

        StringBuilder st = new StringBuilder();
        if (d == 0) {
            return st.toString();
        }
        //String s = String.valueOf(d);
        st.append(d);
        int i = st.indexOf(".");
        st.delete(0, i);
        i = st.length();
        if (i == 2 && st.charAt(1) == '0') {
            st.delete(0, i);
            return st.toString();
        }
        if (i > 6) {
            char c = st.charAt(6);
            st.delete(6, i);
            if (c >= '5' && c <= '9') {
                i = 5;
                c = st.charAt(i);

                while (c == '9') {  //ha a kerekítendő is 9 akkor meg kell keresni hátulról az első nem 9-es számot
                    i--;            // vagy el kel menni a tizedes pontig amia 0. elem
                    c = st.charAt(i);
                }
                if (!(i == 0)) {
                    st.delete(i, 6);  //A hossz itt már 6
                    st.append((char) (c + 1));
                }

            }
            i = st.lastIndexOf("0");
        }
        //0-ás karakterek eltávolítása
        while ((!(i == -1)) && ((st.length() - 1) == i)) {
            st.deleteCharAt(i);
            i = st.lastIndexOf("0");
        }
        return st.toString();
    }
}
