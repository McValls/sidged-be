package com.mvalls.sidged.test.email;

import com.mvalls.sidged.model.emails.Email;
import com.mvalls.sidged.services.EmailsService;

/**
 * 
 * @author Marcelo Valls
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
public class MailTest {
	
	public static void main(String[] args) {
		EmailsService emailsService = new EmailsService();
		emailsService.sendEmail(Email.builder()
				.to("cmarcelovalls@gmail.com")
				.message("Holis, prueba")
				.subject("Prueba SUBJECT")
				.build());
	}

}
