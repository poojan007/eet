package bt.gov.moh.eet.util;

import bt.gov.moh.eet.dao.GuestDao;
import bt.gov.moh.eet.dto.*;

public class FlagGuestHelper {
	private GuestDao guestDao;

	public GuestLogDTO run(GuestLogDTO guestLogDTO) throws Exception {
		guestLogDTO = determineEntryOrExit(guestLogDTO);
		guestLogDTO = determineAlertFlag(guestLogDTO);
		return guestLogDTO;
	}

	private GuestLogDTO determineEntryOrExit(GuestLogDTO guestLogDTO) throws Exception {
		guestDao = new GuestDao();
		GuestDTO guestDTO = guestDao.getInstance().fetchGuestDetail(guestLogDTO.getGuest_id());
		GuestLogDTO lastGuestLogDTO = guestDao.getInstance().fetchLastGuestLogDetails(guestLogDTO.getGuest_id());

		if (guestDTO == null && lastGuestLogDTO == null && guestLogDTO.getEntry_or_exit() == null) {
			throw new Exception("For the guest registering for the first time should set exit or entry manually");
		} else {
			if (guestDTO != null && guestDTO.getResiding_across_border().equals('Y')) {
				guestLogDTO.setEntry_or_exit(lastGuestLogDTO.getEntry_or_exit().equals("ENTRY") ? "EXIT" : "ENTRY");
			}
		}
		return guestLogDTO;
	}

	private GuestLogDTO determineAlertFlag(GuestLogDTO guestLogDTO) throws Exception {
		if (guestLogDTO.getTemperature() == null) {
			throw new Exception("For the guest registering for the first time should set exit or entry manually");
		} else if (guestLogDTO.getTemperature() > 37) {
			guestLogDTO.setAlert_flag('Y');
		}
		return guestLogDTO;
	}
}
