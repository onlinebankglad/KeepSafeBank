package com.lti.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AdminDto;
import com.lti.dto.BeneficaryDto;
import com.lti.dto.CustomerLoginDto;
import com.lti.dto.DocDto;
import com.lti.dto.Status;
import com.lti.dto.TransactionDto;
import com.lti.dto.UpdatecustDto;
import com.lti.dto.Status.StatusType;
import com.lti.exception.CustomerServiceException;
import com.lti.model.Account;
import com.lti.model.Admin;
import com.lti.model.Beneficary;
import com.lti.model.Customer;
import com.lti.model.CustomerMailBox;
import com.lti.model.Transaction;
import com.lti.service.BankingService;

@RestController
@CrossOrigin
public class BankingController {

	@Autowired
	BankingService bankingservice;
	

	//@RequestMapping(path = .., method = POST, ...)
	@PostMapping(path = "/register")
	public Status register(@RequestBody Customer customer) {
		try {
			bankingservice.register(customer);
			
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Registration successful!");
			return status;
		}
		catch(CustomerServiceException e) {
			Status status = new Status();
			status.setStatus(StatusType.FAILURE);
			status.setMessage(e.getMessage());
			return status;
		}
	}
	
	@RequestMapping(value="/addorupdatecust", method= RequestMethod.POST)
	public Customer addorUpdateCustomer(@ModelAttribute DocDto docdto) {
		Customer cust=new Customer();
		//cust.setAadhar(DocDto.);
		cust.setCustAge(docdto.getCustAge());
		cust.setParentsName(docdto.getParentsName());
		cust.setCustName(docdto.getCustName());
		cust.setPermanentAddress(docdto.getPermanentAddress());
		cust.setResidentialAddress(docdto.getResidentialAddress());
		cust.setPanno(docdto.getPanno());
		cust.setAadharno(docdto.getAadharno());
		cust.setMobileNumber(docdto.getMobileNumber());
		cust.setEmailID(docdto.getEmailID());
		cust.setLoginPassword(docdto.getLoginPassword());
		cust.setState(docdto.getState());
		cust.setCity(docdto.getCity());
		cust.setPinCode(docdto.getPinCode());
		
		
		String imageUploadLocation = "c:/uploads/";
		String panCard = docdto.getPan().getOriginalFilename();
		String panfile =panCard;
		try {
			FileCopyUtils.copy(docdto.getPan().getInputStream(), new FileOutputStream(panfile));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
			String AadharCard = docdto.getAadhar().getOriginalFilename();
			String aadharfile =AadharCard;
			try {
				FileCopyUtils.copy(docdto.getAadhar().getInputStream(), new FileOutputStream(aadharfile));
			} catch (IOException e1) {
				e1.printStackTrace();
			
			}
//			Customer customer = bankingservice.get(docdto.getCustomerId());
			
			cust.setPan(panfile);
			cust.setAadhar(aadharfile); // updating profile pic

		
		
		Customer cust1=bankingservice.addorUpdateCustomer(cust);
		Status status = new Status();
		status.setStatus(StatusType.SUCCESS);
		status.setMessage("Registration successful!");
		
		
		
		return cust;
	}

	
	@RequestMapping(value="/addacct",method= RequestMethod.POST)
	public Account addAccount(@RequestBody Account account,@RequestParam("custid")int custid){
		account.setCustomer(findCustomerById(custid));
		return bankingservice.addAccount(account);
	}
	
	@RequestMapping(value="/updateacct",method= RequestMethod.POST)
	public Account UpdateAccount(@RequestBody Account account,@RequestParam("custid")int custid){
		return bankingservice.UpdateAccount(account,custid);
	}

	@GetMapping(value="/findcustbyid")
	public Customer findCustomerById(@RequestParam("custId") int custId) {
		// TODO Auto-generated method stub
		return bankingservice.findCustomerById(custId);
	}
	
	@GetMapping(value="/findCustomerIdbyAccNo")
	public Customer findCustomerIdbyAccNo(@RequestParam("accNo") int accNo) {
		// TODO Auto-generated method stub
		return bankingservice.findCustomerIdbyAccNo(accNo);
	}
	
	
	

	@RequestMapping(value="/findaccountbyno",method= RequestMethod.POST)
	public Account findAccountByNo(@RequestParam("accNo") int accNo){   //by acc no
		return bankingservice.findAccountByNo(accNo);
	}

	@RequestMapping(value="/viewallaccts",method= RequestMethod.POST)
	public List<Account>  viewAllAccounts(){
		
		return bankingservice.viewAllAccounts();
	}

	@RequestMapping(value="/AddBeneficary",method=RequestMethod.POST)
	public Beneficary addBenefiary(@RequestBody BeneficaryDto beneficarydto){
		return bankingservice.addBenefiary(beneficarydto);
	}
	@RequestMapping(value="/addTranscation",method=RequestMethod.POST)
	public Transaction makeTransaction(@RequestBody TransactionDto tranDto){
		System.out.println(tranDto.getTmodes());
		return bankingservice.addATransaction(tranDto.getToAccNO(),tranDto.getFromacc_no(),tranDto.getAmount(), tranDto.getTmodes());
	}
	
	
	@RequestMapping(value="/viewAllTransactionByDate",method=RequestMethod.POST)  //ASK SIR
	public List<Transaction> viewAllTransactionByDate(@RequestParam LocalDate date) {
		return bankingservice.viewAllTransactionByDate(date);
	}

	@RequestMapping(value = "/addanadmin", method = RequestMethod.POST) // done
	public void addAnAdmin(@RequestBody Admin admin) {
		// TODO Auto-generated method stub
		bankingservice.addAnAdmin(admin);
	}
	

	// DTO

	@PostMapping(value = "/admindto") 
	public boolean loginAdmin(@RequestBody AdminDto admin) {
		return bankingservice.loginAdmin(admin.getaId(), admin.getaPwd());
	}

	

	

	@PostMapping(value ="/approvedCustomerList")    
	public List<Customer> approvedCustomerList() {
		// TODO Auto-generated method stub
		return bankingservice.approvedCustomerList();
	}
	
	

	@PostMapping(value = "/logindto")
	public boolean loginCustomer(@RequestBody CustomerLoginDto loginDto)// done
	{
		// TODO Auto-generated method stub
		return bankingservice.loginCustomer(loginDto.getCustId(), loginDto.getLoginPassword());
	}
	
	
	@GetMapping(value="/findAccountsByCustId")
	Account findAccountsByCustId(@RequestParam("custId")int custId){
		Account  b=null;
        List<Account> acc=bankingservice.findAccountsByCustId(custId);
        for(Account a:acc) {
            b=a;
           
        }
        return b;
	}
	
	@PostMapping(value = "/viewAllBeneficaryofUser")    
	List<Beneficary> viewAllBeneficaryofUser(@RequestParam("custId")int i){
		return bankingservice.viewAllBeneficaryofUser(i);
		
	}
	
	@PostMapping(value = "/aprrovecustbyid")    //pending
	public Customer aprroveCustomerbyId(@RequestParam("custId") int custId) //pending
	{
		// TODO Auto-generated method stub
		
		return bankingservice.aprroveCustomerbyId(custId);
	}
	@RequestMapping(value = "/custmailbox", method = RequestMethod.POST)
    public void contactus(@RequestBody CustomerMailBox pcustomer) {
         bankingservice.contactus(pcustomer);
    }
	

	@RequestMapping(value="/viewAllCustomers",method= RequestMethod.POST)
    public List<Customer>  viewAllCustomers(HttpServletRequest request){
		
       
		List<Customer> clist=bankingservice.viewAllCustomers();
		String projPath = request.getServletContext().getRealPath("/");
		String tempDownloadPath = projPath + "/downloads/";
		//creating a folder within the project where we will place the profile pic of the customer getting fetched
		File f = new File(tempDownloadPath);
		if(!f.exists())
			f.mkdir();
		for(Customer cust: clist) {
			String aadhartargetFile = tempDownloadPath + cust.getAadhar();
			String pantargetFile = tempDownloadPath + cust.getPan();

			
			String uploadedImagesPath = "c:/uploads/";
			String aadharsourceFile = uploadedImagesPath + cust.getAadhar();
			String pansourceFile = uploadedImagesPath + cust.getPan();

			
			try {
				FileCopyUtils.copy(new File(aadharsourceFile), new File(aadhartargetFile));
				FileCopyUtils.copy(new File(pansourceFile), new File(pantargetFile));

			} catch (IOException e) {
				e.printStackTrace();
				//maybe for this customer there is no profile pic
			}
		}
		
		//the original location where the uploaded images are present
		
		return clist;
		
    }
	
	@PostMapping(value = "/unAprroveCustomerbyId")   
    public Customer unAprroveCustomerbyId(@RequestParam("custId") int custId)
    {
        // TODO Auto-generated method stub
        return bankingservice.unaprroveCustomerbyId(custId);
    }
	
	@RequestMapping(value = "/generateotp", method = RequestMethod.POST)
    public int generateotp(@RequestParam  int custId) {
         return bankingservice.generateotp(custId);
    }


	
	@PostMapping("/unapprovedCustomerList")
	public List<Customer> unapprovedCustomerList(HttpServletRequest request) {
		//fetching customer data from the database
		//fetching customer data from the database
				List<Customer> clist=bankingservice.unapprovedCustomerList();

				//reading the project's deployed folder location
				String projPath = request.getServletContext().getRealPath("/");
				String tempDownloadPath = projPath + "/downloads/";
				//creating a folder within the project where we will place the profile pic of the customer getting fetched
				File f = new File(tempDownloadPath);
				if(!f.exists())
					f.mkdir();
				for(Customer cust: clist) {
					String aadhartargetFile = tempDownloadPath + cust.getAadhar();
					String pantargetFile = tempDownloadPath + cust.getPan();

					
					String uploadedImagesPath = "c:/uploads/";
					String aadharsourceFile = uploadedImagesPath + cust.getAadhar();
					String pansourceFile = uploadedImagesPath + cust.getPan();

					
					try {
						FileCopyUtils.copy(new File(aadharsourceFile), new File(aadhartargetFile));
						FileCopyUtils.copy(new File(pansourceFile), new File(pantargetFile));

					} catch (IOException e) {
						e.printStackTrace();
						//maybe for this customer there is no profile pic
					}
				}
				
				//the original location where the uploaded images are present
				
				return clist;
	}
	

    @RequestMapping(value = "/generateotpforId", method = RequestMethod.POST)// generate otp for forget password
    public int generateotpforforgetId(@RequestParam ("accNo") int accNO) {
         return bankingservice.generateotpforforgetId(accNO);
    }
   
    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    public void resetPassword(@RequestParam ("custId")int custId,@RequestParam ("loginPassword")  String password) {
         bankingservice.resetPassword(custId, password);
    }
    
    @RequestMapping(value = "/findBenAccFromBenId", method = RequestMethod.GET)
    public int findBenAccFromBenId(@RequestParam ("benId") int benId) {
		// TODO Auto-generated method stub
		return bankingservice.findBenAccFromBenId(benId);
	}
    
    @RequestMapping(value = "/viewAllTransactionOfUser", method = RequestMethod.GET)
    public List<Transaction> viewAllTransactionOfUser(@RequestParam("custID")int custID){
    	return bankingservice.viewAllTransactionOfUser(custID);
    }

    
    @RequestMapping(value = "/returnaccpwdfromcustId", method = RequestMethod.GET)// generate otp for forget password
    public int returnaccpwdfromcustId(@RequestParam int custId){
    	return bankingservice.returnaccpwdfromcustId(custId);
    }
    
    @RequestMapping(value = "/resetAccPassword", method = RequestMethod.POST)
    public void resetAccPassword(@RequestParam("custId") int custId, @RequestParam("accPwd")int accPwd){
    	 bankingservice.resetAccPassword(custId, accPwd);
    }
    @RequestMapping(value = "/resetAdminPassword", method = RequestMethod.POST)
    public void resetAdminPassword(@RequestBody AdminDto admindto  ) {
        bankingservice.resetAdminPassword(admindto.getaId(),admindto.getaPwd());
    
    }
    
    @RequestMapping(value="/updatecust", method= RequestMethod.POST)
    public Customer UpdateCustomer(@RequestBody UpdatecustDto dto) {
        System.out.println(dto.getCustId());
        Customer cust=findCustomerById(dto.getCustId());
       
        System.out.println(dto.getCustId());
        //cust.setAadhar(DocDto.);
       
        cust.setCustAge(dto.getCustAge());
        cust.setParentsName(dto.getParentsName());
        cust.setCustName(dto.getCustName());
        cust.setPermanentAddress(dto.getPermanentAddress());
        cust.setResidentialAddress(dto.getResidentialAddress());
        cust.setPanno(dto.getPanno());
        cust.setAadharno(dto.getAadharno());
        cust.setMobileNumber(dto.getMobileNumber());
        cust.setEmailID(dto.getEmailID());
        cust.setLoginPassword(dto.getLoginPassword());
        cust.setState(dto.getState());
        cust.setCity(dto.getCity());
        cust.setPinCode(dto.getPinCode());
           
        return bankingservice.updatecustomer(cust);   
       
    }
}
