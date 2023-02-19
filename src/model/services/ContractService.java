package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, Integer months) {
		
		double valueMonth = contract.getTotalValue() / months;
				
		for(int i=1; i <= months; i++) {
			double interest = onlinePaymentService.interest(valueMonth, i); 
			double fee = onlinePaymentService.paymentFee(valueMonth + interest);
			double quota = valueMonth + interest + fee;
			
			LocalDate dueDate = contract.getDate().plusMonths(i);
			
			contract.getInstallments().add(new Installment(dueDate, quota));
			
		}
	}
}	
