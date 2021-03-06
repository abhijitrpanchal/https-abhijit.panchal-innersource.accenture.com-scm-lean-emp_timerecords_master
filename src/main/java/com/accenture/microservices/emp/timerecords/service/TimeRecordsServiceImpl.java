package com.accenture.microservices.emp.timerecords.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.accenture.microservices.emp.timerecords.client.AssignmentsService;
import com.accenture.microservices.emp.timerecords.client.ChargeCodeService;
import com.accenture.microservices.emp.timerecords.client.EmployeeDetailsService;
import com.accenture.microservices.emp.timerecords.client.vo.ChargeCode;
import com.accenture.microservices.emp.timerecords.client.vo.EmployeeAssignments;
import com.accenture.microservices.emp.timerecords.client.vo.EmployeeDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class TimeRecordsServiceImpl implements TimeRecordsService {
	
	public static final Logger log = LoggerFactory.getLogger(TimeRecordsServiceImpl.class);
	
	@Autowired
	private ChargeCodeService chargeCodeService;
	
	@Autowired
	private AssignmentsService assignmentsService;
	
	@Autowired
	private EmployeeDetailsService employeeDetailsService;

	@Override
	@HystrixCommand(fallbackMethod="handleGetChargeCodeDetails")
	public ChargeCode getChargeCodeDetails(String wbs) {
	
		ChargeCode chargeCode=chargeCodeService.getChargeCodeDetails(wbs);
		
		return	chargeCode;
	}
	
	/*
	 *  hystrix circuitbreaker fallbackMethod for getChargeCodeDetails
	 */
	
	public ChargeCode handleGetChargeCodeDetails(String wbs,Throwable t) {
		
		log.info("fallback method  handleGetChargeCodeDetails called,the error thrown is: "+getErrorStackTrace(t));
		
		ChargeCode chargeCode=new ChargeCode();
		
		return	chargeCode;
	}

	@Override
	@HystrixCommand(fallbackMethod="handleGetChargeCodeDetailsOfAnEmployee")
	public ChargeCode getChargeCodeDetailsOfAnEmployee(String wbs, Integer empid) {
		
		
		ChargeCode chargeCode=chargeCodeService.getChargeCodeDetailsOfAnEmployee(wbs, empid);
		
		return	chargeCode;
	}
	
	/*
	 *  hystrix circuitbreaker fallbackMethod for getChargeCodeDetailsOfAnEmployee
	 */
	public ChargeCode handleGetChargeCodeDetailsOfAnEmployee(String wbs, Integer empid,Throwable t) {
		
		log.info("fallback method  handleGetChargeCodeDetailsOfAnEmployee called,the error thrown is: "+getErrorStackTrace(t));
		
		ChargeCode chargeCode=chargeCodeService.getChargeCodeDetailsOfAnEmployee(wbs, empid);
		
		return	chargeCode;
	}
	
	@Override
	@HystrixCommand(fallbackMethod="handleGetEmployeeAssignment")
	public EmployeeAssignments getEmployeeAssignment(Integer id){
		
		EmployeeAssignments employeeAssignments=assignmentsService.getEmployeeAssignment(id);
		
		return employeeAssignments;
		
	}
	
	/*
	 *  hystrix circuitbreaker fallbackMethod for handleGetEmployeeAssignment
	 */
	
	public EmployeeAssignments handleGetEmployeeAssignment(Integer id,Throwable t){
		
		log.info("fallback method  handleGetEmployeeAssignment called,the error thrown is: "+getErrorStackTrace(t));
		
		EmployeeAssignments employeeAssignments=new EmployeeAssignments();
		
		return employeeAssignments;
		
	}
	

	public String getErrorStackTrace(Throwable t){
		
		if(t!=null){
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			return sw.toString();
		}else {
			return null;
		}
		
	}
	
	@Override
	@HystrixCommand(fallbackMethod="handleGetEmployeeDetails")
	public EmployeeDetails getEmployeeDetails(long id){
		
		EmployeeDetails employeeDetails=employeeDetailsService.getEmployeeDetails(id);
		
		return employeeDetails;
		
	}
	
	/*
	 *  hystrix circuitbreaker fallbackMethod for getEmployeeDetails
	 */
	
	public EmployeeDetails handleGetEmployeeDetails(long id,Throwable t){
		
		log.info("fallback method  handleGetEmployeeDetails called,the error thrown is: "+getErrorStackTrace(t));
		
		EmployeeDetails employeeDetails=new EmployeeDetails();
		
		return employeeDetails;
		
	}
	
	
	@Override
	@HystrixCommand(fallbackMethod="handleGetAllEmployees")
	public List<EmployeeDetails> getAllEmployees(){
		
		 List<EmployeeDetails> employeeDetailsList =employeeDetailsService.getAllEmployees();
		
		return employeeDetailsList;
		
	}
	
	/*
	 *  hystrix circuitbreaker fallbackMethod for getEmployeeDetails
	 */
	
	public List<EmployeeDetails> handleGetAllEmployees(Throwable t){
		
		log.info("fallback method  handleGetAllEmployees called,the error thrown is: "+getErrorStackTrace(t));
		
		EmployeeDetails employeeDetails=new EmployeeDetails();
		
		return Arrays.asList(employeeDetails);
		
	}

}
