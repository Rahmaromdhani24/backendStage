package com.rahma.AvEchelon.Entity;

import jakarta.persistence.*;


/**
 * The persistent class for the t_salaire database table.
 * 
 */
@Entity
@Table(name="t_salaire")
public class Salaire {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
    
	@Lob
	private String cat;
	
	@Column(name="s_cat")
	@Lob
	private String sCat;
	
	@Lob
	private String inddiff1;
	
	@Lob
    private String inddiff2;

	@Lob
    private String inddiff3;
	
	@Lob
    private String inddiff4;

	@Lob
    private String inddiff5;

	@Lob
    private String inddiff6;

	@Lob
    private String inddiff7;

	@Lob
    private String inddiff8;

	@Lob
    private String inddiff9;

	@Lob
	private String inddiff10;

	@Lob
    private String inddiff11;

	@Lob
    private String inddiff12;

	@Lob
    private String inddiff13;

	@Lob
    private String inddiff14;

	@Lob
    private String inddiff15;

	@Lob
    private String inddiff16;

	@Lob
    private String inddiff17;

	@Lob
    private String inddiff18;

	@Lob
    private String inddiff19;

	
	@Lob
    private String inddiff20;

	@Lob
    private String inddiff21;

	@Lob
    private String inddiff22;

	@Lob
    private String inddiff23;

	@Lob
    private String inddiff24;

	@Lob
    private String inddiff25;

	@Lob
    private String inddiff26;

	@Lob
    private String inddiff27;

	@Lob
    private String inddiff28;

	@Lob
    private String inddiff29;


	@Lob
    private String inddiff30;

	@Lob
    private String inddiff31;

	@Lob
    private String inddiff32;

	@Lob
    private String inddiff33;

	@Lob
    private String inddiff34;

	@Lob
    private String inddiff35;

	@Lob
    private String inddiff36;

	@Lob
    private String inddiff37;

	@Lob
    private String inddiff38;

	@Lob
    private String inddiff39;

	@Lob
    private String inddiff40;
	
	@Lob
	private String th1;

	@Lob
	private String th2;
	
	@Lob
	private String th3;
	
	@Lob
	private String th4;
	
	@Lob
	private String th5;

	@Lob
	private String th6;

	@Lob
	private String th7;

	@Lob
	private String th8;

	@Lob
	private String th9;
	
	@Lob
	private String th10;

	@Lob
	private String th11;

	@Lob
	private String th12;

	@Lob
	private String th13;

	@Lob
	private String th14;

	@Lob
	private String th15;

	@Lob
	private String th16;

	@Lob
	private String th17;

	@Lob
	private String th18;

	@Lob
	private String th19;

	@Lob
	private String th20;

	@Lob
	private String th21;

	@Lob
	private String th22;

	@Lob
	private String th23;

	@Lob
	private String th24;

	@Lob
	private String th25;

	@Lob
	private String th26;

	@Lob
	private String th27;

	@Lob
	private String th28;

	@Lob
	private String th29;

	@Lob
	private String th30;

	@Lob
	private String th31;

	@Lob
	private String th32;

	@Lob
	private String th33;

	@Lob
	private String th34;

	@Lob
	private String th35;

	@Lob
	private String th36;

	@Lob
	private String th37;

	@Lob
	private String th38;

	@Lob
	private String th39;

	@Lob
	private String th40;

	

	public Salaire() {	}

	public int getId() {		return id;	}

	public String getCat() {		return cat;	}

	public String getsCat() {return sCat;}

	public String getInddiff1() {return inddiff1;}

	public String getInddiff2() {	return inddiff2;}

	public String getInddiff3() {	return inddiff3;	}
	
	public String getInddiff4() {	return inddiff4;}
	
	public String getInddiff5() {	return inddiff5;}
	
	public String getInddiff6() {return inddiff6;}
	
	public String getInddiff7() {	return inddiff7;}
	
	public String getInddiff8() {	return inddiff8;	}

	public String getInddiff9() {		return inddiff9;}
	
	public String getInddiff10() {		return inddiff10;}

	public String getInddiff11() {	return inddiff11;}
	
	public String getInddiff12() {	return inddiff12;}
	
	public String getInddiff13() {		return inddiff13;	}

	public String getInddiff14() {		return inddiff14;	}

	public String getInddiff15() {		return inddiff15;}

	public String getInddiff16() {		return inddiff16;}

	public String getInddiff17() {		return inddiff17;	}

	public String getInddiff18() {	return inddiff18;}
	
	public String getInddiff19() {		return inddiff19;	}

	public String getInddiff20() {		return inddiff20;	}

	public String getInddiff21() {		return inddiff21;	}

	public String getInddiff22() {		return inddiff22;	}

	public String getInddiff23() {		return inddiff23;	}
	
	public String getInddiff24() {		return inddiff24;}

	public String getInddiff25() {		return inddiff25;}
	
	public String getInddiff26() {	return inddiff26;}
	
	public String getInddiff27() {	return inddiff27;}
	
	public String getInddiff28() {	return inddiff28;	}
	
	public String getInddiff29() {		return inddiff29;	}
	
	public String getInddiff30() {		return inddiff30;	}

	public String getInddiff31() {		return inddiff31;	}

	public String getInddiff32() {	return inddiff32;}
	
	public String getInddiff33() {	return inddiff33;	}
	
	public String getInddiff34() {		return inddiff34;}

	public String getInddiff35() {		return inddiff35;}

	public String getInddiff36() {	return inddiff36;}
	
	public String getInddiff37() {		return inddiff37;	}

	public String getInddiff38() {		return inddiff38;	}

	public String getInddiff39() {		return inddiff39;	}
	
	public String getInddiff40() {		return inddiff40;	}
	
	public String getTh1() {		return th1;	}

	public String getTh2() {		return th2;	}

	public String getTh3() {		return th3;	}

	public String getTh4() {		return th4;}

	public String getTh5() {		return th5;	}

	public String getTh6() {		return th6;	}

	public String getTh7() {		return th7;	}

	public String getTh8() {		return th8;}

	public String getTh9() {return th9;	}
	
	public String getTh10() {	return th10;}
	
	public String getTh11() {	return th11;}
	
	public String getTh12() {	return th12;}
	
	public String getTh13() {	return th13;}
	
	public String getTh14() {	return th14;}
	
	public String getTh15() {	return th15;	}
	
	public String getTh16() {	return th16;	}
	
	public String getTh17() {	return th17; }
	
	public String getTh18() {	return th18;	}
	
	public String getTh19() {	return th19;}
	
	public String getTh20() {		return th20;	}

	public String getTh21() {	return th21;}
	
	public String getTh22() {	return th22;}
	
	public String getTh23() {	return th23;}
	
	public String getTh24() {	return th24;}
	
	public String getTh25() {	return th25;}
	
	public String getTh26() {	return th26;}
	
	public String getTh27() {	return th27;}
	
	public String getTh28() {	return th28;}
	
	public String getTh29() {	return th29; }
	
	public String getTh30() {	return th30;}
	
	public String getTh31() {	return th31;}
	
	public String getTh32() {	return th32;}
	
	public String getTh33() {	return th33; }
	
	public String getTh34() {	return th34;}
	
	public String getTh35() { 	return th35; }
	
	public String getTh36() {		return th36;	}

	public String getTh37() { 		return th37;	}

	public String getTh38() { 	return th38; }
	
	public String getTh39() {		return th39;}

	public String getTh40() {	return th40;}
}