package com.pjh.bookmark;

import com.pjh.bookmark.component.TokenEncoding;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookmarkApplicationTests {

	@Test
	void contextLoads() {
		TokenEncoding tokenEncoding = new TokenEncoding();
		String pass = "park";
		String passEncrypt = tokenEncoding.encrypt(pass);
		System.out.println(passEncrypt);
		System.out.println(tokenEncoding.decrypt(passEncrypt));

		TokenEncoding tokenEncoding1 = new TokenEncoding();
		String passEncrypt1 = tokenEncoding1.encrypt(pass);
		System.out.println(passEncrypt1);
		System.out.println(tokenEncoding1.decrypt(passEncrypt1));
	}

}
