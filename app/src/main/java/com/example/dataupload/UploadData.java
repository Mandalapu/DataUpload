package com.example.dataupload;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;



import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;


public class UploadData {
	static JSONObject obj;
	/**
	 * @param args
	 */
	static String user;
	static String audiofiles[]=new String[30];
	static String loginquery,aquery,dquery,rfquery,vequery,pvquery,nsquery,arquery,spquery,swquery,wquery,trackquery,voquery;
	public static String getuser(){return user;}
	public static String getlogin(){return loginquery;}
	public static String geta(){return aquery;}
	public static String getd(){return dquery;}
	public static String getrf(){return rfquery;}
	public static String getve(){return vequery;}
	public static String getpv(){return pvquery;}
	public static String getns(){return nsquery;}
	public static String getar(){return arquery;}
	public static String getvo(){return voquery;}
	public static String getsp(){return spquery;}
	public static String getsw(){return swquery;}
	public static String getw(){return wquery;}
	public static String gettrack(){return trackquery;}
	public static String getaudio(int i){return audiofiles[i];}
	public static void setaudio(String afile, int i){audiofiles[i]=afile;}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	public static void uploadJson(String u, String filename) throws IOException, JSONException {
		user=u;
		String f = Environment.getExternalStorageDirectory()+"/"+user+"/"+filename;

		 BufferedReader reader = new BufferedReader( new FileReader (f));
		 String content = "",line=null;
		 while ((line=reader.readLine())!=null)
			 content += line;
		Log.d("JSON content", content);
		 reader.close();
		 obj = new JSONObject(content);
		 //user=Integer.parseInt(uid);
		 
		 //forming all the queries
		 
		 //sectionA();
		Log.d("Upload Data Activity", "Calling each section to form the queries");
		sectionAR();
		 sectionD();
		sectionRF();
		sectionSP();
		sectionVO();
		sectionW();
		tasktrack();
		login();
		sectionNS();
		Log.d("UploadData Activity", "All the queries are generated");
	}
	public static void login() {
		loginquery="INSERT INTO `log_in` (`user_id`) VALUES ("+user+")";
	}

	/**
	 * Modified, query should be updated.
	 * @throws JSONException
     */
	public static void sectionD() throws JSONException{
		String d106="uploads/"+user+"_audio/audio_d106_"+user+".mp3",d148="uploads/"+user+"_audio/audio_d148_"+user+".mp3",d121="uploads/"+user+"_audio/audio_d121_"+user+".mp3";
		String d101,d102,d106_start,d106_end,d121_start,d121_end,d142_start,d142_end,d143_start,d143_end,d144_start,d144_end,d145_start,d145_end,d146_start,d146_end,d148_start,d148_end,d178_start,d178_end,d179_start,d179_end,d180_start,d180_end;
		int d142,d143,d144,d145,d146,d178,d179,d180;

		if(obj.has("d106_start"))d106_start=obj.getString("d106_start");
		else d106_start="0000-00-00 00:00:00";
		if(obj.has("d121_start"))d121_start=obj.getString("d121_start");
		else d121_start="0000-00-00 00:00:00";

		if(obj.has("d148_start"))d148_start=obj.getString("d148_start");
		else d148_start="0000-00-00 00:00:00";

		if(obj.has("d106_end"))d106_end=obj.getString("d106_end");
		else d106_end="0000-00-00 00:00:00";
		if(obj.has("d121_end"))d121_end=obj.getString("d121_end");
		else d121_end="0000-00-00 00:00:00";
		if(obj.has("d148_end"))d148_end=obj.getString("d148_end");
		else d148_end="0000-00-00 00:00:00";

		dquery = "INSERT INTO `self_rated_memory`(`user_id`, `D106_ans`, `D106_start_time`, `D106_end_time`, `D121_ans`, `D121_start_time`, `D121_end_time`, `D148_ans`, `D148_start_time`, `D148_end_time`) VALUES ("+user+",'"+d106+"','"+d106_start+"','"+d106_end+"','"+d121+"','"+d121_start+"','"+d121_end+"','"+d148+"','"+d148_start+"','"+d148_end+"')";
		Log.d("UploadActivity-dquery", dquery);
	}

	/**
	 * Modified, query should be updated.
	 * @throws JSONException
     */
	public static void sectionRF() throws JSONException{
		String rfstart1,rfstart2,rfstart3,rfend1,rfend2,rfend3;
		rfstart1=obj.getString("rf101_start");
		rfend1=obj.getString("rf101_end");
		String rfcode1="uploads/"+user+"_audio/audio_rf101_"+user+".mp3";//rfcode2="uploads/"+user+"_audio/audio_rf201_"+user+".mp3",rfcode3="uploads/"+user+"_audio/audio_rf301_"+user+".mp3";
		rfquery="INSERT INTO `retrieval_fluency` (`user_id`, `RF101_ans`, `RF101_start_time`, `RF101_end_time`) VALUES ("+user+", '"+rfcode1+"', '"+rfstart1+"', '"+rfend1+"')";
		Log.d("UploadActivity-rfquery", rfquery);
	}

	public static void sectionNS() throws JSONException{
		String qn1="nsa31",qn2="nsa32",qn3="nsa33";
		String qn4="",qn5="",qn6="",qn1s,qn2s,qn3s,qn4s,qn5s,qn6s,qn1e,qn2e,qn3e,qn4e,qn5e,qn6e;
		int an1,an2,an3,an4,an5,an6=-1,sn1,sn2,sn3,sn4,sn5,sn6,ans1=-1,ans2=-1;
		an1=obj.getInt(qn1+"_ans");an2=obj.getInt(qn2+"_ans");an3=obj.getInt(qn3+"_ans");
		sn1=obj.getInt(qn1+"_score");sn2=obj.getInt(qn2+"_score");sn3=obj.getInt(qn3+"_score");
		int sum=sn1+sn2+sn3;
		if(sum==0){qn4="nsa11";qn5="nsa12";qn6="nsa13";}
		if(sum==1){qn4="nsa21";qn5="nsa22";qn6="nsa23";}
		if(sum==2){qn4="nsa41";qn5="nsa42";qn6="nsa43";}
		if(sum==3){qn4="nsa51";qn5="nsa52";qn6="nsa53";}
		an4=obj.getInt(qn4+"_ans");an5=obj.getInt(qn5+"_ans");
		if(sum==3){
			String ans = obj.getString(qn6+"_ans");
			ans1=Integer.parseInt(ans.substring(0,ans.indexOf(',')));
			ans2=Integer.parseInt(ans.substring(ans.indexOf(',')+1));
		}
		else an6=obj.getInt(qn6+"_ans");
		sn4=obj.getInt(qn4+"_score");sn5=obj.getInt(qn5+"_score");sn6=obj.getInt(qn6+"_score");
		qn1s=obj.getString(qn1+"_start");qn2s=obj.getString(qn2+"_start");qn3s=obj.getString(qn3+"_start");
		qn1e=obj.getString(qn1+"_end");qn2e=obj.getString(qn2+"_end");qn3e=obj.getString(qn3+"_end");
		qn4s=obj.getString(qn4+"_start");qn5s=obj.getString(qn5+"_start");qn6s=obj.getString(qn6+"_start");
		qn4e=obj.getString(qn4+"_end");qn5e=obj.getString(qn5+"_end");qn6e=obj.getString(qn6+"_end");
		//make ns query
		if(sum==3){
			nsquery="INSERT INTO `number_series` (`user_id`, `q_1_code`, `q_1_ans`, `q_1_start_time`, `q_1_end_time`, `q_1_score`, `q_2_code`, `q_2_ans`, `q_2_start_time`, `q_2_end_time`, `q_2_score`, `q_3_code`, `q_3_ans`, `q_3_start_time`, `q_3_end_time`, `q_3_score`, `q_4_code`, `q_4_ans`, `q_4_start_time`, `q_4_end_time`, `q_4_score`, `q_5_code`, `q_5_ans`, `q_5_start_time`, `q_5_end_time`, `q_5_score`, `q_6_code`, `q_6_ans`, `q_6_ans_sub`, `q_6_start_time`, `q_6_end_time`, `q_6_score`) VALUES ("+user+", '"+qn1+"', "+an1+", '"+qn1s+"', '"+qn1e+"', "+sn1+", '"+qn2+"', "+an2+", '"+qn2s+"', '"+qn2e+"', "+sn2+",'"+qn3+"', "+an3+", '"+qn3s+"', '"+qn3e+"', "+sn3+",'"+qn4+"', "+an4+", '"+qn4s+"', '"+qn4e+"', "+sn4+",'"+qn5+"', "+an5+", '"+qn5s+"', '"+qn5e+"', "+sn5+",'"+qn6+"', "+ans1+", "+ans2+", '"+qn6s+"', '"+qn6e+"', "+sn6+")";
		}
		else
			nsquery="INSERT INTO `number_series` (`user_id`, `q_1_code`, `q_1_ans`, `q_1_start_time`, `q_1_end_time`, `q_1_score`, `q_2_code`, `q_2_ans`, `q_2_start_time`, `q_2_end_time`, `q_2_score`, `q_3_code`, `q_3_ans`, `q_3_start_time`, `q_3_end_time`, `q_3_score`, `q_4_code`, `q_4_ans`, `q_4_start_time`, `q_4_end_time`, `q_4_score`, `q_5_code`, `q_5_ans`, `q_5_start_time`, `q_5_end_time`, `q_5_score`, `q_6_code`, `q_6_ans`, `q_6_start_time`, `q_6_end_time`, `q_6_score`) VALUES ("+user+", '"+qn1+"', "+an1+", '"+qn1s+"', '"+qn1e+"', "+sn1+", '"+qn2+"', "+an2+", '"+qn2s+"', '"+qn2e+"', "+sn2+",'"+qn3+"', "+an3+", '"+qn3s+"', '"+qn3e+"', "+sn3+",'"+qn4+"', "+an4+", '"+qn4s+"', '"+qn4e+"', "+sn4+",'"+qn5+"', "+an5+", '"+qn5s+"', '"+qn5e+"', "+sn5+",'"+qn6+"', "+an6+", '"+qn6s+"', '"+qn6e+"', "+sn6+")";
		Log.d("UploadActivity-nsquery", nsquery);
	}

	/**
	 * Should not be changed, need to see if queries are to be updated.(checked)
	 * @throws JSONException
     */
	public static void sectionAR() throws JSONException{
		String qn1="ar31",qn2="ar32",qn3="ar33";
		String qn4="",qn5="",qn6="",qn1s,qn2s,qn3s,qn4s,qn5s,qn6s,qn1e,qn2e,qn3e,qn4e,qn5e,qn6e;
		int an1,an2,an3,an4,an5,an6,sn1,sn2,sn3,sn4,sn5,sn6;
		an1=obj.getInt(qn1+"_ans");an2=obj.getInt(qn2+"_ans");an3=obj.getInt(qn3+"_ans");
		sn1=obj.getInt(qn1+"_score");sn2=obj.getInt(qn2+"_score");sn3=obj.getInt(qn3+"_score");
		int sum=sn1+sn2+sn3;
		if(sum==0){qn4="ar11";qn5="ar12";qn6="ar13";}
		if(sum==1){qn4="ar21";qn5="ar22";qn6="ar23";}
		if(sum==2){qn4="ar41";qn5="ar42";qn6="ar43";}
		if(sum==3){qn4="ar51";qn5="ar52";qn6="ar53";}
		an4=obj.getInt(qn4+"_ans");an5=obj.getInt(qn5+"_ans");an6=obj.getInt(qn6+"_ans");
		sn4=obj.getInt(qn4+"_score");sn5=obj.getInt(qn5+"_score");sn6=obj.getInt(qn6+"_score");
		qn1s=obj.getString(qn1+"_start");qn2s=obj.getString(qn2+"_start");qn3s=obj.getString(qn3+"_start");
		qn1e=obj.getString(qn1+"_end");qn2e=obj.getString(qn2+"_end");qn3e=obj.getString(qn3+"_end");
		qn4s=obj.getString(qn4+"_start");qn5s=obj.getString(qn5+"_start");qn6s=obj.getString(qn6+"_start");
		qn4e=obj.getString(qn4+"_end");qn5e=obj.getString(qn5+"_end");qn6e=obj.getString(qn6+"_end");
		//make ar query
		arquery="INSERT INTO `abstract_reasoning` (`user_id`, `q_1_code`, `q_1_ans`, `q_1_start_time`, `q_1_end_time`, `q_1_score`, `q_2_code`, `q_2_ans`, `q_2_start_time`, `q_2_end_time`, `q_2_score`, `q_3_code`, `q_3_ans`, `q_3_start_time`, `q_3_end_time`, `q_3_score`, `q_4_code`, `q_4_ans`, `q_4_start_time`, `q_4_end_time`, `q_4_score`, `q_5_code`, `q_5_ans`, `q_5_start_time`, `q_5_end_time`, `q_5_score`, `q_6_code`, `q_6_ans`, `q_6_start_time`, `q_6_end_time`, `q_6_score`) VALUES ("+user+", '"+qn1+"', "+an1+", '"+qn1s+"', '"+qn1e+"', "+sn1+", '"+qn2+"', "+an2+", '"+qn2s+"', '"+qn2e+"', "+sn2+",'"+qn3+"', "+an3+", '"+qn3s+"', '"+qn3e+"', "+sn3+",'"+qn4+"', "+an4+", '"+qn4s+"', '"+qn4e+"', "+sn4+",'"+qn5+"', "+an5+", '"+qn5s+"', '"+qn5e+"', "+sn5+",'"+qn6+"', "+an6+", '"+qn6s+"', '"+qn6e+"', "+sn6+")";
		Log.d("Ar-Query", arquery);
	}

	/**
	 * Newly added section. Need to see whether the queries are updated.
	 * @throws JSONException
     */
	public static void sectionVO() throws JSONException{
		String qn1="vo31",qn2="vo32",qn3="vo33";
		String qn4="",qn5="",qn6="",qn1s,qn2s,qn3s,qn4s,qn5s,qn6s,qn1e,qn2e,qn3e,qn4e,qn5e,qn6e;
		int an1,an2,an3,an4,an5,an6,sn1,sn2,sn3,sn4,sn5,sn6;
//		an1=obj.getInt(qn1+"_ans");an2=obj.getInt(qn2+"_ans");an3=obj.getInt(qn3+"_ans");
//		sn1=obj.getInt(qn1+"_score");sn2=obj.getInt(qn2+"_score");sn3=obj.getInt(qn3+"_score");
		an1=obj.getInt(qn1);an2=obj.getInt(qn2);an3=obj.getInt(qn3);
		sn1=obj.getInt(qn1+"_score");sn2=obj.getInt(qn2+"_score");sn3=obj.getInt(qn3+"_score");
		int sum=sn1+sn2+sn3;
		if(sum==0){qn4="vo11";qn5="vo12";qn6="vo13";}
		if(sum==1){qn4="vo21";qn5="vo22";qn6="vo23";}
		if(sum==2){qn4="vo41";qn5="vo42";qn6="vo43";}
		if(sum==3){qn4="vo51";qn5="vo52";qn6="vo53";}
//		an4=obj.getInt(qn4+"_ans");an5=obj.getInt(qn5+"_ans");an6=obj.getInt(qn6+"_ans");
		an4=obj.getInt(qn4);an5=obj.getInt(qn5);an6=obj.getInt(qn6);
		sn4=obj.getInt(qn4+"_score");sn5=obj.getInt(qn5+"_score");sn6=obj.getInt(qn6+"_score");
		qn1s=obj.getString(qn1+"_start");qn2s=obj.getString(qn2+"_start");qn3s=obj.getString(qn3+"_start");
		qn1e=obj.getString(qn1+"_end");qn2e=obj.getString(qn2+"_end");qn3e=obj.getString(qn3+"_end");
		qn4s=obj.getString(qn4+"_start");qn5s=obj.getString(qn5+"_start");qn6s=obj.getString(qn6+"_start");
		qn4e=obj.getString(qn4+"_end");qn5e=obj.getString(qn5+"_end");qn6e=obj.getString(qn6+"_end");
		//make vo query
		voquery="INSERT INTO `vocab` (`user_id`, `q_1_code`, `q_1_ans`, `q_1_start_time`, `q_1_end_time`, `q_1_score`, `q_2_code`, `q_2_ans`, `q_2_start_time`, `q_2_end_time`, `q_2_score`, `q_3_code`, `q_3_ans`, `q_3_start_time`, `q_3_end_time`, `q_3_score`, `q_4_code`, `q_4_ans`, `q_4_start_time`, `q_4_end_time`, `q_4_score`, `q_5_code`, `q_5_ans`, `q_5_start_time`, `q_5_end_time`, `q_5_score`, `q_6_code`, `q_6_ans`, `q_6_start_time`, `q_6_end_time`, `q_6_score`) VALUES ("+user+", '"+qn1+"', "+an1+", '"+qn1s+"', '"+qn1e+"', "+sn1+", '"+qn2+"', "+an2+", '"+qn2s+"', '"+qn2e+"', "+sn2+",'"+qn3+"', "+an3+", '"+qn3s+"', '"+qn3e+"', "+sn3+",'"+qn4+"', "+an4+", '"+qn4s+"', '"+qn4e+"', "+sn4+",'"+qn5+"', "+an5+", '"+qn5s+"', '"+qn5e+"', "+sn5+",'"+qn6+"', "+an6+", '"+qn6s+"', '"+qn6e+"', "+sn6+")";
		Log.d("voquery", voquery);
	}

	/**
	 * Should not be changed, but need to see if the queries are to be updated.
	 * @throws JSONException
     */
	public static void sectionSP()throws JSONException{
		String qn1="sp31",qn2="sp32",qn3="sp33";
		String qn4="",qn5="",qn6="",qn1s,qn2s,qn3s,qn4s,qn5s,qn6s,qn1e,qn2e,qn3e,qn4e,qn5e,qn6e;
		int an1,an2,an3,an4,an5,an6,sn1,sn2,sn3,sn4,sn5,sn6;
		an1=obj.getInt(qn1+"_ans");an2=obj.getInt(qn2+"_ans");an3=obj.getInt(qn3+"_ans");
		sn1=obj.getInt(qn1+"_score");sn2=obj.getInt(qn2+"_score");sn3=obj.getInt(qn3+"_score");		
		int sum=sn1+sn2+sn3;
		if(sum==0){qn4="sp11";qn5="sp12";qn6="sp13";}
		if(sum==1){qn4="sp21";qn5="sp22";qn6="sp23";}
		if(sum==2){qn4="sp41";qn5="sp42";qn6="sp43";}
		if(sum==3){qn4="sp51";qn5="sp52";qn6="sp53";}
		an4=obj.getInt(qn4+"_ans");an5=obj.getInt(qn5+"_ans");an6=obj.getInt(qn6+"_ans");
		sn4=obj.getInt(qn4+"_score");sn5=obj.getInt(qn5+"_score");sn6=obj.getInt(qn6+"_score");
		qn1s=obj.getString(qn1+"_start");qn2s=obj.getString(qn2+"_start");qn3s=obj.getString(qn3+"_start");
		qn1e=obj.getString(qn1+"_end");qn2e=obj.getString(qn2+"_end");qn3e=obj.getString(qn3+"_end");
		qn4s=obj.getString(qn4+"_start");qn5s=obj.getString(qn5+"_start");qn6s=obj.getString(qn6+"_start");
		qn4e=obj.getString(qn4+"_end");qn5e=obj.getString(qn5+"_end");qn6e=obj.getString(qn6+"_end");
		//make sp query
		spquery="INSERT INTO `spatial_visualization` (`user_id`, `q_1_code`, `q_1_ans`, `q_1_start_time`, `q_1_end_time`, `q_1_score`, `q_2_code`, `q_2_ans`, `q_2_start_time`, `q_2_end_time`, `q_2_score`, `q_3_code`, `q_3_ans`, `q_3_start_time`, `q_3_end_time`, `q_3_score`, `q_4_code`, `q_4_ans`, `q_4_start_time`, `q_4_end_time`, `q_4_score`, `q_5_code`, `q_5_ans`, `q_5_start_time`, `q_5_end_time`, `q_5_score`, `q_6_code`, `q_6_ans`, `q_6_start_time`, `q_6_end_time`, `q_6_score`) VALUES ("+user+", '"+qn1+"', "+an1+", '"+qn1s+"', '"+qn1e+"', "+sn1+", '"+qn2+"', "+an2+", '"+qn2s+"', '"+qn2e+"', "+sn2+",'"+qn3+"', "+an3+", '"+qn3s+"', '"+qn3e+"', "+sn3+",'"+qn4+"', "+an4+", '"+qn4s+"', '"+qn4e+"', "+sn4+",'"+qn5+"', "+an5+", '"+qn5s+"', '"+qn5e+"', "+sn5+",'"+qn6+"', "+an6+", '"+qn6s+"', '"+qn6e+"', "+sn6+")";
		Log.d("spquery", spquery);
	}

	/**
	 * Should not be modifed,but check for the queries.
 	 * @throws JSONException
     */
	public static void sectionW() throws JSONException{
		String w001,w002,w003,w023_desktop,w023_laptop,w023_tablet,w023_ereader,w023_smartphone,w023_mobile,w015,w016,w017,w017_sub,w004,w007,w008,w010,w011,w012;
		int w015_sub,w018,w019,w020,w021,w022;
		if(obj.has("w001"))w001=obj.getString("w001");
		else w001=null;
		if(obj.has("w002"))w002=obj.getString("w002");
		else w002=null;
		if(obj.has("w003"))w003=obj.getString("w003");
		else w003=null;
		if(obj.has("w015"))w015=obj.getString("w015");
		else w015=null;
		if(obj.has("w016"))w016=obj.getString("w016");
		else w016=null;
		if(obj.has("w017"))w017=obj.getString("w017");
		else w017=null;
		if(obj.has("w017_sub"))w017_sub=obj.getString("w017_sub");
		else w017_sub=null;
		if(obj.has("w004"))w004=obj.getString("w004");
		else w004=null;
		if(obj.has("w007"))w007=obj.getString("w007");
		else w007=null;
		if(obj.has("w008"))w008=obj.getString("w008");
		else w008=null;
		if(obj.has("w010"))w010=obj.getString("w010");
		else w010=null;
		if(obj.has("w011"))w011=obj.getString("w011");
		else w011=null;
		if(obj.has("w012"))w012=obj.getString("w012");
		else w012=null;
		if(obj.has("w023_desktop"))w023_desktop=obj.getString("w023_desktop");
		else w023_desktop=null;
		if(obj.has("w023_laptop"))w023_laptop=obj.getString("w023_laptop");
		else w023_laptop=null;
		if(obj.has("w023_tablet"))w023_tablet=obj.getString("w023_tablet");
		else w023_tablet=null;
		if(obj.has("w023_ereader"))w023_ereader=obj.getString("w023_ereader");
		else w023_ereader=null;
		if(obj.has("w023_smartphone"))w023_smartphone=obj.getString("w023_smartphone");
		else w023_smartphone=null;
		if(obj.has("w023_mobile"))w023_mobile=obj.getString("w023_mobile");
		else w023_mobile=null;
		if(obj.has("w018"))w018=obj.getInt("w018");
		else w018=-1;
		if(obj.has("w019"))w019=obj.getInt("w019");
		else w019=-1;
		if(obj.has("w020"))w020=obj.getInt("w020");
		else w020=-1;
		if(obj.has("w021"))w021=obj.getInt("w021");
		else w021=-1;
		if(obj.has("w022"))w022=obj.getInt("w022");
		else w022=-1;
		if(obj.has("w015_sub"))w015_sub=obj.getInt("w015_sub");
		else w015_sub=-1;

		wquery = "INSERT INTO `wrap_up`(`user_id`, `use_email`, `email_usage`, `web_usage`, `tech_usage_desktop`, `tech_usage_laptop`, `tech_usage_tablet`, `tech_usage_ereader`, `tech_usage_smartphone`, `tech_usage_mobile`, `W015_ans`, `W015_sub_ans`, `W016_ans`, `W017_ans`, `W017_sub_ans`, `W018_ans`, `W019_ans`, `W020_ans`, `W021_ans`, `W022_ans`, `enjoy`, `fatigue`, `multitask`, `pol_orient`, `W011_ans`, `W012_ans`) VALUES ("+user+",'"+w001+"','"+w002+"','"+w003+"','"+w023_desktop+"','"+w023_laptop+"','"+w023_tablet+"','"+w023_ereader+"','"+w023_smartphone+"','"+w023_mobile+"','"+w015+"',"+w015_sub+",'"+w016+"','"+w017+"','"+w017_sub+"',"+w018+","+w019+","+w020+","+w021+","+w022+",'"+w004+"','"+w007+"','"+w008+"','"+w010+"','"+w011+"','"+w012+"')";
		Log.d("WrapQuery", wquery);
	}


	public static void tasktrack() throws JSONException {
		String sur_s=obj.getString("sur_start");
		String sur_e=obj.getString("sur_end");
		String secd_s=obj.getString("sec_d_start");
		String secd_e=obj.getString("sec_d_end");
		String secrf_s=obj.getString("sec_rf_start");
		String secrf_e=obj.getString("sec_rf_end");
		String secns_s=obj.getString("sec_ns_start");
		String secns_e=obj.getString("sec_ns_end");
		String secar_s=obj.getString("sec_ar_start");
		String secar_e=obj.getString("sec_ar_end");
		String secsp_s=obj.getString("sec_sp_start");
		String secsp_e=obj.getString("sec_sp_end");
		String secw_s=obj.getString("sec_wrap_start");
		String secw_e=obj.getString("sec_wrap_end");
		trackquery="INSERT INTO `task_tracking` (`user_id`, `sur_start_time`, `sec_d_start_time`, `sec_d_end_time`, `sec_ns_start_time`, `sec_ns_end_time`, `sec_rf_start_time`, `sec_rf_end_time`, `sec_ar_start_time`, `sec_ar_end_time`, `sec_sp_start_time`, `sec_sp_end_time`, `sec_wrap_start_time`, `sec_wrap_end_time`, `sur_end_time`) VALUES ("+user+", '"+sur_s+"', '"+secd_s+"', '"+secd_e+"', '"+secns_s+"', '"+secns_e+"', '"+secrf_s+"', '"+secrf_e+"', '"+secar_s+"', '"+secar_e+"', '"+secsp_s+"', '"+secsp_e+"', '"+secw_s+"', '"+secw_e+"', '"+sur_e+"')";
		Log.d("trackquery", trackquery);
	}
}
