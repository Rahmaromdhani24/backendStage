package com.rahma.AvEchelon.Services.Personnel;

import java.util.Date;


public interface MesPersonnels {

    String getMle();
    String getNom();
    String getPrenom();
    String getCin() ;
    String getCollege();
    String getQualification() ;
    Date getDate_Anc() ;
    Date getDate_N() ;
    String getNum_CNSS() ; 
    String getTel() ;
    String getAdresse() ; 
    String getVille()  ; 
	String getNom_Service();
	String getRef() ; 
	String getNom_Dep() ; 
	String getCategorie();
	String getSousCategorie();

}
