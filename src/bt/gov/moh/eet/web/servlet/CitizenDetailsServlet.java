package bt.gov.moh.eet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wso2.client.api.DCRC_IndividualCitizenDetailAPI.DefaultApi;
import org.wso2.client.model.DCRC_IndividualCitizenDetailAPI.CitizenOBJ;
import org.wso2.client.model.DCRC_IndividualCitizenDetailAPI.CitizenResponse;

import com.squareup.okhttp.OkHttpClient;
import bt.gov.moh.eet.dto.CitizenDTO;

/**
 * Servlet implementation class CitizenDetailsServlet
 */
@WebServlet("/getCitizenDetails")
public class CitizenDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CitizenDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer buffer = new StringBuffer();
		
		try {
			String cidNo = request.getParameter("cidNo");
			
			if(cidNo.length() > 11) {
				parseBarcode(request, response, cidNo);
			} else {
				ResourceBundle bundle = ResourceBundle.getBundle("eet");
				
				OkHttpClient httpClient = new OkHttpClient();
				httpClient.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
				httpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
				
				org.wso2.client.api.ApiClient apiClient = new org.wso2.client.api.ApiClient();
				apiClient.setHttpClient(httpClient);
				apiClient.setBasePath(bundle.getString("getCitizenDetailsAPI.endPointURL"));
				
				apiClient.setAccessToken("7dd8dc5b-a021-36f8-a8bb-99753c9c5cdb");
				DefaultApi api = new DefaultApi(apiClient);
				CitizenResponse citizenDetailsResponse = api.citizendetailCidGet(cidNo);
				CitizenOBJ citizendetailsObj = null;
				
				if(citizenDetailsResponse.getCitizendetails().getCitizendetail()!=null && !citizenDetailsResponse.getCitizendetails().getCitizendetail().isEmpty() ){
					citizendetailsObj = citizenDetailsResponse.getCitizendetails().getCitizendetail().get(0);
					
					String firstName = citizendetailsObj.getFirstName();
					String middleName = citizendetailsObj.getMiddlename();
					String lastName = citizendetailsObj.getLastName();
					String dob = citizendetailsObj.getDob();
					String gender = citizendetailsObj.getGender();
					
					CitizenDTO dto = new CitizenDTO();
					dto.setCitizenName(firstName+" "+middleName+" "+lastName);
					dto.setGender(gender);
					dto.setDob(dob);
					
					buffer.append("<xml-response>");
						buffer.append("<guestId></guestId>");
						buffer.append("<cidNo>"+citizendetailsObj.getCidNumber()+"</cidNo>");
						buffer.append("<name>"+dto.getCitizenName()+"</name>");
						buffer.append("<gender>"+dto.getGender()+"</gender>");
						buffer.append("<dob>"+dto.getDob()+"</dob>");
						buffer.append("<contactno></contactno>");
						buffer.append("<nationality></nationality>");
						buffer.append("<presentAddress></presentAddress>");
						buffer.append("<residentflag></residentflag>");
						buffer.append("<imagepath></imagepath>");
						buffer.append("<data-type>CITIZEN_API</data-type>");
					buffer.append("</xml-response>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error at GetCitizenDetails[doAction]"+e);
		}
		
		out.println(buffer);
		out.flush();
	}
	
	/**
	 * @throws ParseException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void parseBarcode(HttpServletRequest request, HttpServletResponse response, String param) throws ServletException, IOException, ParseException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		StringBuffer buffer = new StringBuffer();
		
		String cidNumber=null, cidIssueDate=null, cidExpiryDate=null, surname=null, firstname=null, houseNo=null, thramNo=null, 
		dzongkhag=null, dob=null, gender=null, village= null, gewog = null, motherLastName=null, motherMiddleName = null, motherFirstName=null, fatherLastName=null, 
		fatherMiddleName=null, fatherFirstName=null, age = null;
		
		String checkOldNewCard = param.substring(0,1);
		if(checkOldNewCard.matches("[a-zA-z]")){
			//First Name
			int a=0, counter=0;
			boolean isFemale = param.contains("Female");
			boolean isMale = param.contains("Male");
			if(isMale){
				firstname = param.substring(0, param.indexOf("Male"));
				gender = "Male";
				param  = param.substring(firstname.length()+gender.length(), param.length());
			}
			if(isFemale){
				firstname = param.substring(0, param.indexOf("Female"));
				gender = "Female";
				param  = param.substring(firstname.length()+gender.length(), param.length());
			}
			
			//dob
			dob = param.substring(counter, counter+10);
			counter = counter + 10;
			
			//cid number
			cidNumber = param.substring(counter, counter+11);
			counter = counter + 11;
			
			//household number
			houseNo = param.substring(counter, counter+9);
			counter = counter + 9;
			
			//now reverse the string after reading FMR
			String reverseString  = param.substring(counter, param.lastIndexOf("FMR"));
			String replaceReverseString = reverseString.replaceAll(" ", "#");
			if(replaceReverseString.contains("##")){
				replaceReverseString = replaceReverseString.replace("##", "#");
			}
			
			String[] dzoArray = {"Bumthang","Chukha","Dagana","Gasa","Haa","Lhuentse","Mongar","Paro","Pemagatshel","Punakha","Samdrupjongkhar","Samtse","Sarpang","Thimphu","Trashigang","Trashiyangtse","Trongsa","Tsirang","Wangdue Phodrang","Zhemgang"};
			
			for(int i=0;i<dzoArray.length;i++){
				if(replaceReverseString.contains(dzoArray[i])){
					dzongkhag = dzoArray[i];
				}
			}
			
			int rsc=0, upperCount=0;
			int dzon =  replaceReverseString.indexOf(dzongkhag);
			String upperChars = replaceReverseString.substring(dzon+dzongkhag.length(),replaceReverseString.length());
			String upperChars1 = replaceReverseString.substring(dzon+dzongkhag.length(),replaceReverseString.length());
			String hashContainedText = null;
			for(rsc=0; rsc<upperChars.length(); rsc++){
				
				if(upperChars.contains("#")){
					hashContainedText = upperChars.substring(0,upperChars.indexOf("#"));
					for(int rsct=0; rsct<hashContainedText.length(); rsct++){
						boolean countUpper = Character.isUpperCase(hashContainedText.charAt(rsct));
						if(countUpper){
							upperCount ++;
						}
						
					}
				}
				else{
					if(upperChars.substring(0, rsc).contains("#")){
						continue;
					}
					else{
						upperCount=2;
					}
				}
				
				if(upperCount==1){
					upperChars = upperChars.substring(hashContainedText.length()+1, upperChars.length());
					upperCount --;
				}
				if(upperCount==2){
					break;
				}
				
			}
			
		    String name=null, name1 = null;
			if(upperCount==2){
				
				for(int i=1; i<upperChars.length(); i++){
					boolean countUpper = Character.isUpperCase(upperChars.charAt(i+1));
					if(countUpper){
						name = upperChars.substring(0,i+1);
						name1 = upperChars.substring(i+1,upperChars.length()-1);
						break;
					}
				}
				
			}
			
			fatherMiddleName = upperChars1.substring(0, upperChars1.indexOf(name)) + ""+name;
			fatherFirstName = fatherMiddleName.replaceAll("#", " ");
			motherMiddleName = upperChars.substring(upperChars.indexOf(name1));
			motherFirstName = motherMiddleName.replaceAll("#", " ");
			
			//gewog
			String comThramVillGewog = replaceReverseString.substring(0, replaceReverseString.indexOf(dzongkhag));
			String afterSlash=null,beforeSlash=null;
			for(int hs =comThramVillGewog.length(); hs>0; hs--){
				boolean isUpper = Character.isUpperCase(comThramVillGewog.charAt(hs-1));
				if(isUpper){
					String checkSlash = comThramVillGewog.substring(hs-2, comThramVillGewog.length());
					boolean hasSlash = checkSlash.contains("/");
					if(hasSlash){
						afterSlash = comThramVillGewog.substring(comThramVillGewog.indexOf("/"), comThramVillGewog.length());
						beforeSlash = comThramVillGewog.substring(0, comThramVillGewog.indexOf("/"));
						for(int abc=beforeSlash.length();abc>0;abc--){
							
							boolean checkIsUpper = Character.isUpperCase(beforeSlash.charAt(abc-1));
							if(checkIsUpper){
								beforeSlash = beforeSlash.substring(abc-1, comThramVillGewog.indexOf("/"));
								break;
							}
						}
						gewog = beforeSlash + afterSlash;
						break;
					}
					else{
						for(int abc=comThramVillGewog.length();abc>0;abc--){
							boolean checkIsUpper = Character.isUpperCase(comThramVillGewog.charAt(abc-1));
							if(checkIsUpper){
								gewog = comThramVillGewog.substring(abc-1, comThramVillGewog.length());
								break;
							}
						}
						break;
					}
				}
			}
			
			String comBracket = replaceReverseString.substring(0, replaceReverseString.indexOf("("));
			String beforeBracket=null, withinBracket=null;
			for(int abc=comBracket.length();abc>0;abc--){
				boolean isUpper = Character.isUpperCase(comBracket.charAt(abc-1));
				if(isUpper){
					beforeBracket = comThramVillGewog.substring(abc-1, comBracket.length());
					break;
				}
				withinBracket = replaceReverseString.substring(replaceReverseString.indexOf("("), replaceReverseString.indexOf(")")+1);
				
			}
			village = beforeBracket + withinBracket;
			thramNo = replaceReverseString.substring(0, replaceReverseString.indexOf(village));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date d = sdf.parse(dob);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			LocalDate l1 = LocalDate.of(year, month, date);
			LocalDate now1 = LocalDate.now();
			Period diff1 = Period.between(l1, now1);
			age = Integer.toString(diff1.getYears());
		}
		
		buffer.append("<xml-response>");
			buffer.append("<cid>"+cidNumber+"</cid>");
			buffer.append("<name>"+firstname+"</name>");
			buffer.append("<gender>"+gender+"</gender>");
			buffer.append("<age>"+age+"</age>");
		buffer.append("</xml-response>");
		
		out.println(buffer);
		out.flush();
	}
}
