package Server.DatabaseModules;

import Server.DatabaseFiles.*;
import Server.DatabaseFiles.Responses.DatabaseResponseStatus;
import Server.DatabaseFiles.Responses.IDatabaseResponse;
import Server.DatabaseFiles.Responses.TicketResponse;
import Server.DatabaseFiles.Responses.TicketStatusResponse;
import SupportFiles.TicketStatus;
import Server.DatabaseFiles.Requests.GetTicketStatusRequest;
import Server.DatabaseFiles.Requests.IDatabaseRequest;

public class TicketStatusCheckingModule implements IDatabaseModule {
	public IDatabaseResponse execute(IDatabaseRequest request) {
		Database db = Database.getInstance();
		GetTicketStatusRequest formalRequest = (GetTicketStatusRequest) request;
		if (formalRequest.getTicket() == null)
			return new TicketStatusResponse(DatabaseResponseStatus.FAILURE, null);

		TicketTableRequest readTicketRequest = new TicketTableRequest(formalRequest.getTicket().getTicketID());
		TicketResponse ticketResponse = (TicketResponse) db.read(readTicketRequest);
		if(ticketResponse.getStatus() == DatabaseResponseStatus.FAILURE){
			return new TicketStatusResponse(DatabaseResponseStatus.FAILURE, TicketStatus.UTILIZED);
		}
		return new TicketStatusResponse(DatabaseResponseStatus.SUCCESS, ticketResponse.getTicketStatus());
	}
}