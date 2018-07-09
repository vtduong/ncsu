package edu.ncsu.csc.itrust.beans;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrthopedicOfficeVisitBean  {
	
	/**The mid of the user.*/
	private long mid;

	/**The date of the Ophthalmology office visit.*/
	private String visitDate;
	
	private String injuredLimb;
	
	private DiagnosisBean diagnosis;
	
	private String mriReport;
	
	private BufferedImage MRI;
	
	private BufferedImage XRAY;
	
	/**
	 * For use ONLY by DAOs
	 * setters and getters method
	 * @param visitID
	 */
	public OrthopedicOfficeVisitBean(String  visitDate, String injuredLimb) {
		this.visitDate = visitDate;
		this.injuredLimb = injuredLimb;
	}

	
	/**
	 * @return the mid
	 */
	public long getMid() {
		return mid;
	}


	/**
	 * @param mid the mid to set
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}


	/**
	 * Getter for the visitDate as a Date object.
	 * @return the visit date.
	 */
	public Date getVisitDate(){
		if (visitDate == null)
			return null;
		Date date = null; 
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(visitDate);
		} catch (ParseException e) {
			//If it can't be parsed, return null.
			return null;
		}
		return date;
	}


	/**
	 * @param visitDate the visitDate to set
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}


	/**
	 * @return the injuredLimb
	 */
	public String getInjuredLimb() {
		return injuredLimb;
	}


	/**
	 * @param injuredLimb the injuredLimb to set
	 */
	public void setInjuredLimb(String injuredLimb) {
		this.injuredLimb = injuredLimb;
	}


	/**
	 * @return the diagnosis
	 */
	public DiagnosisBean getDiagnosis() {
		return diagnosis;
	}


	/**
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(DiagnosisBean diagnosis) {
		this.diagnosis = diagnosis;
	}


	/**
	 * @return the mriReport
	 */
	public String getMriReport() {
		return mriReport;
	}


	/**
	 * @param mriReport the mriReport to set
	 */
	public void setMriReport(String mriReport) {
		this.mriReport = mriReport;
	}


	/**
	 * @return the mRI
	 */
	public BufferedImage getMRI() {
		return MRI;
	}


	/**
	 * @param mRI the mRI to set
	 */
	public void setMRI(BufferedImage mRI) {
		MRI = mRI;
	}


	/**
	 * @return the xRAY
	 */
	public BufferedImage getXRAY() {
		return XRAY;
	}


	/**
	 * @param xRAY the xRAY to set
	 */
	public void setXRAY(BufferedImage xRAY) {
		XRAY = xRAY;
	}

}
