package jorge.rv.quizzz.userservice.service.utils;

import java.util.Date;

public interface DateHelper {
	Date getCurrentDate();

	Date getExpirationDate(Date from, int offset);
}
