package com.mvalls.sidged.utils;

import com.mvalls.sidged.model.ContactData;

public class ContactDataUtils {
	
	public static boolean mailsHaveChanged(ContactData original, ContactData newer) {
		return !original.getEmails().equals(newer.getEmails());
	}

}
